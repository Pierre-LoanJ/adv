package adverity

import adverity.model.Dimension
import adverity.model.Metric
import adverity.model.TimeDimension
import groovy.util.logging.Slf4j

import java.math.RoundingMode

@Slf4j
class MarketingMetricService {

    MarketingMetricDataService marketingMetricDataService

    private static final int DECIMAL_DIGITS = 5

    // Clicks
    static int getTotalOf(Metric metric, List<MarketingMetric> metrics) {
        int totalClicks = metrics.inject(0){ total, data ->
            metric == Metric.clicks ? (data.clicks + total): (data.impressions + total)
        }
        totalClicks
    }

    List<MarketingMetric> getMarketingMetricsForSourceAndDates(String source, Date from, Date to) {
        MarketingMetric.createCriteria().list {
            eq "${Dimension.source}", source
            lte "${TimeDimension.date.name()}", to
            gte "${TimeDimension.date.name()}", from
        }
    }

    List<MarketingMetric> getMarketingMetricsFor(Dimension dimension, String value) {
        MarketingMetric.createCriteria().list {
            eq "${dimension.name()}", value
        }
    }

    // Rates
    BigDecimal getClickThroughRateFor(Dimension dimension, String value) {
        List<MarketingMetric> filteredSources = getMarketingMetricsFor(dimension, value)
        int totalClicksForCampaign = getTotalOf(Metric.clicks, filteredSources)
        int totalImpressionsForCampaign = getTotalOf(Metric.impressions, filteredSources)
        computeRate(totalClicksForCampaign, totalImpressionsForCampaign)
    }

    // Impressions
    int getImpressions(Date date) {
        List<MarketingMetric> marketingMetrics = marketingMetricDataService.findAllByDate(date)
        int totalImpressions = marketingMetrics.inject(0){ acc, data ->
            data.impressions + acc
        }
        totalImpressions
    }

    private static BigDecimal computeRate(int a, int b) {
        BigDecimal rate
        try {
            rate = a / b
        } catch (ArithmeticException e) {
            log.error("Division by zero. Invalid number: ${b}")
            throw new MarketingMetricException("Division by zero.")
        }
        rate.setScale(DECIMAL_DIGITS, RoundingMode.DOWN)
    }
}

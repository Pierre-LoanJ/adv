package adverity

import adverity.model.Dimension
import adverity.model.TimeDimension
import adverity.model.Metric
import adverity.utils.DateUtils
import adverity.utils.DimensionUtils
import adverity.utils.HttpUtils
import grails.converters.JSON
import groovy.util.logging.Slf4j

@Slf4j
class ClickController {

    MarketingMetricDataService marketingMetricDataService
    MarketingMetricService marketingMetricService

    def allClicks() {
        List<MarketingMetric> marketingMetrics = marketingMetricDataService.findAll()
        int totalClicks = marketingMetricService.getTotalOf(Metric.clicks, marketingMetrics)
        render new JSON(['total_all_clicks': "${totalClicks}"])
    }

    def clicksForSource(String sourceParam) {
        if (!DimensionUtils.hasValidSource(sourceParam)) {
            render new JSON(status: HttpUtils.STATUS_CODE_BAD_REQUEST, message:
                    HttpUtils.buildMessage(HttpUtils.STATUS_CODE_BAD_REQUEST, Dimension.source.name()))
        }
        String source = DimensionUtils.getParamSource(sourceParam)
        List<MarketingMetric> marketingMetrics = marketingMetricService.getMarketingMetricsFor(Dimension.source, source)
        int totalClicks = marketingMetricService.getTotalOf(Metric.clicks, marketingMetrics)
        render new JSON([total_clicks_for_source: "${totalClicks}", source: "${source}"])
    }

    def clicksForSourceAndDates(String sourceParam, String from, String to) {
        if (!DimensionUtils.hasValidSource(sourceParam)) {
            render new JSON(status: HttpUtils.STATUS_CODE_BAD_REQUEST, message: HttpUtils.buildMessage(HttpUtils.STATUS_CODE_BAD_REQUEST, Dimension.source.name()))
        }
        String source = DimensionUtils.getParamSource(sourceParam)
        if (!DateUtils.hasValidDateFormat(from) || !DateUtils.hasValidDateFormat(to)) {
            render new JSON(status: HttpUtils.STATUS_CODE_BAD_REQUEST, message: HttpUtils.buildMessage(HttpUtils.STATUS_CODE_BAD_REQUEST, TimeDimension.date.name()))
        }
        Date toDate = DateUtils.dateBuilder(to)
        Date fromDate = DateUtils.dateBuilder(from)
        if (fromDate > toDate) {
            render new JSON(status: HttpUtils.STATUS_CODE_BAD_REQUEST, message: HttpUtils.buildMessage(HttpUtils.STATUS_CODE_BAD_REQUEST, TimeDimension.date.name()))
        }

        List<MarketingMetric> marketingMetrics = marketingMetricService.getMarketingMetricsForSourceAndDates(source, fromDate, toDate)
        int totalClicks = marketingMetricService.getTotalOf(Metric.clicks, marketingMetrics)

        render new JSON([total_clicks_for_source_and_dates: "${totalClicks}", source: "${source}"])
    }
}

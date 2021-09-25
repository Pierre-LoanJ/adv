package adverity

import groovy.util.logging.Slf4j
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

import java.time.Instant
import java.time.temporal.ChronoUnit
import java.time.temporal.Temporal

@Slf4j
class BootStrap {

    private final String DATE_PATTERN = 'MM/dd/yy'
    private final String FILE_PATH = "grails-app/assets/data/data.csv"
    private final int LINES_LIMIT = 30000 // Useful for dev mode
    private final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern(DATE_PATTERN)
    private final int SAVE_BATCH_SIZE = 100

    def init = { servletContext ->
        boolean isFirstLine = true
        int counter = 0
        Date tick = new Date()
        List<MarketingMetric> marketingMetricsToSAve = []
        new File("${FILE_PATH}").splitEachLine(",") { line ->
            if (!isFirstLine/* && counter <= LINES_LIMIT*/) { // Just to show where it's used.
                MarketingMetric marketingMetric
                try {
                    marketingMetric = new MarketingMetric(
                            source: line[0],
                            campaign: line[1],
                            date: FORMATTER.parseDateTime(line[2]).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toDate(),
                            clicks: line[3] as int,
                            impressions: line[4] as int)
                    marketingMetricsToSAve << marketingMetric
                } catch (Exception e) {
                    log.error("Could not parse line, line number: ${counter + 1}. Line is escaped.")
                }
                if (marketingMetricsToSAve.size() >= SAVE_BATCH_SIZE) {
                    def result = MarketingMetric.saveAll(marketingMetricsToSAve)
                    log.info("Saved operation: ${result.size()}")
                    marketingMetricsToSAve = []
                }
                counter++
            }
            isFirstLine = false
        }
        Date tock = new Date()
        BigDecimal duration = (tock.getTime() - tick.getTime()) / 1000
        log.info("Sucessfully started in ${duration} s")
    }
    def destroy = {
    }
}

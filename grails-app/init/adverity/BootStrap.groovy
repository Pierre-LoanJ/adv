package adverity

import grails.plugin.awssdk.s3.AmazonS3Service
import groovy.util.logging.Slf4j
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter


@Slf4j
class BootStrap {

    AmazonS3Service amazonS3Service

    private final String DATE_PATTERN = 'MM/dd/yy'
    private final String FILE = "data.csv"
    private final String FILE_PATH = "data"
    private final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern(DATE_PATTERN)
    private final int SAVE_BATCH_SIZE = 100

    def init = { servletContext ->
        boolean isFirstLine = true
        int counter = 0
        Date tick = new Date()
        List<MarketingMetric> marketingMetricsToSAve = []
        File file
        try {
            file = amazonS3Service.getFile(FILE, FILE_PATH)
            file.splitEachLine(",") { line ->
                if (!isFirstLine) {
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
                        log.debug("Save operations: ${result.size()}")
                        marketingMetricsToSAve = []
                    }
                    counter++
                }
                isFirstLine = false
            }
            Date tock = new Date()
            BigDecimal duration = (tock.getTime() - tick.getTime()) / 1000
            log.info("Sucessfully started in ${duration} s")
        } catch (Exception e) {
            log.error("Failed getting or parsing file. ${e.printStackTrace()}")
        }
    }
}

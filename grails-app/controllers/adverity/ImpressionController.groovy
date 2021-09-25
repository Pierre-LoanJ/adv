package adverity

import adverity.model.TimeDimension
import adverity.utils.DateUtils
import adverity.utils.HttpUtils
import grails.converters.JSON
import groovy.util.logging.Slf4j

@Slf4j
class ImpressionController {

    MarketingMetricService marketingMetricService

    def impressionsForDate(String dateParam) {
        if (!DateUtils.hasValidDateFormat(dateParam)) {
            render new JSON(status: HttpUtils.STATUS_CODE_BAD_REQUEST, message: HttpUtils.buildMessage(HttpUtils.STATUS_CODE_BAD_REQUEST, TimeDimension.date.name()))
        }
        Date date = DateUtils.dateBuilder(dateParam)
        int totalImpressions = marketingMetricService.getImpressions(date)
        render new JSON(['total_impressions': "${totalImpressions}", date: "${dateParam}"])
    }
}

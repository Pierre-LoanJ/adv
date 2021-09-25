package adverity

import adverity.model.Dimension
import adverity.utils.DimensionUtils
import adverity.utils.HttpUtils
import grails.converters.JSON
import groovy.util.logging.Slf4j

@Slf4j
class RateController {

    MarketingMetricService marketingMetricService

    def rateForCampaign(String campaignParam) {
        String campaign = campaignParam.replaceAll('%20', ' ')
        BigDecimal rate = 0
        try {
            rate = marketingMetricService.getClickThroughRateFor(Dimension.campaign, campaign)
        } catch (MarketingMetricException ignored) {
            render new JSON(status: HttpUtils.STATUS_CODE_SERVER_ERROR,
                    message: HttpUtils.buildMessage(
                            HttpUtils.STATUS_CODE_SERVER_ERROR,
                            campaign,
                            HttpUtils.SERVER_ERROR_ARITHMETIC_CAUSE
            ))
        }
        render new JSON(['ctr_for_campaign': "${rate}", campaign: "${campaign}"])
    }

    def rateForSource(String sourceParam) {
        if (!DimensionUtils.hasValidSource(sourceParam)) {
            render new JSON(status: HttpUtils.STATUS_CODE_BAD_REQUEST, message: HttpUtils.BAD_REQUEST_TITLE + "${Dimension.source.name()}")
        }

        String source = DimensionUtils.getParamSource(sourceParam)
        BigDecimal rate = 0
        try {
            rate = marketingMetricService.getClickThroughRateFor(Dimension.source, source)
        } catch (MarketingMetricException ignored) {
            render new JSON(status: HttpUtils.STATUS_CODE_SERVER_ERROR,
                    message: HttpUtils.buildMessage(
                            HttpUtils.STATUS_CODE_SERVER_ERROR,
                            source,
                            HttpUtils.SERVER_ERROR_ARITHMETIC_CAUSE
                    ))
        }
        render new JSON(['ctr_for_source': "${rate}", source: "${source}"])
    }
}

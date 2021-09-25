package adverity

class UrlMappings {

    static mappings = {
        // Clicks API
        "/clicks"(controller: "click", action: "allClicks")
        "/clicks/$sourceParam"(controller: "click", action: "clicksForSource")
        "/clicks/$sourceParam/$from/$to"(controller: "click", action: "clicksForSourceAndDates")

        // Rates API
        "/rate/source/$sourceParam"(controller: "rate", action: "rateForSource")
        "/rate/campaign/$campaignParam"(controller: "rate", action: "rateForCampaign")

        // Impressions API
        "/impressions/$dateParam"(controller: "impression", action: "impressionsForDate")

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}

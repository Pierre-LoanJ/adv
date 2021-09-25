package adverity

import grails.gorm.services.Service

@Service(MarketingMetric)
interface MarketingMetricDataService {

    MarketingMetric get(Serializable id)
    List<MarketingMetric> findAll()
    List<MarketingMetric> findAllByDate(Date date)
    void delete(Serializable id)
    MarketingMetric save(MarketingMetric marketingMetric)
}

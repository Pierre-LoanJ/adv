# Adverity

## Architecture

This project is a web application built with Grails 3, backed by a H2 database.
At Bootstrap, the data are parsed and stored in memory.
The API's are available for querying the data.

## API documentation

There are 3 groups of API: Clicks, Rate & Impressions.
All the API return result in a key-value pair format.

* Click API

 `GET /clicks`
    Returns the sum of the click metric of all the data.


 `GET /clicks/SOURCE_PARAMETER`

  `SOURCE_PARAMETER` must be one of the following values: `facebook | google | twitter`.
  Returns the sum of the 'clicks' metric grouped by the given `SOURCE_PARAMETER`.



 `GET /clicks/SOURCE_PARAMETER/DATE_BEGIN_PARAMETER/DATE_END_PARAMETER`

    `DATE_BEGIN_PARAMETER` and `DATE_END_PARAMETER` must be a date using the format `MM-dd-yy`.
    Returns the sum of the click metric grouped by the given SOURCE_PARAMETER filtered on the given dates.



* Rate API

The CTR is defined as: the number of clicks divided by the number of impressions.

 `GET /rate/source/SOURCE_PARAMETER`

    Returns the computed CTR grouped by the given `SOURCE_PARAMETER`.


 `GET /rate/campaign/CAMPAIGN_PARAMETER`

    Returns the computed CTR grouped by the given `CAMPAIGN_PARAMETER`.



* Impression API

 `GET /impressions/DATE_PARAMETER`

    `DATE_PARAMETER` must be a date using the format `MM-dd-yy`.
    Returns the sum of the 'impressions' metric for the given date.

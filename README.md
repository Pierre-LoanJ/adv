# Adverity

## Architecture

This project is a web application built with Grails 3, backed by a H2 database.
It's convenient because there is no extra cost of configuring the infrastructure database.
At startup, the bootstrap is calling the S3 bucket where the data.csv is stored.
The data are then parsed and stored in memory.
I first simply parsed the file from the file system where the file ws dropped initially.
I encountered the main issue here because generating the war file and deploying it to AWS ended up in being unable to reach the file during bootstrap.
So I decided to go for a more elaborated solution (and elegant by the way) with S3.
I used org.grails.plugins:aws-sdk-s3 why is the only extra dependency. (+ joda-time:joda-time)
Eventually, the API's are available for querying.

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

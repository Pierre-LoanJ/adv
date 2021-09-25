package adverity.utils

import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

class DimensionUtils {

    static final ALLOWED_SOURCES = ['facebook', 'google', 'twitter']

    static boolean hasValidSource(String source) {
        if (ALLOWED_SOURCES.indexOf(source) < 0) {
            return false
        }
        true
    }

    static String getParamSource(String source) {
        switch (source) {
            case 'facebook':
                return 'Facebook Ads'
                break
            case 'google':
                return 'Google Ads'
                break
            case 'twitter':
                return 'Twitter Ads'
                break
            default:
                return ''
        }
    }

}

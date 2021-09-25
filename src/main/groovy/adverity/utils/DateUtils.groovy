package adverity.utils

import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

class DateUtils {

    static final String DATE_PATTERN = 'MM-dd-yy'
    static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern(DATE_PATTERN)

    static boolean hasValidDateFormat(String date) {
        if (!date) {
            return false
        }
        if (date.split('-').size() != 3) {
            //'MM-dd-yy' split on '-' gives ['MM', 'dd', 'yy']
            return false
        }
        try {
            FORMATTER.parseDateTime(date)
        } catch (IllegalArgumentException ignored) {
            return false
        }
        true
    }

    static Date dateBuilder(String dateAsString) {
        FORMATTER.parseDateTime(dateAsString)
                .withHourOfDay(0)
                .withMinuteOfHour(0)
                .withSecondOfMinute(0)
                .toDate()
    }

}

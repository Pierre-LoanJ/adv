package adverity.utils

class HttpUtils {

    static final String BAD_REQUEST_TITLE = "Bad request: "
    static final String BAD_REQUEST_MESSAGE_DESCRIPTION = " invalid "
    static final String SERVER_ERROR_TITLE = "Server Error: "
    static final String ARITHMETIC_ERROR_MESSAGE_DESCRIPTION = "Division by zero is mathematically impossible." +
            " Possible cause: query didn't match any result for parameter: "
    static final String SERVER_ERROR_ARITHMETIC_CAUSE = "Arithmetic"
    static final String SERVER_ERROR_UNKNOWN_CAUSE = "Unknown"
    static final String SERVER_ERROR_UNKNOWN_DESCRIPTION = "No information available"

    static final int STATUS_CODE_BAD_REQUEST = 400
    static final int STATUS_CODE_SERVER_ERROR = 500
    static final Map<Integer, Object> CODE_TO_MESSAGE = [
            400: [title: BAD_REQUEST_TITLE, description: BAD_REQUEST_MESSAGE_DESCRIPTION],
            500: [title: SERVER_ERROR_TITLE, description: [
                    Arithmetic: ARITHMETIC_ERROR_MESSAGE_DESCRIPTION,
                    Unknown: SERVER_ERROR_UNKNOWN_DESCRIPTION
            ], cause: SERVER_ERROR_ARITHMETIC_CAUSE],
    ]


    static String buildMessage(Integer errorCode, String optional = null, String cause = null) {
        String title = CODE_TO_MESSAGE[errorCode]['title']
        String description = ''
        switch (errorCode) {
            case STATUS_CODE_BAD_REQUEST:
                description = CODE_TO_MESSAGE[errorCode]['description'] + optional
                break
            case STATUS_CODE_SERVER_ERROR:
                if (CODE_TO_MESSAGE[errorCode]['cause'] == SERVER_ERROR_ARITHMETIC_CAUSE) {
                    description = CODE_TO_MESSAGE[errorCode]['description'][SERVER_ERROR_ARITHMETIC_CAUSE] + optional
                } else {
                    description = CODE_TO_MESSAGE[errorCode]['description'][SERVER_ERROR_UNKNOWN_CAUSE]
                }
                break
        }
        title + description
    }
}

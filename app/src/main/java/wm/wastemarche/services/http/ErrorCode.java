package wm.wastemarche.services.http;

import java.net.HttpURLConnection;

public final class ErrorCode {
    private ErrorCode() {
    }

    public static final int NO_ERROR = 0;
    public static final int NETWORK_FAILURE = 1;
    public static final int PARSING_ERROR = 2;
    public static final int UNAUTHORIZED = 3;
    public static final int OBJECT_NOT_FOUND = 4;

    public static String getLoclizedMessage(final int code) {
        String ret = "";

        switch (code) {
            case NO_ERROR:
                break;
            case NETWORK_FAILURE:
                ret = "Network unavailable";
                break;
            case PARSING_ERROR:
                ret = "Data cannot be parsed";
                break;
            case UNAUTHORIZED:
                ret = "Needs to login";
                break;
            case HttpURLConnection.HTTP_BAD_REQUEST:
                ret = "Request malFormed";
                break;
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                ret = "Server error";
                break;
            case HttpURLConnection.HTTP_NOT_FOUND:
                ret = "File not found";
                break;
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                ret = "Not authorized/invalid apikey";
                break;
            default:
                ret = "An error occurred";
                break;
        }

        return ret;
    }
}

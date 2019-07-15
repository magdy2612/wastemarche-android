package wm.wastemarche.services.http;

import java.util.HashMap;
import java.util.Map;

import wm.wastemarche.services.datacenter.DataCenter;

public final class HttpConstants {
    private HttpConstants() {
    }

    public static final String HOSTNAME = "http://api.wastemarche.com/public/api";
    public static final String IMAGES_HOSTNAME = "http://test.wastemarche.com/uploads";
    public static final String BOUNDARY = "----WebKitFormBoundary7MA4YWxkTrZu0gW";
    public static final String LINE_FEED = "\r\n";

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
    public static final String URL_ENCODED = "application/x-www-form-urlencoded";
    public static final String MULTI_PART = "multipart/form-data";
    public static final String JSON_ENCODED = "application/json";
    public static final String GZIP = "gzip";
    public static final String ACCEPT = "Accept";

    public static Map<String, String> defaultHeaders() {
        final Map<String, String> headers = new HashMap<>(0);
        headers.put("Accept", "application/json");
        headers.put("Authorization", "Bearer " + DataCenter.token);
        return headers;
    }

    public static Map<String, String> postHeaders() {
        final Map<String, String> headers = new HashMap<>(0);
        headers.put("Accept", "application/json");
        headers.put("Authorization", "Bearer " + DataCenter.token);
        headers.put("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        return headers;
    }
}

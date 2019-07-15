package wm.wastemarche.services.http;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class ConnectionManager extends AsyncTask<String, Void, String> {
    private String httpMethod;
    private String apiMethod;
    private List<Bitmap> bitmaps;
    private Map<String, String> httpHeaders;
    private Map<String, String> fields;
    private Handler handler;

    public void makePOSTRequest(final String method, final Map<String, String> fields, final Map<String, String> httpHeaders, final Handler handler) {
        httpMethod = HttpConstants.POST;
        apiMethod = method;
        this.fields = fields == null ? new HashMap<String, String>(0) : fields;
        this.httpHeaders = httpHeaders == null ? new HashMap<String, String>(0) : httpHeaders;
        this.handler = handler;
        bitmaps = new ArrayList<>(0);
        execute();
    }

    public void makePostRequestWithFiles(final String method, final Map<String, String> fields, final List<Bitmap> bitmaps, final Map<String, String> httpHeaders, final Handler handler) {
        httpMethod = HttpConstants.POST;
        apiMethod = method;
        this.fields = fields == null ? new HashMap<String, String>(0) : fields;
        this.httpHeaders = httpHeaders == null ? new HashMap<String, String>(0) : httpHeaders;
        //this.httpHeaders.put(HttpConstants.CONTENT_TYPE, HttpConstants.MULTI_PART + "; boundary=" + HttpConstants.BOUNDARY);
        this.bitmaps = bitmaps == null ? new ArrayList<Bitmap>(0) : bitmaps;
        this.handler = handler;
        execute();
    }

    public void makePutRequestWithFiles(final String method, final Map<String, String> fields, final List<Bitmap> bitmaps, final Map<String, String> httpHeaders, final Handler handler) {
        httpMethod = "PUT";
        apiMethod = method;
        this.fields = fields == null ? new HashMap<String, String>(0) : fields;
        this.httpHeaders = httpHeaders == null ? new HashMap<String, String>(0) : httpHeaders;
        //this.httpHeaders.put(HttpConstants.CONTENT_TYPE, HttpConstants.MULTI_PART + "; boundary=" + HttpConstants.BOUNDARY);
        this.bitmaps = bitmaps == null ? new ArrayList<Bitmap>(0) : bitmaps;
        this.handler = handler;
        execute();
    }

    public void makeGETRequest(final String method, final Map<String, String> httpHeaders, final Handler handler) {
        httpMethod = HttpConstants.GET;
        apiMethod = method;
        this.httpHeaders = new HashMap<>(httpHeaders);
        this.handler = handler;
        execute();
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(final String... params) {
        openAPI();
        return null;
    }

    private void openAPI() {
        final Message msg = new Message();

        try {
            String urlString = HttpConstants.HOSTNAME + apiMethod;
            if (apiMethod.startsWith("http://")) {
                urlString = apiMethod;
            }
            final URL url = new URL(urlString);
            final HttpURLConnection conn;
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(httpMethod);

            for (final Map.Entry<String, String> entry : httpHeaders.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }

            Log.d("HTTP", "URL : " + conn.getURL());
            Log.d("HTTP", "Method : " + conn.getRequestMethod());
            Log.d("HTTP", "Headers : " + httpHeaders.toString());

            if (httpMethod.equalsIgnoreCase(HttpConstants.GET)) {
            } else /*if (httpMethod.equalsIgnoreCase(HttpConstants.POST))*/ {
                conn.setDoInput(true);
                conn.setDoOutput(true);

                final OutputStream os = conn.getOutputStream();
                final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                if (bitmaps.isEmpty()) {
                    writer.append(urlEncode(fields));
                } else {
                    writer.append(addFields());
                    addFiles(os, writer);
                    writer.append("--" + HttpConstants.BOUNDARY + "--" + HttpConstants.LINE_FEED);
                }

                writer.flush();
                writer.close();
                os.close();
            }

            final int responseCode = conn.getResponseCode();
            Log.d("HTTP", "ResponseCode : " + responseCode);
            //Response.log("data loaded for (" + urlString + ") with http code + " + responseCode);
            final BufferedReader in;

            if (responseCode < HttpURLConnection.HTTP_BAD_REQUEST) {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                msg.arg1 = ErrorCode.NO_ERROR;
            } else {
                in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                msg.arg1 = responseCode;
            }

            final StringBuilder sb = new StringBuilder(0);
            String line = "";
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
            msg.obj = sb.toString();

            Log.d("HTTP", "Response : " + sb.toString());

            if (responseCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
                //Response.log(sb.toString());
            }

        } catch (final Exception e) {
            e.printStackTrace();
            msg.arg1 = ErrorCode.NETWORK_FAILURE;
        }

        if (handler != null) {
            handler.sendMessage(msg);
        }
    }

    public static String urlEncode(final Map<String, String> fields) {
        final StringBuilder ret = new StringBuilder(0);
        for (final Entry<String, String> field : fields.entrySet()) {
            ret.append(field.getKey());
            ret.append('=');
            ret.append(Uri.encode(field.getValue(), "utf-8"));
            ret.append('&');
        }
        Log.d("HTTP", "Body urlEncode : " + ret.toString());
        return ret.toString();
    }

    private CharSequence addFields() {
        final StringBuilder sb = new StringBuilder(0);
        for (final Entry<String, String> field : fields.entrySet()) {
            sb.append("--" + HttpConstants.BOUNDARY)
                    .append(HttpConstants.LINE_FEED)
                    .append("Content-Disposition: form-data; name=\"" + field.getKey() + '"')
                    .append(HttpConstants.LINE_FEED)
                    .append(HttpConstants.LINE_FEED)
                    .append(field.getValue())
                    .append(HttpConstants.LINE_FEED);
        }
        Log.d("HTTP", "Body MultiPart : " + sb.toString());
        return sb.toString();
    }

    private void addFiles(final OutputStream os, final Writer writer) {
        for (int i = 0, len = bitmaps.size(); i < len; i++) {
            final Bitmap bitmap = bitmaps.get(i);
            try {
                writer.append("--" + HttpConstants.BOUNDARY)
                        .append(HttpConstants.LINE_FEED)
                        .append("Content-Disposition: form-data; name=\"" + (i == 0 ? "main_image" : "image_name") + "\"; filename=\"image_" + i + ".png\"")
                        .append(HttpConstants.LINE_FEED)
                        .append(HttpConstants.LINE_FEED);
                writer.flush();
                bitmap.compress(CompressFormat.PNG, 85, os);
                writer.append(HttpConstants.LINE_FEED).flush();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }
}

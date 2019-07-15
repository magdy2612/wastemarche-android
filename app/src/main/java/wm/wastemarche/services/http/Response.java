package wm.wastemarche.services.http;

import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Response implements Serializable {

    public static JSONArray getPropertyArray(final String key, final JSONObject info) {
        return getPropertyArray(key, info, Boolean.FALSE);
    }

    public static JSONArray getPropertyArray(final String key, final JSONObject info, final Boolean optional) {
        try {
            return info.getJSONArray(key);
        }
        catch (final JSONException e) {
            if (!optional.booleanValue()) {
                log("Cannot find key '" + key + "' in json object");
            }
        }
        return new JSONArray();
    }

    public static JSONObject getPropertyObject(final String key, final JSONObject info) {
        return getPropertyObject(key, info, Boolean.FALSE);
    }

    public static JSONObject getPropertyObject(final String key, final JSONObject info, final Boolean optional) {
        try {
            return info.getJSONObject(key);
        } catch (final JSONException e) {
            if (!optional.booleanValue()) {
                log("Cannot find key '" + key + "' in json object");
            }
        }
        return null;
    }

    public static String getPropertyString(final String key, final JSONObject info) {
        return getPropertyString(key, info, Boolean.FALSE);
    }

    public static String getPropertyString(final String key, final JSONObject info, final Boolean optional) {
        try {
            return info.getString(key);
        } catch (final JSONException e) {
            if (!optional.booleanValue()) {
                log("Cannot find key '" + key + "' in json object");
            }
        }
        return "";
    }

    public static int getPropertyInt(final String key, final JSONObject info) {
        return getPropertyInt(key, info, Boolean.FALSE);
    }

    public static int getPropertyInt(final String key, final JSONObject info, final Boolean optional) {
        try {
            return info.getInt(key);
        } catch (final JSONException e) {
            if (!optional.booleanValue()) {
                log("Cannot find key '" + key + "' in json object");
            }
        }
        return -1;
    }

    public static boolean getPropertyBoolean(final String key, final JSONObject info) {
        try {
            return info.getBoolean(key);
        } catch (final JSONException e) {
            log("Cannot find key '" + key + "' in json object");
        }
        return false;
    }


    public static JSONObject parse(final String str) {
        JSONObject obj = null;
        try {
            obj = new JSONObject(str);
        } catch (final Exception e) {
            log("Cannot parse json file");
        }
        return  obj;
    }

    public static JSONArray parseAsArray(final String str) {
        JSONArray obj = null;
        try {
            obj = new JSONArray(str);
        } catch (final Exception e) {
           e.printStackTrace();
        }
        return  obj;
    }

    public static JSONObject parseResponse(final Message msg, final ApiProtocol delegate) {
        if(msg.arg1 != ErrorCode.NO_ERROR) {
            delegate.apiError(msg.arg1);
            return null;
        }

        final JSONObject json = Response.parse((String) msg.obj);
        if(json == null) {
            delegate.apiError(ErrorCode.PARSING_ERROR);
            return null;
        }

        final String message = Response.getPropertyString("message", json);
        if( message.equalsIgnoreCase("Unauthenticated.") ) {
            delegate.apiError(ErrorCode.UNAUTHORIZED);
            return null;
        }

        return json;
    }

    public static void log(final String message) {
        Log.d("---", message);
    }
}

package fi.iki.mkuokkanen.seda.api.json;

import org.json.simple.JSONObject;

/**
 * Help can be found from here.
 * http://code.google.com/p/json-simple/wiki/EncodingExamples
 * 
 * @author mkuokkanen
 */
public class JsonCreator {

    @SuppressWarnings("unchecked")
    public static String createPushMsg(String key, String val) {
        JSONObject obj = new JSONObject();
        obj.put("op", "push");
        obj.put("key", key);
        obj.put("val", val);
        return obj.toString();
    }

    @SuppressWarnings("unchecked")
    public static String createDeleteMsg(String key) {
        JSONObject obj = new JSONObject();
        obj.put("op", "delete");
        obj.put("key", key);
        return obj.toString();
    }

    @SuppressWarnings("unchecked")
    public static String createBroadcastMsg() {
        JSONObject obj = new JSONObject();
        obj.put("op", "broadcast");
        return obj.toString();
    }

}

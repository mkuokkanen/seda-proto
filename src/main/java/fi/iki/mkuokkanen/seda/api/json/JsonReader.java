package fi.iki.mkuokkanen.seda.api.json;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Help can be found from here.
 * http://code.google.com/p/json-simple/wiki/EncodingExamples
 * 
 * @author mkuokkanen
 */
public class JsonReader {

    private final JSONObject jsonObject;

    public JsonReader(String json) throws ParseException {
        JSONParser parser = new JSONParser();
        jsonObject = (JSONObject) parser.parse(json);
    }

    public String getOperationType() {
        return getValForKey("op");
    }

    public String getValForKey(String key) {
        return jsonObject.get(key).toString();
    }

}

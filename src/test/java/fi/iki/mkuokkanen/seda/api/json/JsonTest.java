package fi.iki.mkuokkanen.seda.api.json;

import static org.junit.Assert.assertTrue;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import fi.iki.mkuokkanen.seda.api.json.JsonCreator;
import fi.iki.mkuokkanen.seda.api.json.JsonReader;

public class JsonTest {

    @Test
    public void testPushCreation() throws ParseException {
        String str = JsonCreator.createPushMsg("myKey", "myVal");
        System.out.println(str);

        String op = new JsonReader(str).getOperationType();

        assertTrue("push".equals(op));
    }

    @Test
    public void testDeleteCreation() throws ParseException {
        String str = JsonCreator.createDeleteMsg("myKey");
        System.out.println(str);

        String op = new JsonReader(str).getOperationType();

        assertTrue("delete".equals(op));
    }

    @Test
    public void testBroadcastCreation() throws ParseException {
        String str = JsonCreator.createBroadcastMsg();
        System.out.println(str);

        String op = new JsonReader(str).getOperationType();

        assertTrue("broadcast".equals(op));
    }

}

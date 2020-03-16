import JSONHandlers.JSONHandler;
import JSONHandlers.JSONReader;
import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class JSONHandlerFindNearestUserTests implements IURLForTests {
    private JSONReader reader = null;
    private static JSONArray users = null;

    @Before
    public void setUP() {
        reader = new JSONReader();
        Optional<StringBuilder> response = Optional.ofNullable(reader.readFromURL(usersURL));
        if (!response.isPresent()) {
            System.out.println("Nie udalo sie pobrac danych z linku: " + usersURL);
            return;
        }

        users = new JSONArray(response.get().toString());
    }


    @Test
    public void getNearestUserForFirstUserInSmallerArray() {
        JSONArray myUsers = new JSONArray();
        myUsers.put(users.getJSONObject(0));
        myUsers.put(users.getJSONObject(1));
        myUsers.put(users.getJSONObject(2));
        myUsers.put(users.getJSONObject(3));
        myUsers.put(users.getJSONObject(4));
        Assert.assertEquals(users.getJSONObject(4), JSONHandler.findNearestUser(users.getJSONObject(0), myUsers));
    }

    @Test
    public void getNearestUserForSecondUserInSmallerArray() {
        JSONArray myUsers = new JSONArray();
        myUsers.put(users.getJSONObject(0));
        myUsers.put(users.getJSONObject(1));
        myUsers.put(users.getJSONObject(2));
        myUsers.put(users.getJSONObject(3));
        myUsers.put(users.getJSONObject(4));
        Assert.assertEquals(users.getJSONObject(2), JSONHandler.findNearestUser(users.getJSONObject(1), myUsers));

    }

    @Test
    public void getNearestUserForThirdUserInSmallerArray() {
        JSONArray myUsers = new JSONArray();
        myUsers.put(users.getJSONObject(0));
        myUsers.put(users.getJSONObject(1));
        myUsers.put(users.getJSONObject(2));
        myUsers.put(users.getJSONObject(3));
        myUsers.put(users.getJSONObject(4));
        Assert.assertEquals(users.getJSONObject(1), JSONHandler.findNearestUser(users.getJSONObject(2), myUsers));
    }

    @Test
    public void getNearestUserForFourthUserInSmallerArray() {
        JSONArray myUsers = new JSONArray();
        myUsers.put(users.getJSONObject(0));
        myUsers.put(users.getJSONObject(1));
        myUsers.put(users.getJSONObject(2));
        myUsers.put(users.getJSONObject(3));
        myUsers.put(users.getJSONObject(4));
        Assert.assertEquals(users.getJSONObject(0), JSONHandler.findNearestUser(users.getJSONObject(3), myUsers));
    }

    @Test
    public void getNearestUserForFifthUserInSmallerArray() {
        JSONArray myUsers = new JSONArray();
        myUsers.put(users.getJSONObject(0));
        myUsers.put(users.getJSONObject(1));
        myUsers.put(users.getJSONObject(2));
        myUsers.put(users.getJSONObject(3));
        myUsers.put(users.getJSONObject(4));
        Assert.assertEquals(users.getJSONObject(0), JSONHandler.findNearestUser(users.getJSONObject(4), myUsers));
    }

    @Test
    public void getNearestUserForSixthUserInSmallerArray() {
        JSONArray myUsers = new JSONArray();
        myUsers.put(users.getJSONObject(5));
        myUsers.put(users.getJSONObject(6));
        myUsers.put(users.getJSONObject(7));
        myUsers.put(users.getJSONObject(8));
        myUsers.put(users.getJSONObject(9));
        Assert.assertEquals(users.getJSONObject(9), JSONHandler.findNearestUser(users.getJSONObject(5), myUsers));
    }

    @Test
    public void getNearestUserForSeventhUserInSmallerArray() {
        JSONArray myUsers = new JSONArray();
        myUsers.put(users.getJSONObject(5));
        myUsers.put(users.getJSONObject(6));
        myUsers.put(users.getJSONObject(7));
        myUsers.put(users.getJSONObject(8));
        myUsers.put(users.getJSONObject(9));
        Assert.assertEquals(users.getJSONObject(9), JSONHandler.findNearestUser(users.getJSONObject(6), myUsers));
    }

    @Test
    public void getNearestUserForEighthUserInSmallerArray() {
        JSONArray myUsers = new JSONArray();
        myUsers.put(users.getJSONObject(5));
        myUsers.put(users.getJSONObject(6));
        myUsers.put(users.getJSONObject(7));
        myUsers.put(users.getJSONObject(8));
        myUsers.put(users.getJSONObject(9));
        Assert.assertEquals(users.getJSONObject(8), JSONHandler.findNearestUser(users.getJSONObject(7), myUsers));
    }

    @Test
    public void getNearestUserForNinthUserInSmallerArray() {
        JSONArray myUsers = new JSONArray();
        myUsers.put(users.getJSONObject(5));
        myUsers.put(users.getJSONObject(6));
        myUsers.put(users.getJSONObject(7));
        myUsers.put(users.getJSONObject(8));
        myUsers.put(users.getJSONObject(9));
        Assert.assertEquals(users.getJSONObject(7), JSONHandler.findNearestUser(users.getJSONObject(8), myUsers));
    }

    @Test
    public void getNearestUserForTenthUserInSmallerArray() {
        JSONArray myUsers = new JSONArray();
        myUsers.put(users.getJSONObject(5));
        myUsers.put(users.getJSONObject(6));
        myUsers.put(users.getJSONObject(7));
        myUsers.put(users.getJSONObject(8));
        myUsers.put(users.getJSONObject(9));
        Assert.assertEquals(users.getJSONObject(5), JSONHandler.findNearestUser(users.getJSONObject(9), myUsers));
    }
}

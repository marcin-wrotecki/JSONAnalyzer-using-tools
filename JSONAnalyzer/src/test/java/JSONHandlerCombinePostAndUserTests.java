import JSONHandlers.JSONHandler;
import JSONHandlers.JSONReader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class JSONHandlerCombinePostAndUserTests implements IURLForTests {
    private JSONReader reader = null;
    private static JSONArray users = null;
    private static JSONArray posts = null;
    private static double delta = 0.01;

    @Before
    public void setUP() {
        reader = new JSONReader();
        Optional<StringBuilder> response = Optional.ofNullable(reader.readFromURL(usersURL));
        if (!response.isPresent()) {
            System.out.println("Nie udalo sie pobrac danych z linku: " + usersURL);
            return;
        }
        users = new JSONArray(response.get().toString());
        response = Optional.ofNullable(reader.readFromURL(postsURL));
        if (!response.isPresent()) {
            System.out.println("Nie udalo sie pobrac danych z linku: " + postsURL);
            return;
        }
        posts = new JSONArray(response.get().toString());
    }

    @Test
    public void combinePostAndUserAndCheckAttributes() {
        JSONObject userAndPost = JSONHandler.combinePostAndUser(posts.getJSONObject(0), users.getJSONObject(0));
        Assert.assertEquals(posts.getJSONObject(0).getInt("id"), userAndPost.getInt("postID"));
        Assert.assertEquals(posts.getJSONObject(0).getInt("userId"), userAndPost.getInt("userID"));
        Assert.assertEquals(users.getJSONObject(0).getInt("id"), userAndPost.getInt("userID"));
        Assert.assertEquals(posts.getJSONObject(0).getString("title"), userAndPost.getString("title"));
        Assert.assertEquals(posts.getJSONObject(0).getString("body"), userAndPost.getString("body"));
        Assert.assertEquals(users.getJSONObject(0).getString("name"), userAndPost.getString("name"));
        Assert.assertEquals(users.getJSONObject(0).getString("username"), userAndPost.getString("username"));
        Assert.assertEquals(users.getJSONObject(0).getString("email"), userAndPost.getString("email"));
        Assert.assertEquals(users.getJSONObject(0).getJSONObject("address").getString("street"), userAndPost.getJSONObject("address").getString("street"));
        Assert.assertEquals(users.getJSONObject(0).getJSONObject("address").getString("suite"), userAndPost.getJSONObject("address").getString("suite"));
        Assert.assertEquals(users.getJSONObject(0).getJSONObject("address").getString("city"), userAndPost.getJSONObject("address").getString("city"));
        Assert.assertEquals(users.getJSONObject(0).getJSONObject("address").getString("zipcode"), userAndPost.getJSONObject("address").getString("zipcode"));
        Assert.assertEquals(users.getJSONObject(0).getJSONObject("address").getJSONObject("geo").getDouble("lat"), userAndPost.getJSONObject("address").getJSONObject("geo").getDouble("lat"), delta);
        Assert.assertEquals(users.getJSONObject(0).getJSONObject("address").getJSONObject("geo").getDouble("lng"), userAndPost.getJSONObject("address").getJSONObject("geo").getDouble("lng"), delta);
        Assert.assertEquals(users.getJSONObject(0).getString("phone"), userAndPost.getString("phone"));
        Assert.assertEquals(users.getJSONObject(0).getString("website"), userAndPost.getString("website"));
        Assert.assertEquals(users.getJSONObject(0).getJSONObject("company").getString("name"), userAndPost.getJSONObject("company").getString("name"));
        Assert.assertEquals(users.getJSONObject(0).getJSONObject("company").getString("catchPhrase"), userAndPost.getJSONObject("company").getString("catchPhrase"));
        Assert.assertEquals(users.getJSONObject(0).getJSONObject("company").getString("bs"), userAndPost.getJSONObject("company").getString("bs"));
    }

    @Test
    public void combineUserWithWrongPostAndGetNull() {
        Assert.assertNull(JSONHandler.combinePostAndUser(posts.getJSONObject(50), users.getJSONObject(2)));
    }

}

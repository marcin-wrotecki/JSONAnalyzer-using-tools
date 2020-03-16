import JSONHandlers.JSONHandler;
import JSONHandlers.JSONReader;
import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;

public class JSONHandlerPrintNumberOfPostsTests implements IURLForTests {
    private JSONReader reader;
    private JSONArray users;
    private JSONArray posts;
    private JSONArray userAndPost;
    private ByteArrayOutputStream outContent;

    @Before
    public void setUP() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

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
        userAndPost = JSONHandler.combinPostsArrayAndUsesrArray(posts, users);

    }

    @Test
    public void countNumberOfPostsForEachUserInSmallerArray() {
        JSONArray smaller = new JSONArray();
        sliceArray(userAndPost, smaller, 0, 25);

        JSONHandler.printNumberOfPosts(users, smaller);
        String expected = "";
        expected += users.getJSONObject(0).getString("username") + " napisał(a) " + 10 + " postów";
        expected += users.getJSONObject(1).getString("username") + " napisał(a) " + 10 + " postów";
        expected += users.getJSONObject(2).getString("username") + " napisał(a) " + 5 + " postów";
        for (int i = 3; i < users.length(); i++) {
            expected += users.getJSONObject(i).getString("username") + " napisał(a) " + 0 + " postów";
        }

        Assert.assertEquals(expected.replaceAll("\n", "").replaceAll("\r", ""), outContent.toString().replaceAll("\n", "").replaceAll("\r", ""));
    }

    @Test
    public void countNumberOfPostsForEachUserInOtherSmallerArray() {
        JSONArray smaller = new JSONArray();
        sliceArray(userAndPost, smaller, 44, 67);

        JSONHandler.printNumberOfPosts(users, smaller);
        String expected = "";

        for (int i = 0; i < 4; i++) {
            expected += users.getJSONObject(i).getString("username") + " napisał(a) " + 0 + " postów";
        }
        expected += users.getJSONObject(4).getString("username") + " napisał(a) " + 6 + " postów";
        expected += users.getJSONObject(5).getString("username") + " napisał(a) " + 10 + " postów";
        expected += users.getJSONObject(6).getString("username") + " napisał(a) " + 7 + " postów";
        for (int i = 7; i < users.length(); i++) {
            expected += users.getJSONObject(i).getString("username") + " napisał(a) " + 0 + " postów";
        }
        Assert.assertEquals(expected.replaceAll("\n", "").replaceAll("\r", ""), outContent.toString().replaceAll("\n", "").replaceAll("\r", ""));
    }

    public void sliceArray(JSONArray source, JSONArray destination, int firstIndex, int lastIndex) {
        for (int i = firstIndex; i < lastIndex; i++) {
            destination.put(source.getJSONObject(i));
        }
    }
}

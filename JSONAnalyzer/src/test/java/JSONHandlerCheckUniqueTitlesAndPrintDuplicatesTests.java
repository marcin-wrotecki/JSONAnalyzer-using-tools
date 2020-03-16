import JSONHandlers.JSONHandler;
import JSONHandlers.JSONReader;
import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;

public class JSONHandlerCheckUniqueTitlesAndPrintDuplicatesTests implements IURLForTests {
    private JSONReader reader;
    private static JSONArray posts;
    private ByteArrayOutputStream outContent;

    @Before
    public void setUP() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        reader = new JSONReader();
        Optional<StringBuilder> response = Optional.ofNullable(reader.readFromURL(postsURL));
        if (!response.isPresent()) {
            System.out.println("Nie udalo sie pobrac danych z linku: " + postsURL);
            return;
        }
        posts = new JSONArray(response.get().toString());
    }


    @Test
    public void returnTrueWhenPostsAreUniqueAndDontPrintAnything() {
        Assert.assertEquals(true, JSONHandler.checkUniqueTitlesAndPrintDuplicates(posts));
        Assert.assertEquals("", outContent.toString());
    }

    @Test
    public void returnTrueWhenTitlesAreUniqueAndDontPrintAnything() {
        String title = posts.getJSONObject(20).getString("title");
        posts.getJSONObject(20).put("id", posts.getJSONObject(1).getInt("id"));
        posts.getJSONObject(20).put("userId", posts.getJSONObject(1).getInt("userId"));
        posts.getJSONObject(20).put("body", posts.getJSONObject(1).getString("body"));
        posts.getJSONObject(20).put("title", title);
        Assert.assertEquals(true, JSONHandler.checkUniqueTitlesAndPrintDuplicates(posts));
        Assert.assertEquals("", outContent.toString());
    }

    @Test
    public void returnFalseAndPrintTitleWhenHasOneDuplication() {
        posts.getJSONObject(3).put("title", posts.getJSONObject(60).getString("title"));
        Assert.assertEquals(false, JSONHandler.checkUniqueTitlesAndPrintDuplicates(posts));
        Assert.assertEquals(posts.getJSONObject(60).getString("title").trim(), outContent.toString().trim());
    }

    @Test
    public void returnFalseAndPrintTitlesWhenHasMoreDuplications() {
        posts.getJSONObject(3).put("title", posts.getJSONObject(40).getString("title"));
        posts.getJSONObject(7).put("title", posts.getJSONObject(20).getString("title"));
        posts.getJSONObject(21).put("title", posts.getJSONObject(1).getString("title"));
        posts.getJSONObject(80).put("title", posts.getJSONObject(73).getString("title"));
        posts.getJSONObject(99).put("title", posts.getJSONObject(12).getString("title"));
        Assert.assertEquals(false, JSONHandler.checkUniqueTitlesAndPrintDuplicates(posts));
        Assert.assertEquals((posts.getJSONObject(7).getString("title") + posts.getJSONObject(1).getString("title") + posts.getJSONObject(3).getString("title") + posts.getJSONObject(73).getString("title") + posts.getJSONObject(12).getString("title")).replaceAll("\n", "").replaceAll("\r", ""), outContent.toString().replaceAll("\n", "").replaceAll("\r", ""));
    }
}

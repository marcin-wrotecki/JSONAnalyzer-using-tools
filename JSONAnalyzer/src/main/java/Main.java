import JSONHandlers.JSONHandler;
import JSONHandlers.JSONReader;
import org.json.JSONArray;

import java.util.Optional;

public class Main {

    final static String postsURL = "https://jsonplaceholder.typicode.com/posts";
    final static String usersURL = "https://jsonplaceholder.typicode.com/users";

    public static void main(String[] args) {

        JSONReader reader = new JSONReader();

        Optional<StringBuilder> response = Optional.ofNullable(reader.readFromURL(postsURL));
        if (!response.isPresent()) {
            System.out.println("Nie udalo sie pobrac danych z linku: " + postsURL);
            return;
        }
        JSONArray posts = new JSONArray(response.get().toString());

        response = Optional.ofNullable(reader.readFromURL(usersURL));
        if (!response.isPresent()) {
            System.out.println("Nie udalo sie pobrac danych z linku: " + usersURL);
            return;
        }
        JSONArray users = new JSONArray(response.get().toString());

        JSONArray usersAndPosts = JSONHandler.combinPostsArrayAndUsesrArray(posts, users);
        JSONHandler.printNumberOfPosts(users, usersAndPosts);

        System.out.println("\nLista postów o powtarzających się tytułach:");
        if (JSONHandler.checkUniqueTitlesAndPrintDuplicates(posts)) {
            System.out.println("Wszystkie posty są unikatowe\n");
        }


        for (int i = 0; i < users.length(); i++) {
            System.out.println("Najblizej uzytkownika " + users.getJSONObject(i).getString("username") + " mieszka uzytkownik " + JSONHandler.findNearestUser(users.getJSONObject(i), users).getString("username"));
        }


    }


}

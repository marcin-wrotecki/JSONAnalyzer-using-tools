package JSONHandlers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

public class JSONHandler {

    private final static int earthRadius = 6371;

    public static JSONObject findNearestUser(JSONObject u, JSONArray usersArray) {
        int index = 0;
        double distance = Double.MAX_VALUE;
        double temp;
        for (int i = 0; i < usersArray.length(); i++) {
            if (u.getInt("id") != usersArray.getJSONObject(i).getInt("id")) {
                temp = countDistance(u, usersArray.getJSONObject(i));
                if (distance > temp) {
                    distance = temp;
                    index = i;
                }
            }
        }
        return usersArray.getJSONObject(index);
    }

    public static double countDistance(JSONObject user1, JSONObject user2) {
        double lng1, lng2, lat1, lat2;
        lng1 = user1.getJSONObject("address").getJSONObject("geo").getDouble("lng");
        lng2 = user2.getJSONObject("address").getJSONObject("geo").getDouble("lng");
        lat1 = user1.getJSONObject("address").getJSONObject("geo").getDouble("lat");
        lat2 = user2.getJSONObject("address").getJSONObject("geo").getDouble("lat");
        double dlong = Math.toRadians(lng2 - lng1);
        double dlat = Math.toRadians(lat2 - lat1);

        double distance = Math.pow(Math.sin(dlat / 2), 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.pow(Math.sin(dlong / 2), 2);

        distance = 2 * Math.asin(Math.sqrt(distance));
        distance = distance * earthRadius;
        return distance;

    }


    public static boolean checkUniqueTitlesAndPrintDuplicates(JSONArray posts) {
        HashSet<String> uniqueTitle = new HashSet<>();
        ArrayList<String> printedTitles = new ArrayList<>();//to avoid duplications
        for (int i = 0; i < posts.length(); i++) {
            if (!uniqueTitle.add(posts.getJSONObject(i).getString("title"))) {
                if (!printedTitles.contains(posts.getJSONObject(i).getString("title"))) {
                    System.out.println(posts.getJSONObject(i).getString("title"));
                    printedTitles.add(posts.getJSONObject(i).getString("title"));
                }
            }
        }
        return uniqueTitle.size() == posts.length();
    }


    public static void printNumberOfPosts(JSONArray users, JSONArray userAndPosts) {
        for (int i = 0; i < users.length(); i++) {
            int userID = users.getJSONObject(i).getInt("id");
            int counter = 0;
            for (int j = 0; j < userAndPosts.length(); j++) {
                if (userAndPosts.getJSONObject(j).getInt("userID") == userID) {
                    counter++;
                }
            }
            System.out.println(users.getJSONObject(i).getString("username") + " napisał(a) " + counter + " postów");
        }
    }

    public static JSONArray combinPostsArrayAndUsesrArray(JSONArray posts, JSONArray users) {
        JSONArray usersAndPosts = new JSONArray();
        for (int i = 0; i < users.length(); i++) {
            for (int j = 0; j < posts.length(); j++) {
                if (users.getJSONObject(i).get("id") == posts.getJSONObject(j).get("userId")) {
                    usersAndPosts.put(combinePostAndUser(posts.getJSONObject(j), users.getJSONObject(i)));
                }
            }
        }
        return usersAndPosts;
    }


    public static JSONObject combinePostAndUser(JSONObject post, JSONObject user) {
        JSONObject combined = new JSONObject();
        int userId, userIdFromPost;
        try {
            userId = user.getInt("id");
            userIdFromPost = post.getInt("userId");

        } catch (JSONException e) {
            System.out.println("Podano niepoprawne obiekty");
            return null;
        }
        if (userId == userIdFromPost) {
            try {
                combined.put("userID", user.getInt("id"));
                combined.put("name", user.getString("name"));
                combined.put("username", user.getString("username"));
                combined.put("email", user.getString("email"));
                combined.put("address", user.get("address"));
                combined.put("phone", user.getString("phone"));
                combined.put("website", user.getString("website"));
                combined.put("company", user.get("company"));
                combined.put("postID", post.getInt("id"));
                combined.put("title", post.getString("title"));
                combined.put("body", post.getString("body"));
                return combined;
            } catch (JSONException e) {
                System.out.println("Podano niepoprawne obiekty");
                return null;
            }

        } else {
            System.out.println("Nie udalo sie polaczyc użytkownika o id " + user.get("id") + " z postem o id " + post.get("id") + " - rozne id użytkownika");
        }
        return null;
    }
}

package unboundfire.gifstore.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

import unboundfire.gifstore.giphy.models.Gif;

/**
 * Created by nitaliano on 9/25/16.
 */

public class Store {
    private static final String SETTINGS = "settings";
    private static final String RECENT_GIFS ="recent";

    public static void addRecentGIF(Activity activity, Gif gif) {
        Set<String> gifIDS = readStringSet(activity, RECENT_GIFS);

        if (!gifIDS.contains(gif.id)) {
            gifIDS.add(gif.id);
        }

        writeStringSet(activity, RECENT_GIFS, gifIDS);
    }

    public static Set<String> getRecentGIFS(Activity activity) {
        return readStringSet(activity, RECENT_GIFS);
    }

    private static void writeStringSet(Activity activity, String key, Set<String> values) {
        SharedPreferences.Editor editor = getEditor(activity);
        editor.putStringSet(key, values);
        editor.commit();
    }

    private static Set<String> readStringSet(Activity activity, String key) {
        return getPrefs(activity).getStringSet(key, new HashSet<>());
    }

    private static SharedPreferences getPrefs(Activity activity) {
        return activity.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Activity activity) {
        SharedPreferences sharedPref = getPrefs(activity);
        return sharedPref.edit();
    }
}

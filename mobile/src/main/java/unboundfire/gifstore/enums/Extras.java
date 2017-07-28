package unboundfire.gifstore.enums;

/**
 * Created by nitaliano on 9/25/16.
 */

public enum Extras {
    GIF("GIF"),
    GIF_SEARCH_QUERY("gif_search_query");

    String mName;

    Extras(String name) {
        mName = name;
    }

    @Override
    public String toString() {
        return mName;
    }
}

package unboundfire.gifstore.enums;

/**
 * Created by nitaliano on 9/22/16.
 */

public enum Tabs {
    RECENT("Recent"),
    TRENDING("Trending"),
    REACTIONS("Reactions"),
    ACTIONS("Actions"),
    EMOTIONS("Emotions"),
    MEMES("Memes"),
    TV("Tv"),
    MOVIES("Movies"),
    MUSIC("Music"),
    ANINMALS("Animals");

    String mName;

    Tabs(String name) {
        mName = name;
    }

    public static Tabs get(String name) {
        Tabs[] tabs = Tabs.values();

        for (Tabs tab : tabs) {
            if (name.equals(tab.toString())) {
                return tab;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return mName;
    }

    public boolean isEqual(Tabs tab) {
        return mName.equals(tab.toString());
    }
}

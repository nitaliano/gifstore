package unboundfire.gifstore.enums;

/**
 * Created by nitaliano on 9/25/16.
 */

public enum GifGridFragmentType {
    TRENDING(0),
    SEARCH(1),
    TITLE(2),
    RECENT(3)
    ;

    Integer mValue;

    GifGridFragmentType(int value) {
        mValue = value;
    }

    public static GifGridFragmentType get(int value) {
        GifGridFragmentType[] types = values();

        for (GifGridFragmentType type : types) {
            if (type.value() == value) {
                return type;
            }
        }

        return null;
    }

    public Integer value() {
        return mValue;
    }
}

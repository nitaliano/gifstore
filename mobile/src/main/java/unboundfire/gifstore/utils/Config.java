package unboundfire.gifstore.utils;

import android.content.Context;

/**
 * Created by nitaliano on 9/19/16.
 */

public class Config {
    private static final String ARRAY_RES = "array";
    private static Context mContext;

    public static void initialize(Context context) {
        mContext = context;
    }

    public static String getString(int resID) {
        return mContext.getResources().getString(resID);
    }

    public static String[] getStringArray(int resID) {
        return mContext.getResources().getStringArray(resID);
    }

    public static String[] getStringArrayByName(String name) {
        int resID = mContext.getResources().getIdentifier(name.toLowerCase(), ARRAY_RES, mContext.getPackageName());
        return Config.getStringArray(resID);
    }

    public static Integer getInteger(int resID) {
        return mContext.getResources().getInteger(resID);
    }
}

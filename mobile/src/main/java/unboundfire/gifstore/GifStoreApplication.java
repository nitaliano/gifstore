package unboundfire.gifstore;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;
import unboundfire.gifstore.http.Fetch;
import unboundfire.gifstore.utils.Config;

/**
 * Created by nitaliano on 10/15/16.
 */

public class GifStoreApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Config.initialize(this);
        Fetch.initialize(this);
        TwitterInit();
        FacebookInit();
    }

    private void TwitterInit() {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(
                Config.getString(R.string.twitter_api_key),
                Config.getString(R.string.twitter_api_secret));
        Fabric.with(this, new Twitter(authConfig));
    }

    private void FacebookInit() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}

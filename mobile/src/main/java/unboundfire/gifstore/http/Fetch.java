package unboundfire.gifstore.http;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;

/**
 * Created by nitaliano on 9/18/16.
 */

public class Fetch {
    private static RequestQueue mRequestQueue;

    public static void initialize(final Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        Fresco.initialize(context);
    }

    public static RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return mRequestQueue;
    }

    public static void send(Context context, Request req) {
        getRequestQueue(context).add(req);
    }

    public static void loadGIF(DraweeView view, String url) {
        Fetch.loadGIF(view, url, true);
    }

    public static void loadGIF(DraweeView view, String url, boolean autoplay) {
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(url))
                .setAutoPlayAnimations(autoplay)
                .build();
        view.setController(controller);
    }
}

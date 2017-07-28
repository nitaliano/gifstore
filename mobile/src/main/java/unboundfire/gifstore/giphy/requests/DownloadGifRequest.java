package unboundfire.gifstore.giphy.requests;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import unboundfire.gifstore.R;
import unboundfire.gifstore.giphy.models.Gif;
import unboundfire.gifstore.http.InputStreamRequest;
import unboundfire.gifstore.utils.Config;

/**
 * Created by nitaliano on 10/9/16.
 */

public class DownloadGifRequest extends InputStreamRequest {
    public static final String LOG_TAG = DownloadGifRequest.class.getSimpleName();

    private Gif mGIF;
    private Context mContext;
    private Response.Listener<Boolean> mListener;


    public DownloadGifRequest(Context context, Gif gif, Response.Listener<Boolean> listener) {
        super(gif.images.fixedHeightDownSampled.url, null);
        mContext = context;
        mGIF = gif;
        mListener = listener;
    }

    @Override
    public String getUrl() {
        Uri.Builder builder = Uri.parse(super.getUrl()).buildUpon();

        Map<String, String> params = getParams();
        for (Map.Entry<String, String> param : params.entrySet()) {
            builder.appendQueryParameter(param.getKey(), param.getValue());
        }

        return builder.toString();
    }

    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("api_key", Config.getString(R.string.giphy_api_key));
        return params;
    }

    @Override
    protected void onResponse(byte[] response) {
        try {
            FileOutputStream outputStream;
            outputStream = mContext.openFileOutput(mGIF.slug + ".gif", Context.MODE_PRIVATE);
            outputStream.write(response);
            outputStream.close();
            mListener.onResponse(true);
        } catch (Exception e) {
            Log.d(LOG_TAG, e.toString());
            mListener.onResponse(false);
        }
    }
}

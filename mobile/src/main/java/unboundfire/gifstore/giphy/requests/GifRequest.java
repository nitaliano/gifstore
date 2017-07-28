package unboundfire.gifstore.giphy.requests;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import unboundfire.gifstore.giphy.responses.GiphyObjectResponse;

/**
 * Created by nitaliano on 9/20/16.
 */

public class GifRequest extends BaseRequest<GiphyObjectResponse> {
    public final static String LOG_TAG = GifRequest.class.getSimpleName();
    public final static String URL = "http://api.giphy.com/v1/gifs/%s";

    private Response.Listener<GiphyObjectResponse> mListener;
    private Response.ErrorListener mErrorListener;
    private String mID;

    public GifRequest(String id, Response.Listener<GiphyObjectResponse> listener, Response.ErrorListener errorListener) {
        super(Request.Method.GET, String.format(URL, id), GiphyObjectResponse.class);
        mID = id;
        mListener = listener;
        mErrorListener = errorListener;
    }

    public String getGifID() {
        return mID;
    }

    protected void onResponse(GiphyObjectResponse response) {
        Log.d(LOG_TAG, response.toString());
        mListener.onResponse(response);
    }

    protected void onError(VolleyError error) {
        Log.d(LOG_TAG, error.toString());
        mErrorListener.onErrorResponse(error);
    }
}

package unboundfire.gifstore.giphy.requests;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import unboundfire.gifstore.giphy.responses.GiphyListResponse;

/**
 * Created by nitaliano on 9/18/16.
 */

public class TrendingRequest extends BaseRequest<GiphyListResponse> {
    public final static String LOG_TAG = TrendingRequest.class.getSimpleName();
    public final static String URL = "http://api.giphy.com/v1/gifs/trending";

    private Response.Listener<GiphyListResponse> mListener;
    private Response.ErrorListener mErrorListener;

    public TrendingRequest(Response.Listener<GiphyListResponse> listener, Response.ErrorListener errorListener) {
        super(Method.GET, URL, GiphyListResponse.class);

        mListener = listener;
        mErrorListener = errorListener;

        setTag(LOG_TAG);
    }

    protected void onResponse(GiphyListResponse response) {
        Log.d(LOG_TAG, response.toString());
        mListener.onResponse(response);
    }

    protected void onError(VolleyError error) {
        Log.d(LOG_TAG, error.toString());
        mErrorListener.onErrorResponse(error);
    }
}

package unboundfire.gifstore.giphy.requests;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.Map;
import java.util.Set;

import unboundfire.gifstore.giphy.responses.GiphyListResponse;

/**
 * Created by nitaliano on 9/25/16.
 */

public class GifsRequest extends BaseRequest<GiphyListResponse> {
    public final static String LOG_TAG = SearchRequest.class.getSimpleName();
    public final static String URL = "http://api.giphy.com/v1/gifs";

    private Response.Listener<GiphyListResponse> mListener;
    private Response.ErrorListener mErrorListener;
    private Set<String> mGifIDS;

    public GifsRequest(Set<String> gifIDS, Response.Listener<GiphyListResponse> listener, Response.ErrorListener errorListener) {
        super(Request.Method.GET, URL, GiphyListResponse.class);
        mGifIDS = gifIDS;
        mListener = listener;
        mErrorListener = errorListener;
    }

    protected void onResponse(GiphyListResponse response) {
        Log.d(LOG_TAG, response.toString());
        mListener.onResponse(response);
    }

    protected void onError(VolleyError error) {
        Log.d(LOG_TAG, error.toString());
        mErrorListener.onErrorResponse(error);
    }

    @Override
    protected Map<String, String> getParams() {
        Map<String, String> params = super.getParams();
        params.put("ids", TextUtils.join(",", mGifIDS));
        return params;
    }
}

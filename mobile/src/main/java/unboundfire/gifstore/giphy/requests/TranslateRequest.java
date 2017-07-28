package unboundfire.gifstore.giphy.requests;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.Map;

import unboundfire.gifstore.giphy.responses.GiphyObjectResponse;

/**
 * Created by nitaliano on 9/20/16.
 */

public class TranslateRequest extends BaseRequest<GiphyObjectResponse> {
    public static final String LOG_TAG = TranslateRequest.class.getSimpleName();
    public static final String URL = "http://api.giphy.com/v1/gifs/translate";

    private Response.Listener<GiphyObjectResponse> mListener;
    private Response.ErrorListener mErrorListener;
    private String mQuery;

    public TranslateRequest(String query, Response.Listener<GiphyObjectResponse> listener, Response.ErrorListener errorListener) {
        super(Method.GET, URL, GiphyObjectResponse.class);
        mQuery = query;
        mListener = listener;
        mErrorListener = errorListener;
    }

    public void setQuery(String query) {
        mQuery = query;
    }

    @Override
    protected Map<String, String> getParams() {
        Map<String, String> params = super.getParams();
        params.put("s", mQuery);
        return params;
    }

    @Override
    protected void onResponse(GiphyObjectResponse response) {
        Log.d(LOG_TAG, response.toString());
        mListener.onResponse(response);
    }

    @Override
    protected void onError(VolleyError error) {
        Log.d(LOG_TAG, error.toString());
        mErrorListener.onErrorResponse(error);
    }
}

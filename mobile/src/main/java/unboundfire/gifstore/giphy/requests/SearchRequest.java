package unboundfire.gifstore.giphy.requests;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.Map;

import unboundfire.gifstore.giphy.responses.GiphyListResponse;

/**
 * Created by nitaliano on 9/20/16.
 */

public class SearchRequest extends BaseRequest<GiphyListResponse> {
    public final static String LOG_TAG = SearchRequest.class.getSimpleName();
    public final static String URL = "http://api.giphy.com/v1/gifs/search";

    private Response.Listener<GiphyListResponse> mListener;
    private Response.ErrorListener mErrorListener;
    private String mQuery;

    public SearchRequest(Response.Listener<GiphyListResponse> listener, Response.ErrorListener errorListener) {
        this("", listener, errorListener);
    }

    public SearchRequest(String query, Response.Listener<GiphyListResponse> listener, Response.ErrorListener errorListener) {
        super(Request.Method.GET, URL, GiphyListResponse.class);
        setQuery(query);
        mListener = listener;
        mErrorListener = errorListener;
    }

    public void setQuery(String query) {
        mQuery = query;
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
        params.put("q", mQuery);
        return params;
    }
}

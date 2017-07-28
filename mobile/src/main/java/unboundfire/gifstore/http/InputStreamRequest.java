package unboundfire.gifstore.http;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * Created by nitaliano on 10/9/16.
 */

public abstract class InputStreamRequest extends Request<byte[]> {
    public InputStreamRequest(String url, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        setShouldCache(false);
    }

    protected abstract void onResponse(byte[] response);

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(byte[] response) {
        onResponse(response);
    }
}

package unboundfire.gifstore.http;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;

/**
 * Created by nitaliano on 9/18/16.
 */

public abstract class GSONRequest<T> extends Request<T> {
    private Gson mGSON;
    private Class<T> mClazz;

    public GSONRequest(int method, String url, Class<T> clazz) {
        super(method, url, null);
        mClazz = clazz;
        mGSON = new Gson();
    }

    protected abstract void onResponse(T response);

    protected abstract void onError(VolleyError error);

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(mGSON.fromJson(json, mClazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        return super.parseNetworkError(volleyError);
    }

    @Override
    protected void deliverResponse(T response) {
        onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        onError(error);
    }
}

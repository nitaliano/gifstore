package unboundfire.gifstore.giphy.requests;

import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

import unboundfire.gifstore.R;
import unboundfire.gifstore.giphy.models.Pagination;
import unboundfire.gifstore.http.GSONRequest;
import unboundfire.gifstore.utils.Config;

/**
 * Created by nitaliano on 9/19/16.
 */

public abstract class BaseRequest<T> extends GSONRequest<T> {
    private Pagination mPagination;

    public BaseRequest(int method, String url, Class<T> clazz) {
        super(method, url, clazz);
    }

    public void setPagination(Pagination pagination) {
        mPagination = pagination;
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

        Integer limit = getLimit();
        if (limit > 0) {
            params.put("limit", getLimit().toString());
        }

        if (hasPagination()) {
            Integer offset = mPagination.count + mPagination.offset;
            params.put("offset", offset.toString());
        }

        return params;
    }

    protected Integer getLimit() {
        return Config.getInteger(R.integer.giphy_api_limit);
    }

    private boolean hasPagination() {
        return mPagination != null && mPagination.count > 0;
    }
}

package unboundfire.gifstore.giphy.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nitaliano on 9/19/16.
 */

public class Pagination {
    @SerializedName("total_count")
    public Integer totalCount;

    @SerializedName("count")
    public Integer count;

    @SerializedName("offset")
    public Integer offset;
}

package unboundfire.gifstore.giphy.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import unboundfire.gifstore.giphy.models.Gif;
import unboundfire.gifstore.giphy.models.Meta;
import unboundfire.gifstore.giphy.models.Pagination;

/**
 * Created by nitaliano on 9/19/16.
 */

public class GiphyListResponse {
    @SerializedName("data")
    public List<Gif> gifs;

    @SerializedName("meta")
    public Meta meta;

    @SerializedName("pagination")
    public Pagination pagination;
}

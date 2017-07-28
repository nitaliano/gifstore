package unboundfire.gifstore.giphy.responses;

import com.google.gson.annotations.SerializedName;

import unboundfire.gifstore.giphy.models.Gif;
import unboundfire.gifstore.giphy.models.Meta;

/**
 * Created by nitaliano on 9/19/16.
 */

public class GiphyObjectResponse {
    @SerializedName("data")
    public Gif gif;

    @SerializedName("meta")
    public Meta meta;
}

package unboundfire.gifstore.giphy.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by nitaliano on 9/18/16.
 */

@Parcel
public class Gif {
    @SerializedName("type")
    public String type;

    @SerializedName("id")
    public String id;

    @SerializedName("slug")
    public String slug;

    @SerializedName("url")
    public String url;

    @SerializedName("bitly_gif_url")
    public String bitlyGIFURL;

    @SerializedName("bitly_url")
    public String bitlyURL;

    @SerializedName("embed_url")
    public String embedURL;

    @SerializedName("username")
    public String username;

    @SerializedName("source")
    public String source;

    @SerializedName("rating")
    public String rating;

    @SerializedName("images")
    public Images images;
}

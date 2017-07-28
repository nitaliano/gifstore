package unboundfire.gifstore.giphy.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by nitaliano on 9/19/16.
 */

@Parcel
public class Image {
    @SerializedName("url")
    public String url;

    @SerializedName("width")
    public Integer width;

    @SerializedName("height")
    public Integer height;

    @SerializedName("size")
    public Integer size;

    @SerializedName("mp4")
    public String mp4;

    @SerializedName("mp4_size")
    public String mp4Size;

    @SerializedName("webp")
    public String webp;

    @SerializedName("webp_size")
    public Integer webpSize;
}

package unboundfire.gifstore.giphy.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by nitaliano on 9/19/16.
 */

@Parcel
public class Images {
    @SerializedName("fixed_height")
    public Image fixedHeight;

    @SerializedName("fixed_height_still")
    public Image fixedHeightStill;

    @SerializedName("fixed_height_downsampled")
    public Image fixedHeightDownSampled;

    @SerializedName("fixed_width")
    public Image fixedWidth;

    @SerializedName("fixed_width_still")
    public Image fixedWidthStill;

    @SerializedName("fixed_width_downsampled")
    public Image fixedWidthDownSampled;

    @SerializedName("fixed_height_small")
    public Image fixedHeightSmall;

    @SerializedName("fixed_height_small_still")
    public Image fixedHeightSmallStill;

    @SerializedName("fixed_width_small")
    public Image fixedWidthSmall;

    @SerializedName("fixed_width_small_still")
    public Image fixedWidthSmallStill;

    @SerializedName("downsized")
    public Image downsized;

    @SerializedName("downsized_still")
    public Image downsizedStill;

    @SerializedName("downsized_large")
    public Image downsizedLarge;

    @SerializedName("downsized_medium")
    public Image downsizedMedium;

    @SerializedName("original")
    public Image original;

    @SerializedName("original_still")
    public Image originalStill;
}

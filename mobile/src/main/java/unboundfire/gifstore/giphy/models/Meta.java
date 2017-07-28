package unboundfire.gifstore.giphy.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nitaliano on 9/19/16.
 */

public class Meta {
    @SerializedName("status")
    public Integer status;

    @SerializedName("msg")
    public String message;

    @SerializedName("response_id")
    public String responseID;
}

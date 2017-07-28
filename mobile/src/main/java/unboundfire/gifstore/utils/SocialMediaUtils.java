package unboundfire.gifstore.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.io.File;

import unboundfire.gifstore.R;
import unboundfire.gifstore.giphy.models.Gif;

/**
 * Created by nitaliano on 10/9/16.
 */

public class SocialMediaUtils {
    public static void sendTextMessage(Context context, Gif gif) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra("sms_body", gif.slug);
        intent.putExtra(Intent.EXTRA_STREAM, getURIFromFile(context, getFileFromGIF(context, gif)));
        intent.setType("image/gif");
        context.startActivity(Intent.createChooser(intent, Config.getString(R.string.intent_share_title)));
    }

    public static void sendInstagram(Context context, Gif gif) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, getURIFromFile(context, getFileFromGIF(context, gif)));
        intent.setType("image/gif");
        intent.setPackage(Config.getString(R.string.instagram_pkg));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(Intent.createChooser(intent, Config.getString(R.string.intent_share_title)));
    }

    public static void sendPin(Context context, Gif gif) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, getURIFromFile(context, getFileFromGIF(context, gif)));
        intent.setType("image/gif");
        intent.setPackage(Config.getString(R.string.pintrest_pkg));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(Intent.createChooser(intent, Config.getString(R.string.intent_share_title)));
    }

    public static void sendTweet(Context context, Gif gif) {
        Intent intent = new TweetComposer.Builder(context)
                .image(getURIFromFile(context, getFileFromGIF(context, gif)))
                .createIntent();
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(Intent.createChooser(intent, Config.getString(R.string.intent_share_title)));
    }

    public static void sendFacebookMessage(Context context, Gif gif) {
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(gif.images.fixedHeightDownSampled.url))
                .build();
        ShareDialog dialog = new ShareDialog((Activity) context);
        dialog.show(content);
    }

    private static Uri getURIFromFile(Context context, File outputFile) {
        return FileProvider.getUriForFile(context, Config.getString(R.string.gifstore_pkg), outputFile);
    }

    private static File getFileFromGIF(Context context, Gif gif) {
        File path = new File(context.getFilesDir(), "");
        return new File(path, gif.slug + ".gif");
    }
}

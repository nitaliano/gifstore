package unboundfire.gifstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thedazzler.droidicon.badges.DroidiconBadge;
import com.thedazzler.droidicon.badges.FacebookDroidiconBadge;
import com.thedazzler.droidicon.badges.InstagramDroidiconBadge;
import com.thedazzler.droidicon.badges.PinterestDroidiconBadge;
import com.thedazzler.droidicon.badges.TwitterDroidiconBadge;

import org.parceler.Parcels;

import unboundfire.gifstore.enums.Extras;
import unboundfire.gifstore.giphy.models.Gif;
import unboundfire.gifstore.giphy.models.Image;
import unboundfire.gifstore.giphy.requests.DownloadGifRequest;
import unboundfire.gifstore.http.Fetch;
import unboundfire.gifstore.utils.SocialMediaUtils;
import unboundfire.gifstore.utils.Store;

public class GifDetailActivity extends AbstractActivity {
    private Gif mGIF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_detail);

        Intent intent = getIntent();
        mGIF = Parcels.unwrap(intent.getParcelableExtra(Extras.GIF.toString()));
        setToolbar(mGIF.slug);
        setUpButton();
        setCardboard();

        if (mGIF != null) {
            setGIF();
            Store.addRecentGIF(this, mGIF);
        }

        DroidiconBadge textMessageBadge = (DroidiconBadge) findViewById(R.id.btn_text);
        textMessageBadge.setOnClickListener(onSocialMediaClick(() -> SocialMediaUtils.sendTextMessage(this, mGIF)));

        TwitterDroidiconBadge twitterBadge = (TwitterDroidiconBadge) findViewById(R.id.btn_twitter);
        twitterBadge.setOnClickListener(onSocialMediaClick(() -> SocialMediaUtils.sendTweet(this, mGIF)));

        InstagramDroidiconBadge instagramBadge = (InstagramDroidiconBadge) findViewById(R.id.btn_instagram);
        instagramBadge.setOnClickListener(onSocialMediaClick(() -> SocialMediaUtils.sendInstagram(this, mGIF)));

        FacebookDroidiconBadge facebookBadge = (FacebookDroidiconBadge) findViewById(R.id.btn_facebook);
        facebookBadge.setOnClickListener(onSocialMediaClick(() -> SocialMediaUtils.sendFacebookMessage(this, mGIF)));

        PinterestDroidiconBadge pinterestDroidiconBadge = (PinterestDroidiconBadge) findViewById(R.id.btn_pintrest);
        pinterestDroidiconBadge.setOnClickListener(onSocialMediaClick(() -> SocialMediaUtils.sendPin(this, mGIF)));
    }

    private void setGIF() {
        Image image = mGIF.images.downsizedLarge;
        SimpleDraweeView gifView = (SimpleDraweeView) findViewById(R.id.gif);
        gifView.setAspectRatio((float) image.width / image.height);
        Fetch.loadGIF(gifView, image.url);
    }

    private View.OnClickListener onSocialMediaClick(final OnSend listener) {
        return (view) -> {
            DownloadGifRequest request = new DownloadGifRequest(this, mGIF, (isDownloaded) -> {
                if (isDownloaded) {
                    listener.send();
                } else {
                    Toast.makeText(this, "Cannot download " + mGIF.slug, Toast.LENGTH_SHORT).show();
                }
            });
            Fetch.send(this, request);
        };
    }

    private interface OnSend {
        void send();
    }
}

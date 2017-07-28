package unboundfire.gifstore;

import android.os.Bundle;

import com.google.vr.sdk.base.GvrActivity;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

/**
 * Created by nitaliano on 10/15/16.
 */

public class CardboardMainActivity extends GvrActivity {
    public static final String LOG_TAG = CardboardMainActivity.class.getSimpleName();

    private VrPanoramaView mPanoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardboard);
        mPanoView = (VrPanoramaView) findViewById(R.id.pano_view);
        mPanoView.setFullscreenButtonEnabled(true);
        mPanoView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    protected void onPause() {
        mPanoView.pauseRendering();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPanoView.resumeRendering();
    }

    @Override
    protected void onDestroy() {
        mPanoView.shutdown();
        super.onDestroy();
    }
}

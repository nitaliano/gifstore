package unboundfire.gifstore.listeners;

import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.Locale;

import unboundfire.gifstore.adapters.SectionsPagerAdapter;
import unboundfire.gifstore.fragments.GifGridFragment;

/**
 * Created by nitaliano on 10/10/16.
 */

public class PageChangeListener implements ViewPager.OnPageChangeListener {
    public final static String LOG_TAG = PageChangeListener.class.getSimpleName();

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private int mPreviousPagePosition;

    public PageChangeListener(SectionsPagerAdapter sectionsPagerAdapter) {
        mSectionsPagerAdapter = sectionsPagerAdapter;
        mPreviousPagePosition = -1;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d(LOG_TAG, String.format(Locale.ENGLISH, "Tabs page scrolled - %d", position));
    }

    @Override
    public void onPageSelected(int position) {
        Log.d(LOG_TAG, String.format(Locale.ENGLISH, "Tab page selected - %d", position));

        boolean isShowLoading = mPreviousPagePosition == -1 || mPreviousPagePosition > 2;
        mPreviousPagePosition = position;

        if (position != 0) {
            return;
        }

        GifGridFragment fragment = (GifGridFragment) mSectionsPagerAdapter.getRegisteredFragment(0);
        if (fragment == null) {
            return;
        }

        fragment.getData(isShowLoading);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d(LOG_TAG, String.format(Locale.ENGLISH, "Tabs Scroll state changed - %d", state));
    }
}

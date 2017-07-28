package unboundfire.gifstore.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import unboundfire.gifstore.enums.Tabs;
import unboundfire.gifstore.fragments.GifGridFragment;

/**
 * Created by nitaliano on 9/24/16.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private Tabs[] mTabs;
    private SparseArray<Fragment> mRegisteredFragments;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
        mTabs = Tabs.values();
        mRegisteredFragments = new SparseArray<>();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        mRegisteredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object obj) {
        mRegisteredFragments.remove(position);
        super.destroyItem(container, position, obj);
    }

    @Override
    public Fragment getItem(int position) {
        Tabs tab = mTabs[position];
        return GifGridFragment.newInstance(tab, tab.toString());
    }

    public Fragment getRegisteredFragment(int position) {
        return mRegisteredFragments.get(position);
    }

    @Override
    public int getCount() {
        return mTabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs[position].toString();
    }
}

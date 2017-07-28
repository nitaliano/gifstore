package unboundfire.gifstore;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import unboundfire.gifstore.adapters.SectionsPagerAdapter;
import unboundfire.gifstore.listeners.PageChangeListener;
import unboundfire.gifstore.utils.Config;

public class MainActivity extends AbstractActivity {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar(Config.getString(R.string.app_name));
        setViewPager();
        setCardboard();
    }

    private void setViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.container);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new PageChangeListener(mSectionsPagerAdapter));
    }
}

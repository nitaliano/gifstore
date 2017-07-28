package unboundfire.gifstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import unboundfire.gifstore.enums.Extras;
import unboundfire.gifstore.fragments.GifGridFragment;

public class GifSearchResultsActivity extends AbstractActivity {
    public static final String LOG_TAG = GifSearchResultsActivity.class.getSimpleName();

    private String mSearchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_grid);

        Intent intent = getIntent();
        mSearchQuery = intent.getStringExtra(Extras.GIF_SEARCH_QUERY.toString());

        setToolbar(mSearchQuery);
        setUpButton();
        setGrid();
        setCardboard();

        Log.d(LOG_TAG, "GifSearchResultsActivity.onCreate");
    }

    private void setGrid() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.gif_scroll_container, GifGridFragment.newInstance(null, mSearchQuery), "search")
                .commit();
    }
}

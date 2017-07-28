package unboundfire.gifstore;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;

import java.util.ArrayList;
import java.util.List;

import unboundfire.gifstore.enums.Extras;

/**
 * Created by nitaliano on 10/2/16.
 */

public class AbstractActivity extends AppCompatActivity {
    private EditText mSearch;
    private Button mCardboardBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();

        // make keyboard start hidden
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // apply fade transition between activities
        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        window.setExitTransition(fade);
        window.setEnterTransition(fade);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setToolbar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title.toUpperCase());
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
        }

        mSearch = (EditText) findViewById(R.id.gif_search);
        mSearch.setOnEditorActionListener((view, actionID, event) -> {
            if (actionID == EditorInfo.IME_ACTION_SEARCH) {
                Intent intent = new Intent(this, GifSearchResultsActivity.class);
                intent.putExtra(Extras.GIF_SEARCH_QUERY.toString(), mSearch.getText().toString());
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

    public void setCardboard() {
        mCardboardBtn = (Button) findViewById(R.id.btn_cardboard);

        mCardboardBtn.setOnClickListener((view) -> {
            Intent intent = new Intent(this, CardboardMainActivity.class);
            startActivity(intent);
        });
    }

    public void setUpButton() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public Pair<View, String>[] getSharedElements(Integer[] sharedElementIDs) {
        List<Pair<View, String>> sharedElements = new ArrayList<>();

        if (sharedElementIDs != null) {
            for (Integer sharedElementID : sharedElementIDs) {
                View view = findViewById(sharedElementID);
                sharedElements.add(Pair.create(view, view.getTransitionName()));
            }
        }

        View decorView = getWindow().getDecorView();
        View appBar = findViewById(R.id.appbar_layout);
        if (appBar != null) {
            sharedElements.add(Pair.create(appBar, appBar.getTransitionName()));
        }

        View navigationBar = decorView.findViewById(android.R.id.navigationBarBackground);
        if (navigationBar != null) {
            sharedElements.add(Pair.create(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME));
        }

        View statusBar = decorView.findViewById(android.R.id.statusBarBackground);
        if (statusBar != null) {
            sharedElements.add(Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME));
        }

        return sharedElements.toArray(new Pair[sharedElements.size()]);
    }
}

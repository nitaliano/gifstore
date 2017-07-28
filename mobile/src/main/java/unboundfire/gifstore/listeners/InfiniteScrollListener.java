package unboundfire.gifstore.listeners;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by nitaliano on 9/24/16.
 */

public abstract class InfiniteScrollListener extends RecyclerView.OnScrollListener {
    public static final String LOG_TAG = InfiniteScrollListener.class.getSimpleName();

    private GridLayoutManager mGridLayoutManager;

    private int mVisibleThreshold = 4;
    private int mPreviousItemCount = 0;
    private boolean mIsLoading = true;

    public InfiniteScrollListener(GridLayoutManager gridLayoutManager) {
        mGridLayoutManager = gridLayoutManager;
        mVisibleThreshold *= mGridLayoutManager.getSpanCount();
    }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        Log.d(LOG_TAG, "onScrolled");

        // leave early if we are scrolling up
        if (dy < 0) {
            return;
        }

        int lastVisibleItemPosition = mGridLayoutManager.findLastVisibleItemPosition();
        int totalItemCount = mGridLayoutManager.getItemCount();

        if (totalItemCount < mPreviousItemCount) {
            mPreviousItemCount = totalItemCount;

            if (totalItemCount == 0) {
                mIsLoading = true;
            }
        }

        if (mIsLoading && (totalItemCount > mPreviousItemCount)) {
            mIsLoading = false;
            mPreviousItemCount = totalItemCount;
        }

        if (!mIsLoading && (lastVisibleItemPosition + mVisibleThreshold) > totalItemCount) {
            onLoadMore(totalItemCount);
            mIsLoading = true;
        }
    }

    public abstract boolean onLoadMore(int totalItemCount);
}

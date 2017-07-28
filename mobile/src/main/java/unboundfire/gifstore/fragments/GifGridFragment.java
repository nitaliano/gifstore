package unboundfire.gifstore.fragments;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.facebook.drawee.view.SimpleDraweeView;
import com.victor.loading.newton.NewtonCradleLoading;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import unboundfire.gifstore.AbstractActivity;
import unboundfire.gifstore.GifDetailActivity;
import unboundfire.gifstore.GifSearchResultsActivity;
import unboundfire.gifstore.R;
import unboundfire.gifstore.adapters.GifGridRecyclerViewAdapter;
import unboundfire.gifstore.enums.Extras;
import unboundfire.gifstore.enums.GifGridFragmentType;
import unboundfire.gifstore.enums.Tabs;
import unboundfire.gifstore.giphy.models.Gif;
import unboundfire.gifstore.giphy.models.Pagination;
import unboundfire.gifstore.giphy.requests.BaseRequest;
import unboundfire.gifstore.giphy.requests.GifsRequest;
import unboundfire.gifstore.giphy.requests.SearchRequest;
import unboundfire.gifstore.giphy.requests.TranslateRequest;
import unboundfire.gifstore.giphy.requests.TrendingRequest;
import unboundfire.gifstore.giphy.responses.GiphyListResponse;
import unboundfire.gifstore.giphy.responses.GiphyObjectResponse;
import unboundfire.gifstore.http.Fetch;
import unboundfire.gifstore.listeners.InfiniteScrollListener;
import unboundfire.gifstore.utils.Config;
import unboundfire.gifstore.utils.Store;

/**
 * Created by nitaliano on 9/22/16.
 */

public class GifGridFragment extends Fragment {
    public static final String LOG_TAG = GifGridFragment.class.getSimpleName();

    private static final String GIF_QUERY = "GIF_QUERY";
    private static final String FRAGMENT_TYPE = "FRAGMENT_TYPE";

    private Context mContext;
    private GifGridRecyclerViewAdapter mAdapter;
    private RecyclerView mGifRecyclerView;
    private NewtonCradleLoading mLoadingIcon;
    private boolean mIsLoadingData;
    private Pagination mPagination;
    private GifGridFragmentType mType;
    private String mGifQuery;

    public GifGridFragment() {
    }

    public static Fragment newInstance(Tabs tab, String gifQuery) {
        GifGridFragment fragment = new GifGridFragment();
        Bundle args = new Bundle();

        if (tab == null) {
            args.putInt(FRAGMENT_TYPE, GifGridFragmentType.SEARCH.value());
            args.putString(GIF_QUERY, gifQuery);
        } else if (tab.equals(Tabs.TRENDING)) {
            args.putInt(FRAGMENT_TYPE, GifGridFragmentType.TRENDING.value());
            args.putString(GIF_QUERY, "");
        } else if (tab.equals(Tabs.RECENT)) {
            args.putInt(FRAGMENT_TYPE, GifGridFragmentType.RECENT.value());
            args.putString(GIF_QUERY, "");
        } else {
            args.putInt(FRAGMENT_TYPE, GifGridFragmentType.TITLE.value());
            args.putString(GIF_QUERY, tab.toString());
        }

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();

        mGifQuery = getArguments().getString(GIF_QUERY);
        mType = GifGridFragmentType.get(getArguments().getInt(FRAGMENT_TYPE));

        View rootView = inflater.inflate(R.layout.fragment_gif_grid, container, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        mGifRecyclerView = (RecyclerView) rootView.findViewById(R.id.gif_list);

        mLoadingIcon = (NewtonCradleLoading) rootView.findViewById(R.id.gif_list_loading);
        mLoadingIcon.setLoadingColor(Color.parseColor(Config.getString(R.color.colorAccent)));
        mLoadingIcon.setVisibility(View.INVISIBLE);

        mAdapter = new GifGridRecyclerViewAdapter(onGifClick());
        mGifRecyclerView.setAdapter(mAdapter);
        getData(true);

        mGifRecyclerView.setHasFixedSize(true);
        mGifRecyclerView.setLayoutManager(gridLayoutManager);

        if (mType.equals(GifGridFragmentType.SEARCH) || mType.equals(GifGridFragmentType.TRENDING)) {
            mGifRecyclerView.addOnScrollListener(onScroll(gridLayoutManager));
        }

        return rootView;
    }

    public void getData(boolean isInitialLoad) {
        if (isInitialLoad) {
            showLoading();
        }

        if (mType.equals(GifGridFragmentType.TRENDING)) {
            getInfiniteListData("");
        } else if (mType.equals(GifGridFragmentType.RECENT)) {
            getRecentListData();
        } else if (mType.equals(GifGridFragmentType.SEARCH)) {
            getInfiniteListData(mGifQuery);
        } else {
            getTitleData();
        }
    }

    private void getRecentListData() {
        Activity activity = getActivity();
        Set<String> gifIDS = Store.getRecentGIFS(activity);
        Fetch.send(activity, new GifsRequest(gifIDS, onGifsResponse(), onGifsError()));
    }

    private void getInfiniteListData(String searchQuery) {
        BaseRequest<GiphyListResponse> req;

        if (searchQuery.isEmpty()) {
            req = new TrendingRequest(onGifsResponse(), onGifsError());
        } else {
            req = new SearchRequest(searchQuery, onGifsResponse(), onGifsError());
        }

        if (mPagination != null) {
            req.setPagination(mPagination);
        }

        mIsLoadingData = true;
        Fetch.send(mContext, req);
    }

    private void getTitleData() {
        String[] titles = Config.getStringArrayByName(mGifQuery);

        int position = 0;
        for (String title : titles) {
            BaseRequest<GiphyObjectResponse> req = new TranslateRequest(title, onGifResponse(title, position++), onGifsError());
            Fetch.send(mContext, req);
        }
    }

    private InfiniteScrollListener onScroll(GridLayoutManager gridLayoutManager) {
        return new InfiniteScrollListener(gridLayoutManager) {
            @Override
            public boolean onLoadMore(int totalItemCount) {
                if (mIsLoadingData) {
                    return false;
                }
                getData(false);
                return true;
            }
        };
    }

    private OnClickListener onGifClick() {
        return (view, gif) -> {
            Intent intent;

            // open search results activity if we are on a title page
            if (mType.equals(GifGridFragmentType.TITLE)) {
                intent = new Intent(getActivity(), GifSearchResultsActivity.class);
                intent.putExtra(Extras.GIF_SEARCH_QUERY.toString(), mAdapter.getTitle(gif));
            } else { // open gif detail activity
                intent = new Intent(getActivity(), GifDetailActivity.class);
                intent.putExtra(Extras.GIF.toString(), Parcels.wrap(gif));

                AbstractActivity activity = (AbstractActivity) getActivity();
                Pair<View, String>[] sharedElements = activity.getSharedElements(null);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sharedElements);
                startActivity(intent, options.toBundle());
                return;
            }

            startActivity(intent);
        };
    }

    private Response.Listener<GiphyObjectResponse> onGifResponse(String title, int position) {
        return (response) -> {
            mAdapter.setItem(response.gif, title, position);
            hideLoading();
        };
    }

    private Response.Listener<GiphyListResponse> onGifsResponse() {
        return (response) -> {
            mPagination = response.pagination;

            if (mType.equals(GifGridFragmentType.RECENT)) {
                mAdapter.resetItems(response.gifs);
            } else {
                mAdapter.setItems(response.gifs);
            }

            mIsLoadingData = false;
            hideLoading();
        };
    }

    private Response.ErrorListener onGifsError() {
        return (error) -> {
            Log.d(LOG_TAG, error.toString());
            mIsLoadingData = false;
        };
    }

    private void showLoading() {
        mLoadingIcon.start();
        mLoadingIcon.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        mLoadingIcon.setVisibility(View.INVISIBLE);

        if (mLoadingIcon.isStart()) {
            mLoadingIcon.stop();
        }
    }

    public interface OnClickListener {
        void onClick(SimpleDraweeView view, Gif gif);
    }
}

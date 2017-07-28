package unboundfire.gifstore.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import unboundfire.gifstore.R;
import unboundfire.gifstore.fragments.GifGridFragment;
import unboundfire.gifstore.giphy.models.Gif;
import unboundfire.gifstore.http.Fetch;

public class GifGridRecyclerViewAdapter extends RecyclerView.Adapter<GifGridRecyclerViewAdapter.ViewHolder> {
    public static final String LOG_TAG = GifGridRecyclerViewAdapter.class.getSimpleName();

    private final List<Gif> mValues;
    private final List<String> mTitles;
    private final GifGridFragment.OnClickListener mListener;

    public GifGridRecyclerViewAdapter(GifGridFragment.OnClickListener listener) {
        this(new ArrayList<>(), listener);
    }

    public GifGridRecyclerViewAdapter(List<Gif> items, GifGridFragment.OnClickListener listener) {
        mValues = items;
        mListener = listener;
        mTitles = new ArrayList<>();
    }

    public void setItem(Gif item, String title) {
        setItem(item, title, -1);
    }

    public void setItem(Gif item, String title, int position) {
        mValues.add(item);
        mTitles.add(title);
        notifyItemInserted(mValues.size() - 1);
    }

    public void setItems(List<Gif> items) {
        int fromIndex = mValues.size() - 1;
        mValues.addAll(items);
        notifyItemRangeInserted(fromIndex, mValues.size());
    }

    public void resetItems(List<Gif> items) {
        mValues.clear();
        mValues.addAll(items);
        notifyDataSetChanged();
    }

    public String getTitle(Gif gif) {
        int position = mValues.indexOf(gif);
        return mTitles.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_gif_grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Gif gif = mValues.get(position);
        if (gif != null) {
            holder.setGIF(gif);
            holder.mView.setOnClickListener((v) -> mListener.onClick(holder.mGifView, holder.mGif));
        }

        String title = "";
        try {
            title = mTitles.get(position);

            if (!title.isEmpty()) {
                holder.setTitle(title);
            }
        } catch (IndexOutOfBoundsException e) {
            Log.d(LOG_TAG, e.toString());
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public SimpleDraweeView mGifView;
        public TextView mTitleView;
        public Gif mGif;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) mView.findViewById(R.id.gif_title);
            mGifView = (SimpleDraweeView) mView.findViewById(R.id.gif);
            mGifView.setAspectRatio(1.33f);
        }

        public void setGIF(Gif gif) {
            mGif = gif;
            Fetch.loadGIF(mGifView, mGif.images.fixedWidthDownSampled.url);
        }

        public void setTitle(String title) {
            mTitleView.setText(title);
            mTitleView.setVisibility(View.VISIBLE);
        }
    }
}

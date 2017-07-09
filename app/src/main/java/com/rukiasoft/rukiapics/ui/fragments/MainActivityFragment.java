package com.rukiasoft.rukiapics.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rukiasoft.rukiapics.R;
import com.rukiasoft.rukiapics.model.PicturePojo;
import com.rukiasoft.rukiapics.ui.activities.MainActivity;
import com.rukiasoft.rukiapics.ui.adapters.FlickrRecyclerViewAdapter;
import com.rukiasoft.rukiapics.ui.scroll.FastScroller;
import com.rukiasoft.rukiapics.ui.ui.presenters.MainFragmentPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements FlickrRecyclerViewAdapter.OnCardClickListener{

    @BindView(R.id.tag_search)
    View parent;
    @BindView(R.id.send_button)
    FloatingActionButton sendButton;
    @BindView(R.id.pics_recycler_view)
    RecyclerView mRecyclerView;
    @Nullable
    @BindView((R.id.fastscroller))
    FastScroller fastScroller;

    MainFragmentPresenter presenter;
    Unbinder unbinder;



    private static final String TAG = MainActivityFragment.class.getSimpleName();

    public MainActivityFragment() {
        presenter = new MainFragmentPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);

        if(presenter.isTagShown()){
            ((MainActivity)getActivity()).getTagButton().setVisibility(View.INVISIBLE);
            parent.setVisibility(View.VISIBLE);
        }
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCardClick(View view, PicturePojo pictureItem) {
        Log.d(TAG, "clicked called in fragment");
    }


    public MainFragmentPresenter getPresenter() {
        return presenter;
    }

    public View getParent() {
        return parent;
    }

    public void setParent(View parent) {
        this.parent = parent;
    }

    public FloatingActionButton getSendButton() {
        return sendButton;
    }

    public void setSendButton(FloatingActionButton sendButton) {
        this.sendButton = sendButton;
    }

    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    @Nullable
    public FastScroller getFastScroller() {
        return fastScroller;
    }

    public void setFastScroller(@Nullable FastScroller fastScroller) {
        this.fastScroller = fastScroller;
    }
}

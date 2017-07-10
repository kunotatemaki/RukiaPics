package com.rukiasoft.rukiapics.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.rukiasoft.rukiapics.R;
import com.rukiasoft.rukiapics.model.PicturePojo;
import com.rukiasoft.rukiapics.ui.activities.MainActivity;
import com.rukiasoft.rukiapics.ui.adapters.FlickrRecyclerViewAdapter;
import com.rukiasoft.rukiapics.ui.scroll.FastScroller;
import com.rukiasoft.rukiapics.ui.ui.presenters.MainFragmentPresenter;
import com.rukiasoft.rukiapics.utilities.DisplayUtility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.tag_input)EditText tagInput;


    MainFragmentPresenter presenter;
    Unbinder unbinder;

    public enum Order {
        PUBLISHED,
        TAKEN
    }


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
        presenter.showDialog(pictureItem);
        //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pictureItem.getUrlM()));
        //startActivity(browserIntent);
    }

    @OnClick(R.id.send_button)
    public void sendTag(View view){
        DisplayUtility.hideKeyboard(getActivity());
        String tags = tagInput.getText().toString();
        //// TODO: 10/7/17 quitar esto
        tags = "perro";
        if(tags == null || tags.isEmpty()){
            ((MainActivity)getActivity()).getPresenter().hideTagInput();
            Snackbar.make(view, getString(R.string.no_tags), Snackbar.LENGTH_SHORT).show();
            return;
        }
        ((MainActivity)getActivity()).getPresenter().getPicsByTags(tags);
        ((MainActivity)getActivity()).getPresenter().hideTagInput();
        tagInput.setText("");
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

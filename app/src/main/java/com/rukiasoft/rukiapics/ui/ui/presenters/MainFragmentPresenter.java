package com.rukiasoft.rukiapics.ui.ui.presenters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.EditText;

import com.rukiasoft.rukiapics.model.PicturePojo;
import com.rukiasoft.rukiapics.model.RevealCoordinates;
import com.rukiasoft.rukiapics.ui.adapters.FlickrRecyclerViewAdapter;
import com.rukiasoft.rukiapics.ui.fragments.MainActivityFragment;
import com.rukiasoft.rukiapics.utilities.BaseActivityTools;
import com.rukiasoft.rukiapics.utilities.DisplayUtility;
import com.rukiasoft.rukiapics.utilities.LogHelper;

import java.util.List;

/**
 * Created by Roll on 8/7/17.
 */

public class MainFragmentPresenter {

    private static final String TAG = LogHelper.makeLogTag(MainFragmentPresenter.class);

    private boolean tagShown;
    private RevealCoordinates revealCoordinates;

    private MainActivityFragment fragment;

    public MainFragmentPresenter(MainActivityFragment fragment) {
        this.fragment = fragment;
        revealCoordinates = new RevealCoordinates();
    }

    public boolean isTagShown() {
        return tagShown;
    }

    public void setTagShown(boolean tagShown) {
        this.tagShown = tagShown;
    }

    public void showInputTag(Activity activity, View sender){

        /*cx = (view.getRight() + view.getLeft()) / 2;
        int margin = DisplayUtility.getScreenWidth(getActivity())- cx;
        cx = parent.getRight() - margin;
        cy = (view.getTop() + view.getBottom()) / 2;
        margin = DisplayUtility.getScreenHeight(getActivity()) - cy;
        cy = parent.getHeight() - margin;*/
        revealCoordinates.setCx((fragment.getSendButton().getRight() + fragment.getSendButton().getLeft()) / 2);
        revealCoordinates.setCy((fragment.getSendButton().getTop() + fragment.getSendButton().getBottom()) / 2);
        revealCoordinates.setInitialRadius((sender.getRight() - sender.getLeft()) / 2);

        revealCoordinates.setEndRadius( Math.max(DisplayUtility.getScreenWidth(activity), DisplayUtility.getScreenHeight(activity)));
        Animator anim =
                ViewAnimationUtils.createCircularReveal(fragment.getParent(), revealCoordinates.getCx(), revealCoordinates.getCy(),
                        revealCoordinates.getInitialRadius(), revealCoordinates.getEndRadius());

        // make the view visible and start the animation
        sender.setVisibility(View.INVISIBLE);
        fragment.getParent().setVisibility(View.VISIBLE);
        anim.setDuration(500);

        anim.start();
        fragment.getPresenter().setTagShown(true);

    }

    public void hideInputTag(final View view){
        Animator anim =
                ViewAnimationUtils.createCircularReveal(fragment.getParent(), revealCoordinates.getCx(), revealCoordinates.getCy(),
                        revealCoordinates.getEndRadius(), revealCoordinates.getInitialRadius());

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fragment.getParent().setVisibility(View.INVISIBLE);
                view.setVisibility(View.VISIBLE);
            }
        });
        anim.setDuration(500);
        anim.start();
        fragment.getPresenter().setTagShown(false);

    }


    public void setData(List<PicturePojo> items){
        if(fragment.isResumed()) {
            BaseActivityTools tools = new BaseActivityTools();
            tools.hideRefreshLayout(fragment.getActivity());
        }
        FlickrRecyclerViewAdapter adapter = new FlickrRecyclerViewAdapter(fragment.getContext(), items);
        adapter.setHasStableIds(true);
        adapter.setOnCardClickListener(fragment);
        fragment.getmRecyclerView().setAdapter(adapter);
        int columnCount = calculateNoOfColumns(fragment.getContext());
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);


        fragment.getmRecyclerView().setLayoutManager(layoutManager);


        if(fragment.getFastScroller() != null) {
            fragment.getFastScroller().setRecyclerView(fragment.getmRecyclerView());
        }


    }


    private static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 180);
    }


}

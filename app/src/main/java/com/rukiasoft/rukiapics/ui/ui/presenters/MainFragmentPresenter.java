package com.rukiasoft.rukiapics.ui.ui.presenters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.AbsListView;

import com.rukiasoft.rukiapics.R;
import com.rukiasoft.rukiapics.model.PicturePojo;
import com.rukiasoft.rukiapics.model.RevealCoordinates;
import com.rukiasoft.rukiapics.ui.activities.MainActivity;
import com.rukiasoft.rukiapics.ui.adapters.FlickrRecyclerViewAdapter;
import com.rukiasoft.rukiapics.ui.fragments.MainActivityFragment;
import com.rukiasoft.rukiapics.utilities.BaseActivityTools;
import com.rukiasoft.rukiapics.utilities.DisplayUtility;
import com.rukiasoft.rukiapics.utilities.ListDatePublishedComparator;
import com.rukiasoft.rukiapics.utilities.ListDateTakenComparator;
import com.rukiasoft.rukiapics.utilities.LogHelper;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.ButterKnife;

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

    private void setTagShown(boolean tagShown) {
        this.tagShown = tagShown;
    }

    void showInputTag(Activity activity, View sender){

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

    void hideInputTag(final View view){
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


        //set fast scroller (with dog thumbnail)
        if(fragment.getFastScroller() != null) {
            fragment.getFastScroller().setRecyclerView(fragment.getmRecyclerView());
        }

        //animate tag button on scroll
        fragment.getmRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0) {
                    ((MainActivity) fragment.getActivity()).getTagButton().hide();
                }else if (dy < 0) {
                    ((MainActivity) fragment.getActivity()).getTagButton().show();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    ((MainActivity)fragment.getActivity()).getTagButton().show();
                }
            }
        });

    }


    private static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 180);
    }

    public void orderList(MainActivityFragment.Order type){
        FlickrRecyclerViewAdapter adapter = (FlickrRecyclerViewAdapter)fragment.getmRecyclerView().getAdapter();
        if(adapter == null){
            return;
        }
        Comparator comparator = null;

        //get comparator
        if(type == MainActivityFragment.Order.PUBLISHED){
            comparator = new ListDatePublishedComparator();
        }else if(type == MainActivityFragment.Order.TAKEN){
            comparator = new ListDateTakenComparator();
        }

        //order
        List<PicturePojo> list = ((FlickrRecyclerViewAdapter)fragment.getmRecyclerView().getAdapter()).getItems();
        Collections.sort(list, comparator);
        setData(list);
    }


    public void showDialog(PicturePojo item) {


        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getActivity());
        // Get the layout inflater
        LayoutInflater inflater = fragment.getActivity().getLayoutInflater();


        final View detailsView = inflater.inflate(R.layout.picture_details, null);

        ButterKnife.bind(fragment, detailsView);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(detailsView);
                // Add action buttons
                /*.setPositiveButton(fragment.getResources().getString(R.string.aceptar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        CheckBox swipe = (CheckBox) detailsView.findViewById(R.id.checkbox_swipe);
                        hideSwipeDialog(swipe.isChecked());
                        showSwipe = false;
                    }
                });*/

        builder.show();
    }
}

package com.rukiasoft.rukiapics.ui.ui.presenters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.rukiasoft.rukiapics.R;
import com.rukiasoft.rukiapics.model.PicturePojo;
import com.rukiasoft.rukiapics.model.RevealCoordinates;
import com.rukiasoft.rukiapics.ui.activities.MainActivity;
import com.rukiasoft.rukiapics.ui.adapters.FlickrRecyclerViewAdapter;
import com.rukiasoft.rukiapics.ui.fragments.MainActivityFragment;
import com.rukiasoft.rukiapics.utilities.BaseActivityTools;
import com.rukiasoft.rukiapics.utilities.DisplayUtility;
import com.rukiasoft.rukiapics.utilities.LogHelper;
import com.rukiasoft.rukiapics.utilities.RukiaConstants;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.State;

/**
 * Created by Roll on 8/7/17.
 */

public class MainFragmentPresenter {

    private static final String TAG = LogHelper.makeLogTag(MainFragmentPresenter.class);

    @Nullable
    @BindView(R.id.details_title)
    TextView title;
    @Nullable
    @BindView(R.id.details_by_tv)
    TextView owner;
    @Nullable
    @BindView(R.id.details_published_tv)
    TextView published;
    @Nullable
    @BindView(R.id.details_taken_tv)
    TextView taken;
    @Nullable
    @BindView(R.id.browser_button)
    Button browserButton;
    @Nullable
    @BindView(R.id.gallery_button)
    Button galleryButton;


    private RevealCoordinates revealCoordinates;

    private MainActivityFragment fragment;

    public MainFragmentPresenter(MainActivityFragment fragment) {
        this.fragment = fragment;
        revealCoordinates = new RevealCoordinates();
    }

    public boolean isTagShown() {
        return fragment.isTagShown();
    }

    private void setTagShown(boolean tagShown) {
        fragment.setTagShown(tagShown);
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

    public void orderList(RukiaConstants.Order type){

        List<PicturePojo> list = null;
        //get comparator
        if(type == RukiaConstants.Order.PUBLISHED){
            list = fragment.getListPublished();
        }else if(type == RukiaConstants.Order.TAKEN){
            list = fragment.getListTaken();
        }

        if(list != null) {
            setData(list);
        }else{
            ((MainActivity)fragment.getActivity()).getPresenter().getPicsByTags(fragment.getLastTags(), type);
        }
    }


    public void showDialog(PicturePojo item) {


        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getActivity());
        // Get the layout inflater
        LayoutInflater inflater = fragment.getActivity().getLayoutInflater();


        final View detailsView = inflater.inflate(R.layout.picture_details, null);


        ButterKnife.bind(this, detailsView);
        if(title != null) {
            //set pic's title
            title.setText(item.getTitle());
        }
        if(owner != null) {
            //set pic's owner
            owner.setText(item.getOwnername());
        }
        if(published != null) {
            //set pic's published date
            SimpleDateFormat myFormat = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
            Date datePublished = new Date(Long.valueOf(item.getDateupload()));
            published.setText(myFormat.format(datePublished));
        }
        if(taken != null) {
            //set pic's taken date
            taken.setText(item.getDatetaken());
        }
        //pass the item to the buttons to get it when clicked
        if(browserButton != null){
            browserButton.setTag(item);
        }
        if(galleryButton != null){
            galleryButton.setTag(item);
        }

        builder.setView(detailsView)
                .setNegativeButton(fragment.getResources().getString(R.string.close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });


        builder.show();
    }

    @OnClick(R.id.browser_button)
    public void openInBrowserClicked(View view){
        PicturePojo picture = (PicturePojo) view.getTag();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(picture.getUrlM()));
        fragment.getActivity().startActivity(browserIntent);
    }

}

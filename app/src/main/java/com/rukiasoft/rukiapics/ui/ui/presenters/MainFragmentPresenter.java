package com.rukiasoft.rukiapics.ui.ui.presenters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
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
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private AlertDialog dialog = null;

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

    public void orderList(RukiaConstants.Order order){

        List<PicturePojo> list = null;
        fragment.setLastOrder(order);
        //get comparator
        if(order == RukiaConstants.Order.PUBLISHED){
            list = fragment.getListPublished();
        }else if(order == RukiaConstants.Order.TAKEN){
            list = fragment.getListTaken();
        }

        if(list != null) {
            setData(list);
        }else{
            ((MainActivity)fragment.getActivity()).getPresenter().getPicsByTags(fragment.getLastTags(), order);
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
                fragment.setDialogItem(null);
                dialog.cancel();
            }
        });
        //store reference to dialog
        dialog = builder.create();

        dialog.show();
    }

    @OnClick(R.id.browser_button)
    void openInBrowserClicked(View view){
        PicturePojo picture = (PicturePojo) view.getTag();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(picture.getUrlM()));
        fragment.getActivity().startActivity(browserIntent);
        if(dialog != null) {
            dialog.dismiss();
        }
    }

    @OnClick(R.id.gallery_button)
    void openSaveInGalleryClicked(View view){
        fragment.getPermissionsPresenter().createPermissionListeners(fragment.getActivity(), this);
        fragment.getPermissionsPresenter().askForAllPermissions(fragment.getActivity());

    }

    public void saveImageToGallery() {
        Log.d(TAG, "Logic save image to gallery");
        if(dialog != null) {
            dialog.dismiss();
        }
        // Save image to gallery
        if(fragment.getDialogItem() == null){
            showNotAllowedStorage();
        }
        View view = getViewFromRecyclerKnowingTheItem(fragment.getDialogItem());
        ImageView imageView = view.findViewById(R.id.pic_item);
        if(imageView != null){
            Bitmap bitmap = ((GlideBitmapDrawable)imageView.getDrawable()).getBitmap();
            String savedImageURL = MediaStore.Images.Media.insertImage(
                    fragment.getActivity().getContentResolver(),
                    bitmap,
                    fragment.getDialogItem().getTitle(),
                    fragment.getDialogItem().getOwnername()
            );
            Log.d(TAG, savedImageURL);
            ContentValues values = new ContentValues();

            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
            values.put(MediaStore.Images.Media.DATE_MODIFIED, System.currentTimeMillis());
            values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.MediaColumns.DATA, savedImageURL);

            fragment.getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Snackbar.make(((MainActivity)fragment.getActivity()).getTagButton(), fragment.getActivity().getString(R.string.image_saved),
                Snackbar.LENGTH_SHORT).show();

        }else{
            showNotAllowedStorage();
        }


    }

    private View getViewFromRecyclerKnowingTheItem(PicturePojo dialogItem) {
        FlickrRecyclerViewAdapter adapter = (FlickrRecyclerViewAdapter)fragment.getmRecyclerView().getAdapter();
        int position = adapter.getPositionOfItem(dialogItem);
        return fragment.getmRecyclerView().getLayoutManager().findViewByPosition(position);
    }

    private void showNotAllowedStorage(){
        Snackbar.make(((MainActivity)fragment.getActivity()).getTagButton(), fragment.getContext().getString(R.string.operation_not_allowed),
                BaseTransientBottomBar.LENGTH_SHORT).show();
    }
}

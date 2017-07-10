package com.rukiasoft.rukiapics.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.rukiasoft.rukiapics.R;
import com.rukiasoft.rukiapics.model.PictureLists;
import com.rukiasoft.rukiapics.model.PicturePojo;
import com.rukiasoft.rukiapics.ui.activities.MainActivity;
import com.rukiasoft.rukiapics.ui.adapters.FlickrRecyclerViewAdapter;
import com.rukiasoft.rukiapics.ui.scroll.FastScroller;
import com.rukiasoft.rukiapics.ui.ui.presenters.MainFragmentPresenter;
import com.rukiasoft.rukiapics.ui.ui.presenters.PermissionsPresenter;
import com.rukiasoft.rukiapics.utilities.DisplayUtility;
import com.rukiasoft.rukiapics.utilities.RukiaConstants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import icepick.State;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends BaseFragment implements FlickrRecyclerViewAdapter.OnCardClickListener{

    //region binding views
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

    //endregion

    @State
    PictureLists pictureLists;
    @State String lastTags;
    @State
    RukiaConstants.Order lastOrder = null;
    @State
    boolean tagShown;
    @State PicturePojo dialogItem;

    MainFragmentPresenter presenter;
    PermissionsPresenter permissionsPresenter;
    Unbinder unbinder;


    private static final String TAG = MainActivityFragment.class.getSimpleName();

    public MainActivityFragment() {
        presenter = new MainFragmentPresenter(this);
        permissionsPresenter = new PermissionsPresenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        pictureLists = new PictureLists();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);

        if(presenter.isTagShown()){
            parent.setVisibility(View.VISIBLE);
        }

        //reload data if existing
        if(lastOrder != null){
            switch (lastOrder){
                case PUBLISHED:
                    presenter.setData(pictureLists.getListPublished());
                    break;
                case TAKEN:
                    presenter.setData(pictureLists.getListTaken());
                    break;
                default:
                    break;
            }
        }

        if(dialogItem != null){
            presenter.showDialog(dialogItem);
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
        dialogItem = pictureItem;
        presenter.showDialog(pictureItem);
    }

    @OnClick(R.id.send_button)
    public void sendTag(View view){
        DisplayUtility.hideKeyboard(getActivity());
        setLastOrder(RukiaConstants.Order.PUBLISHED);
        String tags = tagInput.getText().toString();
        //// TODO: 10/7/17 quitar esto
        tags = "perro";
        if(tags == null || tags.isEmpty()){
            ((MainActivity)getActivity()).getPresenter().hideTagInput();
            Snackbar.make(view, getString(R.string.no_tags), Snackbar.LENGTH_SHORT).show();
            return;
        }
        lastTags = tags;
        ((MainActivity)getActivity()).getPresenter().getPicsByTags(tags, RukiaConstants.Order.PUBLISHED);
        ((MainActivity)getActivity()).getPresenter().hideTagInput();
        tagInput.setText("");
    }


    // region getters and setters
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

    public List<PicturePojo> getListPublished() {
        return pictureLists.getListPublished();
    }

    public void setListPublished(List<PicturePojo> listPublished) {
        this.pictureLists.setListPublished(listPublished);
    }

    public List<PicturePojo> getListTaken() {
        return pictureLists.getListTaken();
    }

    public void setListTaken(List<PicturePojo> listTaken) {
        this.pictureLists.setListTaken(listTaken);
    }

    public String getLastTags() {
        return lastTags;
    }

    public boolean isTagShown() {
        return tagShown;
    }

    public void setTagShown(boolean tagShown) {
        this.tagShown = tagShown;
    }

    public void setLastTags(String lastTags) {
        this.lastTags = lastTags;
    }

    public RukiaConstants.Order getLastOrder() {
        return lastOrder;
    }

    public void setLastOrder(RukiaConstants.Order lastOrder) {
        this.lastOrder = lastOrder;
    }

    public PicturePojo getDialogItem() {
        return dialogItem;
    }

    public void setDialogItem(PicturePojo dialogItem) {
        this.dialogItem = dialogItem;
    }
    //endregion
}

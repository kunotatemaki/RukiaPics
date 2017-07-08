package com.rukiasoft.rukiapics.ui.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

import com.rukiasoft.rukiapics.R;
import com.rukiasoft.rukiapics.ui.activities.MainActivity;
import com.rukiasoft.rukiapics.ui.ui.presenters.MainFragmentPresenter;
import com.rukiasoft.rukiapics.utilities.DisplayUtility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    @BindView(R.id.tag_search)
    View parent;
    @BindView(R.id.send_button)
    FloatingActionButton sendButton;

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
}

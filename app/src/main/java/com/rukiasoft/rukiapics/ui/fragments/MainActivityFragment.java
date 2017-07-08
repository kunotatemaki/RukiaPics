package com.rukiasoft.rukiapics.ui.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.rukiasoft.rukiapics.R;
import com.rukiasoft.rukiapics.ui.activities.MainActivity;
import com.rukiasoft.rukiapics.ui.ui.presenters.MainFragmentPresenter;
import com.rukiasoft.rukiapics.ui.utilities.DisplayUtility;

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

    int cx, cy;
    float initialRadius, endRadius;

    private static final String TAG = MainActivityFragment.class.getSimpleName();

    public MainActivityFragment() {
        presenter = new MainFragmentPresenter();
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

    public void showInputTag(final View view){

        /*cx = (view.getRight() + view.getLeft()) / 2;
        int margin = DisplayUtility.getScreenWidth(getActivity())- cx;
        cx = parent.getRight() - margin;
        cy = (view.getTop() + view.getBottom()) / 2;
        margin = DisplayUtility.getScreenHeight(getActivity()) - cy;
        cy = parent.getHeight() - margin;*/
        cx = (sendButton.getRight() + sendButton.getLeft()) / 2;
        cy = (sendButton.getTop() + sendButton.getBottom()) / 2;
        initialRadius = (view.getRight() - view.getLeft()) / 2;

        endRadius = Math.max(DisplayUtility.getScreenWidth(getActivity()), DisplayUtility.getScreenHeight(getActivity()));
        Animator anim =
                ViewAnimationUtils.createCircularReveal(parent, cx, cy, initialRadius, endRadius);

        // make the view visible and start the animation
        view.setVisibility(View.INVISIBLE);
        parent.setVisibility(View.VISIBLE);
        anim.setDuration(500);

        anim.start();
        presenter.setTagShown(true);

    }

    public void hideInputTag(final View view){
        Animator anim =
                ViewAnimationUtils.createCircularReveal(parent, cx, cy, endRadius, initialRadius);

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                parent.setVisibility(View.INVISIBLE);
                view.setVisibility(View.VISIBLE);
            }
        });
        anim.setDuration(500);
        anim.start();
        presenter.setTagShown(false);

    }

    public MainFragmentPresenter getPresenter() {
        return presenter;
    }
}

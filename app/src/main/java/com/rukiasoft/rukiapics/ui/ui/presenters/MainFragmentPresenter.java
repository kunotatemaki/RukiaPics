package com.rukiasoft.rukiapics.ui.ui.presenters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.rukiasoft.rukiapics.ui.fragments.MainActivityFragment;
import com.rukiasoft.rukiapics.utilities.DisplayUtility;

/**
 * Created by Roll on 8/7/17.
 */

public class MainFragmentPresenter {

    private boolean tagShown;
    int cx, cy;
    float initialRadius, endRadius;

    MainActivityFragment fragment;

    public MainFragmentPresenter(MainActivityFragment fragment) {
        this.fragment = fragment;
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
        cx = (fragment.getSendButton().getRight() + fragment.getSendButton().getLeft()) / 2;
        cy = (fragment.getSendButton().getTop() + fragment.getSendButton().getBottom()) / 2;
        initialRadius = (sender.getRight() - sender.getLeft()) / 2;

        endRadius = Math.max(DisplayUtility.getScreenWidth(activity), DisplayUtility.getScreenHeight(activity));
        Animator anim =
                ViewAnimationUtils.createCircularReveal(fragment.getParent(), cx, cy, initialRadius, endRadius);

        // make the view visible and start the animation
        sender.setVisibility(View.INVISIBLE);
        fragment.getParent().setVisibility(View.VISIBLE);
        anim.setDuration(500);

        anim.start();
        fragment.getPresenter().setTagShown(true);

    }

    public void hideInputTag(final View view){
        Animator anim =
                ViewAnimationUtils.createCircularReveal(fragment.getParent(), cx, cy, endRadius, initialRadius);

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

}

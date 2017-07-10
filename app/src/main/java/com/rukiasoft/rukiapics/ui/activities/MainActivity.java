package com.rukiasoft.rukiapics.ui.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rukiasoft.rukiapics.R;
import com.rukiasoft.rukiapics.ui.fragments.MainActivityFragment;
import com.rukiasoft.rukiapics.ui.ui.presenters.MainActivityPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends ToolbarAndProgressActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.tag_button) FloatingActionButton tagButton;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout refreshLayout;

    MainActivityPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar);
        setRefreshLayout(refreshLayout);
        enableRefreshLayoutSwipe(false);
        presenter = new MainActivityPresenter(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_order_date_published:
                Log.d(TAG, "date published");
                presenter.getShownFragment().getPresenter().orderList(MainActivityFragment.Order.PUBLISHED);
                break;
            case R.id.action_order_date_taken:
                presenter.getShownFragment().getPresenter().orderList(MainActivityFragment.Order.PUBLISHED);
                Log.d(TAG, "date taken");
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        MainActivityFragment fragment = presenter.getShownFragment();
        if(fragment.getPresenter().isTagShown()){
            presenter.hideTagInput();
        }else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.tag_button)
    public void tagButtonClick(View view){
        presenter.showTagInput(view);
    }

    public FloatingActionButton getTagButton() {
        return tagButton;
    }

    public MainActivityPresenter getPresenter() {
        return presenter;
    }
}

package com.rukiasoft.rukiapics.ui.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

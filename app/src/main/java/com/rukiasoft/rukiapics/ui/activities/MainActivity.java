package com.rukiasoft.rukiapics.ui.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.rukiasoft.rukiapics.R;
import com.rukiasoft.rukiapics.ui.fragments.MainActivityFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.tag_button) FloatingActionButton tagButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
        MainActivityFragment fragment = getShownFragment();
        if(fragment.getPresenter().isTagShown()){
            hideTagInput();
        }else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.tag_button)
    public void tagButtonClick(View view){
        showTagInput(view);
    }

    public FloatingActionButton getTagButton() {
        return tagButton;
    }

    private void showTagInput(View view){
        MainActivityFragment fragment = getShownFragment();
        fragment.getPresenter().showInputTag(this, view);
    }

    private void hideTagInput(){
        MainActivityFragment fragment = getShownFragment();
        fragment.getPresenter().hideInputTag(tagButton);
    }

    private MainActivityFragment getShownFragment(){
        return (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    }

}

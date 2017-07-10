package com.rukiasoft.rukiapics.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rukiasoft.rukiapics.utilities.RukiaConstants;

import icepick.State;


public class SplashActivity extends AppCompatActivity {

    @State boolean started = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //start animation if needed
        if(!started){
            launchAnimation();
        }else {
            launchMainScreen();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RukiaConstants.REQUEST_CODE_ANIMATION:
                launchMainScreen();
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void launchAnimation(){
        Intent animationIntent = new Intent(this, AnimationActivity.class);
        startActivityForResult(animationIntent, RukiaConstants.REQUEST_CODE_ANIMATION);
    }

    private void launchMainScreen(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
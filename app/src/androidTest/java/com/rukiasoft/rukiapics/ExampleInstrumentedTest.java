package com.rukiasoft.rukiapics;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rukiasoft.rukiapics.model.PicturePojo;
import com.rukiasoft.rukiapics.ui.activities.MainActivity;
import com.rukiasoft.rukiapics.utilities.RukiaConstants;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    String mTitle;
    String mOwner;
    String sDateTaken;
    long datePublished;


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);


    @Before
    public void setUpTest(){
        //get reference to activity
        final MainActivity activity = mActivityRule.getActivity();

        //Create an item, and add to the recycler
        PicturePojo pic = new PicturePojo();
        SimpleDateFormat dateFormat = new SimpleDateFormat(RukiaConstants.DATEFORMAT);
        long dateTaken = System.currentTimeMillis();
        sDateTaken = dateFormat.format(new Date(dateTaken));
        pic.setDatetaken(sDateTaken);
        datePublished = System.currentTimeMillis();
        pic.setDateupload(String.valueOf(datePublished));
        mTitle = "Ethan Hunt";
        pic.setTitle(mTitle);
        mOwner = "Tom cruise";
        pic.setOwnername(mOwner);
        pic.setUrlM("file:///android_asset/ethan.jpeg");

        final List<PicturePojo> list = new ArrayList<>();
        list.add(pic);

        //populate recycler
        activity.getPresenter().getShownFragment().setListPublished(list);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.getPresenter().getShownFragment().getPresenter().setData(list);
            }
        });

        try {
            //wait for recycler being populated
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clickCard_ShowInfoInModal() {
        //get reference to activity
        final MainActivity activity = mActivityRule.getActivity();

        SimpleDateFormat dateFormat = new SimpleDateFormat(RukiaConstants.DATEFORMAT);

        // simulate click
        Espresso.onView(ViewMatchers.withId(R.id.cardview_item)).perform(ViewActions.click());

        //return showed item
        PicturePojo showedPic = activity.getPresenter().getShownFragment().getPresenter().getShowedItem();

        //check showed item is item set in recycler
        assertTrue(mTitle.equals(showedPic.getTitle()));
        assertTrue(mOwner.equals(showedPic.getOwner()));
        assertTrue(sDateTaken.equals(showedPic.getDatetaken()));
        assertTrue(dateFormat.format(datePublished).equals(showedPic.getDateupload()));
    }

    @Test
    public void launchSystemBrowser() throws Exception {

        final MainActivity activity = mActivityRule.getActivity();

        // simulate click in pic
        Espresso.onView(ViewMatchers.withId(R.id.cardview_item)).perform(ViewActions.click());

        // simulate click in save gallery
        Espresso.onView(ViewMatchers.withId(R.id.gallery_button)).perform(ViewActions.click());

        String filePath = activity.getPresenter().getShownFragment().getPresenter().getSavedImagePath();

        assertTrue(filePath != null && !filePath.isEmpty());

    }


}

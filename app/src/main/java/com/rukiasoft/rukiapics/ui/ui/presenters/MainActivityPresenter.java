package com.rukiasoft.rukiapics.ui.ui.presenters;

import android.util.Log;

import com.rukiasoft.rukiapics.BuildConfig;
import com.rukiasoft.rukiapics.FlickrConnection.FlickrEndpoints;
import com.rukiasoft.rukiapics.model.PicturePojo;
import com.rukiasoft.rukiapics.ui.activities.MainActivity;
import com.rukiasoft.rukiapics.utilities.BaseActivityTools;
import com.rukiasoft.rukiapics.utilities.RukiaConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Roll on 8/7/17.
 */

public class MainActivityPresenter {

    private final static String TAG = MainActivityPresenter.class.getSimpleName();
    boolean isDownloading = false;
    private MainActivity activity;

    public MainActivityPresenter(MainActivity activity) {
        this.activity = activity;
    }

    public void getPicsByTags(final String tags){
        Log.d(TAG, "getPicsByTags");
        isDownloading = true;
        final BaseActivityTools tools = new BaseActivityTools();
        tools.showRefreshLayout(activity);
        FlickrEndpoints flickrEndpoints = FlickrEndpoints.retrofit.create(FlickrEndpoints.class);
        Map<String, String> params = new HashMap<>();
        params.put(RukiaConstants.FLICKR_METHOD, "flickr.photos.search");
        params.put(RukiaConstants.FLICKR_API_KEY, BuildConfig.API_KEY);
        params.put(RukiaConstants.FLICKR_TAGS, tags);
        params.put(RukiaConstants.FLICKR_SAFE_SEARCH, "1");
        params.put(RukiaConstants.FLICKR_EXTRAS, "date_upload,date_taken,url_m");
        params.put(RukiaConstants.FLICKR_FORMAT, "json");
        params.put(RukiaConstants.FLICKR_NO_JSON_CALLBACK, "1");

        final Call<PicturePojo> call =
                flickrEndpoints.getPicsByTags(params);

        call.enqueue(new Callback<PicturePojo>() {
            @Override
            public void onResponse(Call<PicturePojo> call, Response<PicturePojo> response) {
                Log.d(TAG, "response_flickr");
                tools.hideRefreshLayout(activity);
                isDownloading = false;
                if(response.body() == null) return;
                /*List<MovieData> items = response.body().getResults();
                if(mPopularList.isEmpty()) {
                    setData(items);
                }else{
                    addData(items);
                }
                mPopularList.addAll(items);*/
            }
            @Override
            public void onFailure(Call<PicturePojo> call, Throwable t) {
                tools.hideRefreshLayout(activity);
                isDownloading = false;
                Log.d(TAG, "Something went wrong: " + t.getMessage());
            }
        });
    }

}

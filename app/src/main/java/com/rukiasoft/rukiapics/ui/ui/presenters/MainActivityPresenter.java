package com.rukiasoft.rukiapics.ui.ui.presenters;

import android.util.Log;

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

    private void getPicsByTags(final String tags){
        Log.d(TAG, "getPicsByTags");
        isDownloading = true;
        tools.showRefreshLayout(getActivity());
        MovieEndpoints movieEndpoints = MovieEndpoints.retrofit.create(MovieEndpoints.class);
        Map<String, String> params = new HashMap<>();
        params.put("page", page.toString());
        params.put("api_key", BuildConfig.API_KEY);
        final Call<MovieListResponse> call =
                movieEndpoints.GetPopularMoviesPage(params);

        call.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                Log.d(TAG, "responsepopularlist");
                tools.hideRefreshLayout(getActivity());
                isDownloading = false;
                if(response.body() == null) return;
                List<MovieData> items = response.body().getResults();
                if(mPopularList.isEmpty()) {
                    setData(items);
                }else{
                    addData(items);
                }
                mPopularList.addAll(items);
            }
            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {
                tools.hideRefreshLayout(getActivity());
                isDownloading = false;
                Log.d(TAG, "Something went wrong: " + t.getMessage());
            }
        });
    }

}

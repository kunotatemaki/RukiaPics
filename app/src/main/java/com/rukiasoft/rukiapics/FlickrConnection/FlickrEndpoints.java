package com.rukiasoft.rukiapics.FlickrConnection;


import com.rukiasoft.rukiapics.model.FlickrResponse;
import com.rukiasoft.rukiapics.model.PicturePojo;
import com.rukiasoft.rukiapics.utilities.RukiaConstants;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by iRoll on 29/1/17.
 */

public interface FlickrEndpoints {

    @GET(".")
    Call<FlickrResponse> getPicsByTags(
            @QueryMap Map<String, String> params);



    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(RukiaConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}

package com.hp.trackzonec.Retro;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {
    public Apis getApi()
    {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://srishti-systems.info/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Apis apis=retrofit.create(Apis.class);
        return apis;
    }
}

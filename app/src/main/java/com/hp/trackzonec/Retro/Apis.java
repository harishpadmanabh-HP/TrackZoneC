package com.hp.trackzonec.Retro;

import com.hp.trackzonec.model.Loginmodel;
import com.hp.trackzonec.model.RegModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Apis {

    @GET("projects/cTracker/register.php?")
    Call<RegModel> regCall(@Query("name") String name,
                            @Query("email") String email,
                            @Query("password") String password,
                            @Query("lat") Double lat,
                            @Query("log") Double log);



    @GET("projects/cTracker/login.php?")
    Call<Loginmodel> loginCall(@Query("email")String email,@Query("password")String pass);

}

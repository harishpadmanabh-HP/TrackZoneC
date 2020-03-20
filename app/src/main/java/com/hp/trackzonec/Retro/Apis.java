package com.hp.trackzonec.Retro;

import com.hp.trackzonec.model.HealthIssuesModel;
import com.hp.trackzonec.model.LocUpdate;
import com.hp.trackzonec.model.Loginmodel;
import com.hp.trackzonec.model.RegModel;
import com.hp.trackzonec.model.UserDetail;
import com.hp.trackzonec.model.UsersList;

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

    @GET("projects/cTracker/viewuser.php?")
    Call<UserDetail> userdetailCall(@Query("id") String id);

    @GET("projects/cTracker/update_location.php?")
     Call<LocUpdate> LOC_UPDATE_CALL(@Query("lat")Double lat,@Query("log")Double log,@Query("id")String id);


    @GET("projects/cTracker/view.php")
    Call<UsersList> USERS_LIST_CALL();

    @GET("projects/cTracker/view_tip.php")
    Call<HealthIssuesModel> healthIssuesCall();
}

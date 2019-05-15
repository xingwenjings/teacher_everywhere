package com.example.teacher_everywhere.net;

import com.example.teacher_everywhere.bean.MainDataBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface HomeSeriver {
    public static final String getUrl="http://api.banmi.com/";
    @Headers("banmi-app-token:JVy0IvZamK7f7FBZLKFtoniiixKMlnnJ6dWZ6NlsY4HGsxcAA9qvFo8yacHCKHE8YAcd0UF9L59nEm7zk9AUixee0Hl8EeWA880c0ikZBW0KEYuxQy5Z9NP3BNoBi3o3Q0g")
    @GET("api/3.0/content/routesbundles?page=1")
    Observable<MainDataBean> getLogin();
}

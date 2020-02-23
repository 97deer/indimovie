package com.example.mainindimovie_ex03.aApi;

import com.example.mainindimovie_ex03.Do.Join;
import com.example.mainindimovie_ex03.Do.Funding;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

//RestAPI를 사용해서 서버 연결하기 위한 내용
public interface Api {
     public static final String API_URL = "http://13.209.75.222:8000";

    //회원가입
    @POST("/movie/join/")
    Call<Join> postJoin(@Body Join join);

    //펀딩
    @POST("/movie/funding/")
    Call<Funding> postFunding(@Body Funding funding);

}

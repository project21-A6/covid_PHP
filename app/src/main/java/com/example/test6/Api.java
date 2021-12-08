package com.example.test6;

import java.util.List;

import retrofit2.Call;


import retrofit2.http.POST;

public interface Api {

    String BASE_URL = "http://152.70.245.49/"; //서버url

    @POST("data.php")
    Call<List<Covid>> get_Covid(); //반환타입: list, 배열로 받아야 됨.

}

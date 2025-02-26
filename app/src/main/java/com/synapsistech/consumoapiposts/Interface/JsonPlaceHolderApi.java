package com.synapsistech.consumoapiposts.Interface;

import com.synapsistech.consumoapiposts.Model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    /* Conseguir los datos*/
    @GET("posts")
    Call<List<Post>> getPost();
}

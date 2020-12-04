package com.netcracker_study_autumn_2020.data.custom.services;

import com.netcracker_study_autumn_2020.data.entity.TagEntity;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TagService {

    @GET("user/{user_id}/tag")
    Call<List<TagEntity>> getUserTags(@Header("Authorization") String token,
                                      @Path("user_id") long userId);

    @GET("image/{image_id}/tag")
    Call<List<TagEntity>> getImageTags(@Header("Authorization") String token,
                                       @Path("image_id") long imageId);

    @PUT("/user/{user_id}/tag/{tag_name}")
    Call<ResponseBody> addUserTag(@Header("Authorization") String token,
                                  @Path("user_id") long userId,
                                  @Path("tag_name") String tagName);

    @PUT("/user/{user_id}/image/{image_id}/image_tag/{tag_name}")
    Call<ResponseBody> addImageTag(@Header("Authorization") String token,
                                   @Path("user_id") long userId,
                                   @Path("image_id") long imageId,
                                   @Path("tag_name") String tagName);

    @DELETE("/user/{user_id}/tag/{tag_name}")
    Call<ResponseBody> deleteUserTag(@Header("Authorization") String token,
                                     @Path("user_id") long userId,
                                     @Path("tag_name") String tagName);

    @DELETE("/user/{user_id}/image/{image_id}/image_tag/{tag_name}")
    Call<ResponseBody> deleteImageTag(@Header("Authorization") String token,
                                      @Path("user_id") long user_id,
                                      @Path("image_id") long image_id,
                                      @Path("tag_name") String tagName);

}

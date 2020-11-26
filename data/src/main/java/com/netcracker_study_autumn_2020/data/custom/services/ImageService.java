package com.netcracker_study_autumn_2020.data.custom.services;

import com.netcracker_study_autumn_2020.data.entity.ImageEntity;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ImageService {

    @GET("/space/{space_id}/image")
    Call<List<ImageEntity>> getImagesBySpaceId(@Header("Authorization") String token,
                                               @Path("space_id") long spaceId);

    @PUT("/image/edit_info")
    Call<ResponseBody> editImageInfo(@Header("Authorization") String token,
                                     @Body ImageEntity imageEntity);

    @DELETE("/image/delete_image")
    Call<ResponseBody> deleteImage(@Header("Authorization") String token,
                                   @Query("id") long imageId);
}

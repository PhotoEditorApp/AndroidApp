package com.netcracker_study_autumn_2020.data.custom.services;

import com.netcracker_study_autumn_2020.data.entity.ImageEntity;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ImageService {

    @Multipart
    @POST("/image/upload_image")
    Call<ResponseBody> uploadImage(@Header("Authorization") String token,
                                   @Query("user_id") long userId,
                                   @Query("space_id") long spaceId,
                                   @Part MultipartBody.Part filePart);

    @GET("/image/get_image_id")
    Call<ResponseBody> getImageById(@Header("Authorization") String token,
                                    @Query("id") long imageId);

    @GET("/image/get_collage")
    Call<ResponseBody> createCollage(@Header("Authorization") String token,
                                     @Query("ids") long[] imageIds);

    @GET("/space/{space_id}/image")
    Call<List<ImageEntity>> getImagesBySpaceId(@Header("Authorization") String token,
                                               @Path("space_id") long spaceId);

    @PUT("/image/edit_info")
    Call<ResponseBody> editImageInfo(@Header("Authorization") String token,
                                     @Query("imageId") long imageId,
                                     @Query("newName") String newName);

    @PUT("/user/{user_id}/image/{image_id}/rating")
    Call<ResponseBody> rateImage(@Header("Authorization") String token,
                                 @Path("user_id") long userId,
                                 @Path("image_id") long imageId,
                                 @Query("rating_number") int rating);

    @DELETE("/image/delete_image")
    Call<ResponseBody> deleteImage(@Header("Authorization") String token,
                                   @Query("id") long imageId);
}

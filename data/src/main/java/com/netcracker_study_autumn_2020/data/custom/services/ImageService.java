package com.netcracker_study_autumn_2020.data.custom.services;

import com.netcracker_study_autumn_2020.data.entity.ImageEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ImageService {

    @GET("")
    Call<List<ImageEntity>> getImagesBySpaceId(@Header("Authorization") String token,
                                               @Query("space_id") long spaceId);
}

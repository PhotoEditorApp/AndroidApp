package com.netcracker_study_autumn_2020.data.custom.services;

import com.netcracker_study_autumn_2020.data.entity.SpaceAccessEntity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SpaceAccessService {

    @PUT("/space_access")
    Call<ResponseBody> createSpaceAccess(@Header("Authorization") String token,
                                         @Body SpaceAccessEntity spaceAccessEntity);

    @DELETE("/space_access/{id}")
    Call<ResponseBody> deleteSpaceAccess(@Path("id") long spaceAccessId);
}

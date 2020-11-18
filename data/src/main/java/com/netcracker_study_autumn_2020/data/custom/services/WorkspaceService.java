package com.netcracker_study_autumn_2020.data.custom.services;

import com.netcracker_study_autumn_2020.data.entity.WorkspaceEntity;

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

public interface WorkspaceService {

    @GET("/space/my_spaces")
    Call<List<WorkspaceEntity>> getUserWorkspaces(@Header("Authorization") String token,
                                                  @Query("user_id") long userId,
                                                  @Query("type") String accessType);

    //@GET("")
    //Call<WorkspaceEntity> getWorkspace(long workspaceId);

    @PUT("/space/{user_id}")
    Call<ResponseBody> createWorkspace(@Path("user_id") long user_id,
                                       @Header("Authorization") String token,
                                       @Body WorkspaceEntity workspaceEntity);

    @PUT("/space/{user_id}")
    Call<ResponseBody> editWorkspace(@Path("user_id") long user_id,
                                     @Header("Authorization") String token,
                                     @Body WorkspaceEntity workspaceEntity);

    @DELETE("/space/{id}")
    Call<ResponseBody> deleteWorkspace(@Path("id") long spaceId,
                                       @Header("Authorization") String token);


}

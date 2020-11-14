package com.netcracker_study_autumn_2020.data.custom.services;

import com.netcracker_study_autumn_2020.data.entity.WorkspaceEntity;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface WorkspaceService {

    @GET("/space/{user_id}")
    Call<List<WorkspaceEntity>> getUserWorkspaces(@Path("user_id") long userId);

    //@GET("")
    //Call<WorkspaceEntity> getWorkspace(long workspaceId);

    @PUT("/space/{user_id}")
    Call<ResponseBody> createWorkspace(@Path("user_id") long user_id
            , @Body WorkspaceEntity workspaceEntity);

    @PUT("/space/{user_id}")
    Call<ResponseBody> editWorkspace(@Path("user_id") long user_id
            , @Body WorkspaceEntity workspaceEntity);

    @DELETE("/space/{id}")
    Call<ResponseBody> deleteWorkspace(@Path("id") long spaceId);


}

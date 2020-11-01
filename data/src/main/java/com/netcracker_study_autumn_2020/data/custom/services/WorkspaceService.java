package com.netcracker_study_autumn_2020.data.custom.services;

import com.netcracker_study_autumn_2020.data.custom.workspace.WorkspaceEntityStore;
import com.netcracker_study_autumn_2020.data.entity.UserEntity;
import com.netcracker_study_autumn_2020.data.entity.WorkspaceEntity;
import com.netcracker_study_autumn_2020.library.UserCredentials;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WorkspaceService {

    @GET("")
    Call<List<WorkspaceEntity>> getUserWorkspaces(int userId);

    @GET("")
    Call<WorkspaceEntity> getWorkspace(int userId, int workspaceId);

    @POST("")
    Call<ResponseBody> createWorkspace(@Body WorkspaceEntity workspaceEntity);

    @POST("")
    Call<ResponseBody> editWorkspace(@Body WorkspaceEntity workspaceEntity);

    @DELETE("")
    Call<ResponseBody> deleteWorkspace(int userId, int spaceId);


}

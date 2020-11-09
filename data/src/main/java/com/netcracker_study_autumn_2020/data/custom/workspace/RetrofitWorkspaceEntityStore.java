package com.netcracker_study_autumn_2020.data.custom.workspace;

import com.netcracker_study_autumn_2020.data.entity.WorkspaceEntity;
import com.netcracker_study_autumn_2020.data.custom.services.WorkspaceService;
import com.netcracker_study_autumn_2020.library.network.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitWorkspaceEntityStore implements WorkspaceEntityStore {
    private WorkspaceService workspaceService;

    //TODO обработка кодов ошибок и сключительных ситуаций

    public RetrofitWorkspaceEntityStore(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtils.API_ADDRESS)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        workspaceService = retrofit.create(WorkspaceService.class);
    }

    @Override
    public void getWorkspace(long spaceId, WorkspaceByIdCallback callback) {

    }

    @Override
    public void allWorkspaces(long userId, WorkspaceListCallback callback) {
        Response<List<WorkspaceEntity>> response;
        //try {
            //response = workspaceService.getUserWorkspaces(userId).execute();
            //callback.onWorkspaceListLoaded(response.body());
        //} catch (IOException e) {
           // e.printStackTrace();
        //}
        callback.onWorkspaceListLoaded(new ArrayList<>());

    }

    @Override
    public void createWorkspace(WorkspaceEntity workspace, WorkspaceCreateCallback callback) {

    }

    @Override
    public void deleteWorkspace(long spaceId, WorkspaceDeleteCallback callback) {

    }


    @Override
    public void editWorkspace(WorkspaceEntity workspace, WorkspaceEditCallback callback) {

    }
}

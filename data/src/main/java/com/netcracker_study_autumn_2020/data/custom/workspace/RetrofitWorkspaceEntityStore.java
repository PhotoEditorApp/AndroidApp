package com.netcracker_study_autumn_2020.data.custom.workspace;

import android.util.Log;

import com.netcracker_study_autumn_2020.data.custom.services.WorkspaceService;
import com.netcracker_study_autumn_2020.data.entity.WorkspaceEntity;
import com.netcracker_study_autumn_2020.data.exception.EntityStoreException;
import com.netcracker_study_autumn_2020.data.manager.SessionManager;
import com.netcracker_study_autumn_2020.library.data.SpaceAccessType;
import com.netcracker_study_autumn_2020.library.network.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
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
        try {
            //TODO get access type properly!
            response = workspaceService.getUserWorkspaces(SessionManager.getSessionToken(),
                    userId, SpaceAccessType.CREATOR.toString()).execute();
            Log.d("TOKEN", SessionManager.getSessionToken());
            if (response.body() != null) {
                Log.d("WorkspaceEntityStore", response.body().toString());
                //WorkspaceEntity ww = response.body().get(0);
                //Log.d("WORKSPACE_ENTITY", ww.getName() + ww.getDescription()
                //+ ww.getCreatedTime());
                callback.onWorkspaceListLoaded(response.body());
            } else {
                callback.onWorkspaceListLoaded(new ArrayList<>());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void createWorkspace(WorkspaceEntity workspace, WorkspaceCreateCallback callback) {
        Response<ResponseBody> response;
        try {
            response = workspaceService.createWorkspace(workspace.getOwnerId(),
                    SessionManager.getSessionToken(),
                    workspace).execute();
            assert response.body() != null;
            if (response.code() == 200) {
                callback.onWorkspaceCreated();
                Log.d("WorkspaceEntityStore", response.body().toString());
            } else {
                callback.onError(new EntityStoreException("Workspace creation failed. Code:" +
                        " " + response.code() +
                        "\n Message: " + response.body().string()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteWorkspace(long spaceId, WorkspaceDeleteCallback callback) {
        Response<ResponseBody> response;
        try {
            response = workspaceService.deleteWorkspace(spaceId,
                    SessionManager.getSessionToken()).execute();
            assert response.body() != null;
            if (response.code() == 200) {
                callback.onWorkspaceDeleted();
            } else {
                callback.onError(new EntityStoreException("Delete workspace failed. Code:" +
                        " " + response.code() +
                        "\n Message: " + response.body().string()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void editWorkspace(WorkspaceEntity workspace, WorkspaceEditCallback callback) {
        Response<ResponseBody> response;
        try {
            response = workspaceService.editWorkspace(workspace.getOwnerId(),
                    SessionManager.getSessionToken(),
                    workspace).execute();
            assert response.body() != null;
            if (response.code() == 200) {
                callback.onWorkspaceEdited();
            } else {
                callback.onError(new EntityStoreException("Edit workspace failed. Code:" +
                        " " + response.code() +
                        "\n Message: " + response.body().string()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

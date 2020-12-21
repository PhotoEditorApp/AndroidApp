package com.netcracker_study_autumn_2020.data.custom.space.access;

import android.util.Log;

import com.netcracker_study_autumn_2020.data.custom.services.SpaceAccessService;
import com.netcracker_study_autumn_2020.data.entity.SpaceAccessEntity;
import com.netcracker_study_autumn_2020.data.exception.EntityStoreException;
import com.netcracker_study_autumn_2020.data.manager.SessionManager;
import com.netcracker_study_autumn_2020.library.network.NetworkUtils;
import com.netcracker_study_autumn_2020.library.network.UnsafeOkHttpClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitSpaceAccessEntityStore implements SpaceAccessEntityStore {
    private SpaceAccessService spaceAccessService;

    public RetrofitSpaceAccessEntityStore() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(UnsafeOkHttpClient.getUnsafeOkHttpClient())
                .baseUrl(NetworkUtils.API_ADDRESS)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        spaceAccessService = retrofit.create(SpaceAccessService.class);
    }


    @Override
    public void createSpaceAccess(SpaceAccessEntity spaceAccessEntity, SpaceAccessCreateCallback callback) {
        Response<ResponseBody> response;
        try {
            response = spaceAccessService.createSpaceAccess(SessionManager.getSessionToken(),
                    spaceAccessEntity).execute();
            if (response.code() == 200) {
                callback.onSpaceAccessCreated();
            } else {
                if (response.body() != null) {
                    callback.onError(new EntityStoreException("SpaceAccess creation failed. Code: " + response.code() +
                            "\n Message: " + response.body().string()));
                } else {
                    Log.d("CREATE_SA", spaceAccessEntity.getUserId() + " " + spaceAccessEntity.getSpaceId());
                    callback.onError(new EntityStoreException("SpaceAccess creation failed. Code: " + response.code()));

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSpaceAccess(long spaceAccessId, SpaceAccessDeleteCallback callback) {

    }
}

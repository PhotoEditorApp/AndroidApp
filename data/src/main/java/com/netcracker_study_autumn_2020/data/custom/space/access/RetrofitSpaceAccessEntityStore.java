package com.netcracker_study_autumn_2020.data.custom.space.access;

import com.netcracker_study_autumn_2020.data.custom.services.SpaceAccessService;
import com.netcracker_study_autumn_2020.data.entity.SpaceAccessEntity;
import com.netcracker_study_autumn_2020.data.exception.EntityStoreException;
import com.netcracker_study_autumn_2020.library.network.NetworkUtils;

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
            response = spaceAccessService.createSpaceAccess(spaceAccessEntity).execute();
            if (response.code() == 200) {
                callback.onSpaceAccessCreated();
            } else {
                callback.onError(new EntityStoreException("SpaceAccess creation failed. Code: " + response.code() +
                        "\n Message: " + response.body().string()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSpaceAccess(long spaceAccessId, SpaceAccessDeleteCallback callback) {

    }
}

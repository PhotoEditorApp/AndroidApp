package com.netcracker_study_autumn_2020.presentation.mvp.presenter;

import android.util.Log;

import com.netcracker_study_autumn_2020.data.manager.SessionManager;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.AddUserTagUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.DeleteUserTagUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.GetUserTagsUseCase;
import com.netcracker_study_autumn_2020.presentation.mvp.view.UserTagsView;

import java.util.ArrayList;
import java.util.List;

public class UserTagsPresenter extends BasePresenter {

    private List<String> userTagsList;

    private UserTagsView userTagsView;

    private GetUserTagsUseCase getUserTagsUseCase;
    private AddUserTagUseCase addUserTagUseCase;
    private DeleteUserTagUseCase deleteUserTagUseCase;


    public UserTagsPresenter(GetUserTagsUseCase getUserTagsUseCase,
                             AddUserTagUseCase addUserTagUseCase,
                             DeleteUserTagUseCase deleteUserTagUseCase) {
        this.getUserTagsUseCase = getUserTagsUseCase;
        this.addUserTagUseCase = addUserTagUseCase;
        this.deleteUserTagUseCase = deleteUserTagUseCase;
        userTagsList = new ArrayList<>();
    }

    public void setUserTagsView(UserTagsView userTagsView) {
        this.userTagsView = userTagsView;
    }

    public List<String> getUserTagsList() {
        return userTagsList;
    }

    public void addTag(String tagName) {
        addUserTagUseCase.execute(SessionManager.getCurrentUserId(), tagName,
                new AddUserTagUseCase.Callback() {
                    @Override
                    public void onUserTagAdded() {
                        refreshData();
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });

    }

    public void deleteTag(String tagName) {
        deleteUserTagUseCase.execute(SessionManager.getCurrentUserId(), tagName,
                new DeleteUserTagUseCase.Callback() {
                    @Override
                    public void onUserTagDeleted() {
                        refreshData();
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });

    }

    public void getUserTags() {
        Log.d("ADD_USER_TAG", "from entity store2");
        getUserTagsUseCase.execute(SessionManager.getCurrentUserId(),
                new GetUserTagsUseCase.Callback() {
                    @Override
                    public void onUserTagsLoaded(List<String> tags) {
                        userTagsList = tags;
                        userTagsView.renderTags();
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });

    }

    public void refreshData() {
        getUserTags();
    }
}

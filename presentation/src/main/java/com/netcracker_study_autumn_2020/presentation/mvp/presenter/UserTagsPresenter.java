package com.netcracker_study_autumn_2020.presentation.mvp.presenter;

import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.AddUserTagUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.DeleteUserTagUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.GetUserTagsUseCase;
import com.netcracker_study_autumn_2020.presentation.mvp.view.UserTagsView;

import java.util.List;

public class UserTagsPresenter extends BasePresenter {

    private List<String> userTags;

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
    }

    public void setUserTagsView(UserTagsView userTagsView) {
        this.userTagsView = userTagsView;
    }

    public void addTag(String tagName) {

    }

    public void deleteTag(String tagName) {

    }

    public void getUserTags() {

    }

    public void refreshData() {

    }
}

package com.netcracker_study_autumn_2020.presentation.mvp.presenter;

import com.netcracker_study_autumn_2020.domain.dto.SpaceAccessDto;
import com.netcracker_study_autumn_2020.domain.dto.UserDto;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.space.access.CreateSpaceAccessUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.space.access.DeleteSpaceAccessUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.GetUserByEmailUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.GetUserByIdUseCase;
import com.netcracker_study_autumn_2020.presentation.mapper.SpaceAccessModelDtoMapper;
import com.netcracker_study_autumn_2020.presentation.mapper.UserModelDtoMapper;
import com.netcracker_study_autumn_2020.presentation.mvp.model.SpaceAccessModel;
import com.netcracker_study_autumn_2020.presentation.mvp.model.UserModel;
import com.netcracker_study_autumn_2020.presentation.mvp.view.FindAndShareView;

import java.util.ArrayList;
import java.util.List;

public class FindAndSharePresenter extends BasePresenter {

    private FindAndShareView findAndShareView;

    private CreateSpaceAccessUseCase createSpaceAccessUseCase;
    private DeleteSpaceAccessUseCase deleteSpaceAccessUseCase;

    private GetUserByEmailUseCase getUserByEmailUseCase;
    private GetUserByIdUseCase getUserByIdUseCase;

    private SpaceAccessModelDtoMapper spaceAccessModelDtoMapper;
    private UserModelDtoMapper userModelDtoMapper;

    private List<UserModel> foundUsers;
    private long currentWorkspaceId;
    private long chosenUserId;


    public FindAndSharePresenter(CreateSpaceAccessUseCase createSpaceAccessUseCase,
                                 DeleteSpaceAccessUseCase deleteSpaceAccessUseCase,
                                 GetUserByEmailUseCase getUserByEmailUseCase,
                                 GetUserByIdUseCase getUserByIdUseCase) {
        this.createSpaceAccessUseCase = createSpaceAccessUseCase;
        this.deleteSpaceAccessUseCase = deleteSpaceAccessUseCase;
        this.getUserByEmailUseCase = getUserByEmailUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;

        spaceAccessModelDtoMapper = new SpaceAccessModelDtoMapper();
        userModelDtoMapper = new UserModelDtoMapper();
        foundUsers = new ArrayList<>();
    }

    public void setFindAndShareView(FindAndShareView findAndShareView) {
        this.findAndShareView = findAndShareView;
    }

    public void findUserById(long userId) {
        getUserByIdUseCase.execute(userId, new GetUserByIdUseCase.Callback() {
            @Override
            public void onUserFound(UserDto userDto) {
                foundUsers.clear();
                foundUsers.add(userModelDtoMapper.map1(userDto));
                findAndShareView.renderFoundUsersList();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void findUserByEmail(String userEmail) {
        getUserByEmailUseCase.execute(userEmail, new GetUserByEmailUseCase.Callback() {
            @Override
            public void onUserFound(UserDto user) {
                foundUsers.clear();
                foundUsers.add(userModelDtoMapper.map1(user));
                findAndShareView.renderFoundUsersList();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void addSpaceAccess(SpaceAccessModel spaceAccessModel) {
        SpaceAccessDto spaceAccessDto = spaceAccessModelDtoMapper.map2(spaceAccessModel);
        createSpaceAccessUseCase.execute(spaceAccessDto, new CreateSpaceAccessUseCase.Callback() {
            @Override
            public void onSpaceAccessCreated() {

            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void deleteSpaceAccess(long spaceAccessId) {
        deleteSpaceAccessUseCase.execute(spaceAccessId, new DeleteSpaceAccessUseCase.Callback() {
            @Override
            public void onSpaceAccessDeleted() {

            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public List<UserModel> getFoundUsers() {
        return foundUsers;
    }

    public void setChosenUserId(long chosenUserId) {
        this.chosenUserId = chosenUserId;
    }

    public long getChosenUserId() {
        return chosenUserId;
    }
}

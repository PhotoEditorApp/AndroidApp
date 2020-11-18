package com.netcracker_study_autumn_2020.presentation.mvp.presenter;

import com.netcracker_study_autumn_2020.domain.dto.SpaceAccessDto;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.space.access.CreateSpaceAccessUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.space.access.DeleteSpaceAccessUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.GetUserByEmailUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.GetUserByIdUseCase;
import com.netcracker_study_autumn_2020.presentation.mapper.SpaceAccessModelDtoMapper;
import com.netcracker_study_autumn_2020.presentation.mvp.model.SpaceAccessModel;
import com.netcracker_study_autumn_2020.presentation.mvp.view.FindAndShareView;

public class FindAndSharePresenter extends BasePresenter {

    private FindAndShareView findAndShareView;

    private CreateSpaceAccessUseCase createSpaceAccessUseCase;
    private DeleteSpaceAccessUseCase deleteSpaceAccessUseCase;

    private GetUserByEmailUseCase getUserByEmailUseCase;
    private GetUserByIdUseCase getUserByIdUseCase;

    private SpaceAccessModelDtoMapper spaceAccessModelDtoMapper;


    public FindAndSharePresenter(CreateSpaceAccessUseCase createSpaceAccessUseCase,
                                 DeleteSpaceAccessUseCase deleteSpaceAccessUseCase,
                                 GetUserByEmailUseCase getUserByEmailUseCase,
                                 GetUserByIdUseCase getUserByIdUseCase) {
        this.createSpaceAccessUseCase = createSpaceAccessUseCase;
        this.deleteSpaceAccessUseCase = deleteSpaceAccessUseCase;
        this.getUserByEmailUseCase = getUserByEmailUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;

        spaceAccessModelDtoMapper = new SpaceAccessModelDtoMapper();
    }

    public void setFindAndShareView(FindAndShareView findAndShareView) {
        this.findAndShareView = findAndShareView;
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
}

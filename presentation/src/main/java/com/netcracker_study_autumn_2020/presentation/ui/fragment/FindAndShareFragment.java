package com.netcracker_study_autumn_2020.presentation.ui.fragment;

import com.netcracker_study_autumn_2020.data.custom.space.access.SpaceAccessEntityStoreFactory;
import com.netcracker_study_autumn_2020.data.custom.user.UserEntityStoreFactory;
import com.netcracker_study_autumn_2020.data.executor.JobExecutor;
import com.netcracker_study_autumn_2020.data.mapper.SpaceAccessEntityDtoMapper;
import com.netcracker_study_autumn_2020.data.mapper.UserEntityDtoMapper;
import com.netcracker_study_autumn_2020.data.repository.SpaceAccessRepositoryImpl;
import com.netcracker_study_autumn_2020.data.repository.UserRepositoryImpl;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.space.access.CreateSpaceAccessUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.space.access.DeleteSpaceAccessUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.space.access.impl.CreateSpaceAccessUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.space.access.impl.DeleteSpaceAccessUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.GetUserByEmailUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.GetUserByIdUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.impl.GetUserByEmailUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.impl.GetUserByIdUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.repository.SpaceAccessRepository;
import com.netcracker_study_autumn_2020.domain.repository.UserRepository;
import com.netcracker_study_autumn_2020.presentation.executor.UIThread;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.FindAndSharePresenter;

public class FindAndShareFragment extends BaseFragment {

    private FindAndSharePresenter findAndSharePresenter;

    @Override
    void initializePresenter() {

        SpaceAccessEntityStoreFactory entityStoreFactory = new SpaceAccessEntityStoreFactory();
        UserEntityStoreFactory userEntityStoreFactory = new UserEntityStoreFactory();

        SpaceAccessRepository spaceAccessRepository = SpaceAccessRepositoryImpl.getInstance(entityStoreFactory,
                new SpaceAccessEntityDtoMapper());
        UserRepository userRepository = UserRepositoryImpl.getInstance(userEntityStoreFactory,
                new UserEntityDtoMapper());
        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        CreateSpaceAccessUseCase createSpaceAccessUseCase = new CreateSpaceAccessUseCaseImpl(spaceAccessRepository,
                postExecutionThread, threadExecutor);
        DeleteSpaceAccessUseCase deleteSpaceAccessUseCase = new DeleteSpaceAccessUseCaseImpl(spaceAccessRepository,
                postExecutionThread, threadExecutor);
        GetUserByIdUseCase getUserByIdUseCase = new GetUserByIdUseCaseImpl(userRepository, postExecutionThread,
                threadExecutor);
        GetUserByEmailUseCase getUserByEmailUseCase = new GetUserByEmailUseCaseImpl(userRepository, postExecutionThread,
                threadExecutor);
        findAndSharePresenter = new FindAndSharePresenter(createSpaceAccessUseCase, deleteSpaceAccessUseCase,
                getUserByEmailUseCase, getUserByIdUseCase);

    }
}

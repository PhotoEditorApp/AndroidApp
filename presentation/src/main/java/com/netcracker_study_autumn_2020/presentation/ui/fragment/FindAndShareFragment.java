package com.netcracker_study_autumn_2020.presentation.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
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
import com.netcracker_study_autumn_2020.library.data.SpaceAccessType;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.executor.UIThread;
import com.netcracker_study_autumn_2020.presentation.mvp.model.SpaceAccessModel;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.FindAndSharePresenter;
import com.netcracker_study_autumn_2020.presentation.mvp.view.FindAndShareView;
import com.netcracker_study_autumn_2020.presentation.ui.adapter.FindUsersAdapter;

public class FindAndShareFragment extends BaseFragment implements FindAndShareView {

    private FindAndSharePresenter findAndSharePresenter;

    private long chosenWorkspaceId;

    private RecyclerView foundUsersList;
    private FindUsersAdapter findUsersAdapter;

    public FindAndShareFragment(long chosenWorkspaceId) {
        this.chosenWorkspaceId = chosenWorkspaceId;
    }

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_find_and_share, container, false);
        initInteractions(root);
        initRecyclerView(root);

        return root;
    }

    private void initInteractions(View root) {
        MaterialButton find = root.findViewById(R.id.button_find_users);
        MaterialButton addSpaceAccess = root.findViewById(R.id.button_add_space_access);
        EditText searchRequest = root.findViewById(R.id.enter_search_text);

        RadioGroup accessType = root.findViewById(R.id.group_check_access_type);

        //TODO переделать по-другому
        String[] values = {"По ID", "По E-mail"};

        Spinner searchCriteria = root.findViewById(R.id.spinner_search_criteria);
        ArrayAdapter<String> criteriaAdapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_spinner_item, values);
        criteriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchCriteria.setAdapter(criteriaAdapter);

        //TODO добавить проверки введенных значений
        find.setOnClickListener(l -> {

            if (searchCriteria.getSelectedItem().toString().equals(values[0])) {
                try {
                    findAndSharePresenter.findUserById(
                            Long.parseLong(searchRequest.getText().toString()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    showToastMessage("Некорректные данные. Введите id - целое число",
                            true);
                }

            } else {
                findAndSharePresenter.findUserByEmail(searchRequest.getText()
                        .toString());
            }

        });
        addSpaceAccess.setOnClickListener(l -> {
            SpaceAccessModel spaceAccessModel = new SpaceAccessModel();
            spaceAccessModel.setSpaceId(chosenWorkspaceId);
            spaceAccessModel.setUserId(findAndSharePresenter.getChosenUserId());
            spaceAccessModel.setType(accessType
                    .getCheckedRadioButtonId() == R.id.radio_viewer ?
                    SpaceAccessType.VIEWER.toString() :
                    SpaceAccessType.EDITOR.toString());
            findAndSharePresenter.addSpaceAccess(spaceAccessModel);
        });
    }

    private void initRecyclerView(View root) {
        foundUsersList = root.findViewById(R.id.find_and_share_found_users_list);
        foundUsersList.setLayoutManager(new LinearLayoutManager(getActivity()));
        findUsersAdapter = new FindUsersAdapter(findAndSharePresenter);
        foundUsersList.setAdapter(findUsersAdapter);
        findUsersAdapter.setFoundUsersList(findAndSharePresenter.getFoundUsers());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findAndSharePresenter.setFindAndShareView(this);
    }

    @Override
    public void renderFoundUsersList() {
        findUsersAdapter.setFoundUsersList(
                findAndSharePresenter.getFoundUsers());
    }
}

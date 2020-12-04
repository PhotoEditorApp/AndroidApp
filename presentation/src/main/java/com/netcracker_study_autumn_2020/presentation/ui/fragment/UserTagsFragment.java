package com.netcracker_study_autumn_2020.presentation.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.netcracker_study_autumn_2020.data.custom.tags.TagEntityStoreFactory;
import com.netcracker_study_autumn_2020.data.executor.JobExecutor;
import com.netcracker_study_autumn_2020.data.repository.TagRepositoryImpl;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.AddUserTagUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.DeleteUserTagUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.GetUserTagsUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.impl.AddUserTagUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.impl.DeleteUserTagUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.impl.GetUserTagsUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.repository.TagRepository;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.executor.UIThread;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.UserTagsPresenter;
import com.netcracker_study_autumn_2020.presentation.mvp.view.UserTagsView;
import com.netcracker_study_autumn_2020.presentation.ui.adapter.TagsAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserTagsFragment extends BaseFragment
        implements UserTagsView {

    private RecyclerView tagsList;
    private UserTagsPresenter userTagsPresenter;

    @Override
    void initializePresenter() {
        PostExecutionThread postExecutionThread = UIThread.getInstance();
        ThreadExecutor threadExecutor = JobExecutor.getInstance();

        TagEntityStoreFactory tagEntityStoreFactory = new TagEntityStoreFactory();
        TagRepository tagRepository = TagRepositoryImpl.getInstance(tagEntityStoreFactory);

        GetUserTagsUseCase getUserTagsUseCase = new GetUserTagsUseCaseImpl(tagRepository,
                postExecutionThread, threadExecutor);
        AddUserTagUseCase addUserTagUseCase = new AddUserTagUseCaseImpl(tagRepository,
                postExecutionThread, threadExecutor);
        DeleteUserTagUseCase deleteUserTagUseCase = new DeleteUserTagUseCaseImpl(tagRepository,
                postExecutionThread, threadExecutor);

        userTagsPresenter = new UserTagsPresenter(getUserTagsUseCase, addUserTagUseCase,
                deleteUserTagUseCase);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_tags, container, false);
        initInteractions(root);
        return root;
    }

    private void initInteractions(View root) {
        tagsList = root.findViewById(R.id.user_tags_list);
        tagsList.setLayoutManager(new LinearLayoutManager(requireContext()));

        List<String> testData = new ArrayList<>();
        testData.add("Лето");
        testData.add("Зима");
        testData.add("Зима");
        testData.add("Зима");
        testData.add("Зима");
        testData.add("Зима");
        testData.add("Зима");
        testData.add("Зима");
        testData.add("Зима");
        testData.add("Зима");
        testData.add("Зима");
        testData.add("Зима");
        testData.add("Зима");
        testData.add("Зима");
        testData.add("Зима");
        testData.add("Зима");
        testData.add("Зима");


        TagsAdapter tagsAdapter = new TagsAdapter();
        tagsList.setAdapter(tagsAdapter);
        tagsAdapter.setTagsList(testData);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userTagsPresenter.setUserTagsView(this);
        userTagsPresenter.refreshData();
    }

    @Override
    public void renderTags() {

    }
}

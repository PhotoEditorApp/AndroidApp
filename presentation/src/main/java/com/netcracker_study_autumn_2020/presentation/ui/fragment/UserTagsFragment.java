package com.netcracker_study_autumn_2020.presentation.ui.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
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

import java.util.List;

public class UserTagsFragment extends BaseFragment
        implements UserTagsView {

    private ConstraintLayout emptyUI;

    private RecyclerView tagsList;
    private TagsAdapter tagsAdapter;
    private UserTagsPresenter userTagsPresenter;

    @Override
    void initializePresenter() {
        PostExecutionThread postExecutionThread = UIThread.getInstance();
        ThreadExecutor threadExecutor = JobExecutor.getInstance();

        TagEntityStoreFactory tagEntityStoreFactory = new TagEntityStoreFactory();
        TagRepository tagRepository = new TagRepositoryImpl(tagEntityStoreFactory);

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
        emptyUI = root.findViewById(R.id.empty_ui);
        tagsList = root.findViewById(R.id.user_tags_list);
        tagsList.setLayoutManager(new LinearLayoutManager(requireContext()));

        MaterialButton addUserTag = root.findViewById(R.id.button_add_user_tag);

        addUserTag.setOnClickListener(l -> {
            AlertDialog alertDialog = initAddUserTagDialog();
            alertDialog.show();
        });


        tagsAdapter = new TagsAdapter(userTagsPresenter, false);
        tagsList.setAdapter(tagsAdapter);

    }

    private AlertDialog initAddUserTagDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.dialog_add_user_tag, null);
        alertDialogBuilder.setView(dialogView);

        final EditText dialogNewUserTagName = dialogView.findViewById(R.id.dialog_enter_new_tag_name);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Добавить",
                        (dialog, which) -> {
                            String tagName = dialogNewUserTagName.getText().toString();
                            userTagsPresenter.addTag(tagName);
                        })
                .setNegativeButton("Отмена",
                        (dialog, which) -> {
                            dialog.cancel();
                        });

        return alertDialogBuilder.create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("ADD_USER_TAG", "from entity store1");
        userTagsPresenter.setUserTagsView(this);
        userTagsPresenter.refreshData();
    }

    @Override
    public void renderTags() {
        List<String> buf = userTagsPresenter.getUserTagsList();
        if (buf.isEmpty()) {
            emptyUI.setVisibility(View.VISIBLE);
        } else {
            emptyUI.setVisibility(View.GONE);
        }
        tagsAdapter.setTagsList(userTagsPresenter.getUserTagsList());

    }
}

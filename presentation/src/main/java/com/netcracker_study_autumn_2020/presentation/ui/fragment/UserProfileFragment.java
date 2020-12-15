package com.netcracker_study_autumn_2020.presentation.ui.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.button.MaterialButton;
import com.netcracker_study_autumn_2020.data.custom.user.UserEntityStoreFactory;
import com.netcracker_study_autumn_2020.data.executor.JobExecutor;
import com.netcracker_study_autumn_2020.data.manager.AuthManager;
import com.netcracker_study_autumn_2020.data.manager.SessionManager;
import com.netcracker_study_autumn_2020.data.manager.impl.RetrofitAuthManagerImpl;
import com.netcracker_study_autumn_2020.data.mapper.UserEntityDtoMapper;
import com.netcracker_study_autumn_2020.data.repository.UserRepositoryImpl;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.EditUserUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.GetUserByIdUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.UploadUserAvatarUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.impl.EditUserUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.impl.GetUserByIdUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.impl.UploadUserAvatarUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.repository.UserRepository;
import com.netcracker_study_autumn_2020.library.files.FilesUtils;
import com.netcracker_study_autumn_2020.library.network.NetworkUtils;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.executor.UIThread;
import com.netcracker_study_autumn_2020.presentation.mapper.UserModelDtoMapper;
import com.netcracker_study_autumn_2020.presentation.mvp.model.UserModel;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.UserProfilePresenter;
import com.netcracker_study_autumn_2020.presentation.mvp.view.UserProfileView;
import com.netcracker_study_autumn_2020.presentation.ui.activity.MainNavigationActivity;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;
import static com.netcracker_study_autumn_2020.presentation.ui.fragment.ImagesFragment.GALLERY_REQUEST;

public class UserProfileFragment extends BaseFragment implements UserProfileView {

    private UserProfilePresenter userProfilePresenter;

    private ConstraintLayout loadingUI;
    private ConstraintLayout userProfileContainer;

    private TextView userFirstName;
    private TextView userLastName;
    private TextView userId;
    private TextView userEmail;
    private CircleImageView userAvatar;

    private String[] galleryPermissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    void initializePresenter() {
        AuthManager authManager = new RetrofitAuthManagerImpl();

        UserEntityDtoMapper userEntityDtoMapper = new UserEntityDtoMapper();
        UserEntityStoreFactory userEntityStoreFactory = new UserEntityStoreFactory();

        UserRepository userRepository = new UserRepositoryImpl(userEntityDtoMapper,
                userEntityStoreFactory);
        PostExecutionThread postExecutionThread = UIThread.getInstance();
        ThreadExecutor threadExecutor = JobExecutor.getInstance();

        GetUserByIdUseCase getUserByIdUseCase = new GetUserByIdUseCaseImpl(
                userRepository, postExecutionThread, threadExecutor
        );
        EditUserUseCase editUserUseCase = new EditUserUseCaseImpl(
                userRepository, postExecutionThread, threadExecutor
        );
        UploadUserAvatarUseCase uploadUserAvatarUseCase = new UploadUserAvatarUseCaseImpl(
                userRepository, postExecutionThread, threadExecutor
        );

        UserModelDtoMapper userModelDtoMapper = new UserModelDtoMapper();
        userProfilePresenter = new UserProfilePresenter(authManager,
                getUserByIdUseCase, editUserUseCase, uploadUserAvatarUseCase,
                userModelDtoMapper);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_profile, container,false);
        initInteractions(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userProfilePresenter.setView(this);
        userProfilePresenter.refreshUserProfile();
    }

    private void initInteractions(View root) {
        MaterialButton editProfile = root.findViewById(R.id.button_edit_user_profile);
        MaterialButton logOut = root.findViewById(R.id.button_log_out);
        MaterialButton uploadAvatar = root.findViewById(R.id.button_upload_user_avatar);

        userEmail = root.findViewById(R.id.user_profile_email);
        userFirstName = root.findViewById(R.id.user_profile_first_name);
        userLastName = root.findViewById(R.id.user_profile_last_name);
        userId = root.findViewById(R.id.user_profile_id);
        userAvatar = root.findViewById(R.id.user_profile_avatar);
        loadingUI = root.findViewById(R.id.loading_ui);
        renderAvatar();

        AlertDialog alertDialog = initEditDialog();
        editProfile.setOnClickListener(l -> alertDialog.show());
        logOut.setOnClickListener(l -> {
            AuthManager authManager = new RetrofitAuthManagerImpl();
            authManager.signOut(new AuthManager.SignOutCallback() {
                @Override
                public void onSignOutFinished() {
                    requireActivity().finish();//navigateToStartActivity();
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            });
        });
        uploadAvatar.setOnClickListener(l -> {
            Log.d("ARE_PERMISSIONS_GRANTED", String.valueOf(
                    EasyPermissions.hasPermissions(requireContext(), galleryPermissions)));
            if (EasyPermissions.hasPermissions(getContext(), galleryPermissions)) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            } else {
                EasyPermissions.requestPermissions(this, "Access for storage",
                        101, galleryPermissions);
            }
        });
    }

    private AlertDialog initEditDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.dialog_edit_user_profile, null);
        alertDialogBuilder.setView(dialogView);

        final EditText dialogFirstName = dialogView.findViewById(R.id.dialog_enter_first_name);
        dialogFirstName.setHint(userFirstName.getText());
        final EditText dialogLastName = dialogView.findViewById(R.id.dialog_enter_last_name);
        dialogLastName.setHint(userLastName.getText());

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Подтвердить",
                        (dialog, which) -> {
                            UserModel bufModel = userProfilePresenter.getUserModel();
                            bufModel.setFirstName(dialogFirstName.getText().toString());
                            bufModel.setLastName(dialogLastName.getText().toString());
                            userProfilePresenter.editUserProfile(bufModel);
                        })
                .setNegativeButton("Отмена",
                        (dialog, which) -> dialog.cancel());

        return alertDialogBuilder.create();
    }

    @Override
    public void renderData() {
        UserModel bufferModel = userProfilePresenter.getUserModel();
        userEmail.setText(bufferModel.getEmail());
        userFirstName.setText(bufferModel.getFirstName());
        userLastName.setText(bufferModel.getLastName());
        userId.setText(String.valueOf(bufferModel.getUser_id()));
    }

    @Override
    public void showLoading() {
        loadingUI.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingUI.setVisibility(View.INVISIBLE);
    }

    @Override
    public void renderAvatar() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request customImageRequest = chain.request().newBuilder()
                            .addHeader("Authorization", SessionManager.getSessionToken())
                            .build();

                    return chain.proceed(customImageRequest);
                })
                .build();
        Picasso picasso = new Picasso.Builder(this.requireContext())
                .downloader(new OkHttp3Downloader(client))
                .build();

        String url = NetworkUtils.API_ADDRESS + NetworkUtils.GET_AVATAR_BY_ID +
                "?profileId=" + SessionManager.getCurrentUserId();
        Log.d("ONBIND", url);
        picasso.load(url)
                .error(R.drawable.default_avatar)
                .placeholder(R.drawable.loading_animation)
                .into(userAvatar);
    }

    private void navigateToStartActivity() {
        ((MainNavigationActivity) requireActivity()).navigateToStartActivity();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        showLoading();

        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String absolutePath = FilesUtils.getPath(getContext(), selectedImage);
                    File userAvatar;
                    if (absolutePath != null && !absolutePath.isEmpty()) {
                        userAvatar = new File(absolutePath);
                        userProfilePresenter.uploadAvatar(userAvatar);
                    } else {
                        Log.d("ABSOLUTE_PATH", "is null");
                    }
                } else {
                    hideLoading();
                }
                break;
            default:
                hideLoading();
        }
    }
}

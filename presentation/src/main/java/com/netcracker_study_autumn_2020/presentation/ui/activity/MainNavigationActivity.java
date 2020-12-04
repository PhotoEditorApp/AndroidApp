package com.netcracker_study_autumn_2020.presentation.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;
import com.netcracker_study_autumn_2020.presentation.mvp.model.WorkspaceModel;
import com.netcracker_study_autumn_2020.presentation.ui.fragment.FindAndShareFragment;
import com.netcracker_study_autumn_2020.presentation.ui.fragment.ImagesFragment;
import com.netcracker_study_autumn_2020.presentation.ui.fragment.UserProfileFragment;
import com.netcracker_study_autumn_2020.presentation.ui.fragment.WorkspacesFragment;

public class MainNavigationActivity extends BaseActivity {

    private static final String CURRENT_USER_ID_EXTRA_NAME = "CURRENT_USER_ID";

    private UserProfileFragment userProfileFragment;
    private WorkspacesFragment workspacesFragment;
    private ImagesFragment imagesFragment;
    private FindAndShareFragment findAndShareFragment;

    //Позволяет передать пары ключ-значение другой Activity при старте
    public static Intent getCallingIntent(Context context, long userId) {
        Intent callingIntent = new Intent(context, MainNavigationActivity.class);
        callingIntent.putExtra(CURRENT_USER_ID_EXTRA_NAME, userId);

        return callingIntent;
    }

    @Override
    protected void initializeActivity(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);
        initFragments();
        initBottomNavigation();
    }

    private void initFragments() {
        userProfileFragment = new UserProfileFragment();
        workspacesFragment = new WorkspacesFragment();

    }

    private void initBottomNavigation(){
        BottomNavigationView navigationView = findViewById(R.id.navigation_bar);
        updateHostFragment(navigationView, R.id.navigation_profile);

        navigationView.setOnNavigationItemSelectedListener(item -> {
            updateHostFragment(navigationView, item.getItemId());
            return false;
        });

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_profile,
                R.id.navigation_workspaces,
                R.id.navigation_tags)
                .build();

    }

    private void updateHostFragment(BottomNavigationView navigationView, int navigationId){
        switch (navigationId){
            case R.id.navigation_profile:
                navigationView.getMenu().findItem(navigationId).setChecked(true);
                getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.nav_host_fragment, userProfileFragment)
                        .commit();
                break;
            case R.id.navigation_workspaces:
                navigationView.getMenu().findItem(navigationId).setChecked(true);
                getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.nav_host_fragment, workspacesFragment)
                        .commit();
                break;
            case R.id.navigation_tags:
                break;
        }
    }


    public void navigateToImagesView(WorkspaceModel workspaceModel) {
        imagesFragment = new ImagesFragment(workspaceModel);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.nav_host_fragment, imagesFragment)
                .commit();
    }

    public void navigateToFindAndShareFragment(long workspaceId) {
        findAndShareFragment = new FindAndShareFragment(workspaceId);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.nav_host_fragment, findAndShareFragment)
                .commit();
    }


    public void navigateToCreateWorkspace(long userId) {
        navigator.navigateToCreateWorkspaceActivity(this, userId);
    }

    public void navigateToFullSizePhotoView(ImageModel imageModel, long workspaceId) {
        navigator.navigateToPhotoView(this, imageModel, workspaceId);
    }


}

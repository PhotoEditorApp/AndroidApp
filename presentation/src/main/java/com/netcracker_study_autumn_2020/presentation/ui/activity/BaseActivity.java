package com.netcracker_study_autumn_2020.presentation.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.netcracker_study_autumn_2020.presentation.navigation.Navigator;

abstract class BaseActivity extends AppCompatActivity {

    protected Navigator navigator;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        initializeActivity(savedInstanceState);
    }

    public void addFragment(int containerViewId, Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        if (fragment != null){
            fragmentTransaction.add(containerViewId, fragment);
        }
        fragmentTransaction.commit();
    }

    public void replaceFragment(int containerViewId, Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null);
        if (fragment != null){
            fragmentTransaction.replace(containerViewId, fragment);
        }
        fragmentTransaction.commit();
    }




    protected void initialize(){ this.navigator = new Navigator(); }

    protected abstract void initializeActivity(Bundle savedInstanceState);
}

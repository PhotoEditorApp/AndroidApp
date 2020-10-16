package com.netcracker_study_autumn_2020.presentation.ui.fragment;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        initializePresenter();
    }

    abstract void initializePresenter();

    public void showToastMessage(String message, boolean isLong){
        Toast.makeText(getActivity(), message,
                isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT)
                .show();
    }
}

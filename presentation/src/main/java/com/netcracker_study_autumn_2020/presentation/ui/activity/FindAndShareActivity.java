package com.netcracker_study_autumn_2020.presentation.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.ui.fragment.FindAndShareFragment;

public class FindAndShareActivity extends BaseActivity {
    private FindAndShareFragment findAndShareFragment;

    @Override
    protected void initializeActivity(Bundle savedInstanceState) {
        //findAndShareFragment = new FindAndShareFragment();
        replaceFragment(R.id.ft_container, findAndShareFragment);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_and_share);
    }
}

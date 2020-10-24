package com.netcracker_study_autumn_2020.presentation.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.netcracker_study_autumn_2020.presentation.ui.fragment.BaseFragment;

public class WorkspacesActivity extends BaseActivity {

    //Позволяет передать пары ключ-значение другой Activity при старте
    public static Intent getCallingIntent(Context context){
        Intent callingIntent = new Intent(context, WorkspacesActivity.class);

        return callingIntent;
    }
    @Override
    protected void initializeActivity(Bundle savedInstanceState) {

    }
}

package com.netcracker_study_autumn_2020.data.custom.user;

import com.netcracker_study_autumn_2020.data.custom.workspace.RetrofitWorkspaceEntityStore;
import com.netcracker_study_autumn_2020.data.custom.workspace.WorkspaceEntityStore;

public class UserEntityStoreFactory {

    public UserEntityStore create(){
        UserEntityStore userEntityStore;

        userEntityStore = new RetrofitUserEntityStore();
        return  userEntityStore;
    }
}

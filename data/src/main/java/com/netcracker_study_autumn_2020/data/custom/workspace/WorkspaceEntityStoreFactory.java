package com.netcracker_study_autumn_2020.data.custom.workspace;

public class WorkspaceEntityStoreFactory {

    //TODO сюда может быть добавлен функционал,
    // определяющий доступность сети и подменяющий RetrofitWorkspaceEntityStore
    // на кеш WorkspaceCache

    public WorkspaceEntityStore create(){
        WorkspaceEntityStore workspaceEntityStore;

        workspaceEntityStore = new RetrofitWorkspaceEntityStore();
        return  workspaceEntityStore;
    }
}

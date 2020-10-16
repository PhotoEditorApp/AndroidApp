package com.netcracker_study_autumn_2020.domain.executor;

public interface ThreadExecutor {
    void execute(final Runnable runnable);
}

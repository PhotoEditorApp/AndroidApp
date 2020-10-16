package com.netcracker_study_autumn_2020.domain.executor;

public interface PostExecutionThread {
    void post(Runnable runnable);
}

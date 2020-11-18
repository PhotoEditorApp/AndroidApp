package com.netcracker_study_autumn_2020.domain.interactor.usecases.space.access;

import com.netcracker_study_autumn_2020.domain.dto.SpaceAccessDto;

public interface CreateSpaceAccessUseCase {

    interface Callback {
        void onSpaceAccessCreated();

        void onError(Exception e);
    }

    void execute(SpaceAccessDto spaceAccessDto,
                 CreateSpaceAccessUseCase.Callback callback);

}

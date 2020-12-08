package com.netcracker_study_autumn_2020.presentation.mvp.presenter;

import android.util.Log;

import com.netcracker_study_autumn_2020.data.manager.SessionManager;
import com.netcracker_study_autumn_2020.domain.dto.ImageDto;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.AddImageUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.CreateCollageUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.DeleteImageUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.EditImageInfoUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.GetWorkspaceImagesInfoUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.RateImageUseCase;
import com.netcracker_study_autumn_2020.presentation.mapper.ImageModelDtoMapper;
import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;
import com.netcracker_study_autumn_2020.presentation.mvp.view.ImagesView;
import com.netcracker_study_autumn_2020.presentation.ui.fragment.ImagesFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ImagesPresenter extends BasePresenter {
    private ImagesView imagesView;

    private AddImageUseCase addImageUseCase;
    private GetWorkspaceImagesInfoUseCase getWorkspaceImagesInfoUseCase;
    private EditImageInfoUseCase editImageInfoUseCase;
    private DeleteImageUseCase deleteImageUseCase;
    private RateImageUseCase rateImageUseCase;
    private CreateCollageUseCase createCollageUseCase;

    private List<ImageModel> imageModels;
    private long spaceId;

    private ImageModelDtoMapper imageModelDtoMapper;

    public ImagesPresenter(long spaceId,
                           AddImageUseCase addImageUseCase,
                           GetWorkspaceImagesInfoUseCase getWorkspaceImagesInfoUseCase,
                           EditImageInfoUseCase editImageInfoUseCase,
                           DeleteImageUseCase deleteImageUseCase,
                           RateImageUseCase rateImageUseCase,
                           CreateCollageUseCase createCollageUseCase) {
        this.addImageUseCase = addImageUseCase;
        this.getWorkspaceImagesInfoUseCase = getWorkspaceImagesInfoUseCase;
        this.editImageInfoUseCase = editImageInfoUseCase;
        this.deleteImageUseCase = deleteImageUseCase;
        this.rateImageUseCase = rateImageUseCase;
        this.createCollageUseCase = createCollageUseCase;
        this.spaceId = spaceId;

        imageModels = new ArrayList<>();
        imageModelDtoMapper = new ImageModelDtoMapper();
    }

    public void setView(ImagesView imagesView) {
        this.imagesView = imagesView;
    }

    private void getWorkspaceImagesInfo(long spaceId) {
        getWorkspaceImagesInfoUseCase.execute(spaceId, new GetWorkspaceImagesInfoUseCase.Callback() {
            @Override
            public void onImagesLoaded(List<ImageDto> images) {
                List<ImageModel> wModels = imageModelDtoMapper.map1(images);
                imageModels.clear();
                imageModels.addAll(wModels);
                //workspaceModels = wModels;
                Log.d(TAG, "onImagesLoaded: ");

                imagesView.renderImages();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void createCollage(long[] imageIds) {
        createCollageUseCase.execute(imageIds, new CreateCollageUseCase.Callback() {
            @Override
            public void onCollageCreated() {
                updateImageList();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

    }

    public void addImage(long userId, long spaceId, File bufferFile) {
        Log.d("GETTING_IMAGE", "8");
        addImageUseCase.execute(spaceId, userId, bufferFile, new AddImageUseCase.Callback() {
            @Override
            public void onImageAdded() {
                getWorkspaceImagesInfo(spaceId);
                imagesView.hideLoading();
            }

            @Override
            public void onError(Exception e) {
                imagesView.hideLoading();
                e.printStackTrace();
            }
        });
    }

    public void editImageInfo(ImageModel imageModel) {
        ImageDto imageDto = imageModelDtoMapper.map2(imageModel);
        editImageInfoUseCase.execute(imageDto, new EditImageInfoUseCase.Callback() {
            @Override
            public void onImageInfoEdited() {
                getWorkspaceImagesInfo(spaceId);

            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void deleteImage(long imageId) {
        deleteImageUseCase.execute(imageId, new DeleteImageUseCase.Callback() {
            @Override
            public void onImageDeleted() {
                getWorkspaceImagesInfo(spaceId);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void rateImage(long imageId, int rating) {
        rateImageUseCase.execute(SessionManager.getCurrentUserId(),
                imageId, rating, new RateImageUseCase.Callback() {
                    @Override
                    public void onImageRated() {
                        ((ImagesFragment) imagesView)
                                .showToastMessage("Вы оценили изображение", false);
                        updateImageList();
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    public void updateImageList() {
        getWorkspaceImagesInfo(spaceId);
    }

    public List<ImageModel> getImageModels() {
        return imageModels;
    }

    public void sortByAverageColor() {
        imageModels.sort((o1, o2) -> {
            if (o1.getAverageColor() > o2.getAverageColor()) {
                return 1;
            } else if (o1.getAverageColor() < o2.getAverageColor()) {
                return -1;
            }
            return 0;
        });
        imagesView.renderImages();
    }

    public void sortByCreateDate() {
        imageModels.sort((o1, o2) -> {
            Date d1 = o1.getCreateTime();
            Date d2 = o2.getCreateTime();
            if (d1.after(d2)) {
                return 1;
            } else if (d1.before(d2)) {
                return -1;
            }
            return 0;
        });
        imagesView.renderImages();
    }

    public void sortByModifiedDate() {
        imageModels.sort((o1, o2) -> {
            Date d1 = o1.getModifiedTime();
            Date d2 = o2.getModifiedTime();
            if (d1.after(d2)) {
                return 1;
            } else if (d1.before(d2)) {
                return -1;
            }
            return 0;
        });
        imagesView.renderImages();
    }

    public void sortByName() {
        imageModels.sort((o1, o2) -> {
            String name1 = o1.getName().toLowerCase();
            String name2 = o2.getName().toLowerCase();
            for (int i = 0;
                 i < (Math.min(name1.length(), name2.length())); i++) {
                if (name1.charAt(i) != name2.charAt(i)) {
                    if (name1.charAt(i) > name2.charAt(i)) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
            return 0;
        });
        imagesView.renderImages();
    }

    public void sortByRating() {
        imageModels.sort((o1, o2) -> {
            float rating1 = o1.getRating();
            float rating2 = o2.getRating();
            if (rating1 > rating2) {
                return -1;
            } else if (rating1 < rating2) {
                return 1;
            }
            return 0;
        });
        imagesView.renderImages();
    }
}

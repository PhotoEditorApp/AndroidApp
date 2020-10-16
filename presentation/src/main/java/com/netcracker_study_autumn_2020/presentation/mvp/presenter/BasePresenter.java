package com.netcracker_study_autumn_2020.presentation.mvp.presenter;

public abstract class BasePresenter {
    //Эти методы будут реализованы позднее

    //В onPause() будет распологаться логика записи состояния приложения
    //И его получение в onResume()
    //Если это будет необходимо
    public void onResume(){}

    public void onPause(){}

    public void onDestroy(){}
}

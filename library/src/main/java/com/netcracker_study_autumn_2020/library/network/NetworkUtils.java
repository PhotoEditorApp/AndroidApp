package com.netcracker_study_autumn_2020.library.network;

public abstract class NetworkUtils {

    //Таймаут в миллисекундах
    public static final int CONNECTION_TIMEOUT = 5000;
    //Текущий адрес бэкенда
    //Не final для динамической отладки
    public static  String API_ADDRESS = "http://8fc92a03be4a.ngrok.io";

    public static void setApiAddress(String apiAddress){
        API_ADDRESS = apiAddress;
    }
}

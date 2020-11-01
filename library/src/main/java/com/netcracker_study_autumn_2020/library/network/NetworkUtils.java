package com.netcracker_study_autumn_2020.library.network;

public abstract class NetworkUtils {

    //Таймаут в миллисекундах
    public static final int CONNECTION_TIMEOUT = 5000;
    //Текущий адрес бэкенда
    //Не final для динамической отладки
    public static  String API_ADDRESS = "http://3495637bb310.ngrok.io";

    public static void setApiAddress(String apiAddress){
        API_ADDRESS = apiAddress;
    }
}

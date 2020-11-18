package com.netcracker_study_autumn_2020.library.network;

public abstract class NetworkUtils {

    //Таймаут в миллисекундах
    public static final int CONNECTION_TIMEOUT = 5000;
    //Текущий адрес бэкенда
    //Не final для динамической отладки
    public static String API_ADDRESS = "http://1f0bf2e03847.ngrok.io";

    //Строковый формат даты для парсинга
    public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'+00:00'";

    public static void setApiAddress(String apiAddress) {
        API_ADDRESS = apiAddress;
    }
}

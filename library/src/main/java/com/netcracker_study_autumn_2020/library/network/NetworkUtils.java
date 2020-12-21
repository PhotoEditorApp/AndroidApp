package com.netcracker_study_autumn_2020.library.network;

public abstract class NetworkUtils {

    //Таймаут в миллисекундах
    public static final int CONNECTION_TIMEOUT = 5000;

    //Текущий адрес бэкенда
    //Не final для динамической отладки
    public static String API_ADDRESS = "https://178.62.224.9:8443";
    public static final String GET_IMAGE_BY_ID_ADDRESS = "/image/get_image_id";
    public static final String GET_IMAGE_BY_PATH = "/image/get_image_path";
    public static final String GET_FRAME_PREVIEW_BY_ID = "/image/frame_preview_id";
    public static final String GET_AVATAR_BY_ID = "/image/photo_profile_id";

    //Строковый формат даты для парсинга
    public static final String DATE_PATTERN_DB = "yyyy-MM-dd'T'HH:mm:ss.SSS'+00:00'";
    public static final String DATE_PATTERN_DISPLAY = "dd.MM.yyyy HH:mm";

    public static void setApiAddress(String apiAddress) {
        API_ADDRESS = apiAddress;
    }
}

package com.netcracker_study_autumn_2020.data.manager;

public abstract class SessionManager {
    protected static String sessionToken;
    protected static long currentUserId;

    public static boolean isSessionOpened() {
        return isSessionOpened;
    }

    protected static boolean isSessionOpened = false;

    public static String getSessionToken() {
        return sessionToken;
    }

    public static long getCurrentUserId() {
        return currentUserId;
    }

    public abstract void openSession(String sessionToken, long userId);

    public abstract void closeSession();


}

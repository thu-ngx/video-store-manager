package com.guccigang.videostoremanager.core;

public class Constants {
    private static final String APP_NAME = "Video Store Manager";

    public static String getAppName() {
        return APP_NAME;
    }

    private static final String DATA_PATH = "/data/";

    private static final String ITEMS_FILE = "items.txt";
    private static final String ACCOUNTS_FILE = "accounts.txt";
    private static final String TRANSACTIONS_FILE = "transactions.txt";

    public static String getItemsFileName() {
        return DATA_PATH + ITEMS_FILE;
    }

    public static String getAccountsFileName() {
        return DATA_PATH + ACCOUNTS_FILE;
    }

    public static String getTransactionsFileName() {
        return DATA_PATH + TRANSACTIONS_FILE;
    }

    private static final String[] scenesToLoad = {
            "loading",
            "adminMain",
//            "main",
//            "login",
//            "signup",
    };

    public static String[] getScenesToLoad() {
        return scenesToLoad;
    }

    public static String getDefaultSceneName() {
        return "adminMain";
    }
}
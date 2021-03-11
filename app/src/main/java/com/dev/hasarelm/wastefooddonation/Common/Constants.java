package com.dev.hasarelm.wastefooddonation.Common;

public class Constants {

    public static final String CONNECT_TO_WIFI = "WIFI";
    public static final String NOT_CONNECT = "NOT_CONNECT";
    public static int CONN_FLG = 0;

    public interface ACTION {
        public static String MAIN_ACTION = "com.marothiatechs.foregroundservice.action.main";
        public static String INIT_ACTION = "com.marothiatechs.foregroundservice.action.init";
        public static String PREV_ACTION = "com.marothiatechs.foregroundservice.action.prev";
        public static String PLAY_ACTION = "com.marothiatechs.foregroundservice.action.play";
        public static String NEXT_ACTION = "com.marothiatechs.foregroundservice.action.next";
        public static String STARTFOREGROUND_ACTION = "com.marothiatechs.foregroundservice.action.startforeground";
        public static String STOPFOREGROUND_ACTION = "com.marothiatechs.foregroundservice.action.stopforeground";
    }

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }
}

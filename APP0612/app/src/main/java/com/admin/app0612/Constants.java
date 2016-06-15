package com.admin.app0612;

/**
 * Created by Administrator on 2016/5/12.
 */
public class Constants {

    public static class Request {
        public static final String BASE_URL = "http://192.168.1.50:8888";
        public static final String URL_USERS = "/radius";
    }

    public static class MsgId {
        public static final int USER_LIST = 1;
        public static final int ADD_USER = 2;
        public static final int DEL_USER = 3;
        public static final int PATCH_USER = 4;
        public static final int QUERY_USER = 5;
    }

    public static Integer PWD_MAX_LENGTH = 20;


}

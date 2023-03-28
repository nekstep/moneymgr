package com.cfcons.moneymgr.config;

public class AppRoles {
    public static String ADMIN_ROLE = "ADMIN";
    public static String USER_ROLE = "USER";

    public static String ROLE_PREFIX="ROLE_";
    public static String getRoleName(String authority) {
        return ROLE_PREFIX + authority;
    }
}

package com.cfcons.moneymgr.config;

/**
 * AppRoles class is used to store constants for role names and relevant functions.
 */
public class AppRoles {
    /**
     * Role for site admin.
     */
    public static String ADMIN_ROLE = "ADMIN";

    /**
     * Role for regular user.
     */
    public static String USER_ROLE = "USER";

    /**
     * HttpSecurity hasRole() expects role names without prefix, but we need to keep it in database with it.
     */
    public static String ROLE_PREFIX="ROLE_";

    /**
     * Statis function to add prefix required by HttpSecurity to work before putting data in database.
     *
     * @param authority     role name without prefix (as used by HttpSecurity)
     * @return              role name with prefix (as stored in database)
     */
    public static String getRoleName(String authority) {
        return ROLE_PREFIX + authority;
    }
}

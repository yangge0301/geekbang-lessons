package org.geektimes.util;

/**
 * @Author: baozi
 * @Date: 2021/3/3 9:25 上午
 * @Version 1.0
 */
public final class DbConStants {
    public final static String INSERT_USER = "INSERT INTO users(name,password,email,phoneNumber) VALUES (?,?,?,?)";
    public final static String QUERY_USER_BY_NAME = "SELECT * FROM users WHERE NAME = ?";
    public final static String QUERY_USER_BY_ID = "SELECT * FROM users WHERE ID = ?";
    public final static String QUERY_USER_BY_NAME_AND_PASSWORD = "SELECT * FROM users WHERE NAME = ? AND PASSWORD = ?";
}

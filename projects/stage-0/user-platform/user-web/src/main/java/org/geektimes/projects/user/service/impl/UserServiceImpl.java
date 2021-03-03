package org.geektimes.projects.user.service.impl;

import org.geektimes.Exception.SystemException;
import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.projects.user.sql.DBConnectionManager;
import org.geektimes.util.ConStants;
import org.geektimes.util.DbConStants;

import java.sql.*;

/**
 * @Author: baozi
 * @Date: 2021/3/3 9:10 上午
 * @Version 1.0
 */
public class UserServiceImpl implements UserService {

    public static final String CREATE_USERS_TABLE_DDL_SQL = "CREATE TABLE users(" +
            "id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
            "name VARCHAR(16) NOT NULL, " +
            "password VARCHAR(64) NOT NULL, " +
            "email VARCHAR(64) NOT NULL, " +
            "phoneNumber VARCHAR(64) NOT NULL" +
            ")";

    @Override
    public boolean register(User user) throws SystemException{
        DBConnectionManager dbConnectionManager = null;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            dbConnectionManager = DBConnectionManager.getInstance();
            connection = dbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(DbConStants.QUERY_USER_BY_NAME);
            preparedStatement.setString(1,user.getName());
            resultSet = preparedStatement.executeQuery();
            if(resultSet != null && resultSet.next()){
                throw new SystemException("用户已存在，请换个用户名再注册！");
            }
            preparedStatement = connection.prepareStatement(DbConStants.INSERT_USER);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setString(3,user.getEmail());
            preparedStatement.setString(4,user.getPhoneNumber());
            int res = preparedStatement.executeUpdate();
            if(res>0){
                return true;
            }

        }
        catch (SQLException e){

        }
        finally {
            try{
                if(resultSet!=null ||!resultSet.isClosed()){
                    resultSet.close();
                }
            }
            catch (Exception e){

            }
            try{
                if(preparedStatement!=null ||!preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            }
            catch (Exception e){

            }

            try{
                if(connection!=null ||!connection.isClosed()){
                    connection.close();
                }
            }
            catch (Exception e){

            }
        }
        return false;
    }

    @Override
    public boolean deregister(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public User queryUserById(Long id) {
        return null;
    }

    @Override
    public User queryUserByName(String name) {
        return null;
    }

    @Override
    public User queryUserByNameAndPassword(String name, String password) {
        return null;
    }
}

package org.geektimes.projects.user.service;

import org.geektimes.Exception.SystemException;
import org.geektimes.projects.user.domain.User;

/**
 * 用户服务
 */
public interface UserService {

    /**
     * 注册用户
     *
     * @param user 用户对象
     * @return 成功返回<code>true</code>
     */
    boolean register(User user) throws SystemException;

    /**
     * 注销用户
     *
     * @param user 用户对象
     * @return 成功返回<code>true</code>
     */
    boolean deregister(User user);

    /**
     * 更新用户信息
     *
     * @param user 用户对象
     * @return
     */
    boolean update(User user);

    /**
     * 根据用户名查询用户对象
     *
     * @param name 用户名
     * @return
     */
    User queryUserByName(String name);

    /**
     * 根据用户Id查询用户对象
     *
     * @param id 用户Id
     * @return
     */
    User queryUserById(Long id);

    User queryUserByNameAndPassword(String name, String password);
}

package org.geektimes.projects.user.web.controller;

import org.geektimes.Exception.SystemException;
import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.projects.user.service.impl.UserServiceImpl;
import org.geektimes.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @Author: baozi
 * @Date: 2021/3/3 12:05 下午
 * @Version 1.0
 */

@Path("/user")
public class RegisterController implements PageController {

    @GET
    @POST
    @Path("/register") // /hello/world -> HelloWorldController
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return "regist/regist.jsp";
    }
    @GET
    @POST
    @Path("/saveUser") // /hello/world -> HelloWorldController
    public String saveUser(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        UserService userService = new UserServiceImpl();
        try{
            if(userService.register(user)){
                request.setAttribute("msg", "注册成功");
            }
            else{
                request.setAttribute("msg", "注册失败");
            }
        }
        catch (SystemException e){
            request.setAttribute("msg", e.getMessage());
        }

        return "regist/result.jsp";
    }
}

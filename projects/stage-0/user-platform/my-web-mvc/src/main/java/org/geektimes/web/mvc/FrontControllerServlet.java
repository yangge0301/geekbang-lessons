package org.geektimes.web.mvc;

import org.apache.commons.lang.StringUtils;
import org.geektimes.web.mvc.controller.Controller;
import org.geektimes.web.mvc.controller.PageController;
import org.geektimes.web.mvc.controller.RestController;
import org.geektimes.web.mvc.header.CacheControlHeaderWriter;
import org.geektimes.web.mvc.header.annotation.CacheControl;
import org.geektimes.web.mvc.util.PathUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.substringAfter;

public class FrontControllerServlet extends HttpServlet {

    /**
     * 请求路径和 Controller 的映射关系缓存
     */
    private Map<String, Controller> controllersMapping = new HashMap<>();

    /**
     * 请求路径和 {@link HandlerMethodInfo} 映射关系缓存
     */
    private Map<String, HandlerMethodInfo> handleMethodInfoMapping = new HashMap<>();

    /**
     * 初始化 Servlet
     *
     * @param servletConfig
     */
    public void init(ServletConfig servletConfig) {
        initHandleMethods();
    }

    /**
     * 读取所有的 RestController 的注解元信息 @Path
     * 利用 ServiceLoader 技术（Java SPI）
     */
    private void initHandleMethods() {
        for (Controller controller : ServiceLoader.load(Controller.class)) {
            Class<?> controllerClass = controller.getClass();
            Path pathFromClass = controllerClass.getAnnotation(Path.class);
            String requestPath = pathFromClass.value();
            if(!(StringUtils.isNotBlank(pathFromClass.value())&&pathFromClass.value().startsWith("/"))){
                requestPath = "/" +requestPath ;
            }
            Method[] publicMethods = controllerClass.getMethods();
            // 处理方法支持的 HTTP 方法集合
            for (Method method : publicMethods) {

                Set<String> supportedHttpMethods = findSupportedHttpMethods(method);
                Path pathFromMethod = method.getAnnotation(Path.class);
                if (pathFromMethod != null) {
                    String tmpPath = "";
                    if(StringUtils.isNotBlank(pathFromMethod.value())&&pathFromMethod.value().startsWith("/")){
                        tmpPath = requestPath + pathFromMethod.value();
                    }
                    else{
                        tmpPath = requestPath + "/"+pathFromMethod.value();
                    }
                    handleMethodInfoMapping.put(tmpPath,
                            new HandlerMethodInfo(requestPath, method, supportedHttpMethods));
                }
//                else{
//                    handleMethodInfoMapping.put(requestPath,
//                            new HandlerMethodInfo(requestPath, method, supportedHttpMethods));
//                }

            }

            String tmpPath = StringUtils.replace(requestPath, "/", "");
            controllersMapping.put(requestPath, controller);
            controllersMapping.put(tmpPath, controller);
        }
    }

    /**
     * 获取处理方法中标注的 HTTP方法集合
     *
     * @param method 处理方法
     * @return
     */
    private Set<String> findSupportedHttpMethods(Method method) {
        Set<String> supportedHttpMethods = new LinkedHashSet<>();
        for (Annotation annotationFromMethod : method.getAnnotations()) {
            HttpMethod httpMethod = annotationFromMethod.annotationType().getAnnotation(HttpMethod.class);
            if (httpMethod != null) {
                supportedHttpMethods.add(httpMethod.value());
            }
        }

        if (supportedHttpMethods.isEmpty()) {
            supportedHttpMethods.addAll(asList(HttpMethod.GET, HttpMethod.POST,
                    HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.HEAD, HttpMethod.OPTIONS));
        }

        return supportedHttpMethods;
    }

    /**
     * SCWCD
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 建立映射关系
        // requestURI = /a/hello/world
        String requestURI = request.getRequestURI();
        // contextPath  = /a or "/" or ""
        String servletContextPath = request.getContextPath();
        String prefixPath = servletContextPath;
        // 映射路径（子路径）
        String requestMappingPath = substringAfter(StringUtils.replace(requestURI, "//", "/"),
                StringUtils.replace(prefixPath, "//", "/"));
        String pathStr = PathUtil.getSubUtilSimple(requestMappingPath,"\\/(.*?)\\/");
        // 映射到 Controller
        Controller controller = controllersMapping.get(pathStr);

        if (controller != null) {

            HandlerMethodInfo handlerMethodInfo = handleMethodInfoMapping.get(requestMappingPath);

            try {
                if (handlerMethodInfo != null) {

                    String httpMethod = request.getMethod();

                    if (!handlerMethodInfo.getSupportedHttpMethods().contains(httpMethod)) {
                        // HTTP 方法不支持
                        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                        return;
                    }

                    if (controller instanceof PageController) {
                        PageController pageController = PageController.class.cast(controller);
//                        String viewPath = pageController.execute(request, response);
                        Object result = handlerMethodInfo.getHandlerMethod().invoke(pageController,request,response);
                        if(result instanceof  String){
                            String viewPath = result.toString();
                            ServletContext servletContext = request.getServletContext();
//
                            if (!viewPath.startsWith("/")) {
                                viewPath = "/" + viewPath;
                            }
                            if(viewPath.contains(".jsp")){
                                viewPath="/WEB-INF/jsp"+viewPath;
                            }
                            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(viewPath);
                            requestDispatcher.forward(request, response);
                        }
                        else{
                            // TODO
                        }

                        return;
                    } else if (controller instanceof RestController) {
                        // TODO
                    }

                }
            } catch (Throwable throwable) {
                if (throwable.getCause() instanceof IOException) {
                    throw (IOException) throwable.getCause();
                } else {
                    throw new ServletException(throwable.getCause());
                }
            }
        }
        else{

        }
    }

//    private void beforeInvoke(Method handleMethod, HttpServletRequest request, HttpServletResponse response) {
//
//        CacheControl cacheControl = handleMethod.getAnnotation(CacheControl.class);
//
//        Map<String, List<String>> headers = new LinkedHashMap<>();
//
//        if (cacheControl != null) {
//            CacheControlHeaderWriter writer = new CacheControlHeaderWriter();
//            writer.write(headers, cacheControl.value());
//        }
//    }
}

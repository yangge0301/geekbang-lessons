package org.geektimes.Exception;

/**
 * @Author: baozi
 * @Date: 2021/3/3 9:33 上午
 * @Version 1.0
 */
public class SystemException extends RuntimeException{
    public SystemException(){
        super();
    }
    public SystemException(String msg){
        super(msg);
    }
}

package com.pinyougou.common;

/**
 * <p>
 * 自定义异常
 * </p>
 *
 * @author 栾宏志
 * @since 2018-07-22
 */
public class NormalException extends RuntimeException {

    //无参构造方法
    public NormalException() {
        super();
    }

    //有参的构造方法
    public NormalException(String message) {
        super(message);
    }

    // 用指定的详细信息和原因构造一个新的异常
    public NormalException(String message, Throwable cause) {
        super(message, cause);
    }

    //用指定原因构造一个新的异常
    public NormalException(Throwable cause) {
        super(cause);
    }

}

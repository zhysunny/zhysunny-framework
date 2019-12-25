package com.zhysunny.framework.springboot.bean;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

/**
 * 后端数据传前端的封装
 * @author 章云
 * @date 2019/12/25 21:21
 */
public class ResultBean<T> implements Serializable {

    private static final long serialVersionUID = -5820119804863335408L;

    private String message = "success";
    private int code = 10000;
    private T data;

    public ResultBean() {
        super();
    }

    public ResultBean(T data) {
        this.data = data;
        this.setData(data);
    }

    public ResultBean(int code, Throwable cause) {
        this.code = code;
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        cause.printStackTrace(pw);
        this.message = sw.toString();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}

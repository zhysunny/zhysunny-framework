package com.zhysunny.framework.common.business;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author 章云
 * @date 2020/1/22 14:47
 */
public abstract class Transfer implements Closeable {

    /**
     * 输入
     */
    protected Input input;
    /**
     * 业务转换
     */
    protected Business business;
    /**
     * 输出
     */
    protected Output[] outputs;

    /**
     * 用于无限循环的终止
     */
    protected boolean running;

    public Transfer(Input input, Business business, Output... outputs) {
        this.input = input;
        this.business = business;
        this.outputs = outputs;
        this.running = true;
    }

    /**
     * 传输
     * @return
     * @throws IOException
     */
    public abstract void transfer() throws IOException;

    @Override
    public void close() {
        this.running = false;
    }
}

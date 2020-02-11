package com.zhysunny.framework.common.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * ES连接池
 * @author 章云
 * @date 2019/9/17 14:01
 */
public class ClientPoolUtils<T> {

    private int length;
    private BlockingQueue<T> clients;
    private Client clientUtil;

    public static ClientPoolUtils init(Client client, int size) {
        ClientPoolUtils instance = new ClientPoolUtils();
        instance.length = size;
        instance.clients = new LinkedBlockingQueue<>();
        instance.clientUtil = client;
        return instance;
    }

    /**
     * 每次获取的时候创建ES连接，最大创建length个
     * @return
     */
    public T getClient() {
        T client = null;
        while (true) {
            client = clients.poll();
            if (client == null) {
                if (length > 0) {
                    // 连接数还没有达到最大
                    client = (T)clientUtil.getClient();
                    clients.add(client);
                    length--;
                    break;
                } else {
                    // 连接数达到最大，一直循环，等待有连接关闭
                    continue;
                }
            } else {
                break;
            }
        }
        return client;
    }

    public void close(T client) {
        clients.add(client);
    }

    public interface Client<T> {

        T getClient();

    }

}

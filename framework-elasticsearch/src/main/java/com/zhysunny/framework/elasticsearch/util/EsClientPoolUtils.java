package com.zhysunny.framework.elasticsearch.util;

import org.elasticsearch.client.transport.TransportClient;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * ES连接池
 * @author 章云
 * @date 2019/9/17 14:01
 */
public class EsClientPoolUtils {

    private static int length;
    private static String clusterName;
    private static String[] transportHosts;

    private EsClientPoolUtils() {}

    private static BlockingQueue<TransportClient> clients;

    public static void init(String clusterName, String[] transportHosts, int size) {
        length = size;
        EsClientPoolUtils.clusterName = clusterName;
        EsClientPoolUtils.transportHosts = transportHosts;
        clients = new LinkedBlockingQueue<>();
    }

    /**
     * 每次获取的时候创建ES连接，最大创建length个
     * @return
     */
    public static TransportClient getClient() {
        TransportClient client = null;
        while (true) {
            if (clients.isEmpty()) {
                if (length > 0) {
                    // 连接数还没有达到最大
                    client = EsClientUtils.getEsTransportClient(clusterName, transportHosts);
                    clients.add(client);
                    length--;
                    break;
                } else {
                    // 连接数达到最大，一直循环，等待有连接关闭
                    continue;
                }
            } else {
                client = clients.poll();
                break;
            }
        }
        return client;
    }

    public static void close(TransportClient client) {
        clients.add(client);
    }

}

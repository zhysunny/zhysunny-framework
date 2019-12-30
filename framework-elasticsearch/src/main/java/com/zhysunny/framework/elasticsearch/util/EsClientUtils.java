package com.zhysunny.framework.elasticsearch.util;

import com.zhysunny.framework.elasticsearch.constant.EsConstants;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import java.net.InetAddress;

/**
 * ES连接工具类
 * @author 章云
 * @date 2019/7/16 14:23
 */
public class EsClientUtils {

    public static TransportClient getEsTransportClient(String clusterName, String... transportHosts) {
        TransportClient client = null;
        try {
            Settings settings = Settings.builder().put("cluster.name", clusterName).build();
            TransportAddress[] addresses = new TransportAddress[transportHosts.length];
            int i = 0;
            for (String host : transportHosts) {
                host = host.replaceAll("http://", "");
                String[] inet = host.split(":");
                addresses[i++] = new InetSocketTransportAddress(InetAddress.getByName(inet[0]), EsConstants.ES_HTTP_IMPORT_PORT);
            }
            client = new PreBuiltTransportClient(settings).addTransportAddresses(addresses);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return client;
    }

    public static void main(String[] args) {
        getEsTransportClient("lv217.dct-znv.com-es", "10.45.154.217");
    }

}

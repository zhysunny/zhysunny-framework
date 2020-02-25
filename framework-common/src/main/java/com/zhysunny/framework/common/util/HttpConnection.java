package com.zhysunny.framework.common.util;

import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HttpClient
 * @author 章云
 * @date 2019/6/17 15:57
 */
public class HttpConnection {

    private HttpURLConnection connection;

    public HttpConnection(String uri, String method) {
        httpConnect(uri, method);
    }

    private void httpConnect(String uri, String method) {
        try {
            // 创建连接
            URL url = new URL(uri);
            connection = (HttpURLConnection)url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(method);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送post请求
     * @param params json对象
     */
    public String send(JSONObject params) {
        return send(params.toString());
    }

    /**
     * 发送post请求
     * @param params json字符串
     */
    public String send(String params) {
        OutputStream out = null;
        int code = 0;
        if (params != null && !"".equals(params)) {
            try {
                out = connection.getOutputStream();
                // 这样可以处理中文乱码问题
                out.write(params.getBytes("UTF-8"));
                out.flush();
                code = connection.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (code == 200) {
            return get();
        } else {
            try {
                System.out.println(connection.getResponseMessage());
            } catch (IOException e) {
            }
            return String.valueOf(code);
        }
    }

    /**
     * 读取结果响应
     * @return
     */
    public String get() {
        StringBuffer sb = new StringBuffer(128);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            while ((lines = br.readLine()) != null) {
                lines = new String(lines.getBytes(), "UTF-8");
                sb.append(lines).append('\n');
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 断开连接
     */
    public void close() {
        try {
            if (connection != null) {
                connection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

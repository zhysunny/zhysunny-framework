package com.zhysunny.framework.common.util;

import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * HttpClient
 * @author 章云
 * @date 2019/6/17 15:57
 */
public class HttpConnection {

    private HttpURLConnection connection;

    public HttpConnection(String uri, String method) throws IOException {
        httpConnect(uri, method);
    }

    public HttpConnection(String uri) throws IOException {
        httpConnect(uri, "GET");
    }

    private void httpConnect(String uri, String method) throws IOException {
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
            throw new IOException(e);
        }
    }

    /**
     * 发送post请求
     * @param params json对象
     */
    public String send(JSONObject params) throws IOException {
        return send(params.toString());
    }

    /**
     * 发送post请求
     * @param params json字符串
     */
    public String send(String params) throws IOException {
        int code = 0;
        if (params != null && !"".equals(params)) {
            OutputStream out = null;
            try {
                out = connection.getOutputStream();
                // 这样可以处理中文乱码问题
                out.write(params.getBytes(StandardCharsets.UTF_8));
                out.flush();
                code = connection.getResponseCode();
            } catch (IOException e) {
                throw new IOException(e);
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }
        if (code == 200) {
            return get();
        } else {
            throw new IOException(code + connection.getResponseMessage());
        }
    }

    /**
     * 读取结果响应
     * @return
     */
    public String get() throws IOException {
        StringBuilder sb = new StringBuilder(128);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));) {
            String lines;
            while ((lines = br.readLine()) != null) {
                lines = new String(lines.getBytes(), StandardCharsets.UTF_8);
                sb.append(lines).append('\n');
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
        return sb.toString();
    }

    /**
     * 断开连接
     */
    public void close() {
        if (connection != null) {
            connection.disconnect();
        }
    }

}

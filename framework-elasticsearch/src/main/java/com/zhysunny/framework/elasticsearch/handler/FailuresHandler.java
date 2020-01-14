package com.zhysunny.framework.elasticsearch.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.common.util.ClassUtils;
import com.zhysunny.framework.elasticsearch.EsExceptionType;
import com.zhysunny.framework.elasticsearch.handler.impl.MapperParsingExceptionHandler;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 入库异常处理
 * @author 章云
 * @date 2019/12/5 14:17
 */
public abstract class FailuresHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FailuresHandler.class);

    private static Map<String, FailuresHandler> instances;
    protected File exceptionDir;
    protected EsExceptionType exceptionType;

    protected FailuresHandler(EsExceptionType exceptionType) {
        this.exceptionType = exceptionType;
        exceptionDir = new File("error", exceptionType.value());
        if (!exceptionDir.exists()) {
            exceptionDir.mkdirs();
        }
    }

    static {
        // 实例化所有异常处理类
        init();
    }

    /**
     * 不同的异常处理
     * @param failure ES入库异常错误信息
     * @throws Exception
     */
    public abstract void handler(JSONObject failure, Map<String, JSONObject> datas) throws Exception;

    /**
     * 异常处理
     * @param bulkResponse
     * @return
     */
    public static int handler(BulkResponse bulkResponse, Map<String, JSONObject> datas) {
        // 打印异常信息
        // 防止日志过多，第一次失败是打印详细日志
        int error = 0;
        for (BulkItemResponse bulkItemResponse : bulkResponse) {
            if (bulkItemResponse.isFailed()) {
                BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
                error++;
                LOGGER.error("{}请求异常：{}", Thread.currentThread().getName(), failure.toString());
                JSONObject json = JSON.parseObject(failure.toString());
                JSONObject cause = json.getJSONObject("cause");
                String type = cause.getString("type");
                FailuresHandler failuresHandler = instances.get(type);
                if (failuresHandler == null) {
                    LOGGER.error("ES请求未知异常：{}", type);
                    System.exit(1);
                } else {
                    try {
                        failuresHandler.handler(json, datas);
                    } catch (Exception e) {
                        LOGGER.error("请求异常处理失败：{}", type);
                    }
                }
            }
        }
        return error;
    }

    private static void init() {
        String handlerPackage = MapperParsingExceptionHandler.class.getPackage().getName();
        // kafka实现类
        List<Class<?>> handlerClasses = ClassUtils.getClasses(handlerPackage);
        instances = new HashMap<>(handlerClasses.size());
        handlerClasses.forEach(clz -> {
            try {
                if (FailuresHandler.class.isAssignableFrom(clz)) {
                    FailuresHandler failuresHandler = (FailuresHandler)clz.newInstance();
                    String value = failuresHandler.getExceptionType().value();
                    instances.put(value, failuresHandler);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                LOGGER.error("kafka实现类反射异常", e);
            }
        });
    }

    public File getExceptionDir() {
        return exceptionDir;
    }

    public EsExceptionType getExceptionType() {
        return exceptionType;
    }

    public static Map<String, FailuresHandler> getInstances() {
        return instances;
    }

}

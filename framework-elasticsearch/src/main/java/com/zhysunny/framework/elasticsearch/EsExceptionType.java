package com.zhysunny.framework.elasticsearch;

/**
 * ES入库异常分类
 * @author 章云
 * @date 2019/12/5 14:48
 */
public enum EsExceptionType {

    /**
     * 一般是字段类型导致的异常
     */
    MAPPER_PARSING_EXCEPTION("mapper_parsing_exception");

    private String value;

    EsExceptionType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}

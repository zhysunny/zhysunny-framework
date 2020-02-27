package com.zhysunny.framework.elasticsearch.handler;

/**
 * ES入库异常分类
 * @author 章云
 * @date 2019/12/5 14:48
 */
public enum EsExceptionType {

    /**
     * 一般是字段类型导致的异常
     */
    MAPPER_PARSING_EXCEPTION("mapper_parsing_exception"),
    /**
     * 更新删除必须指定具体的索引名，不能使用通配符
     */
    INVALID_INDEX_NAME_EXCEPTION("invalid_index_name_exception"),
    /**
     * 更新数据时找不到对应的docid
     */
    DOCUMENT_MISSING_EXCEPTION("document_missing_exception");

    private String value;

    EsExceptionType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}

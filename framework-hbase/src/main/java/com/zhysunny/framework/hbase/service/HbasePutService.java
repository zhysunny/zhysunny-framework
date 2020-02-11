package com.zhysunny.framework.hbase.service;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import java.io.IOException;
import java.util.List;

/**
 * hbase新增数据接口
 * @author 章云
 * @date 2020/2/11 10:46
 */
public class HbasePutService extends HbaseService {

    public HbasePutService(Connection conn, String tableName) throws IOException {
        this(conn, TableName.valueOf(tableName));
    }

    public HbasePutService(Connection conn, TableName tableName) throws IOException {
        this(conn, conn.getTable(tableName));
    }

    public HbasePutService(Connection conn, Table table) throws IOException {
        super(conn, table);
    }

    /**
     * 新增单条hbase数据
     * @param data
     * @throws IOException
     */
    public void put(Put data) throws IOException {
        table.put(data);
    }

    /**
     * 批量新增hbase数据
     * @param datas
     * @throws IOException
     */
    public void put(List<Put> datas) throws IOException {
        table.put(datas);
    }

}

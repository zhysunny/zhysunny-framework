package com.zhysunny.framework.hbase.service;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * hbase删除数据接口
 * @author 章云
 * @date 2020/2/11 10:46
 */
public class HbaseDeleteService extends HbaseService {

    public HbaseDeleteService(Connection conn, String tableName) throws IOException {
        this(conn, TableName.valueOf(tableName));
    }

    public HbaseDeleteService(Connection conn, TableName tableName) throws IOException {
        this(conn, conn.getTable(tableName));
    }

    public HbaseDeleteService(Connection conn, Table table) throws IOException {
        super(conn, table);
    }

    /**
     * 删除单条hbase数据
     * @param rowkey
     * @throws IOException
     */
    public void delete(String rowkey) throws IOException {
        delete(Bytes.toBytes(rowkey));
    }

    /**
     * 删除单条hbase数据
     * @param rowkey
     * @throws IOException
     */
    public void delete(byte[] rowkey) throws IOException {
        try {
            Delete delete = new Delete(rowkey);
            table.delete(delete);
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 批量删除hbase数据
     * @param rowkeys 只支持泛型为Delete、String或byte[]
     * @throws IOException 删除hbase异常，泛型不支持
     */
    public void delete(Collection<?> rowkeys) throws IOException {
        List<Delete> list = new ArrayList<>(rowkeys.size());
        Delete delete;
        for (Object rowkey : rowkeys) {
            if (rowkey == null) {
                continue;
            }
            if (rowkey instanceof String) {
                delete = new Delete(Bytes.toBytes(rowkey.toString()));
            } else if (rowkey instanceof byte[]) {
                delete = new Delete((byte[])rowkey);
            } else if (rowkey instanceof Delete) {
                delete = new Delete((Delete)rowkey);
            } else {
                throw new IOException("只支持Delete、String或byte[]的元素，不支持" + rowkey.getClass());
            }
            list.add(delete);
        }
        if (list.size() > 0) {
            try {
                table.delete(list);
            } catch (IOException e) {
                throw e;
            }
        }
    }

}

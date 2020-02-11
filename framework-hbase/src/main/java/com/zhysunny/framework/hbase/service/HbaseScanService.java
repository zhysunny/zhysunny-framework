package com.zhysunny.framework.hbase.service;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;
import java.io.IOException;
import static org.apache.hadoop.hbase.util.Bytes.*;

/**
 * hbase查询接口
 * @author 章云
 * @date 2020/2/11 10:46
 */
public class HbaseScanService extends HbaseService {

    public HbaseScanService(Connection conn, String tableName) throws IOException {
        this(conn, TableName.valueOf(tableName));
    }

    public HbaseScanService(Connection conn, TableName tableName) throws IOException {
        this(conn, conn.getTable(tableName));
    }

    public HbaseScanService(Connection conn, Table table) throws IOException {
        super(conn, table);
    }

    /**
     * 根据rowkey查询单条hbase数据
     * @param rowkey
     * @throws IOException
     */
    public Result get(String rowkey) throws IOException {
        Get get = new Get(toBytes(rowkey));
        return get(get);
    }

    /**
     * 根据rowkey查询单条hbase数据
     * @param get
     * @return
     * @throws IOException
     */
    public Result get(Get get) throws IOException {
        return table.get(get);
    }

    /**
     * 扫描hbase表，过滤条件可以通过Scan传入
     * @param scan
     * @return
     * @throws IOException
     */
    public ResultScanner getScanner(Scan scan) throws IOException {
        return table.getScanner(scan);
    }

    /**
     * 扫描全表
     * @return
     * @throws IOException
     */
    public ResultScanner getScannerAll() throws IOException {
        Scan scan = new Scan();
        return getScanner(scan);
    }

    /**
     * 用于断点续传，设置开始rowkey
     * @param rowkey
     * @return
     * @throws IOException
     */
    public ResultScanner getScannerContinue(String rowkey) throws IOException {
        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes(rowkey));
        return getScanner(scan);
    }

    /**
     * 用于断点续传，设置开始rowkey
     * @param rowkey
     * @return
     * @throws IOException
     */
    public ResultScanner getScannerContinue(byte[] rowkey) throws IOException {
        Scan scan = new Scan();
        scan.setStartRow(rowkey);
        return getScanner(scan);
    }

    /**
     * 设置rowkey范围
     * @param start
     * @param end
     * @return
     * @throws IOException
     */
    public ResultScanner getScannerRange(String start, String end) throws IOException {
        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes(start));
        scan.setStopRow(Bytes.toBytes(end));
        return getScanner(scan);
    }

    /**
     * 设置rowkey范围
     * @param start
     * @param end
     * @return
     * @throws IOException
     */
    public ResultScanner getScannerRange(byte[] start, byte[] end) throws IOException {
        Scan scan = new Scan();
        scan.setStartRow(start);
        scan.setStopRow(end);
        return getScanner(scan);
    }

    /**
     * 设置过滤条件
     * @param filter
     * @return
     * @throws IOException
     */
    public ResultScanner getScannerFilter(Filter filter) throws IOException {
        Scan scan = new Scan();
        scan.setFilter(filter);
        return getScanner(scan);
    }

}

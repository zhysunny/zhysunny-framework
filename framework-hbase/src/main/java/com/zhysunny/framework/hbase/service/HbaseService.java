package com.zhysunny.framework.hbase.service;

import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Table;
import java.io.Closeable;
import java.io.IOException;

/**
 * @author 章云
 * @date 2020/2/11 11:33
 */
class HbaseService implements Closeable {

    protected Connection conn;
    protected Table table;
    protected boolean running;

    public HbaseService(Connection conn, Table table) {
        this.conn = conn;
        this.table = table;
        this.running = true;
    }

    @Override
    public void close() throws IOException {
        // conn由外部传入，可复用，table针对当前Service使用
        if (table != null) {
            table.close();
        }
        running = false;
    }

}

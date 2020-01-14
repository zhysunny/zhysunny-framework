package com.zhysunny.framework.common.business;

import java.io.IOException;
import java.util.List;

/**
 * 输出管理
 * @author 章云
 * @date 2020/1/4 14:05
 */
public class OutputManage<E> {

    protected Transfer[] transfers;

    public OutputManage(Transfer... transfers) {
        this.transfers = transfers;
    }

    public void start(List<E> datas) {
        for (Transfer transfer : transfers) {
            try {
                transfer.output(datas);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

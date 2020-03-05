package com.zhysunny.framework.common.business.impl;

import com.zhysunny.framework.common.business.Business;
import com.zhysunny.framework.common.business.Input;
import com.zhysunny.framework.common.business.Output;
import com.zhysunny.framework.common.business.Transfer;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author 章云
 * @date 2020/1/22 14:49
 */
public class ListToListTransfer extends Transfer {

    public ListToListTransfer(Input input, Business business, Output... outputs) {
        super(input, business, outputs);
    }

    public ListToListTransfer(Input input, Output... outputs) {
        super(input, null, outputs);
    }

    @Override
    public void transfer() throws IOException {
        List<?> in = input.read();
        final List<?> conversion = business != null ? business.conversion(in) : in;
        for (Output output : outputs) {
            output.write(conversion);
        }
    }

}

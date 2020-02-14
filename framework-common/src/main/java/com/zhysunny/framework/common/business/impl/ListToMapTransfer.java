package com.zhysunny.framework.common.business.impl;

import com.zhysunny.framework.common.business.Business;
import com.zhysunny.framework.common.business.Input;
import com.zhysunny.framework.common.business.Output;
import com.zhysunny.framework.common.business.Transfer;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author 章云
 * @date 2020/1/22 14:49
 */
public class ListToMapTransfer extends Transfer {

    public ListToMapTransfer(Input input, Business business, Output... outputs) {
        super(input, business, outputs);
    }

    @Override
    public void transfer() throws IOException {
        List<?> in = input.read();
        final Map<String, ?> map = business.conversionToMap(in);
        Arrays.stream(outputs).forEach(output -> {
            try {
                output.write(map);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}

package com.zhysunny.framework.example.performance.kafka.consumer;

import com.codahale.metrics.Meter;
import com.zhysunny.framework.common.business.Input;
import com.zhysunny.framework.common.business.Transfer;
import java.io.IOException;
import java.util.List;

/**
 * @author 章云
 * @date 2020/2/14 10:41
 */
public class ConsumerTransfer extends Transfer {

    private Meter meter;

    public ConsumerTransfer(Input input, Meter meter) {
        super(input, null);
        this.meter = meter;
    }

    @Override
    public void transfer() throws IOException {
        while (running) {
            List<byte[]> bytes = this.input.read();
            meter.mark(bytes.size());
        }
    }

}

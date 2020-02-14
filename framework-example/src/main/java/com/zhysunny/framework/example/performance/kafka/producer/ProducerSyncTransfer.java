package com.zhysunny.framework.example.performance.kafka.producer;

import com.codahale.metrics.Meter;
import com.zhysunny.framework.common.business.Input;
import com.zhysunny.framework.common.business.Output;
import com.zhysunny.framework.common.business.Transfer;
import com.zhysunny.framework.example.kafka.StaticArrayServiceImpl;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.io.IOException;
import java.util.List;

/**
 * @author 章云
 * @date 2020/2/14 10:41
 */
public class ProducerSyncTransfer extends Transfer {

    private Meter meter;
    private String topic;

    public ProducerSyncTransfer(Input input, String topic, Meter meter, Output... outputs) {
        super(input, null, outputs);
        this.meter = meter;
        this.topic = topic;
    }

    @Override
    public void transfer() throws IOException {
        List<byte[]> bytes = this.input.read();
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(topic, bytes.get(0));
        StaticArrayServiceImpl output = (StaticArrayServiceImpl)outputs[0];
        while (running) {
            output.sendSync(record);
            meter.mark();
        }
    }

}

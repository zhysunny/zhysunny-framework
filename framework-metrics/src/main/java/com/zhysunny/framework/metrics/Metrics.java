package com.zhysunny.framework.metrics;

import com.codahale.metrics.*;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 章云
 * @date 2020/2/12 14:51
 */
public class Metrics extends MetricRegistry implements Closeable {

    private List<Reporter> reporters;

    private Metrics() {
        super();
        this.reporters = new ArrayList<>();
    }

    private static class Inner {
        private static final Metrics INSTANCE = new Metrics();
    }

    public static Metrics getInstance() {
        return Inner.INSTANCE;
    }

    public void setReporter(List<Reporter> reporters) {
        this.reporters = reporters;
    }

    public void addReporter(Reporter reporters) {
        this.reporters.add(reporters);
    }

    /**
     * 启动reporter
     * @param period
     * @param unit
     */
    public void reporter(long period, TimeUnit unit) {
        if (this.reporters.size() == 0) {
            ConsoleReporter reporter = ConsoleReporter.forRegistry(this).build();
            this.reporters.add(reporter);
        }
        for (Reporter reporter : reporters) {
            if (reporter instanceof JmxReporter) {
                ((JmxReporter)reporter).start();
            } else if (period > 0) {
                ((ScheduledReporter)reporter).start(period, unit);
            }
        }
    }

    /**
     * 只对JMX有效
     */
    public void reporter() {
        reporter(0, TimeUnit.SECONDS);
    }

    @Override
    public void close() {
        for (Reporter reporter : reporters) {
            if (reporter instanceof JmxReporter) {
                ((JmxReporter)reporter).stop();
            } else {
                ((ScheduledReporter)reporter).stop();
            }
        }
    }
}

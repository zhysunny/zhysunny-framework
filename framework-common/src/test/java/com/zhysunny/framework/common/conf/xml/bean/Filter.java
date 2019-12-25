package com.zhysunny.framework.common.conf.xml.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "Filter")
@XmlAccessorType(XmlAccessType.FIELD)
public class Filter {

    @XmlElement(name = "Column")
    private List<Column> columns;

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "columns=" + columns +
                '}';
    }
}

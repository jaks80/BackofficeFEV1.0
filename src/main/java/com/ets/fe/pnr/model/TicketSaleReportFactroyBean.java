package com.ets.fe.pnr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Yusuf
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class TicketSaleReportFactroyBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private static List<TicketSaleReport> saleReports = new ArrayList<>();

    public static Collection getBeanCollection() {
        return saleReports;
    }

    public static List<TicketSaleReport> getSaleReports() {
        return saleReports;
    }
}

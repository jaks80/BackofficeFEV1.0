package com.ets.fe.acdoc_o.model;

import java.io.Serializable;
import java.util.*;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Yusuf
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class ServicesSaleReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String reportTitle;
    @XmlElement
    private String dateFrom;
    @XmlElement
    private String dateTo;
    @XmlElement
    private int totalItems;
    @XmlElement
    private String totalNetSelling;
    @XmlElement
    private String totalNetPurchase;
    @XmlElement
    private String totalRevenue;

    @XmlElement
    private List<SaleReportLine> saleReportLines = new ArrayList<>();    

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public List<SaleReportLine> getSaleReportLines() {
        return saleReportLines;
    }

    public void setSaleReportLines(List<SaleReportLine> saleReportLines) {
        this.saleReportLines = saleReportLines;
    }

    public String getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(String totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public String getTotalNetSelling() {
        return totalNetSelling;
    }

    public void setTotalNetSelling(String totalNetSelling) {
        this.totalNetSelling = totalNetSelling;
    }

    public String getTotalNetPurchase() {
        return totalNetPurchase;
    }

    public void setTotalNetPurchase(String totalNetPurchase) {
        this.totalNetPurchase = totalNetPurchase;
    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlRootElement
    public static class SaleReportLine {

        @XmlElement
        private Long invoiceId;
        @XmlElement
        private String client;
        @XmlElement
        private String docIssuedate;
        @XmlElement
        private String sellingRefference;
        @XmlElement
        private String category;
        @XmlElement
        private String title;

        @XmlElement
        private Integer qty;
        @XmlElement
        private String netSelling;
        @XmlElement
        private String netPurchase;
        @XmlElement
        private String revenue;

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public String getDocIssuedate() {
            return docIssuedate;
        }

        public void setDocIssuedate(String docIssuedate) {
            this.docIssuedate = docIssuedate;
        }

        public String getSellingRefference() {
            return sellingRefference;
        }

        public void setSellingRefference(String sellingRefference) {
            this.sellingRefference = sellingRefference;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getQty() {
            return qty;
        }

        public void setQty(Integer qty) {
            this.qty = qty;
        }

        public String getRevenue() {
            return revenue;
        }

        public void setRevenue(String revenue) {
            this.revenue = revenue;
        }

        public String getNetSelling() {
            return netSelling;
        }

        public void setNetSelling(String netSelling) {
            this.netSelling = netSelling;
        }

        public String getNetPurchase() {
            return netPurchase;
        }

        public void setNetPurchase(String netPurchase) {
            this.netPurchase = netPurchase;
        }

        public Long getInvoiceId() {
            return invoiceId;
        }

        public void setInvoiceId(Long invoiceId) {
            this.invoiceId = invoiceId;
        }
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public void addLine(SaleReportLine line) {
        this.saleReportLines.add(line);
    }
}

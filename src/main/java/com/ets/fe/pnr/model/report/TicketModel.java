package com.ets.fe.pnr.model.report;

import com.ets.fe.pnr.model.Ticket;
import com.ets.fe.util.DateUtil;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Yusuf
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class TicketModel {
    
    @XmlElement
    private String psgrName;
    @XmlElement
    private String ticketNumber;
    @XmlElement
    private String issueDate;
    @XmlElement
    private String status;
    @XmlElement
    private String fare;
    @XmlElement
    private String disc;
    @XmlElement
    private String atol;
    @XmlElement
    private String net;

    public static TicketModel ticketToModel(Ticket t){
            
            TicketModel tm = new TicketModel();
            tm.setPsgrName(t.getFullPaxName());
            tm.setTicketNumber(t.getNumericAirLineCode() + "-" + t.getTicketNo());
            tm.setStatus(t.getTktStatus().toString());
            tm.setIssueDate(DateUtil.dateToString(t.getDocIssuedate()));
            tm.setFare(t.getGrossFare().toString());
            tm.setDisc(t.getDiscount().abs().toString());
            tm.setAtol(t.getAtolChg().toString());
            tm.setNet(t.calculateNetSellingFare().toString());            
        return tm; 
    }
    
    public String getPsgrName() {
        return psgrName;
    }

    public void setPsgrName(String psgrName) {
        this.psgrName = psgrName;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public String getAtol() {
        return atol;
    }

    public void setAtol(String atol) {
        this.atol = atol;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }
}

package com.ets.fe.pnr.task;

import com.ets.fe.pnr.model.TicketSaleReport;
import com.ets.fe.pnr.ws.TicketWSClient;
import com.ets.fe.util.Enums;
import com.ets.fe.util.Enums.ClientType;
import com.ets.fe.util.Enums.TicketStatus;
import com.ets.fe.util.Enums.TicketingType;
import java.util.Date;
import javax.swing.SwingWorker;

/**
 *
 * @author Yusuf
 */
public class TicketSaleReportTask extends SwingWorker<TicketSaleReport, Integer> {

    private TicketSaleReport report;
    private TicketStatus ticketStatus;
    private String airLineCode;
    private Date issueDateFrom;
    private Date issueDateTo;
    private ClientType clienttype;
    private Long clientid;
    private String ticketingAgtOid;
    private Long userid;

    private TicketingType ticketingType;

    public TicketSaleReportTask(Long userid, TicketingType ticketingType, TicketStatus ticketStatus,
            String airLineCode, Date issueDateFrom, Date issueDateTo, Enums.ClientType clienttype,
            Long clientid, String ticketingAgtOid) {

        this.userid = userid;
        this.ticketingType = ticketingType;
        this.ticketStatus = ticketStatus;
        this.airLineCode = airLineCode;
        this.issueDateFrom = issueDateFrom;
        this.issueDateTo = issueDateTo;
        this.clienttype = clienttype;
        this.clientid = clientid;
        this.ticketingAgtOid = ticketingAgtOid;
    }

    @Override
    protected TicketSaleReport doInBackground() {

        TicketWSClient client = new TicketWSClient();
        report = client.saleReport(userid, 
                ticketingType, 
                ticketStatus, 
                airLineCode, 
                issueDateFrom, 
                issueDateTo, 
                clienttype,
                clientid, 
                ticketingAgtOid
        );
        return report;
    }

    @Override
    protected void done() {
        setProgress(100);
        if (!isCancelled()) {

        }
    }
}

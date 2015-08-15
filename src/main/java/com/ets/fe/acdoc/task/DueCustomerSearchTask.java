package com.ets.fe.acdoc.task;

import com.ets.fe.acdoc.ws.TicketingSAcDocWSClient;
import com.ets.fe.acdoc_o.ws.OtherSAcDocWSClient;
import com.ets.fe.client.model.Customer;
import com.ets.fe.util.Enums;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingWorker;
import org.jdesktop.swingx.JXBusyLabel;

/**
 *
 * @author Yusuf
 */
public class DueCustomerSearchTask extends SwingWorker<List<Customer>, Integer> {

    private JXBusyLabel busyLabel = null;

    private Enums.AcDocType acDocType = null;
    private Enums.ClientSearchType clientSearchType = null;

    public DueCustomerSearchTask() {
    }

    public DueCustomerSearchTask(JXBusyLabel busyLabel, Enums.AcDocType acDocType, Enums.ClientSearchType clientSearchType) {
        this.busyLabel = busyLabel;
        this.clientSearchType = clientSearchType;
        this.acDocType = acDocType;
    }

    @Override
    protected List<Customer> doInBackground() {

        setProgress(10);
        if (busyLabel != null) {
            busyLabel.setBusy(true);
        }

        List<Customer> customers = new ArrayList<>();
        if (Enums.ClientSearchType.TICKETING_SALES_DUE_INVOICE.equals(clientSearchType)
                || Enums.ClientSearchType.TICKETING_SALES_DUE_REFUND.equals(clientSearchType)) {

            TicketingSAcDocWSClient client = new TicketingSAcDocWSClient();
            customers = client.outstandingCusotmers(acDocType);
        } else if (Enums.ClientSearchType.OTHER_SALES_DUE_INVOICE.equals(clientSearchType)
                || Enums.ClientSearchType.OTHER_SALES_DUE_REFUND.equals(clientSearchType)) {

            OtherSAcDocWSClient client = new OtherSAcDocWSClient();
            customers = client.outstandingCusotmers(acDocType);
        }
        return customers;
    }

    @Override
    protected void done() {
        if (busyLabel != null) {
            busyLabel.setBusy(false);
        }

        setProgress(100);
    }
}

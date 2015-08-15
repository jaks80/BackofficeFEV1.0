package com.ets.fe.acdoc.task;

import com.ets.fe.acdoc.ws.TicketingPAcDocWSClient;
import com.ets.fe.acdoc.ws.TicketingSAcDocWSClient;
import com.ets.fe.acdoc_o.ws.OtherSAcDocWSClient;
import com.ets.fe.client.model.Agent;
import com.ets.fe.util.Enums;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingWorker;
import org.jdesktop.swingx.JXBusyLabel;

/**
 *
 * @author Yusuf
 */
public class DueAgentSearchTask extends SwingWorker<List<Agent>, Integer> {

    //private Enums.AgentType agentType;
    private JXBusyLabel busyLabel = null;

    private Enums.AcDocType acDocType = null;
    private Enums.ClientSearchType clientSearchType = null;

    public DueAgentSearchTask() {
    }

    public DueAgentSearchTask(JXBusyLabel busyLabel, Enums.AcDocType acDocType, Enums.ClientSearchType clientSearchType) {
        this.busyLabel = busyLabel;
        this.clientSearchType = clientSearchType;
        this.acDocType = acDocType;
    }

    @Override
    protected List<Agent> doInBackground() {

        setProgress(10);
        if (busyLabel != null) {
            busyLabel.setBusy(true);
        }
        List<Agent> agents = new ArrayList<>();

        if (Enums.ClientSearchType.TICKETING_SALES_DUE_INVOICE.equals(clientSearchType) || 
                Enums.ClientSearchType.TICKETING_SALES_DUE_REFUND.equals(clientSearchType)) {
            
            TicketingSAcDocWSClient client = new TicketingSAcDocWSClient();
            agents = client.outstandingAgents(acDocType);

        } else if (Enums.ClientSearchType.TICKETING_PURCHASE_DUE_INVOICE.equals(clientSearchType) || 
                Enums.ClientSearchType.TICKETING_PURCHASE_DUE_REFUND.equals(clientSearchType)){
            
            TicketingPAcDocWSClient client = new TicketingPAcDocWSClient();
            agents = client.outstandingAgents(acDocType);
        } else if (Enums.ClientSearchType.OTHER_SALES_DUE_INVOICE.equals(clientSearchType) || 
                Enums.ClientSearchType.OTHER_SALES_DUE_REFUND.equals(clientSearchType)){
            
            OtherSAcDocWSClient client = new OtherSAcDocWSClient();
            agents = client.outstandingAgents(acDocType);
        }

        return agents;
    }

    @Override
    protected void done() {
        if (busyLabel != null) {
            busyLabel.setBusy(false);
        }

        setProgress(100);
    }
}

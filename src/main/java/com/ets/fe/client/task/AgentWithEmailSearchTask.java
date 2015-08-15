package com.ets.fe.client.task;

import com.ets.fe.client.collection.Agents;
import com.ets.fe.client.ws.AgentWSClient;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import org.jdesktop.swingx.JXBusyLabel;

/**
 *
 * @author Yusuf
 */
public class AgentWithEmailSearchTask extends SwingWorker<Agents, Integer> {

    private String keyword = null;
    private JXBusyLabel busyLabel = null;
    private JProgressBar progressBar = null;
    private String searchType = "EMAIL";

    public AgentWithEmailSearchTask(JProgressBar progressBar, JXBusyLabel busyLabel, String searchType) {
        this.progressBar = progressBar;
        this.busyLabel = busyLabel;
        this.searchType = searchType;
    }

    public AgentWithEmailSearchTask(JProgressBar progressBar, JXBusyLabel busyLabel,String keyword, String searchType) {
        this.keyword = keyword;
        this.searchType = searchType;
        this.progressBar = progressBar;
        this.busyLabel = busyLabel;
    }

    @Override
    protected Agents doInBackground() {

        setProgress(10);
        if (busyLabel != null) {
            busyLabel.setBusy(true);
        }

        if (progressBar != null) {
            progressBar.setIndeterminate(true);
        }

        AgentWSClient client = new AgentWSClient();

        Agents agents = null;
        
        switch (this.searchType) {
            case "EMAIL":
                agents = client.findAgentsWithEmail();
                break;
            case "KEYWORD":
                agents = client.find(keyword);
                break;
        }

        return agents;
    }

    @Override
    protected void done() {
        if (busyLabel != null) {
            busyLabel.setBusy(false);
        }
        if (progressBar != null) {
            progressBar.setIndeterminate(false);
        }
        setProgress(100);
    }
}

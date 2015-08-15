package com.ets.fe.client.task;

import com.ets.fe.client.collection.Customers;
import com.ets.fe.client.ws.CustomerWSClient;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import org.jdesktop.swingx.JXBusyLabel;

/**
 *
 * @author Yusuf
 */
public class CustomerWithEmailSearchTask extends SwingWorker<Customers, Integer> {

    private String keyword = null;
    private JXBusyLabel busyLabel = null;
    private JProgressBar progressBar = null;
    private String searchType = "EMAIL";

    public CustomerWithEmailSearchTask(JProgressBar progressBar, JXBusyLabel busyLabel, String searchType) {
        this.progressBar = progressBar;
        this.busyLabel = busyLabel;
        this.searchType = searchType;
    }

    public CustomerWithEmailSearchTask(JProgressBar progressBar, JXBusyLabel busyLabel,String keyword, String searchType) {
        this.keyword = keyword;
        this.searchType = searchType;
        this.progressBar = progressBar;
        this.busyLabel = busyLabel;
    }

    @Override
    protected Customers doInBackground() {

        setProgress(10);
        if (busyLabel != null) {
            busyLabel.setBusy(true);
        }

        if (progressBar != null) {
            progressBar.setIndeterminate(true);
        }

        CustomerWSClient client = new CustomerWSClient();
        Customers customers = null;

        switch (this.searchType) {
            case "EMAIL":
                customers = client.findCustomersWithEmail();
                break;
            case "KEYWORD":
                customers = client.find(keyword);
                break;
        }

        return customers;
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

package com.ets.fe.acdoc.task;

import com.ets.fe.Application;
import com.ets.fe.acdoc_o.ws.OtherSAcDocWSClient;
import javax.swing.SwingWorker;

/**
 *
 * @author Yusuf
 */
public class DeleteDocumentLineTask extends SwingWorker<Void, Integer> {

    private Long lineId = null;


    public DeleteDocumentLineTask(Long lineId) {
        this.lineId = lineId;
    }

    @Override
    protected Void doInBackground() {

        setProgress(10);
        OtherSAcDocWSClient client = new OtherSAcDocWSClient();

        client.deleteLine(lineId, Application.getLoggedOnUser().getId());
        return null;
    }

    @Override
    protected void done() {
        setProgress(100);
    }
}

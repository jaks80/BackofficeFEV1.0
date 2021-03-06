package com.ets.fe.acdoc.gui.report;

import com.ets.fe.Application;
import com.ets.fe.acdoc.gui.SalesInvoiceDlg;
import com.ets.fe.acdoc.model.report.InvoiceReport;
import com.ets.fe.acdoc.model.report.TktingInvoiceSummery;
import com.ets.fe.acdoc.task.SalesAcDocReportingTask;
import com.ets.fe.report.XMLJasperReport;
import com.ets.fe.util.DateUtil;
import com.ets.fe.util.Enums;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author Yusuf
 */
public class OutstandingFlightReportingFrame extends javax.swing.JInternalFrame implements PropertyChangeListener {

    private JDesktopPane desktopPane;
    private SalesAcDocReportingTask task;
    private InvoiceReport report;
    public static Enums.ClientType client_type;
    private Long client_id;
    private Date from;
    private Date to;
    private String reportType = "UNPAID_FLIGHT";

    public OutstandingFlightReportingFrame(JDesktopPane desktopPane) {
        this.desktopPane = desktopPane;
        initComponents();
        
        dtTo.setDate(DateUtil.getEndOfMonth());
    }

    private void search() {

        btnSearch.setEnabled(false);

        client_type = documentSearchComponent.getContactableType();
        client_id = documentSearchComponent.getClient_id();
        from = new java.util.Date();
        to = dtTo.getDate();

        task = new SalesAcDocReportingTask(reportType, client_type, client_id, from, to, progressBar);
        task.addPropertyChangeListener(this);
        task.execute();
    }

    private void populateSummery(InvoiceReport r) {
        lblInvAmount.setText(r.getTotalInvAmount());
        lblCMemo.setText(r.getTotalCMAmount());
        lblDMemo.setText(r.getTotalDMAmount());
        lblPayment.setText(r.getTotalPayment());
        lblRefund.setText(r.getTotalRefund());
        lblDue.setText(r.getTotalDue());
    }

    private void populateTable() {
        List<TktingInvoiceSummery> invoices = this.report.getInvoices();
        DefaultTableModel tableModel = (DefaultTableModel) tblReport.getModel();
        tableModel.getDataVector().removeAllElements();
        //tblReport.repaint();
        if (invoices.size() > 0) {
            for (int i = 0; i < invoices.size(); i++) {
                TktingInvoiceSummery s = invoices.get(i);
                tableModel.insertRow(i, new Object[]{i + 1,s.getOutBoundDetails(), s.getDocIssueDate(), s.getReference(), s.getClientName(),
                    s.getGdsPnr(), s.getNoOfPax(), s.getLeadPsgr(),
                    s.getDocumentedAmount(), s.getPayment(), s.getOtherAmount(), s.getDue()});
            }
        } else {
            tableModel.insertRow(0, new Object[]{"", "", "", "", "", "", "", "", "", "", "", ""});
        }
        populateSummery(report);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        btnViewReport = new javax.swing.JButton();
        btnViewInvoice = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        documentSearchComponent = new com.ets.fe.acdoc.gui.comp.ClientSearchComp(true,true,true,Enums.AgentType.ALL);
        jSeparator2 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblInvAmount = new javax.swing.JLabel();
        lblCMemo = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblDMemo = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblPayment = new javax.swing.JLabel();
        lblRefund = new javax.swing.JLabel();
        lblDue = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        dtTo = new org.jdesktop.swingx.JXDatePicker();
        progressBar = new javax.swing.JProgressBar();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblReport =         new JXTable() {
            public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int vColIndex) {
                Component c = super.prepareRenderer(renderer, rowIndex, vColIndex);
                String s = this.getModel().getValueAt(rowIndex, 11).toString();
                if (s.startsWith("-")) {
                    c.setForeground(Color.red);
                } else {
                    c.setForeground(Color.GREEN);
                }
                return c;
            }
        };
        jPanel5 = new javax.swing.JPanel();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Unpaid flight report");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/unpaidflight20.png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        btnViewReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/details18.png"))); // NOI18N
        btnViewReport.setMaximumSize(new java.awt.Dimension(40, 22));
        btnViewReport.setMinimumSize(new java.awt.Dimension(40, 22));
        btnViewReport.setPreferredSize(new java.awt.Dimension(40, 22));
        btnViewReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewReportActionPerformed(evt);
            }
        });

        btnViewInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/acdoc18.png"))); // NOI18N
        btnViewInvoice.setMaximumSize(new java.awt.Dimension(40, 22));
        btnViewInvoice.setMinimumSize(new java.awt.Dimension(40, 22));
        btnViewInvoice.setPreferredSize(new java.awt.Dimension(40, 22));
        btnViewInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewInvoiceActionPerformed(evt);
            }
        });

        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/print18.png"))); // NOI18N
        btnPrint.setMaximumSize(new java.awt.Dimension(40, 22));
        btnPrint.setMinimumSize(new java.awt.Dimension(40, 22));
        btnPrint.setPreferredSize(new java.awt.Dimension(40, 22));
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search18.png"))); // NOI18N
        btnSearch.setMaximumSize(new java.awt.Dimension(40, 22));
        btnSearch.setMinimumSize(new java.awt.Dimension(40, 22));
        btnSearch.setPreferredSize(new java.awt.Dimension(40, 22));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(btnViewInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnViewReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(685, 685, 685))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnViewInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnViewReport, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Invoice Amount:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 2, 2);
        jPanel2.add(jLabel1, gridBagConstraints);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Credit Memo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel2.add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Due:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        jPanel2.add(jLabel4, gridBagConstraints);

        lblInvAmount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblInvAmount.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 2, 2);
        jPanel2.add(lblInvAmount, gridBagConstraints);

        lblCMemo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCMemo.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 2);
        jPanel2.add(lblCMemo, gridBagConstraints);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Debit Memo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel2.add(jLabel7, gridBagConstraints);

        lblDMemo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDMemo.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 2);
        jPanel2.add(lblDMemo, gridBagConstraints);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Payment:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        jPanel2.add(jLabel9, gridBagConstraints);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Refund:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        jPanel2.add(jLabel10, gridBagConstraints);

        lblPayment.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPayment.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 2, 4);
        jPanel2.add(lblPayment, gridBagConstraints);

        lblRefund.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRefund.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 4);
        jPanel2.add(lblRefund, gridBagConstraints);

        lblDue.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblDue.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDue.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 4);
        jPanel2.add(lblDue, gridBagConstraints);

        jLabel8.setText("Date To");

        dtTo.setPreferredSize(new java.awt.Dimension(110, 20));

        progressBar.setMaximumSize(new java.awt.Dimension(32767, 18));
        progressBar.setMinimumSize(new java.awt.Dimension(10, 18));
        progressBar.setPreferredSize(new java.awt.Dimension(146, 18));
        progressBar.setStringPainted(true);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dtTo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(2, 2, 2)
                .addComponent(dtTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 191, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tblReport.setBackground(new java.awt.Color(51, 51, 51));
        tblReport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Flight Details", "InvDate", "InvNo", "Client", "PNR", "Psgr", "Lead Psgr", "Inv Amount", "Payment", "Other (+/-)", "Balance"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblReport.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tblReport.setSelectionBackground(new java.awt.Color(255, 255, 153));
        tblReport.setSortable(false);
        tblReport.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblReport);
        if (tblReport.getColumnModel().getColumnCount() > 0) {
            tblReport.getColumnModel().getColumn(0).setMinWidth(40);
            tblReport.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblReport.getColumnModel().getColumn(0).setMaxWidth(60);
            tblReport.getColumnModel().getColumn(1).setMinWidth(130);
            tblReport.getColumnModel().getColumn(1).setPreferredWidth(130);
            tblReport.getColumnModel().getColumn(1).setMaxWidth(160);
            tblReport.getColumnModel().getColumn(2).setMinWidth(80);
            tblReport.getColumnModel().getColumn(2).setPreferredWidth(80);
            tblReport.getColumnModel().getColumn(2).setMaxWidth(80);
            tblReport.getColumnModel().getColumn(3).setMinWidth(65);
            tblReport.getColumnModel().getColumn(3).setPreferredWidth(65);
            tblReport.getColumnModel().getColumn(3).setMaxWidth(100);
            tblReport.getColumnModel().getColumn(5).setMinWidth(65);
            tblReport.getColumnModel().getColumn(5).setPreferredWidth(65);
            tblReport.getColumnModel().getColumn(5).setMaxWidth(100);
            tblReport.getColumnModel().getColumn(6).setMinWidth(40);
            tblReport.getColumnModel().getColumn(6).setPreferredWidth(40);
            tblReport.getColumnModel().getColumn(6).setMaxWidth(40);
        }

        jTabbedPane1.addTab("Outstanding Flight Report", jScrollPane1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 783, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 373, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Print View", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(2, 2, 2))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(documentSearchComponent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(documentSearchComponent, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jSeparator2)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        search();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnViewInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewInvoiceActionPerformed
        int index = tblReport.getSelectedRow();
        if (index != -1) {
            Long id = report.getInvoices().get(index).getId();

            Window w = SwingUtilities.getWindowAncestor(this);
            Frame owner = w instanceof Frame ? (Frame) w : null;
            SalesInvoiceDlg dlg = new SalesInvoiceDlg(owner);
            dlg.showDialog(id);
        }
    }//GEN-LAST:event_btnViewInvoiceActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
       
    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnViewReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewReportActionPerformed
       
    }//GEN-LAST:event_btnViewReportActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnViewInvoice;
    private javax.swing.JButton btnViewReport;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.ets.fe.acdoc.gui.comp.ClientSearchComp documentSearchComponent;
    private org.jdesktop.swingx.JXDatePicker dtTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblCMemo;
    private javax.swing.JLabel lblDMemo;
    private javax.swing.JLabel lblDue;
    private javax.swing.JLabel lblInvAmount;
    private javax.swing.JLabel lblPayment;
    private javax.swing.JLabel lblRefund;
    private javax.swing.JProgressBar progressBar;
    private org.jdesktop.swingx.JXTable tblReport;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress".equals(evt.getPropertyName())) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
            if (progress == 100) {
                try {
                    report = task.get();
                    populateTable();
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(TSalesInvoiceReportingFrame.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    btnSearch.setEnabled(true);
                }
            }
        }
    }
}

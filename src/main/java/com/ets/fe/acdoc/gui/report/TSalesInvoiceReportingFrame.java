package com.ets.fe.acdoc.gui.report;

import com.ets.fe.Application;
import com.ets.fe.a_main.ClientSearchComponent;
import com.ets.fe.acdoc.gui.SalesInvoiceDlg;
import com.ets.fe.acdoc.model.report.InvoiceReport;
import com.ets.fe.acdoc.model.report.TktingInvoiceSummery;
import com.ets.fe.acdoc.task.SalesAcDocReportingTask;
import com.ets.fe.report.BeanJasperReport;
import com.ets.fe.util.DateUtil;
import com.ets.fe.util.Enums;
import com.ets.fe.util.PnrUtil;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import net.sf.jasperreports.swing.JRViewer;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author Yusuf
 */
public class TSalesInvoiceReportingFrame extends javax.swing.JInternalFrame implements PropertyChangeListener {

    private JDesktopPane desktopPane;
    private SalesAcDocReportingTask task;
    private InvoiceReport report;
    public static Enums.ClientType client_type;
    private Long client_id;
    private Date from;
    private Date to;
    private Enums.AcDocType doc_type;
    private String reportType;

    public TSalesInvoiceReportingFrame(JDesktopPane desktopPane) {
        this.desktopPane = desktopPane;
        initComponents();

        dtFrom.setDate(DateUtil.getBeginingOfMonth());
        dtTo.setDate(DateUtil.getEndOfMonth());
    }

    private void search() {

        btnSearch.setEnabled(false);
        tabResult.setSelectedIndex(0);
        client_type = clientSearchComponent.getContactableType();
        client_id = clientSearchComponent.getClient_id();
        from = dtFrom.getDate();
        to = dtTo.getDate();

        if (rdoDueInvoice.isSelected()) {
            //doc_type = Enums.AcDocType.INVOICE;
            reportType = "OUTSTANDING";
            task = new SalesAcDocReportingTask(reportType, doc_type, client_type, client_id, from, to, progressBar);
        } else if (rdoDueRefund.isSelected()) {
            reportType = "OUTSTANDING";
            //doc_type = Enums.AcDocType.REFUND;
            task = new SalesAcDocReportingTask(reportType, doc_type, client_type, client_id, from, to, progressBar);
        } else if (rdoInvHistory.isSelected()) {
            reportType = "HISTORY";
            //doc_type = null;
            task = new SalesAcDocReportingTask(reportType, doc_type, client_type, client_id, from, to, progressBar);
        }

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
                tableModel.insertRow(i, new Object[]{i + 1, s.getDocIssueDate(), s.getReference(), s.getClientName(),
                    s.getGdsPnr(), s.getNoOfPax(), s.getOutBoundDetails(), s.getLeadPsgr(),
                    s.getDocumentedAmount(), s.getPayment(), s.getOtherAmount(), s.getDue(), PnrUtil.calculatePartialName(s.getInvBy())});
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
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        dtFrom = new org.jdesktop.swingx.JXDatePicker();
        jLabel8 = new javax.swing.JLabel();
        dtTo = new org.jdesktop.swingx.JXDatePicker();
        rdoInvHistory = new javax.swing.JRadioButton();
        rdoDueRefund = new javax.swing.JRadioButton();
        rdoDueInvoice = new javax.swing.JRadioButton();
        clientSearchComponent = new com.ets.fe.acdoc.gui.comp.ClientSearchComp(true,true,true,Enums.AgentType.ALL);
        jLabel2 = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        tabResult = new javax.swing.JTabbedPane();
        tabResult.addChangeListener(tabListener);
        jPanel6 = new javax.swing.JPanel();
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
        jPanel1 = new javax.swing.JPanel();
        btnViewInvoice = new javax.swing.JButton();
        btnEmail = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        reportPane = new javax.swing.JScrollPane();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("History: Sales Invoice");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/TicketSalesHistory20.png"))); // NOI18N
        setMinimumSize(new java.awt.Dimension(982, 545));

        jSplitPane1.setDividerLocation(210);
        jSplitPane1.setDividerSize(10);
        jSplitPane1.setOneTouchExpandable(true);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel6.setText("Date From");

        dtFrom.setPreferredSize(new java.awt.Dimension(110, 20));

        jLabel8.setText("Date To");

        dtTo.setPreferredSize(new java.awt.Dimension(110, 20));

        buttonGroup1.add(rdoInvHistory);
        rdoInvHistory.setSelected(true);
        rdoInvHistory.setText("Invoice Hisory");
        rdoInvHistory.addActionListener(radioHistoryListener);
        rdoInvHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoInvHistoryActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoDueRefund);
        rdoDueRefund.setText("Outstanding Refund");
        rdoDueRefund.addActionListener(radioDueRefundListener);

        buttonGroup1.add(rdoDueInvoice);
        rdoDueInvoice.setText("Outstanding Invoice");
        rdoDueInvoice.addActionListener(radioDueInvoiceListener);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8))
                        .addContainerGap())
                    .addComponent(dtTo, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                    .addComponent(dtFrom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdoInvHistory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rdoDueRefund, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                    .addComponent(rdoDueInvoice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(2, 2, 2)
                .addComponent(dtFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(2, 2, 2)
                .addComponent(dtTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(rdoInvHistory)
                .addGap(0, 0, 0)
                .addComponent(rdoDueRefund)
                .addGap(0, 0, 0)
                .addComponent(rdoDueInvoice)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 2, 2);
        jPanel5.add(jPanel4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 2, 0);
        jPanel5.add(clientSearchComponent, gridBagConstraints);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Message:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 2, 2);
        jPanel5.add(jLabel2, gridBagConstraints);

        progressBar.setMaximumSize(new java.awt.Dimension(32767, 18));
        progressBar.setMinimumSize(new java.awt.Dimension(10, 18));
        progressBar.setPreferredSize(new java.awt.Dimension(146, 18));
        progressBar.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 2, 2);
        jPanel5.add(progressBar, gridBagConstraints);

        jSplitPane1.setLeftComponent(jPanel5);

        tabResult.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

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

        tblReport.setBackground(new java.awt.Color(51, 51, 51));
        tblReport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Date", "InvoiceNo", "Client", "PNR", "Psgr", "Flight Details", "Lead Psgr", "Inv Amount", "Payment", "Other (+/-)", "Outstanding", "InvBy"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
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
            tblReport.getColumnModel().getColumn(1).setMinWidth(80);
            tblReport.getColumnModel().getColumn(1).setPreferredWidth(80);
            tblReport.getColumnModel().getColumn(1).setMaxWidth(80);
            tblReport.getColumnModel().getColumn(2).setPreferredWidth(60);
            tblReport.getColumnModel().getColumn(4).setPreferredWidth(60);
            tblReport.getColumnModel().getColumn(5).setMinWidth(40);
            tblReport.getColumnModel().getColumn(5).setPreferredWidth(40);
            tblReport.getColumnModel().getColumn(5).setMaxWidth(40);
            tblReport.getColumnModel().getColumn(6).setMinWidth(130);
            tblReport.getColumnModel().getColumn(6).setPreferredWidth(130);
            tblReport.getColumnModel().getColumn(6).setMaxWidth(160);
        }

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        btnViewInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/acdoc18.png"))); // NOI18N
        btnViewInvoice.setToolTipText("View Invoice");
        btnViewInvoice.setMaximumSize(new java.awt.Dimension(40, 22));
        btnViewInvoice.setMinimumSize(new java.awt.Dimension(40, 22));
        btnViewInvoice.setPreferredSize(new java.awt.Dimension(40, 22));
        btnViewInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewInvoiceActionPerformed(evt);
            }
        });

        btnEmail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/email18.png"))); // NOI18N
        btnEmail.setToolTipText("Email Report");
        btnEmail.setMaximumSize(new java.awt.Dimension(40, 22));
        btnEmail.setMinimumSize(new java.awt.Dimension(40, 22));
        btnEmail.setPreferredSize(new java.awt.Dimension(40, 22));
        btnEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmailActionPerformed(evt);
            }
        });

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search18.png"))); // NOI18N
        btnSearch.setToolTipText("Search");
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
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnViewInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnViewInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE))
        );

        tabResult.addTab("Search", jPanel6);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(reportPane, javax.swing.GroupLayout.DEFAULT_SIZE, 778, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(reportPane, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
        );

        tabResult.addTab("Print View", jPanel3);

        jSplitPane1.setRightComponent(tabResult);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
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

    private void btnEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmailActionPerformed
        if (report == null) {
            return;
        }

        String receipent = report.getEmail();
        String subject = report.getTitle().concat(" From").concat(Application.getMainAgent().getName());
        String body = report.getTitle().concat(" From").concat(Application.getMainAgent().getName());
        String refference = "report";
        if (receipent != null) {
            BeanJasperReport jasperreport = new BeanJasperReport(receipent, subject, body, refference);
            List<InvoiceReport> list = new ArrayList<>();
            list.add(report);
            jasperreport.invoiceReport(list, Enums.SaleType.TKTSALES, "EMAIL");
        } else {
            JOptionPane.showMessageDialog(null, "No Email address", "Email", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnEmailActionPerformed

    private void rdoInvHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoInvHistoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoInvHistoryActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEmail;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnViewInvoice;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.ets.fe.acdoc.gui.comp.ClientSearchComp clientSearchComponent;
    private org.jdesktop.swingx.JXDatePicker dtFrom;
    private org.jdesktop.swingx.JXDatePicker dtTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblCMemo;
    private javax.swing.JLabel lblDMemo;
    private javax.swing.JLabel lblDue;
    private javax.swing.JLabel lblInvAmount;
    private javax.swing.JLabel lblPayment;
    private javax.swing.JLabel lblRefund;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JRadioButton rdoDueInvoice;
    private javax.swing.JRadioButton rdoDueRefund;
    private javax.swing.JRadioButton rdoInvHistory;
    private javax.swing.JScrollPane reportPane;
    private javax.swing.JTabbedPane tabResult;
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

    private void report(String action) {
        BeanJasperReport jasperreport = new BeanJasperReport();
        List<InvoiceReport> list = new ArrayList<>();
        list.add(report);
        JRViewer viewer = jasperreport.invoiceReport(list, Enums.SaleType.TKTSALES, action);
        if (viewer != null) {
            reportPane.setViewportView(viewer);
        }
    }

    private ChangeListener tabListener = new ChangeListener() {

        @Override
        public void stateChanged(ChangeEvent e) {
            if (tabResult.getSelectedIndex() == 1) {
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        report("VIEW");
                    }
                });
            }
        }
    };

    private ActionListener radioHistoryListener = (ActionEvent e) -> {
        clientSearchComponent.setSearch_type(Enums.ClientSearchType.ALL);
        this.doc_type = null;
        clientSearchComponent.setAcDocType(doc_type);
    };

    private ActionListener radioDueInvoiceListener = (ActionEvent e) -> {
        clientSearchComponent.setSearch_type(Enums.ClientSearchType.TICKETING_SALES_DUE_INVOICE);
        this.doc_type = Enums.AcDocType.INVOICE;
        clientSearchComponent.setAcDocType(doc_type);
    };

    private ActionListener radioDueRefundListener = (ActionEvent e) -> {
        clientSearchComponent.setSearch_type(Enums.ClientSearchType.TICKETING_SALES_DUE_REFUND);
        this.doc_type = Enums.AcDocType.REFUND;
        clientSearchComponent.setAcDocType(doc_type);
    };
}

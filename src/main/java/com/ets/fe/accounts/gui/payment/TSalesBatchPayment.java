package com.ets.fe.accounts.gui.payment;

import com.ets.fe.Application;
import com.ets.fe.accounts.gui.logic.PaymentLogic;
import com.ets.fe.acdoc.gui.SalesInvoiceDlg;
import com.ets.fe.acdoc.model.AccountingDocument;
import com.ets.fe.accounts.model.Payment;
import com.ets.fe.acdoc.model.TicketingSalesAcDoc;
import com.ets.fe.acdoc.task.DueInvoiceTask;
import com.ets.fe.accounts.task.PaymentTask;
import com.ets.fe.util.CheckInput;
import com.ets.fe.util.DateUtil;
import com.ets.fe.util.Enums;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author Yusuf
 */
public class TSalesBatchPayment extends javax.swing.JInternalFrame implements PropertyChangeListener {

    private JDesktopPane desktopPane;
    private DueInvoiceTask task;
    private PaymentTask paymentTask;
    private List<TicketingSalesAcDoc> invoices;
    //private InvoiceReport report;
    private String taskType;
    private Enums.AcDocType doc_type = Enums.AcDocType.INVOICE;

    public TSalesBatchPayment(JDesktopPane desktopPane) {
        this.desktopPane = desktopPane;
        initComponents();
        dtFrom.setDate(DateUtil.getBeginingOfMonth());
        dtTo.setDate(DateUtil.getEndOfMonth());

        CheckInput c = new CheckInput(CheckInput.FLOAT);
        c.setNegativeAccepted(true);
        txtAmount.setDocument(c);
        //btnSubmitPayment.setEnabled(false);
        setPaymentType();
        loadDueAgents();
    }

    private void search() {
        Long client_id = clientSearchComponent.getClient_id();
        if (client_id != null) {
            taskType = "SEARCH";
            btnSearch.setEnabled(false);
            Date from = dtFrom.getDate();
            Date to = dtTo.getDate();
            task = new DueInvoiceTask(null, doc_type, Enums.ClientType.AGENT, client_id, from, to, progressBar, "SALES");
            task.addPropertyChangeListener(this);
            task.execute();
        }
    }

    public void processPayment() {
        busyLabel.setText("");
        taskType = "PAYMENT";
        busyLabel.setBusy(true);
        btnSubmitPayment.setEnabled(false);
        String amountString = txtAmount.getText();
        String remark = txtRef.getText();

        if (!amountString.isEmpty() && !remark.isEmpty() && cmbTType.getSelectedIndex() > 0) {

            int choice = JOptionPane.showConfirmDialog(null, "Submit Payment? " + amountString, "Submit Payment", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.NO_OPTION) {
                return;
            }

            BigDecimal amount = new BigDecimal(amountString.trim());
            PaymentLogic logic = new PaymentLogic();

            Enums.PaymentType type = (Enums.PaymentType) cmbTType.getSelectedItem();

            Payment payment = new Payment();
            payment.setPaymentType(type);
            payment.setRemark(remark + "(" + amount.toString() + ")");
            payment.setCreatedBy(Application.getLoggedOnUser());

            int noOfRow = tblInvoices.getRowCount();

            if (amount.compareTo(logic.calculateTotalDueAmount(invoices)) <= 0) {
                for (int i = 0; i < noOfRow; i++) {

                    Object val = tblInvoices.getValueAt(i, 9);
                    if (!val.equals("")) {
                        BigDecimal newPayment = new BigDecimal(val.toString());
                        TicketingSalesAcDoc invoice = this.invoices.get(i);

                        TicketingSalesAcDoc doc = new TicketingSalesAcDoc();
                        doc.setReference(invoice.getReference());
                        doc.setType(Enums.AcDocType.PAYMENT);
                        doc.setStatus(Enums.AcDocStatus.ACTIVE);
                        doc.setDocIssueDate(new java.util.Date());
                        doc.setPnr(invoice.getPnr());
                        doc.setCreatedBy(Application.getLoggedOnUser());
                        doc.setDocumentedAmount(newPayment.negate());//Payment saves as negative                      
                        doc.setParent(invoice);
                        doc.setRemark("Batch payment: " + amountString);
                        payment.addTSalesDocument(doc);
                    }
                }

                if (!payment.gettSalesAcDocuments().isEmpty()) {
                    paymentTask = new PaymentTask(payment, Enums.TaskType.CREATE);
                    paymentTask.addPropertyChangeListener(this);
                    paymentTask.execute();
                } else {
                    busyLabel.setText("Warning! Nothing to submit");
                    btnSubmitPayment.setEnabled(true);
                }
            } else {
                busyLabel.setText("Warning! Excess payment");
                btnSubmitPayment.setEnabled(true);
            }
        } else {
            busyLabel.setText("Warning! Mandatory fields!");
            btnSubmitPayment.setEnabled(true);
        }
    }

    private void populateTable() {

        DefaultTableModel tableModel = (DefaultTableModel) tblInvoices.getModel();
        tableModel.getDataVector().removeAllElements();

        BigDecimal totalInvAmount = new BigDecimal("0.00");
        BigDecimal totalOther = new BigDecimal("0.00");
        BigDecimal totalPayment = new BigDecimal("0.00");
        BigDecimal totalDue = new BigDecimal("0.00");

        if (invoices.size() > 0) {
            for (int i = 0; i < invoices.size(); i++) {
                TicketingSalesAcDoc inv = invoices.get(i);

                totalInvAmount = totalInvAmount.add(inv.getDocumentedAmount());
                totalOther = totalOther.add(inv.calculateRelatedDocBalance());
                totalPayment = totalPayment.add(inv.calculateTotalPayment());
                totalDue = totalDue.add(inv.calculateDueAmount());
                tableModel.insertRow(i, new Object[]{i + 1, inv.getDocIssueDate(), inv.getReference(),
                    inv.getPnr().getGdsPnr(), inv.getPnr().getNoOfPax(), inv.getDocumentedAmount(),
                    inv.calculateTotalPayment(), inv.calculateRelatedDocBalance(), inv.calculateDueAmount(), "", false});
            }
        } else {
            tableModel.insertRow(0, new Object[]{"", "", "", "", "", "", "", "", "", "", false});
        }

        lblInvAmount.setText(totalInvAmount.toString());
        lblOther.setText(totalOther.toString());
        lblPayment.setText(totalPayment.toString());
        lblDue.setText(totalDue.toString());

        tableModel.addTableModelListener(new TableModelListener() {

            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();

                    BigDecimal due = new BigDecimal(tblInvoices.getValueAt(row, 8).toString());
                    BigDecimal newPayment = new BigDecimal("0.00");

                    if (column == 9) {
                        Object val = tblInvoices.getValueAt(row, 9);

                        if (!val.equals("")) {
                            newPayment = new BigDecimal(val.toString());
                            if (newPayment.compareTo(due) <= 0) {
                                if (newPayment.compareTo(due) == 0) {
                                    if (!tblInvoices.getValueAt(row, 10).equals(true)) {
                                        tblInvoices.setValueAt(true, row, 10);
                                    }
                                } else {
                                    if (!tblInvoices.getValueAt(row, 10).equals(false)) {
                                        tblInvoices.setValueAt(false, row, 10);
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Payment can not be more then invoice amount", "Transaction", JOptionPane.WARNING_MESSAGE);
                                tblInvoices.setValueAt("", row, 9);

                            }
                        }
                        totalPaid();
                    } else if (column == 10) {
                        if (tblInvoices.getValueAt(row, 10).equals(true)) {
                            tblInvoices.setValueAt(due, row, 9);
                            totalPaid();
                        } else {
                            tblInvoices.setValueAt("", row, 9);
                            totalPaid();
                        }
                    }
                }
            }
        });
    }

    private void totalPaid() {
        int noOfRow = tblInvoices.getRowCount();
        BigDecimal totalPaid = new BigDecimal("0.00");

        for (int i = 0; i < noOfRow; i++) {
            Object rowVal = tblInvoices.getValueAt(i, 9);
            if (!rowVal.equals("")) {
                BigDecimal newPayment = new BigDecimal(rowVal.toString());
                totalPaid = totalPaid.add(newPayment);
            }
        }
        txtAmount.setText(totalPaid.toString());
    }

    private void reverseEntry() {
        BigDecimal totalAmountEntry = new BigDecimal("0.00");
        BigDecimal remainingAmount = new BigDecimal("0.00");
        BigDecimal currentDue = new BigDecimal("0.00");

        if (!txtAmount.getText().isEmpty()) {
            totalAmountEntry = new BigDecimal(txtAmount.getText());
            remainingAmount = totalAmountEntry;
        }
        int noOfRow = tblInvoices.getRowCount();

        for (int i = 0; i < noOfRow; i++) {
            currentDue = new BigDecimal(String.valueOf(tblInvoices.getValueAt(i, 8)));
            if (remainingAmount.compareTo(currentDue) >= 0) {
                tblInvoices.setValueAt(currentDue, i, 9);
                remainingAmount = remainingAmount.subtract(currentDue);
            } else if (remainingAmount.compareTo(new BigDecimal("0.00")) > 0) {
                tblInvoices.setValueAt(remainingAmount, i, 9);
                remainingAmount = remainingAmount.subtract(remainingAmount);
            }
        }
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
        btnEmail = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        dtFrom = new org.jdesktop.swingx.JXDatePicker();
        jLabel8 = new javax.swing.JLabel();
        dtTo = new org.jdesktop.swingx.JXDatePicker();
        clientSearchComponent = new com.ets.fe.acdoc.gui.comp.ClientSearchComp(false, false, false,Enums.AgentType.ALL);
        progressBar = new javax.swing.JProgressBar();
        jLabel2 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInvoices =         new JXTable() {
            public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int vColIndex) {
                Component c = super.prepareRenderer(renderer, rowIndex, vColIndex);
                String s = this.getModel().getValueAt(rowIndex, 8).toString();
                if (s.startsWith("-")) {
                    c.setForeground(Color.RED);
                } else {
                    c.setForeground(Color.GREEN);
                }
                return c;
            }
        };
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblInvAmount = new javax.swing.JLabel();
        lblOther = new javax.swing.JLabel();
        lblDMemo = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblPayment = new javax.swing.JLabel();
        lblDue = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cmbTType = new javax.swing.JComboBox();
        txtAmount = new javax.swing.JTextField();
        txtRef = new javax.swing.JTextField();
        btnSubmitPayment = new javax.swing.JButton();
        busyLabel = new org.jdesktop.swingx.JXBusyLabel();
        chkReverseEntry = new javax.swing.JCheckBox();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Ticketing Sales: Batch Payment");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/TicketBatchPayment20.png"))); // NOI18N
        setMinimumSize(new java.awt.Dimension(1000, 500));
        setPreferredSize(new java.awt.Dimension(1000, 500));

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        btnViewReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/details18.png"))); // NOI18N
        btnViewReport.setMaximumSize(new java.awt.Dimension(35, 22));
        btnViewReport.setMinimumSize(new java.awt.Dimension(35, 22));
        btnViewReport.setPreferredSize(new java.awt.Dimension(35, 22));
        btnViewReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewReportActionPerformed(evt);
            }
        });

        btnViewInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/acdoc18.png"))); // NOI18N
        btnViewInvoice.setMaximumSize(new java.awt.Dimension(35, 22));
        btnViewInvoice.setMinimumSize(new java.awt.Dimension(35, 22));
        btnViewInvoice.setPreferredSize(new java.awt.Dimension(35, 22));
        btnViewInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewInvoiceActionPerformed(evt);
            }
        });

        btnEmail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/email18.png"))); // NOI18N
        btnEmail.setMaximumSize(new java.awt.Dimension(35, 22));
        btnEmail.setMinimumSize(new java.awt.Dimension(35, 22));
        btnEmail.setPreferredSize(new java.awt.Dimension(35, 22));

        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/print18.png"))); // NOI18N
        btnPrint.setMaximumSize(new java.awt.Dimension(35, 22));
        btnPrint.setMinimumSize(new java.awt.Dimension(35, 22));
        btnPrint.setPreferredSize(new java.awt.Dimension(35, 22));

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search18.png"))); // NOI18N
        btnSearch.setMaximumSize(new java.awt.Dimension(35, 22));
        btnSearch.setMinimumSize(new java.awt.Dimension(35, 22));
        btnSearch.setPreferredSize(new java.awt.Dimension(35, 22));
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
                .addComponent(btnEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(651, 651, 651))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnViewInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnViewReport, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setDividerSize(10);
        jSplitPane1.setOneTouchExpandable(true);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Batch Payment");

        jLabel6.setText("Date From");

        dtFrom.setPreferredSize(new java.awt.Dimension(110, 20));

        jLabel8.setText("Date To");

        dtTo.setPreferredSize(new java.awt.Dimension(110, 20));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dtFrom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dtTo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8))
                        .addContainerGap())))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        clientSearchComponent.setPreferredSize(new java.awt.Dimension(170, 199));

        progressBar.setMaximumSize(new java.awt.Dimension(32767, 18));
        progressBar.setMinimumSize(new java.awt.Dimension(10, 18));
        progressBar.setPreferredSize(new java.awt.Dimension(146, 18));
        progressBar.setStringPainted(true);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Message:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(clientSearchComponent, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientSearchComponent, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(6, 6, 6)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(jPanel7);

        tblInvoices.setBackground(new java.awt.Color(51, 51, 51));
        tblInvoices.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Date", "Reference", "PNR", "Psgr", "Inv Amount", "Payment", "Other (+/-)", "Due", "NewPayment", "Full Pay?"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblInvoices.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tblInvoices.setSelectionBackground(new java.awt.Color(255, 255, 153));
        tblInvoices.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblInvoices.setSortable(false);
        tblInvoices.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblInvoices);
        if (tblInvoices.getColumnModel().getColumnCount() > 0) {
            tblInvoices.getColumnModel().getColumn(0).setMinWidth(40);
            tblInvoices.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblInvoices.getColumnModel().getColumn(0).setMaxWidth(60);
            tblInvoices.getColumnModel().getColumn(1).setMinWidth(80);
            tblInvoices.getColumnModel().getColumn(1).setPreferredWidth(80);
            tblInvoices.getColumnModel().getColumn(1).setMaxWidth(80);
            tblInvoices.getColumnModel().getColumn(4).setMinWidth(40);
            tblInvoices.getColumnModel().getColumn(4).setPreferredWidth(40);
            tblInvoices.getColumnModel().getColumn(4).setMaxWidth(40);
            tblInvoices.getColumnModel().getColumn(10).setMinWidth(60);
            tblInvoices.getColumnModel().getColumn(10).setPreferredWidth(60);
            tblInvoices.getColumnModel().getColumn(10).setMaxWidth(80);
        }

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
        jLabel3.setText("Other (+/-):");
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

        lblOther.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblOther.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 2);
        jPanel2.add(lblOther, gridBagConstraints);

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

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Submit Payment", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanel8.setLayout(new java.awt.GridBagLayout());

        jLabel5.setText("T.Type");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        jPanel8.add(jLabel5, gridBagConstraints);

        jLabel11.setText("Amount");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        jPanel8.add(jLabel11, gridBagConstraints);

        jLabel12.setText("Remark");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        jPanel8.add(jLabel12, gridBagConstraints);

        cmbTType.setMinimumSize(new java.awt.Dimension(28, 19));
        cmbTType.setPreferredSize(new java.awt.Dimension(28, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 2, 2);
        jPanel8.add(cmbTType, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel8.add(txtAmount, gridBagConstraints);

        txtRef.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 4, 2);
        jPanel8.add(txtRef, gridBagConstraints);

        btnSubmitPayment.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnSubmitPayment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/payment18.png"))); // NOI18N
        btnSubmitPayment.setText("Submit");
        btnSubmitPayment.setPreferredSize(new java.awt.Dimension(135, 30));
        btnSubmitPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitPaymentActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel8.add(btnSubmitPayment, gridBagConstraints);

        busyLabel.setDirection(null);
        busyLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel8.add(busyLabel, gridBagConstraints);

        chkReverseEntry.setText("Allocate Amount");
        chkReverseEntry.addActionListener(chkReverseEntryListener);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        jPanel8.add(chkReverseEntry, gridBagConstraints);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel9);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(2, 2, 2))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jSplitPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        search();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnSubmitPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitPaymentActionPerformed
        processPayment();
    }//GEN-LAST:event_btnSubmitPaymentActionPerformed

    private void btnViewReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewReportActionPerformed

    }//GEN-LAST:event_btnViewReportActionPerformed

    private void btnViewInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewInvoiceActionPerformed
        int index = tblInvoices.getSelectedRow();
        if (index != -1) {
            Long id = invoices.get(index).getId();

            Window w = SwingUtilities.getWindowAncestor(this);
            Frame owner = w instanceof Frame ? (Frame) w : null;
            SalesInvoiceDlg dlg = new SalesInvoiceDlg(owner);
            dlg.showDialog(id);
        }
    }//GEN-LAST:event_btnViewInvoiceActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEmail;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSubmitPayment;
    private javax.swing.JButton btnViewInvoice;
    private javax.swing.JButton btnViewReport;
    private org.jdesktop.swingx.JXBusyLabel busyLabel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkReverseEntry;
    private com.ets.fe.acdoc.gui.comp.ClientSearchComp clientSearchComponent;
    private javax.swing.JComboBox cmbTType;
    private org.jdesktop.swingx.JXDatePicker dtFrom;
    private org.jdesktop.swingx.JXDatePicker dtTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblDMemo;
    private javax.swing.JLabel lblDue;
    private javax.swing.JLabel lblInvAmount;
    private javax.swing.JLabel lblOther;
    private javax.swing.JLabel lblPayment;
    private javax.swing.JProgressBar progressBar;
    private org.jdesktop.swingx.JXTable tblInvoices;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtRef;
    // End of variables declaration//GEN-END:variables

    private void resetPaymentComponent() {
        btnSubmitPayment.setEnabled(true);
        cmbTType.setSelectedIndex(0);
        txtAmount.setText("");
        txtRef.setText("");
        chkReverseEntry.setSelected(false);
    }

    private void setPaymentType() {
        DefaultComboBoxModel model = new DefaultComboBoxModel(Enums.PaymentType.values());
        model.insertElementAt("Select", 0);
        cmbTType.setModel(model);
        cmbTType.setSelectedIndex(0);
    }
    private ActionListener chkReverseEntryListener = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            if (!txtAmount.getText().isEmpty()) {
                if (chkReverseEntry.isSelected() == true) {
                    reverseEntry();
                } else {
                    populateTable();
                }
            } else {
                JOptionPane.showMessageDialog(null, "No amount to allocate", "Batch Transaction", JOptionPane.WARNING_MESSAGE);
                chkReverseEntry.setSelected(false);
            }
        }
    };

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress".equals(evt.getPropertyName())) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
            if (progress == 100) {
                try {
                    if (null != taskType) {
                        switch (taskType) {
                            case "SEARCH":
                                invoices = new ArrayList<>();
                                List<AccountingDocument> list = task.get();
                                for (AccountingDocument doc : list) {
                                    invoices.add((TicketingSalesAcDoc) doc);
                                }
                                populateTable();
                                taskType = "";
                                break;
                            case "PAYMENT":
                                resetPaymentComponent();
                                search();
                                break;
                        }
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(TSalesBatchPayment.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    btnSearch.setEnabled(true);
                }
            }
        }
    }

    private void loadDueAgents() {
        clientSearchComponent.setSearch_type(Enums.ClientSearchType.TICKETING_SALES_DUE_INVOICE);
        this.doc_type = Enums.AcDocType.INVOICE;
        clientSearchComponent.setAcDocType(doc_type);
        clientSearchComponent.setClient_type(Enums.ClientType.AGENT);
        clientSearchComponent.dueAgentTask();
    }

}

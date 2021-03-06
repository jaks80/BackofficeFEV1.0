package com.ets.fe.pnr.gui;

import com.ets.fe.client.model.Customer;
import com.ets.fe.pnr.model.Ticket;
import com.ets.fe.util.CheckInput;
import com.ets.fe.util.Enums;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;

/**
 *
 * @author Yusuf
 */
public class DlgTicketPurchase extends javax.swing.JDialog implements ActionListener {

    private boolean save;
    private Ticket ticket;

    public DlgTicketPurchase(java.awt.Frame parent) {
        super(parent, true);
        initComponents();
        btnOk.addActionListener(this);        
        CheckInput a = new CheckInput();
        CheckInput b = new CheckInput();
        CheckInput c = new CheckInput();
        CheckInput d = new CheckInput();
        c.setNegativeAccepted(true);
        txtBaseFare.setDocument(a);
        txtTax.setDocument(b);
        txtBspCom.setDocument(c);
        txtFees.setDocument(d);
        cmbTicketStatus();
    }

    public boolean showDialog(Ticket ticket) {
        this.ticket = ticket;
        if (ticket != null) {
            txtName.setText(this.ticket.getSurName() + "/" + this.ticket.getForeName());
            txtAirlineCode.setText(this.ticket.getNumericAirLineCode());
            txtTktNo.setText(this.ticket.getTicketNo());
            cmbStatus.setSelectedItem(this.ticket.getTktStatus());
            dtIssueDate.setDate(this.ticket.getDocIssuedate());

            txtBaseFare.setText(this.ticket.getBaseFare().toString());
            txtTax.setText(this.ticket.getTax().toString());
            txtFees.setText(this.ticket.getFee().toString());
            txtBspCom.setText(this.ticket.getCommission().toString());
            txtNetPurchaseFare.setText(this.ticket.calculateNetPurchaseFare().toString());
            txtBaseFare.requestFocus();
        }
        save = false;
        setLocationRelativeTo(this);
        setVisible(true);

        if (save) {
            ticket = this.ticket;
        }

        return save;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();
        if (source == btnOk) {
            save = true;
            setVisible(false);

        } else {
            dispose();
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtAirlineCode = new javax.swing.JTextField();
        txtBaseFare = new javax.swing.JTextField();
        txtTax = new javax.swing.JTextField();
        txtBspCom = new javax.swing.JTextField();
        txtNetPurchaseFare = new javax.swing.JTextField();
        cmbStatus = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtName = new javax.swing.JTextArea();
        jSeparator2 = new javax.swing.JSeparator();
        txtTktNo = new javax.swing.JTextField();
        dtIssueDate = new org.jdesktop.swingx.JXDatePicker();
        jLabel13 = new javax.swing.JLabel();
        txtFees = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Ticket");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel1.add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 4, 0);
        jPanel1.add(jSeparator1, gridBagConstraints);

        jLabel2.setText("Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jLabel2, gridBagConstraints);

        jLabel3.setText("TktNo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jLabel3, gridBagConstraints);

        jLabel4.setText("Status");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jLabel4, gridBagConstraints);

        jLabel5.setText("B.Fare");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jLabel5, gridBagConstraints);

        jLabel6.setText("Tax");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jLabel6, gridBagConstraints);

        jLabel7.setText("Com");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jLabel7, gridBagConstraints);

        jLabel8.setText("NetFare");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jLabel8, gridBagConstraints);

        txtAirlineCode.setEditable(false);
        txtAirlineCode.setToolTipText("Airline Code");
        txtAirlineCode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAirlineCodeFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(txtAirlineCode, gridBagConstraints);

        txtBaseFare.setBackground(new java.awt.Color(255, 255, 204));
        txtBaseFare.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtBaseFare.setMinimumSize(new java.awt.Dimension(70, 20));
        txtBaseFare.setPreferredSize(new java.awt.Dimension(70, 20));
        txtBaseFare.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBaseFareFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBaseFareFocusLost(evt);
            }
        });
        txtBaseFare.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBaseFareKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(txtBaseFare, gridBagConstraints);

        txtTax.setBackground(new java.awt.Color(255, 255, 204));
        txtTax.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtTax.setMinimumSize(new java.awt.Dimension(70, 20));
        txtTax.setPreferredSize(new java.awt.Dimension(70, 20));
        txtTax.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTaxFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTaxFocusLost(evt);
            }
        });
        txtTax.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTaxKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(txtTax, gridBagConstraints);

        txtBspCom.setBackground(new java.awt.Color(255, 255, 204));
        txtBspCom.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtBspCom.setMinimumSize(new java.awt.Dimension(70, 20));
        txtBspCom.setPreferredSize(new java.awt.Dimension(70, 20));
        txtBspCom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBspComFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBspComFocusLost(evt);
            }
        });
        txtBspCom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBspComKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(txtBspCom, gridBagConstraints);

        txtNetPurchaseFare.setEditable(false);
        txtNetPurchaseFare.setBackground(new java.awt.Color(0, 0, 0));
        txtNetPurchaseFare.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtNetPurchaseFare.setForeground(new java.awt.Color(255, 255, 0));
        txtNetPurchaseFare.setMinimumSize(new java.awt.Dimension(70, 20));
        txtNetPurchaseFare.setPreferredSize(new java.awt.Dimension(70, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(txtNetPurchaseFare, gridBagConstraints);

        cmbStatus.setToolTipText("");
        cmbStatus.setMaximumSize(new java.awt.Dimension(32767, 19));
        cmbStatus.setMinimumSize(new java.awt.Dimension(28, 19));
        cmbStatus.setPreferredSize(new java.awt.Dimension(28, 19));
        cmbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbStatusActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(cmbStatus, gridBagConstraints);

        txtName.setEditable(false);
        txtName.setColumns(12);
        txtName.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtName.setLineWrap(true);
        txtName.setRows(2);
        txtName.setToolTipText("Surname / Forename(s)");
        jScrollPane1.setViewportView(txtName);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(jScrollPane1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        jPanel1.add(jSeparator2, gridBagConstraints);

        txtTktNo.setEditable(false);
        txtTktNo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTktNoFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(txtTktNo, gridBagConstraints);

        dtIssueDate.setToolTipText("Date: Booking / Issue / Refund");
        dtIssueDate.setMaximumSize(new java.awt.Dimension(108, 20));
        dtIssueDate.setMinimumSize(new java.awt.Dimension(108, 20));
        dtIssueDate.setPreferredSize(new java.awt.Dimension(112, 20));
        dtIssueDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dtIssueDateActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(dtIssueDate, gridBagConstraints);

        jLabel13.setText("Fees");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jLabel13, gridBagConstraints);

        txtFees.setBackground(new java.awt.Color(255, 255, 204));
        txtFees.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtFees.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFeesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFeesFocusLost(evt);
            }
        });
        txtFees.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFeesKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(txtFees, gridBagConstraints);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel18.png"))); // NOI18N
        jButton1.setText("Cancel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ok18.png"))); // NOI18N
        btnOk.setText("OK");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOk))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(btnOk))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtAirlineCodeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAirlineCodeFocusLost

    }//GEN-LAST:event_txtAirlineCodeFocusLost

    private void txtBaseFareFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBaseFareFocusGained
        txtBaseFare.selectAll();
    }//GEN-LAST:event_txtBaseFareFocusGained

    private void txtBaseFareFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBaseFareFocusLost

    }//GEN-LAST:event_txtBaseFareFocusLost

    private void txtBaseFareKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBaseFareKeyReleased
        int key = evt.getKeyCode();
        String text = txtBaseFare.getText().replaceAll("[^.0-9]", "");
        if (key != KeyEvent.VK_ENTER && key != KeyEvent.VK_DOWN && key != KeyEvent.VK_UP
                && key != KeyEvent.VK_RIGHT && key != KeyEvent.VK_LEFT) {
            if (!text.isEmpty() && key != KeyEvent.VK_PERIOD && key != KeyEvent.VK_DECIMAL) {
                BigDecimal val = new BigDecimal(text);
                if (ticket.getTktStatus() == Enums.TicketStatus.REFUND) {
                    val = val.negate();
                }
                this.ticket.setBaseFare(val);
                calculatePurchaseBalance();
                //setSaveNeeded(true);
            }
        } else {
            keyEvents(evt, txtBspCom, txtTax);
        }
    }//GEN-LAST:event_txtBaseFareKeyReleased

    private void txtTaxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTaxFocusGained
        txtTax.selectAll();
    }//GEN-LAST:event_txtTaxFocusGained

    private void txtTaxFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTaxFocusLost

    }//GEN-LAST:event_txtTaxFocusLost

    private void txtTaxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTaxKeyReleased
        int key = evt.getKeyCode();
        String text = txtTax.getText().replaceAll("[^.0-9]", "");
        if (key != KeyEvent.VK_ENTER && key != KeyEvent.VK_DOWN && key != KeyEvent.VK_UP
                && key != KeyEvent.VK_RIGHT && key != KeyEvent.VK_LEFT) {
            if (!text.isEmpty() && key != KeyEvent.VK_PERIOD && key != KeyEvent.VK_DECIMAL) {
                BigDecimal val = new BigDecimal(text);
                if (ticket.getTktStatus() == Enums.TicketStatus.REFUND) {
                    val = val.negate();
                }
                this.ticket.setTax(val);
                calculatePurchaseBalance();
                //setSaveNeeded(true);
            }
        } else {
            keyEvents(evt, txtBaseFare, txtFees);
        }
    }//GEN-LAST:event_txtTaxKeyReleased

    private void txtBspComFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBspComFocusGained
        txtBspCom.selectAll();
    }//GEN-LAST:event_txtBspComFocusGained

    private void txtBspComFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBspComFocusLost

    }//GEN-LAST:event_txtBspComFocusLost

    private void txtBspComKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBspComKeyReleased
        int key = evt.getKeyCode();
        String text = txtBspCom.getText().replaceAll("[^.0-9]", "");
        if (key != KeyEvent.VK_ENTER && key != KeyEvent.VK_DOWN && key != KeyEvent.VK_UP
                && key != KeyEvent.VK_RIGHT && key != KeyEvent.VK_LEFT) {
            if (!text.isEmpty() && key != KeyEvent.VK_PERIOD && key != KeyEvent.VK_DECIMAL) {
                BigDecimal val = new BigDecimal(text);
                if (ticket.getTktStatus() != Enums.TicketStatus.REFUND) {
                    val = val.negate();
                }
                this.ticket.setCommission(val);
                calculatePurchaseBalance();
                //setSaveNeeded(true);
            }

        } else {
            keyEvents(evt, txtFees, txtBaseFare);
        }
    }//GEN-LAST:event_txtBspComKeyReleased

    private void cmbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbStatusActionPerformed
        ticket.setTktStatus((Enums.TicketStatus) cmbStatus.getSelectedItem());
    }//GEN-LAST:event_cmbStatusActionPerformed

    private void txtTktNoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTktNoFocusLost
        String val = txtTktNo.getText();
        if (val != null && !val.isEmpty()) {
            ticket.setTicketNo(val);
            //setSaveNeeded(true);
        }
    }//GEN-LAST:event_txtTktNoFocusLost

    private void dtIssueDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dtIssueDateActionPerformed
        ticket.setDocIssuedate(dtIssueDate.getDate());
    }//GEN-LAST:event_dtIssueDateActionPerformed

    private void txtFeesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFeesFocusGained
        txtFees.selectAll();
    }//GEN-LAST:event_txtFeesFocusGained

    private void txtFeesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFeesFocusLost

    }//GEN-LAST:event_txtFeesFocusLost

    private void txtFeesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFeesKeyReleased
        int key = evt.getKeyCode();
        String text = txtFees.getText().replaceAll("[^.0-9]", "");
        if (key != KeyEvent.VK_ENTER && key != KeyEvent.VK_DOWN && key != KeyEvent.VK_UP
                && key != KeyEvent.VK_RIGHT && key != KeyEvent.VK_LEFT) {
            if (!text.isEmpty() && key != KeyEvent.VK_PERIOD && key != KeyEvent.VK_DECIMAL) {
                BigDecimal val = new BigDecimal(text);
                if (ticket.getTktStatus() == Enums.TicketStatus.REFUND) {
                    val = val.negate();
                }
                this.ticket.setFee(val);
                calculatePurchaseBalance();
                //setSaveNeeded(true);
            }
        } else {
            keyEvents(evt, txtTax, txtBspCom);
        }
    }//GEN-LAST:event_txtFeesKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOk;
    private javax.swing.JComboBox cmbStatus;
    private org.jdesktop.swingx.JXDatePicker dtIssueDate;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField txtAirlineCode;
    private javax.swing.JTextField txtBaseFare;
    private javax.swing.JTextField txtBspCom;
    private javax.swing.JTextField txtFees;
    private javax.swing.JTextArea txtName;
    private javax.swing.JTextField txtNetPurchaseFare;
    private javax.swing.JTextField txtTax;
    private javax.swing.JTextField txtTktNo;
    // End of variables declaration//GEN-END:variables

    private void keyEvents(java.awt.event.KeyEvent evt, JTextField previousField, JTextField nextField) {
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER || key == KeyEvent.VK_DOWN) {
            nextField.requestFocus();
        } else if (key == KeyEvent.VK_UP) {
            previousField.requestFocus();
        }
    }

    private void cmbTicketStatus() {
        DefaultComboBoxModel model = new DefaultComboBoxModel(Enums.TicketStatus.values());
        cmbStatus.setModel(model);
    }

    private void calculatePurchaseBalance() {
        txtNetPurchaseFare.setText(ticket.calculateNetPurchaseFare().toString());
    }
}

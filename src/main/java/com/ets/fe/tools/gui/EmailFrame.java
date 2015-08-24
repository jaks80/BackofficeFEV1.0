package com.ets.fe.tools.gui;

import com.ets.fe.acdoc.gui.comp.ClientSearchComp;
import com.ets.fe.client.collection.Agents;
import com.ets.fe.client.collection.Customers;
import com.ets.fe.client.model.Agent;
import com.ets.fe.client.model.Customer;
import com.ets.fe.client.task.AgentWithEmailSearchTask;
import com.ets.fe.client.task.CustomerWithEmailSearchTask;
import com.ets.fe.util.EmailService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Yusuf
 */
public class EmailFrame extends javax.swing.JInternalFrame implements PropertyChangeListener {

    private AgentWithEmailSearchTask agentTask;
    private CustomerWithEmailSearchTask customerTask;

    private List<Customer> customerList = new ArrayList<>();
    private List<Agent> agentList = new ArrayList<>();

    private Set<String> emailids = new LinkedHashSet<>();
    private String[] attachPaths = new String[4];
    private int MAX_FILE_SIZE = 9000000;
    private EmailService emailService;

    public EmailFrame() {
        initComponents();
    }

    private void displayAgent() {
        DefaultTableModel tableModel = (DefaultTableModel) tblClient.getModel();
        tableModel.getDataVector().removeAllElements();

        if (agentList.isEmpty()) {
            tableModel.insertRow(0, new Object[]{"", "", ""});
            return;
        }

        int i = 0;
        for (Agent agent : agentList) {
            tableModel.insertRow(i, new Object[]{agent.getName(), agent.getEmail(), false});
            i++;
        }
        if (i > 0) {
            tblClient.getModel().addTableModelListener(tblClientListener);
        }
    }

    private void displayCustomer() {
        DefaultTableModel tableModel = (DefaultTableModel) tblClient.getModel();
        tableModel.getDataVector().removeAllElements();

        if (customerList.isEmpty()) {
            tableModel.insertRow(0, new Object[]{"", "", ""});
            return;
        }

        int i = 0;
        for (Customer cust : customerList) {
            tableModel.insertRow(i, new Object[]{cust.calculateFullName(), cust.getEmail(), false});
            i++;
        }
        if (i > 0) {
            tblClient.getModel().addTableModelListener(tblClientListener);
        }
    }

    private void displayEmailIds() {        
        txtRecepient.setText("");
        for (String s : emailids) {
            txtRecepient.append(s);
            txtRecepient.append(",");
        }
    }

    private void selectAll() {
        emailids = new LinkedHashSet<>();
        if (btnSelect.isSelected()) {
            for (int i = 0; i < tblClient.getRowCount(); i++) {
                tblClient.getModel().setValueAt(true, i, 2);
                String email = (String) tblClient.getValueAt(i, 1);
                if (!email.isEmpty() && email.contains("@")) {
                    emailids.add(email);
                }
            }
        } else {
            for (int i = 0; i < tblClient.getRowCount(); i++) {
                tblClient.getModel().setValueAt(false, i, 2);
                String email = (String) tblClient.getValueAt(i, 1);
                if (!email.isEmpty()) {
                    emailids.remove(email);
                }
            }
        }
        displayEmailIds();
    }

    TableModelListener tblClientListener = new TableModelListener() {

        @Override
        public void tableChanged(TableModelEvent e) {

            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getLastRow();
                int column = e.getColumn();

                if (column == 2) {
                    if ((boolean) tblClient.getValueAt(row, 2)) {
                        String email = (String) tblClient.getValueAt(row, 1);
                        if (!StringUtils.isBlank(email) && email.contains("@")) {
                            emailids.add(email);
                        }
                    } else {
                        String email = (String) tblClient.getValueAt(row, 1);
                        if (!StringUtils.isBlank(email)) {
                            emailids.remove(email);
                        }
                    }
                    displayEmailIds();
                }
            }
        }
    };

    public File getFile() {
        int returnVal = fileChooser.showOpenDialog(this);
        File file = fileChooser.getSelectedFile();
        if (file != null && file.length() > MAX_FILE_SIZE) {
            JOptionPane.showMessageDialog(null, "Email", "Attachment too big. Max Size: " + this.MAX_FILE_SIZE, JOptionPane.WARNING_MESSAGE);
            return null;//too big
        }
        return file;
    }

    private byte[] convertByteArray(File file) throws IOException {

        Path path = Paths.get(file.getAbsolutePath());
        byte[] data = Files.readAllBytes(path);
        return data;
    }

    private void sendMail() throws IOException {
        String subject = txtSubject.getText();
        String body = txtMessage.getText();
        Map<String, byte[]> attachments = new HashMap();
        for (String path : attachPaths) {
            if (path != null && !path.isEmpty()) {
                File file = new File(path);
                attachments.put(file.getName(), convertByteArray(file));
            }
        }
        emailService = new EmailService();
        emailService.SendMail(emailids, subject, body, attachments);
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

        jScrollPane5 = new javax.swing.JScrollPane();
        jXTable1 = new org.jdesktop.swingx.JXTable();
        buttonGroup1 = new javax.swing.ButtonGroup();
        fileChooser = new javax.swing.JFileChooser();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        rdoAgent = new javax.swing.JRadioButton();
        rdoCustomer = new javax.swing.JRadioButton();
        btnSelect = new javax.swing.JToggleButton();
        busyLabel = new org.jdesktop.swingx.JXBusyLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblClient = new org.jdesktop.swingx.JXTable();
        txtKeyword = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        mainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtRecepient = new javax.swing.JTextArea();
        txtSubject = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtMessage = new javax.swing.JTextPane();
        btnSend = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        progressBar = new javax.swing.JProgressBar();
        lblStatus = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        btnAttach0 = new javax.swing.JButton();
        btnAttach1 = new javax.swing.JButton();
        btnAttach2 = new javax.swing.JButton();
        btnAttach3 = new javax.swing.JButton();

        jXTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jXTable1);

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Email Sender");

        jSplitPane1.setDividerLocation(500);
        jSplitPane1.setDividerSize(12);
        jSplitPane1.setResizeWeight(1.0);
        jSplitPane1.setToolTipText("");
        jSplitPane1.setOneTouchExpandable(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Client", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        buttonGroup1.add(rdoAgent);
        rdoAgent.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rdoAgent.setText("Agent");
        rdoAgent.setToolTipText("Select to Search/Get All Agent");

        buttonGroup1.add(rdoCustomer);
        rdoCustomer.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rdoCustomer.setText("Customer");

        btnSelect.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnSelect.setText("All");
        btnSelect.setToolTipText("Select/Unselect All");
        btnSelect.setPreferredSize(new java.awt.Dimension(47, 27));
        btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });

        busyLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        tblClient.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Email", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblClient.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(tblClient);
        if (tblClient.getColumnModel().getColumnCount() > 0) {
            tblClient.getColumnModel().getColumn(2).setMaxWidth(20);
        }

        txtKeyword.setToolTipText("<html>\nName or SurName or ForeName or TelNo or PostCode or City<br>\nLeave Empty to get All Agent or Customer that has email id.\n</html>");
        txtKeyword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtKeywordFocusGained(evt);
            }
        });
        txtKeyword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKeywordKeyReleased(evt);
            }
        });

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search18.png"))); // NOI18N
        btnSearch.setToolTipText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(rdoAgent)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdoCustomer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(busyLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtKeyword, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(btnSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(busyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rdoAgent)
                        .addComponent(rdoCustomer)))
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtKeyword)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(jPanel2);

        jScrollPane2.setViewportView(jTextPane1);

        jSplitPane1.setLeftComponent(jScrollPane2);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Mail To");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Subject");

        txtRecepient.setColumns(20);
        txtRecepient.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        txtRecepient.setLineWrap(true);
        txtRecepient.setRows(5);
        jScrollPane1.setViewportView(txtRecepient);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Message");

        jScrollPane3.setViewportView(txtMessage);

        btnSend.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnSend.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/email18.png"))); // NOI18N
        btnSend.setText("Send");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/clear18.png"))); // NOI18N
        jButton3.setText("Clear");

        progressBar.setMaximumSize(new java.awt.Dimension(145, 17));
        progressBar.setMinimumSize(new java.awt.Dimension(145, 17));
        progressBar.setPreferredSize(new java.awt.Dimension(145, 17));
        progressBar.setStringPainted(true);

        lblStatus.setText("Status");

        jPanel3.setLayout(new java.awt.GridBagLayout());

        btnAttach0.setText("Attach");
        btnAttach0.setToolTipText("Click to attach file. Click again to remove file.");
        btnAttach0.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnAttach0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAttach0ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel3.add(btnAttach0, gridBagConstraints);

        btnAttach1.setText("Attach");
        btnAttach1.setToolTipText("Click to attach file. Click again to remove file.");
        btnAttach1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnAttach1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAttach1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel3.add(btnAttach1, gridBagConstraints);

        btnAttach2.setText("Attach");
        btnAttach2.setToolTipText("Click to attach file. Click again to remove file.");
        btnAttach2.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnAttach2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAttach2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel3.add(btnAttach2, gridBagConstraints);

        btnAttach3.setText("Attach");
        btnAttach3.setToolTipText("Click to attach file. Click again to remove file.");
        btnAttach3.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnAttach3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAttach3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel3.add(btnAttach3, gridBagConstraints);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addComponent(txtSubject)))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSend))
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane3)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtSubject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSend)
                    .addComponent(jButton3))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStatus))
                .addGap(4, 4, 4))
        );

        jSplitPane1.setLeftComponent(mainPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 966, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectActionPerformed
        selectAll();
    }//GEN-LAST:event_btnSelectActionPerformed

    private void btnAttach0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAttach0ActionPerformed
        if (btnAttach0.getText().equals("Attach")) {
            File file = getFile();

            this.attachPaths[0] = file.getAbsolutePath();
            btnAttach0.setText(file.getName());
            btnAttach0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete18.png")));
        } else {
            this.attachPaths[0] = "";
            btnAttach0.setText("Attach");
            btnAttach0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete18.png")));
        }
    }//GEN-LAST:event_btnAttach0ActionPerformed

    private void btnAttach1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAttach1ActionPerformed
        if (btnAttach1.getText().equals("Attach")) {
            File file = getFile();

            this.attachPaths[1] = file.getAbsolutePath();
            btnAttach1.setText(file.getName());
            btnAttach1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete18.png")));
        } else {
            this.attachPaths[1] = "";
            btnAttach1.setText("Attach");
            btnAttach1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete18.png")));
        }
    }//GEN-LAST:event_btnAttach1ActionPerformed

    private void btnAttach2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAttach2ActionPerformed
        if (btnAttach2.getText().equals("Attach")) {
            File file = getFile();

            this.attachPaths[2] = file.getAbsolutePath();
            btnAttach2.setText(file.getName());
            btnAttach2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete18.png")));
        } else {
            this.attachPaths[2] = "";
            btnAttach2.setText("Attach");
            btnAttach2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete18.png")));
        }
    }//GEN-LAST:event_btnAttach2ActionPerformed

    private void btnAttach3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAttach3ActionPerformed
        if (btnAttach3.getText().equals("Attach")) {
            File file = getFile();

            this.attachPaths[3] = file.getAbsolutePath();
            btnAttach3.setText(file.getName());
            btnAttach3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete18.png")));
        } else {
            this.attachPaths[3] = "";
            btnAttach3.setText("Attach");
            btnAttach3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete18.png")));
        }
    }//GEN-LAST:event_btnAttach3ActionPerformed

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        try {
            sendMail();
        } catch (IOException ex) {
            Logger.getLogger(EmailFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSendActionPerformed

    private void txtKeywordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtKeywordFocusGained
        txtKeyword.selectAll();
    }//GEN-LAST:event_txtKeywordFocusGained

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String keyword = txtKeyword.getText();

        if (keyword != null && !keyword.isEmpty()) {
            querySearch(keyword);
        } else {
            if (rdoAgent.isSelected()) {
                agentTask();
            } else if (rdoCustomer.isSelected()) {
                customerTask();
            }
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtKeywordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKeywordKeyReleased
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            String keyword = txtKeyword.getText();
            querySearch(keyword);
        }
    }//GEN-LAST:event_txtKeywordKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAttach0;
    private javax.swing.JButton btnAttach1;
    private javax.swing.JButton btnAttach2;
    private javax.swing.JButton btnAttach3;
    private javax.swing.JButton btnSearch;
    private javax.swing.JToggleButton btnSelect;
    private javax.swing.JButton btnSend;
    private org.jdesktop.swingx.JXBusyLabel busyLabel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextPane jTextPane1;
    private org.jdesktop.swingx.JXTable jXTable1;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JRadioButton rdoAgent;
    private javax.swing.JRadioButton rdoCustomer;
    private org.jdesktop.swingx.JXTable tblClient;
    private javax.swing.JTextField txtKeyword;
    private javax.swing.JTextPane txtMessage;
    private javax.swing.JTextArea txtRecepient;
    private javax.swing.JTextField txtSubject;
    // End of variables declaration//GEN-END:variables
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            if (progress == 100) {
                try {
                    if (rdoAgent.isSelected()) {
                        Agents agents = (Agents) agentTask.get();
                        agentList = agents.getList();
                        busyLabel.setText(agentList.size() + " Agents");
                        displayAgent();
                    } else if (rdoCustomer.isSelected()) {
                        Customers customers = (Customers) customerTask.get();
                        customerList = customers.getList();
                        busyLabel.setText(customerList.size() + " Customers");
                        displayCustomer();
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(ClientSearchComp.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    busyLabel.setBusy(false);
                }
            }
        }
    }

    private void querySearch(String keyword) {
        if (keyword.length() < 2) {
            return;
        }

        if (rdoAgent.isSelected()) {
            busyLabel.setBusy(true);
            agentTask = new AgentWithEmailSearchTask(null, busyLabel, keyword, "KEYWORD");
            agentTask.addPropertyChangeListener(this);
            agentTask.execute();
        } else if (rdoCustomer.isSelected()) {
            busyLabel.setBusy(true);
            customerTask = new CustomerWithEmailSearchTask(null, busyLabel, keyword, "KEYWORD");
            customerTask.addPropertyChangeListener(this);
            customerTask.execute();
        }
    }

    public void agentTask() {
        busyLabel.setBusy(true);
        agentTask = new AgentWithEmailSearchTask(null, busyLabel, "EMAIL");
        agentTask.addPropertyChangeListener(this);
        agentTask.execute();
    }

    private void customerTask() {
        busyLabel.setBusy(true);
        customerTask = new CustomerWithEmailSearchTask(null, busyLabel, "EMAIL");
        customerTask.addPropertyChangeListener(this);
        customerTask.execute();
    }

    private final ActionListener radioAgentListener = (ActionEvent e) -> {
        txtKeyword.setText("");
        agentTask();
    };

    private final ActionListener radioCustomerListener = (ActionEvent e) -> {
        txtKeyword.setText("");
        customerTask();
    };
}

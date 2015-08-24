package com.ets.fe.acdoc.gui.comp;

import com.ets.fe.acdoc.task.DueAgentSearchTask;
import com.ets.fe.acdoc.task.DueCustomerSearchTask;
import com.ets.fe.client.collection.Agents;
import com.ets.fe.client.collection.Customers;
import com.ets.fe.client.task.AgentSearchTask;
import com.ets.fe.client.task.CustomerSearchTask;
import com.ets.fe.client.model.Agent;
import com.ets.fe.client.model.Customer;
import com.ets.fe.util.Enums;
import com.ets.fe.util.Enums.ClientSearchType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Yusuf
 */
public class ClientSearchComp extends javax.swing.JPanel implements PropertyChangeListener {

    private AgentSearchTask agentTask;
    private DueAgentSearchTask dueAgentSearchTask;
    private DueCustomerSearchTask dueCustomerSearchTask;

    private CustomerSearchTask customerTask;

    private List<Customer> customerList = new ArrayList<>();
    private List<Agent> agentList = new ArrayList<>();
    private Enums.ClientType client_type;
    private Enums.AgentType agentType;
    private Long client_id;
    private ClientSearchType search_type = Enums.ClientSearchType.ALL;//ALL/DUE/
    private Enums.AcDocType acDocType = null;

    public ClientSearchComp() {
        initComponents();
    }

    public ClientSearchComp(boolean _rdoAgent, boolean _rdoCustomer, boolean _rdoAll, Enums.AgentType agentType) {
        initComponents();
        this.agentType = agentType;
        rdoAgent.setVisible(_rdoAgent);
        rdoCustomer.setVisible(_rdoCustomer);
        rdoAll.setVisible(_rdoAll);
        rdoAll.doClick();
    }

    private void displayAgent() {
        List cmbElement = new ArrayList();

        for (Agent agent : agentList) {
            cmbElement.add(agent.calculateFullName() + "-" + agent.getPostCode()
                    + "-" + agent.getId());
        }

        Collections.sort(cmbElement);
        DefaultComboBoxModel cmbContactableModel = new DefaultComboBoxModel(cmbElement.toArray());
        cmbContactableModel.insertElementAt("All", 0);
        cmbContactable.setModel(cmbContactableModel);
        cmbContactable.setSelectedIndex(0);
        cmbContactable.setEnabled(true);
    }

    private void displayCustomer() {
        List cmbElement = new ArrayList();

        for (Customer customer : customerList) {
            cmbElement.add(customer.getSurName() + "/"
                    + customer.getForeName() + "-" + customer.getPostCode()
                    + "-" + customer.getId());
        }

        Collections.sort(cmbElement);
        DefaultComboBoxModel cmbContactableModel = new DefaultComboBoxModel(cmbElement.toArray());
        cmbContactableModel.insertElementAt("All", 0);
        cmbContactable.setModel(cmbContactableModel);
        cmbContactable.setSelectedIndex(0);
        cmbContactable.setEnabled(true);
    }

    public void agentTask() {
        busyLabel.setBusy(true);
        agentTask = new AgentSearchTask(busyLabel, agentType);
        agentTask.addPropertyChangeListener(this);
        agentTask.execute();
    }

    public void dueAgentTask() {
        busyLabel.setBusy(true);
        dueAgentSearchTask = new DueAgentSearchTask(busyLabel, acDocType, search_type);
        dueAgentSearchTask.addPropertyChangeListener(this);
        dueAgentSearchTask.execute();
    }

    private void customerTask() {
        busyLabel.setBusy(true);
        customerTask = new CustomerSearchTask(null, null, null);
        customerTask.addPropertyChangeListener(this);
        customerTask.execute();
    }

    private void dueCustomerTask() {
        busyLabel.setBusy(true);
        dueCustomerSearchTask = new DueCustomerSearchTask(busyLabel, acDocType, search_type);
        dueCustomerSearchTask.addPropertyChangeListener(this);
        dueCustomerSearchTask.execute();
    }

    private ActionListener radioAgentListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (Enums.AcDocType.INVOICE.equals(acDocType) || Enums.AcDocType.REFUND.equals(acDocType)) {
                dueAgentTask();
            } else {
                agentTask();
            }
            setClient_type(Enums.ClientType.AGENT);
            txtClientDetails.setText("");
        }
    };

    private ActionListener radioCustomerListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (Enums.AcDocType.INVOICE.equals(acDocType) || Enums.AcDocType.REFUND.equals(acDocType)) {
                dueCustomerTask();
            } else {
                customerTask();
            }
            setClient_type(Enums.ClientType.CUSTOMER);
            txtClientDetails.setText("");
        }
    };

    private ActionListener radioAllListener = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            txtClientDetails.setText("");
            busyLabel.setText("");
            cmbContactable.setSelectedIndex(-1);
            cmbContactable.setEnabled(false);
            setClient_type(null);
        }
    };

    private ActionListener cmbContactableListener = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            String[] data;
            long id = 0;
            if (cmbContactable.getSelectedIndex() > 0) {
                data = cmbContactable.getSelectedItem().toString().split("-");
                id = Long.parseLong(data[2]);
            } else if (cmbContactable.getSelectedIndex() == 0) {
                txtClientDetails.setText("");
            }

            if (Enums.ClientType.AGENT.equals(client_type)) {
                loop:
                for (Agent a : agentList) {
                    if (a.getId() == id) {
                        txtClientDetails.setText(a.calculateFullName());
                        txtClientDetails.append(a.getFullAddressCRSeperated());
                        break loop;
                    }
                }
            } else if (Enums.ClientType.CUSTOMER.equals(client_type)) {
                loop:
                for (Customer c : customerList) {
                    if (c.getId() == id) {
                        txtClientDetails.setText(c.calculateFullName());
                        txtClientDetails.append(c.getFullAddressCRSeperated());
                        break loop;
                    }
                }
            }
            client_id = getContactableId();
        }
    };

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
        rdoAgent = new javax.swing.JRadioButton();
        rdoCustomer = new javax.swing.JRadioButton();
        rdoAll = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        cmbContactable = new javax.swing.JComboBox();
        cmbContactable.addActionListener(cmbContactableListener);
        AutoCompleteDecorator.decorate(cmbContactable);
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtClientDetails = new javax.swing.JTextArea();
        busyLabel = new org.jdesktop.swingx.JXBusyLabel();

        setLayout(new java.awt.GridBagLayout());

        buttonGroup1.add(rdoAgent);
        rdoAgent.setText("Agent");
        rdoAgent.setToolTipText("Click to load Agents");
        rdoAgent.addActionListener(radioAgentListener);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 2, 2);
        add(rdoAgent, gridBagConstraints);

        buttonGroup1.add(rdoCustomer);
        rdoCustomer.setText("Customer");
        rdoCustomer.setToolTipText("Click to load Customers");
        rdoCustomer.addActionListener(radioCustomerListener);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 2, 2);
        add(rdoCustomer, gridBagConstraints);

        buttonGroup1.add(rdoAll);
        rdoAll.setText("All");
        rdoAll.addActionListener(radioAllListener);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 2, 2);
        add(rdoAll, gridBagConstraints);

        jLabel1.setText("Select");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(jLabel1, gridBagConstraints);

        cmbContactable.setEditable(true);
        cmbContactable.setToolTipText("Select Agent/Customer/All");
        cmbContactable.setMaximumSize(new java.awt.Dimension(28, 19));
        cmbContactable.setMinimumSize(new java.awt.Dimension(28, 19));
        cmbContactable.setPreferredSize(new java.awt.Dimension(28, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(cmbContactable, gridBagConstraints);

        jLabel4.setText("Client Details");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        add(jLabel4, gridBagConstraints);

        txtClientDetails.setEditable(false);
        txtClientDetails.setColumns(16);
        txtClientDetails.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtClientDetails.setLineWrap(true);
        txtClientDetails.setRows(5);
        jScrollPane1.setViewportView(txtClientDetails);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jScrollPane1, gridBagConstraints);

        busyLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(busyLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXBusyLabel busyLabel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cmbContactable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdoAgent;
    private javax.swing.JRadioButton rdoAll;
    private javax.swing.JRadioButton rdoCustomer;
    private javax.swing.JTextArea txtClientDetails;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress".equals(evt.getPropertyName())) {
            int progress = (Integer) evt.getNewValue();
            if (progress == 100) {
                try {
                    if (Enums.AgentType.TICKETING_AGT.equals(agentType)) {
                        if (Enums.ClientSearchType.TICKETING_PURCHASE_DUE_INVOICE.equals(search_type)
                                || Enums.ClientSearchType.TICKETING_PURCHASE_DUE_REFUND.equals(search_type)) {
                            agentList = dueAgentSearchTask.get();

                        } else {
                            Agents agents = (Agents) agentTask.get();
                            agentList = agents.getList();
                        }
                        busyLabel.setText(agentList.size() + " Agents");
                        displayAgent();
                    } else {
                        if (Enums.ClientType.AGENT.equals(client_type)) {
                            if (Enums.AcDocType.INVOICE.equals(acDocType) || Enums.AcDocType.REFUND.equals(acDocType)) {
                                agentList = dueAgentSearchTask.get();
                            } else {
                                Agents agents = (Agents) agentTask.get();
                                agentList = agents.getList();
                            }
                            busyLabel.setText(agentList.size() + " Agents");
                            displayAgent();
                        } else if (Enums.ClientType.CUSTOMER.equals(client_type)) {
                            if (Enums.AcDocType.INVOICE.equals(acDocType) || Enums.AcDocType.REFUND.equals(acDocType)) {
                                customerList = dueCustomerSearchTask.get();
                            } else {
                                Customers customers = (Customers) customerTask.get();
                                customerList = customers.getList();
                            }
                            busyLabel.setText(customerList.size() + " Customers");
                            displayCustomer();
                        }
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(ClientSearchComp.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    busyLabel.setBusy(false);
                }
            }
        }
    }

    private Long getContactableId() {
        Long id = null;
        if (cmbContactable.getSelectedIndex() > 0) {
            String[] data = cmbContactable.getSelectedItem().toString().split("-");
            id = Long.parseLong(data[2]);
        } else {
            return null;
        }
        return id;
    }

    public Enums.ClientType getContactableType() {
        return client_type;
    }

    public Long getClient_id() {
        return client_id;
    }

    public Enums.ClientType getClient_type() {
        return client_type;
    }

    /**
     * @param search_type the search_type to set
     */
    public void setSearch_type(ClientSearchType search_type) {
        this.search_type = search_type;
    }

    /**
     * @param acDocType the acDocType to set
     */
    public void setAcDocType(Enums.AcDocType acDocType) {
        this.acDocType = acDocType;
    }

    /**
     * @param client_type the client_type to set
     */
    public void setClient_type(Enums.ClientType client_type) {
        this.client_type = client_type;
    }

}

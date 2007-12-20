/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 * 
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

/*
 * MessageNameConfigurationPanel.java
 *
 * Created on September 8, 2006, 12:53 PM
 */

package org.netbeans.modules.xml.wsdl.ui.view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author  radval
 */
public class MessageNameConfigurationPanel extends javax.swing.JPanel {
    
    private JPanel mPartsTablePanel;
    
    private List mExistingMessagesList = new ArrayList();
    
    private boolean mIsNewMessage = true;
    
    private JTextField messageEditor = new JTextField();
    
    /** Creates new form MessageNameConfigurationPanel */
    public MessageNameConfigurationPanel() {
        initComponents();
    }
    
    public MessageNameConfigurationPanel(JPanel partsTablePanel) {
        this.mPartsTablePanel = partsTablePanel;
        initComponents();
        initGUI();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        messageNameComboBox = new javax.swing.JComboBox();

        setName("Form"); // NOI18N

        messageNameComboBox.setEditable(true);
        messageNameComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        messageNameComboBox.setName("messageNameComboBox"); // NOI18N
        messageNameComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                messageNameComboBoxItemStateChanged(evt);
            }
        });
        messageNameComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messageNameComboBoxActionPerformed(evt);
            }
        });
        messageNameComboBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                messageNameComboBoxKeyReleased(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(messageNameComboBox, 0, 209, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(messageNameComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void messageNameComboBoxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_messageNameComboBoxKeyReleased
       
    }//GEN-LAST:event_messageNameComboBoxKeyReleased

    public boolean isNewMessage() {
        return this.mIsNewMessage;
    }
    
    public String getMessageName() {
        return messageEditor.getText();
    }
    
    public void setMessages(String[] existingMessages, String newMessageName, javax.swing.event.DocumentListener msgNameDocumentListener) {
            int existingMessageCount = 0;
            if(existingMessages != null) {
                existingMessageCount = existingMessages.length;
            }
            
            String[] messages = new String[existingMessageCount +1];
            messages[0] = newMessageName;
            
            for(int i = 0; i < existingMessageCount; i++) {
                messages[i+1] = existingMessages[i];
                mExistingMessagesList.add(existingMessages[i]);
            }
            
            DefaultComboBoxModel messageModel = new DefaultComboBoxModel(messages);
            this.messageNameComboBox.setModel(messageModel);
            
            if(msgNameDocumentListener != null) {
            	messageEditor.getDocument().addDocumentListener(msgNameDocumentListener);
            }
    }
    
    private void initGUI() {
    	messageEditor = (JTextField)this.messageNameComboBox.getEditor().getEditorComponent();
    	
    	
    }
    
    private void messageNameComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_messageNameComboBoxActionPerformed
         
    }//GEN-LAST:event_messageNameComboBoxActionPerformed

    private void messageNameComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_messageNameComboBoxItemStateChanged
        String messageName = (String) evt.getItem();
        if(mExistingMessagesList.contains(messageName)) {
            this.mPartsTablePanel.setEnabled(false);
            mIsNewMessage = false;
        } else {
            this.mPartsTablePanel.setEnabled(true);
            mIsNewMessage = true;
        }
        
        this.mPartsTablePanel.revalidate();
    }//GEN-LAST:event_messageNameComboBoxItemStateChanged
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox messageNameComboBox;
    // End of variables declaration//GEN-END:variables
    
}

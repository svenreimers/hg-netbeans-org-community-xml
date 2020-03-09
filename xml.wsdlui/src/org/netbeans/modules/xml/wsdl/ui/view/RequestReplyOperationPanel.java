/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */

/*
 * OperationConfigurationPanel.java
 *
 * Created on August 25, 2006, 1:15 PM
 */

package org.netbeans.modules.xml.wsdl.ui.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.text.Document;

import org.netbeans.api.project.Project;
import org.netbeans.modules.xml.wsdl.model.WSDLModel;


/**
 *
 */
public class RequestReplyOperationPanel extends javax.swing.JPanel implements OperationConfiguration {
    
    private Project mProject = null;
    private Document mCommonOperationTextFieldDocument;
    private Map<String, String> namespaceToPrefixMap = new HashMap<String, String>();
    private boolean mIsShowMessageComboBoxes = false;
    private WSDLModel mModel;
    private boolean showPortType;
    
    /** Creates new form OperationConfigurationPanel 
     * @param project */
    public RequestReplyOperationPanel(Project project, 
                                      Document operationNameTextFieldDocument, 
                                      Map<String, String> namespaceToPrefixMap,
                                      boolean isShowMessageComboBoxes,
                                      WSDLModel model,
                                      boolean showPortType) {
        this.mProject = project;
        this.mCommonOperationTextFieldDocument = operationNameTextFieldDocument;
        this.namespaceToPrefixMap = namespaceToPrefixMap;
        this.mIsShowMessageComboBoxes = isShowMessageComboBoxes;
        mModel = model;
        this.showPortType = showPortType;
        initComponents();
        initGUI();
    }
    
    /** Mattise require default constructor otherwise will not load in design view of mattise
     **/
    public RequestReplyOperationPanel() {
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

        portTypeNameLabel = new javax.swing.JLabel();
        portTypeNameTextField = new javax.swing.JTextField();
        OperationNameLabel = new javax.swing.JLabel();
        operationNameTextField = new javax.swing.JTextField();
        if(mCommonOperationTextFieldDocument != null) {
            operationNameTextField.setDocument(mCommonOperationTextFieldDocument);
        }
        operationTypeLabel = new javax.swing.JLabel();
        operationTypeComboBox = new javax.swing.JComboBox();
        inputLabel = new javax.swing.JLabel();
        inputPanel = new javax.swing.JPanel();
        inputMessagePartsConfigurationTable = new org.netbeans.modules.xml.wsdl.ui.view.CommonMessageConfigurationPanel(mProject, namespaceToPrefixMap, mModel);
        inputMessageNameConfigurationPanel1 = new MessageNameConfigurationPanel(this.inputMessagePartsConfigurationTable);
        inputMessagePartsConfigurationTable = inputMessagePartsConfigurationTable;
        outputLabel = new javax.swing.JLabel();
        outputPanel = new javax.swing.JPanel();
        outputMessagePartsConfigurationTable = new org.netbeans.modules.xml.wsdl.ui.view.CommonMessageConfigurationPanel(mProject, namespaceToPrefixMap, mModel);
        outputMessageNameConfigurationPanel1 = new MessageNameConfigurationPanel(this.outputMessagePartsConfigurationTable);
        outputMessagePartsConfigurationTable = outputMessagePartsConfigurationTable;
        faultLabel = new javax.swing.JLabel();
        faultPanel = new javax.swing.JPanel();
        faultMessagePartsConfigurationTable = new org.netbeans.modules.xml.wsdl.ui.view.CommonMessageConfigurationPanel(mProject, namespaceToPrefixMap, mModel);
        faultMessageNameConfigurationPanel1 = new MessageNameConfigurationPanel(this.faultMessagePartsConfigurationTable);
        faultMessagePartsConfigurationTable = faultMessagePartsConfigurationTable;
        autoGenPartnerLinkTypeCheckBox = new javax.swing.JCheckBox();

        setName("Form"); // NOI18N

        portTypeNameLabel.setLabelFor(portTypeNameTextField);
        org.openide.awt.Mnemonics.setLocalizedText(portTypeNameLabel, org.openide.util.NbBundle.getMessage(RequestReplyOperationPanel.class, "RequestReplyOperationPanel.portTypeNameLabel.text")); // NOI18N
        portTypeNameLabel.setToolTipText(org.openide.util.NbBundle.getMessage(RequestReplyOperationPanel.class, "RequestReplyOperationPanel.portTypeNameLabel.toolTipText")); // NOI18N
        portTypeNameLabel.setName("portTypeNameLabel"); // NOI18N

        portTypeNameTextField.setName("portTypeNameTextField"); // NOI18N

        OperationNameLabel.setLabelFor(operationNameTextField);
        org.openide.awt.Mnemonics.setLocalizedText(OperationNameLabel, org.openide.util.NbBundle.getMessage(RequestReplyOperationPanel.class, "RequestReplyOperationPanel.OperationNameLabel.text")); // NOI18N
        OperationNameLabel.setToolTipText(org.openide.util.NbBundle.getMessage(RequestReplyOperationPanel.class, "RequestReplyOperationPanel.OperationNameLabel.toolTipText")); // NOI18N
        OperationNameLabel.setName("OperationNameLabel"); // NOI18N

        operationNameTextField.setName("operationNameTextField"); // NOI18N

        operationTypeLabel.setLabelFor(operationTypeComboBox);
        org.openide.awt.Mnemonics.setLocalizedText(operationTypeLabel, org.openide.util.NbBundle.getMessage(RequestReplyOperationPanel.class, "RequestReplyOperationPanel.operationTypeLabel.text")); // NOI18N
        operationTypeLabel.setToolTipText(org.openide.util.NbBundle.getMessage(RequestReplyOperationPanel.class, "RequestReplyOperationPanel.operationTypeLabel.toolTipText")); // NOI18N
        operationTypeLabel.setName("operationTypeLabel"); // NOI18N

        operationTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Request-Response Operation", "One-Way Operation" }));
        operationTypeComboBox.setToolTipText(org.openide.util.NbBundle.getMessage(RequestReplyOperationPanel.class, "RequestReplyOperationPanel.operationTypeComboBox.toolTipText")); // NOI18N
        operationTypeComboBox.setName("operationTypeComboBox"); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(inputLabel, org.openide.util.NbBundle.getMessage(RequestReplyOperationPanel.class, "RequestReplyOperationPanel.inputLabel.text")); // NOI18N
        inputLabel.setToolTipText(org.openide.util.NbBundle.getMessage(RequestReplyOperationPanel.class, "RequestReplyOperationPanel.inputLabel.toolTipText")); // NOI18N
        inputLabel.setName("inputLabel"); // NOI18N

        inputPanel.setName("inputPanel"); // NOI18N
        inputPanel.setLayout(new javax.swing.BoxLayout(inputPanel, javax.swing.BoxLayout.Y_AXIS));

        inputMessageNameConfigurationPanel1.setToolTipText(org.openide.util.NbBundle.getMessage(RequestReplyOperationPanel.class, "RequestReplyOperationPanel.inputMessageNameConfigurationPanel1.toolTipText")); // NOI18N
        inputMessageNameConfigurationPanel1.setName("inputMessageNameConfigurationPanel1"); // NOI18N
        inputPanel.add(inputMessageNameConfigurationPanel1);

        inputMessagePartsConfigurationTable.setName("inputMessagePartsConfigurationTable"); // NOI18N
        inputPanel.add(inputMessagePartsConfigurationTable);

        org.openide.awt.Mnemonics.setLocalizedText(outputLabel, org.openide.util.NbBundle.getMessage(RequestReplyOperationPanel.class, "RequestReplyOperationPanel.outputLabel.text")); // NOI18N
        outputLabel.setToolTipText(org.openide.util.NbBundle.getMessage(RequestReplyOperationPanel.class, "RequestReplyOperationPanel.outputLabel.toolTipText")); // NOI18N
        outputLabel.setName("outputLabel"); // NOI18N

        outputPanel.setName("outputPanel"); // NOI18N
        outputPanel.setLayout(new javax.swing.BoxLayout(outputPanel, javax.swing.BoxLayout.Y_AXIS));

        outputMessageNameConfigurationPanel1.setToolTipText(org.openide.util.NbBundle.getMessage(RequestReplyOperationPanel.class, "RequestReplyOperationPanel.outputMessageNameConfigurationPanel1.toolTipText")); // NOI18N
        outputMessageNameConfigurationPanel1.setName("outputMessageNameConfigurationPanel1"); // NOI18N
        outputPanel.add(outputMessageNameConfigurationPanel1);

        outputMessagePartsConfigurationTable.setName("outputMessagePartsConfigurationTable"); // NOI18N
        outputPanel.add(outputMessagePartsConfigurationTable);

        org.openide.awt.Mnemonics.setLocalizedText(faultLabel, org.openide.util.NbBundle.getMessage(RequestReplyOperationPanel.class, "RequestReplyOperationPanel.faultLabel.text")); // NOI18N
        faultLabel.setToolTipText(org.openide.util.NbBundle.getMessage(RequestReplyOperationPanel.class, "RequestReplyOperationPanel.faultLabel.toolTipText")); // NOI18N
        faultLabel.setName("faultLabel"); // NOI18N

        faultPanel.setName("faultPanel"); // NOI18N
        faultPanel.setLayout(new javax.swing.BoxLayout(faultPanel, javax.swing.BoxLayout.Y_AXIS));

        faultMessageNameConfigurationPanel1.setToolTipText(org.openide.util.NbBundle.getMessage(RequestReplyOperationPanel.class, "RequestReplyOperationPanel.outputMessageNameConfigurationPanel1.toolTipText")); // NOI18N
        faultMessageNameConfigurationPanel1.setName("faultMessageNameConfigurationPanel1"); // NOI18N
        faultPanel.add(faultMessageNameConfigurationPanel1);

        faultMessagePartsConfigurationTable.setName("faultMessagePartsConfigurationTable"); // NOI18N
        faultPanel.add(faultMessagePartsConfigurationTable);

        autoGenPartnerLinkTypeCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(autoGenPartnerLinkTypeCheckBox, org.openide.util.NbBundle.getMessage(RequestReplyOperationPanel.class, "LBL_autoGeneratePartnerLinktypeCheckBox.text")); // NOI18N
        autoGenPartnerLinkTypeCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(RequestReplyOperationPanel.class, "TT_autoGeneratePartnerLinktypeCheckBox.toolTipText")); // NOI18N
        autoGenPartnerLinkTypeCheckBox.setName("autoGenPartnerLinkTypeCheckBox"); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, autoGenPartnerLinkTypeCheckBox, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, OperationNameLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, inputLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, operationTypeLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, faultLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, outputLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(portTypeNameLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(faultPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                            .add(outputPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                            .add(inputPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                            .add(operationTypeComboBox, 0, 404, Short.MAX_VALUE)
                            .add(operationNameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                            .add(portTypeNameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(portTypeNameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(portTypeNameLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(OperationNameLabel)
                    .add(operationNameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(operationTypeLabel)
                    .add(operationTypeComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(inputPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                    .add(inputLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(outputPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                    .add(outputLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(faultLabel)
                    .add(faultPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(autoGenPartnerLinkTypeCheckBox)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    public String getOperationName() {
        return this.operationNameTextField.getText();
    }
    
    public void setOperationName(String operationName) {
        this.operationNameTextField.setText(operationName);
    }
    
    public OperationType getOperationType() {
        return (OperationType) this.operationTypeComboBox.getSelectedItem();
    }
    
    public JComboBox getOperationTypeComboBox() {
        return this.operationTypeComboBox;
    }
    
    
    public List<PartAndElementOrTypeTableModel.PartAndElementOrType> getInputMessageParts() {
        return inputMessagePartsConfigurationTable.getPartAndElementOrType();
    }
        
    public List<PartAndElementOrTypeTableModel.PartAndElementOrType> getOutputMessageParts() {
        return outputMessagePartsConfigurationTable.getPartAndElementOrType();
    }

    public List<PartAndElementOrTypeTableModel.PartAndElementOrType> getFaultMessageParts() {
        return faultMessagePartsConfigurationTable.getPartAndElementOrType();
    }
    
    public void setInputMessages(String[] existingMessages, String newMessageName, javax.swing.event.DocumentListener msgNameDocumentListener) {
        inputMessageNameConfigurationPanel1.setMessages(existingMessages, newMessageName, msgNameDocumentListener);
    }
    
    public void setOutputMessages(String[] existingMessages, String newMessageName, javax.swing.event.DocumentListener msgNameDocumentListener) {
        outputMessageNameConfigurationPanel1.setMessages(existingMessages, newMessageName, msgNameDocumentListener);
    }
    
    public void setFaultMessages(String[] existingMessages, String newMessageName, javax.swing.event.DocumentListener msgNameDocumentListener) {
        faultMessageNameConfigurationPanel1.setMessages(existingMessages, newMessageName, msgNameDocumentListener);
    }
    
    public boolean isNewInputMessage() {
       return inputMessageNameConfigurationPanel1.isNewMessage();
    }
    
    public boolean isNewOutputMessage() {
       return outputMessageNameConfigurationPanel1.isNewMessage();
    }
    
    
    public boolean isNewFaultMessage() {
       return faultMessageNameConfigurationPanel1.isNewMessage();
    }
    
     public String getOutputMessageName() {
        return this.outputMessageNameConfigurationPanel1.getMessageName();
    }
    
    
    public String getInputMessageName() {
        return this.inputMessageNameConfigurationPanel1.getMessageName();
    }


    public String getFaultMessageName() {
        return this.faultMessageNameConfigurationPanel1.getMessageName();
    }
    
    private void initGUI() {
        inputMessagePartsConfigurationTable.addNewRow();
        inputMessagePartsConfigurationTable.clearSelection();
        outputMessagePartsConfigurationTable.addNewRow();
        outputMessagePartsConfigurationTable.clearSelection();
        
        faultMessagePartsConfigurationTable.addPropertyChangeListener(CommonMessageConfigurationPanel.PARTS_LISTENER, new PropertyChangeListener() {
        
            public void propertyChange(PropertyChangeEvent evt) {
                firePropertyChange(OperationConfigurationPanel.FAULT_PARTS_LISTENER, evt.getOldValue(), evt.getNewValue());
            }
        
        });
        
        inputMessageNameConfigurationPanel1.setVisible(this.mIsShowMessageComboBoxes);
        outputMessageNameConfigurationPanel1.setVisible(this.mIsShowMessageComboBoxes);
        faultMessageNameConfigurationPanel1.setVisible(this.mIsShowMessageComboBoxes);
        inputMessageNameConfigurationPanel1.setEnabled(this.mIsShowMessageComboBoxes);
        outputMessageNameConfigurationPanel1.setEnabled(this.mIsShowMessageComboBoxes);
        faultMessageNameConfigurationPanel1.setEnabled(this.mIsShowMessageComboBoxes);
        
        if (mIsShowMessageComboBoxes) {
            inputLabel.setLabelFor(inputMessageNameConfigurationPanel1);
            outputLabel.setLabelFor(outputMessageNameConfigurationPanel1);
            faultLabel.setLabelFor(faultMessageNameConfigurationPanel1);
        } else {
            inputLabel.setLabelFor(inputMessagePartsConfigurationTable);
            outputLabel.setLabelFor(outputMessagePartsConfigurationTable);
            faultLabel.setLabelFor(faultMessagePartsConfigurationTable);
        }
        
        portTypeNameLabel.setEnabled(showPortType);
        portTypeNameTextField.setEnabled(showPortType);
        portTypeNameLabel.setVisible(showPortType);
        portTypeNameTextField.setVisible(showPortType);
        autoGenPartnerLinkTypeCheckBox.setVisible(showPortType);
        autoGenPartnerLinkTypeCheckBox.setEnabled(showPortType);
        
    }
    
    public JTextField getOperationNameTextField() {
        return this.operationNameTextField;
    }
    
    public String getPortTypeName() {
        return portTypeNameTextField.getText();
    }

    public JTextField getPortTypeNameTextField() {
        return portTypeNameTextField;
    }

    public void setPortTypeName(String portTypeName) {
        portTypeNameTextField.setText(portTypeName);
    }
        
    public static void main(String[] args) {
        
/*        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new BorderLayout());
        OperationConfigurationPanel p = new OperationConfigurationPanel();
        frame.getContentPane().add(p, BorderLayout.CENTER);
        frame.setSize(200, 200);
        frame.setVisible(true);*/
        
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel OperationNameLabel;
    private javax.swing.JCheckBox autoGenPartnerLinkTypeCheckBox;
    private javax.swing.JLabel faultLabel;
    private org.netbeans.modules.xml.wsdl.ui.view.MessageNameConfigurationPanel faultMessageNameConfigurationPanel1;
    private org.netbeans.modules.xml.wsdl.ui.view.CommonMessageConfigurationPanel faultMessagePartsConfigurationTable;
    private javax.swing.JPanel faultPanel;
    private javax.swing.JLabel inputLabel;
    private org.netbeans.modules.xml.wsdl.ui.view.MessageNameConfigurationPanel inputMessageNameConfigurationPanel1;
    private org.netbeans.modules.xml.wsdl.ui.view.CommonMessageConfigurationPanel inputMessagePartsConfigurationTable;
    private javax.swing.JPanel inputPanel;
    private javax.swing.JTextField operationNameTextField;
    private javax.swing.JComboBox operationTypeComboBox;
    private javax.swing.JLabel operationTypeLabel;
    private javax.swing.JLabel outputLabel;
    private org.netbeans.modules.xml.wsdl.ui.view.MessageNameConfigurationPanel outputMessageNameConfigurationPanel1;
    private org.netbeans.modules.xml.wsdl.ui.view.CommonMessageConfigurationPanel outputMessagePartsConfigurationTable;
    private javax.swing.JPanel outputPanel;
    private javax.swing.JLabel portTypeNameLabel;
    private javax.swing.JTextField portTypeNameTextField;
    // End of variables declaration//GEN-END:variables

    public boolean isAutoGeneratePartnerLinkType() {
        return autoGenPartnerLinkTypeCheckBox.isEnabled() && autoGenPartnerLinkTypeCheckBox.isSelected();
    }

    public void setAutoGeneratePartnerLinkType(boolean autoGenPartnerLinkType) {
        autoGenPartnerLinkTypeCheckBox.setSelected(autoGenPartnerLinkType);
    }


    
}

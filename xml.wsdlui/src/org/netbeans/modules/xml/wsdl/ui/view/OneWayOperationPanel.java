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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.Document;

import org.netbeans.api.project.Project;
import org.netbeans.modules.xml.wsdl.model.WSDLModel;

/**
 *
 */
public class OneWayOperationPanel extends javax.swing.JPanel implements OperationConfiguration {

    private Project mProject = null;
    private Document mCommonOperationTextFieldDocument;
    private Map<String, String> namespaceToPrefixMap = new HashMap<String, String>();
    private boolean mIsShowMessageComboBoxes = false;
    private WSDLModel mModel;
    private boolean showPortType;

    /** Creates new form OperationConfigurationPanel 
     * @param project */
    public OneWayOperationPanel(Project project,
            Document operationNameTextFieldDocument,
            Map<String, String> namespaceToPrefixMap,
            boolean isShowMessageComboBoxes, WSDLModel model, boolean showPortType) {
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
    public OneWayOperationPanel() {
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
        autoGeneratePartnerLinktypeCheckBox = new javax.swing.JCheckBox();

        setName("Form"); // NOI18N

        portTypeNameLabel.setLabelFor(portTypeNameTextField);
        org.openide.awt.Mnemonics.setLocalizedText(portTypeNameLabel, org.openide.util.NbBundle.getMessage(OneWayOperationPanel.class, "OneWayOperationPanel.portTypeNameLabel.text")); // NOI18N
        portTypeNameLabel.setToolTipText(org.openide.util.NbBundle.getMessage(OneWayOperationPanel.class, "OneWayOperationPanel.portTypeNameLabel.toolTipText")); // NOI18N
        portTypeNameLabel.setName("portTypeNameLabel"); // NOI18N

        portTypeNameTextField.setName("portTypeNameTextField"); // NOI18N

        OperationNameLabel.setLabelFor(operationNameTextField);
        org.openide.awt.Mnemonics.setLocalizedText(OperationNameLabel, org.openide.util.NbBundle.getMessage(OneWayOperationPanel.class, "OneWayOperationPanel.OperationNameLabel.text")); // NOI18N
        OperationNameLabel.setToolTipText(org.openide.util.NbBundle.getMessage(OneWayOperationPanel.class, "OneWayOperationPanel.OperationNameLabel.toolTipText")); // NOI18N
        OperationNameLabel.setName("OperationNameLabel"); // NOI18N

        operationNameTextField.setName("operationNameTextField"); // NOI18N

        operationTypeLabel.setLabelFor(operationTypeComboBox);
        org.openide.awt.Mnemonics.setLocalizedText(operationTypeLabel, org.openide.util.NbBundle.getMessage(OneWayOperationPanel.class, "OneWayOperationPanel.operationTypeLabel.text")); // NOI18N
        operationTypeLabel.setToolTipText(org.openide.util.NbBundle.getMessage(OneWayOperationPanel.class, "OneWayOperationPanel.operationTypeLabel.toolTipText")); // NOI18N
        operationTypeLabel.setName("operationTypeLabel"); // NOI18N

        operationTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Request-Response Operation", "One-Way Operation" }));
        operationTypeComboBox.setToolTipText(org.openide.util.NbBundle.getMessage(OneWayOperationPanel.class, "OneWayOperationPanel.operationTypeComboBox.toolTipText")); // NOI18N
        operationTypeComboBox.setName("operationTypeComboBox"); // NOI18N

        inputLabel.setLabelFor(inputMessageNameConfigurationPanel1);
        org.openide.awt.Mnemonics.setLocalizedText(inputLabel, org.openide.util.NbBundle.getMessage(OneWayOperationPanel.class, "OneWayOperationPanel.inputLabel.text")); // NOI18N
        inputLabel.setToolTipText(org.openide.util.NbBundle.getMessage(OneWayOperationPanel.class, "OneWayOperationPanel.inputLabel.toolTipText")); // NOI18N
        inputLabel.setName("inputLabel"); // NOI18N

        inputPanel.setName("inputPanel"); // NOI18N
        inputPanel.setLayout(new javax.swing.BoxLayout(inputPanel, javax.swing.BoxLayout.Y_AXIS));

        inputMessageNameConfigurationPanel1.setToolTipText(org.openide.util.NbBundle.getMessage(OneWayOperationPanel.class, "OneWayOperationPanel.inputMessageNameConfigurationPanel1.toolTipText")); // NOI18N
        inputMessageNameConfigurationPanel1.setName("inputMessageNameConfigurationPanel1"); // NOI18N
        inputPanel.add(inputMessageNameConfigurationPanel1);

        inputMessagePartsConfigurationTable.setName("inputMessagePartsConfigurationTable"); // NOI18N
        inputPanel.add(inputMessagePartsConfigurationTable);

        autoGeneratePartnerLinktypeCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(autoGeneratePartnerLinktypeCheckBox, org.openide.util.NbBundle.getMessage(OneWayOperationPanel.class, "LBL_autoGeneratePartnerLinktypeCheckBox.text")); // NOI18N
        autoGeneratePartnerLinktypeCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(OneWayOperationPanel.class, "TT_autoGeneratePartnerLinktypeCheckBox.toolTipText")); // NOI18N
        autoGeneratePartnerLinktypeCheckBox.setName("autoGeneratePartnerLinktypeCheckBox"); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, autoGeneratePartnerLinktypeCheckBox, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(OperationNameLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(inputLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(operationTypeLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(portTypeNameLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(inputPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                            .add(operationNameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                            .add(operationTypeComboBox, 0, 404, Short.MAX_VALUE)
                            .add(portTypeNameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(portTypeNameLabel)
                    .add(portTypeNameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
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
                    .add(inputLabel)
                    .add(inputPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(autoGeneratePartnerLinktypeCheckBox)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    public void setInputMessages(String[] existingMessages, String newMessageName, javax.swing.event.DocumentListener msgNameDocumentListener) {
        inputMessageNameConfigurationPanel1.setMessages(existingMessages, newMessageName, msgNameDocumentListener);
    }

    public boolean isNewInputMessage() {
        return inputMessageNameConfigurationPanel1.isNewMessage();
    }

    public boolean isNewOutputMessage() {
        return false;
    }

    public boolean isNewFaultMessage() {
        return false;
    }

    public String getOutputMessageName() {
        return null;
    }

    public String getInputMessageName() {
        return this.inputMessageNameConfigurationPanel1.getMessageName();
    }

    public String getFaultMessageName() {
        return null;
    }

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
        return null;
    }

    public List<PartAndElementOrTypeTableModel.PartAndElementOrType> getFaultMessageParts() {
        return null;
    }

    private void initGUI() {
        inputMessagePartsConfigurationTable.addNewRow();
        inputMessagePartsConfigurationTable.clearSelection();
        inputMessageNameConfigurationPanel1.setVisible(this.mIsShowMessageComboBoxes);
        if (mIsShowMessageComboBoxes) {
            inputLabel.setLabelFor(inputMessageNameConfigurationPanel1);
        } else {
            inputLabel.setLabelFor(inputMessagePartsConfigurationTable);
        }

        portTypeNameLabel.setEnabled(showPortType);
        portTypeNameTextField.setEnabled(showPortType);
        portTypeNameLabel.setVisible(showPortType);
        portTypeNameTextField.setVisible(showPortType);
        autoGeneratePartnerLinktypeCheckBox.setEnabled(showPortType);
        autoGeneratePartnerLinktypeCheckBox.setVisible(showPortType);

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
    private OperationType selectedOperationType;
    private JPanel operationCardPanel;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel OperationNameLabel;
    private javax.swing.JCheckBox autoGeneratePartnerLinktypeCheckBox;
    private javax.swing.JLabel inputLabel;
    private org.netbeans.modules.xml.wsdl.ui.view.MessageNameConfigurationPanel inputMessageNameConfigurationPanel1;
    private org.netbeans.modules.xml.wsdl.ui.view.CommonMessageConfigurationPanel inputMessagePartsConfigurationTable;
    private javax.swing.JPanel inputPanel;
    private javax.swing.JTextField operationNameTextField;
    private javax.swing.JComboBox operationTypeComboBox;
    private javax.swing.JLabel operationTypeLabel;
    private javax.swing.JLabel portTypeNameLabel;
    private javax.swing.JTextField portTypeNameTextField;
    // End of variables declaration//GEN-END:variables
    
    public boolean isAutoGeneratePartnerLinkType() {
        return autoGeneratePartnerLinktypeCheckBox.isEnabled() && autoGeneratePartnerLinktypeCheckBox.isSelected();
    }

    public void setAutoGeneratePartnerLinkType(boolean autoGenPartnerLinkType) {
        autoGeneratePartnerLinktypeCheckBox.setSelected(autoGenPartnerLinkType);
    }
}

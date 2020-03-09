/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2009-2017 Oracle and/or its affiliates. All rights reserved.
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
 * Software is Sun Microsystems, Inc. Portions Copyright 2009-2010 Sun
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
 * ConfigurationEditorPanel.java
 *
 * Created on May 27, 2008, 12:21 PM
 */

package org.netbeans.modules.xml.wsdl.bindingsupport.configeditor.ui;

import org.netbeans.modules.xml.wsdl.bindingsupport.common.CommonMessagePanel;
import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import org.netbeans.modules.xml.wsdl.bindingsupport.spi.ExtensibilityElementConfigurationEditorComponent;
import org.netbeans.modules.xml.wsdl.bindingsupport.spi.ExtensibilityElementConfigurationEditorProvider;
import org.netbeans.modules.xml.wsdl.model.Operation;
import org.openide.DialogDescriptor;

/**
 *
 */
public class ConfigurationEditorPanel extends javax.swing.JPanel implements PropertyChangeListener {

    private CommonMessagePanel messagePanel;
    private DialogDescriptor descriptor;
    private boolean showOperations;
    //private ExtensibilityElementConfigurationEditorComponent editorComponent;
    private ExtensibilityElementConfigurationEditorProvider provider;
    private JComponent innerPane;
    private Collection<Operation> operations;
    private Set<Operation> configuredOperations;
    private Operation lastSelectedOperation;
    boolean multipleOperationConfigurationSupported;
    
    private String title;

    /** Creates new form ConfigurationEditorPanel */
    public ConfigurationEditorPanel(Collection<Operation> operations, boolean multipleOperationSupported) {
        this.operations = operations;
        this.multipleOperationConfigurationSupported = multipleOperationSupported;
        configuredOperations = new HashSet<Operation>();
        if (operations != null && operations.size() > 1) {
            showOperations = true;
        }
        initComponents();
        multipleOperationCommentLabel.setVisible(multipleOperationSupported);
        messagePanel = new CommonMessagePanel();
        commonMessagePanelHolder.add(messagePanel);
        operationPanel.setEnabled(showOperations);
        operationPanel.setVisible(showOperations);
        if (showOperations) {
            Vector operationList = new Vector();
            operationList.add("");
            for (Operation operation : operations) {
                operationList.add(new OperationItem(operation.getName(), operation));
            }
            operationCombobox.setModel(new DefaultComboBoxModel(operationList));
        }
    }
    
    public void setInnerPanel(JComponent innerComponent) {
        if (this.innerPane != null) {
            this.innerPane.removePropertyChangeListener(this);
            bindingConfigurationPanelHolder.remove(this.innerPane);
        }
        this.innerPane = innerComponent;
        if (innerComponent != null) {
            innerComponent.addPropertyChangeListener(this);
            bindingConfigurationPanelHolder.add(innerComponent);
        }
        messagePanel.setMessage("");
        revalidate();
        Window windowAncestor = SwingUtilities.getWindowAncestor(this);
        if (windowAncestor != null) {
            windowAncestor.pack();
        }
        
    }

    Operation getSelectedOperation() {
        return lastSelectedOperation;
    }
    
    Set<Operation> getAllConfiguredOperations() {
        return configuredOperations;
    }

    void setDialogDescriptor(DialogDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    boolean setProvider(ExtensibilityElementConfigurationEditorProvider provider) {
        this.provider = provider;
        if (operations != null && operations.size() > 0) {
            lastSelectedOperation = operations.iterator().next();
            if (!configuredOperations.contains(lastSelectedOperation)) {
                configuredOperations.add(lastSelectedOperation);
            }
            ExtensibilityElementConfigurationEditorComponent component = provider.getOperationBasedEditorComponent(lastSelectedOperation);
            if (component == null) {
                return false;
            }
            if (operations.size() > 1) {
                return true;
            }
            setInnerPanel(component.getEditorPanel());
            title = component.getTitle();
        }
        return true;
    }
    
    public String getTitle() {
        return title;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        operationPanel = new javax.swing.JPanel();
        operationLabel = new javax.swing.JLabel();
        operationCombobox = new javax.swing.JComboBox();
        commentLabel = new javax.swing.JLabel();
        multipleOperationCommentLabel = new javax.swing.JLabel();
        bindingConfigurationPanelHolder = new javax.swing.JPanel();
        commonMessagePanelHolder = new javax.swing.JPanel();

        setName("Form"); // NOI18N

        operationPanel.setName("operationPanel"); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(operationLabel, org.openide.util.NbBundle.getMessage(ConfigurationEditorPanel.class, "ConfigurationEditorPanel.operationLabel.text")); // NOI18N
        operationLabel.setName("operationLabel"); // NOI18N

        operationCombobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        operationCombobox.setName("operationCombobox"); // NOI18N
        operationCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                operationComboboxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(commentLabel, org.openide.util.NbBundle.getMessage(ConfigurationEditorPanel.class, "ConfigurationEditorPanel.commentLabel.text")); // NOI18N
        commentLabel.setName("commentLabel"); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(multipleOperationCommentLabel, org.openide.util.NbBundle.getMessage(ConfigurationEditorPanel.class, "ConfigurationEditorPanel.multipleOperationCommentLabel.text")); // NOI18N
        multipleOperationCommentLabel.setName("multipleOperationCommentLabel"); // NOI18N

        org.jdesktop.layout.GroupLayout operationPanelLayout = new org.jdesktop.layout.GroupLayout(operationPanel);
        operationPanel.setLayout(operationPanelLayout);
        operationPanelLayout.setHorizontalGroup(
            operationPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(operationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(operationPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(commentLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                    .add(multipleOperationCommentLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                    .add(operationPanelLayout.createSequentialGroup()
                        .add(operationLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(operationCombobox, 0, 431, Short.MAX_VALUE)))
                .addContainerGap())
        );
        operationPanelLayout.setVerticalGroup(
            operationPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(operationPanelLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(commentLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(multipleOperationCommentLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(operationPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(operationLabel)
                    .add(operationCombobox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        );

        bindingConfigurationPanelHolder.setName("bindingConfigurationPanelHolder"); // NOI18N
        bindingConfigurationPanelHolder.setLayout(new javax.swing.BoxLayout(bindingConfigurationPanelHolder, javax.swing.BoxLayout.Y_AXIS));

        commonMessagePanelHolder.setName("commonMessagePanelHolder"); // NOI18N
        commonMessagePanelHolder.setLayout(new javax.swing.BoxLayout(commonMessagePanelHolder, javax.swing.BoxLayout.LINE_AXIS));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(operationPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(commonMessagePanelHolder, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                .addContainerGap())
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(bindingConfigurationPanelHolder, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(operationPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(bindingConfigurationPanelHolder, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(commonMessagePanelHolder, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

private void operationComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_operationComboboxActionPerformed
    if (provider != null) {
        Object object = operationCombobox.getSelectedItem();
        if (object instanceof OperationItem) {
            lastSelectedOperation = ((OperationItem) object).getOperation();
            ExtensibilityElementConfigurationEditorComponent component = provider.getOperationBasedEditorComponent(lastSelectedOperation);
            if (component == null) {
                setInnerPanel(null);
                return;
            }
            setInnerPanel(component.getEditorPanel());
            if (!configuredOperations.contains(lastSelectedOperation)) {
                configuredOperations.add(lastSelectedOperation);
            }
        } else {
            lastSelectedOperation = null;
            setInnerPanel(null);
        }
    }
}//GEN-LAST:event_operationComboboxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bindingConfigurationPanelHolder;
    private javax.swing.JLabel commentLabel;
    private javax.swing.JPanel commonMessagePanelHolder;
    private javax.swing.JLabel multipleOperationCommentLabel;
    private javax.swing.JComboBox operationCombobox;
    private javax.swing.JLabel operationLabel;
    private javax.swing.JPanel operationPanel;
    // End of variables declaration//GEN-END:variables

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt == null) {
            return;
        }
        if (evt.getNewValue() instanceof String) {
            String message = (String) evt.getNewValue();
            boolean valid = true;
            if (evt.getPropertyName().equals(ExtensibilityElementConfigurationEditorComponent.PROPERTY_ERROR_EVT)) {
                messagePanel.setErrorMessage(message);
                valid = false;
            } else if (evt.getPropertyName().equals(ExtensibilityElementConfigurationEditorComponent.PROPERTY_WARNING_EVT)) {
                messagePanel.setWarningMessage(message);
            } else if (evt.getPropertyName().equals(ExtensibilityElementConfigurationEditorComponent.PROPERTY_CLEAR_MESSAGES_EVT)) {
                messagePanel.setMessage("");
            } else if (evt.getPropertyName().equals(ExtensibilityElementConfigurationEditorComponent.PROPERTY_NORMAL_MESSAGE_EVT)) {
                messagePanel.setMessage(message);
            }
            descriptor.setValid(valid);
        }
    }
    
    class OperationItem {

        String name;
        Operation operation;

        public OperationItem(String name, Operation operation) {
            this.name = name;
            this.operation = operation;
        }

        public Operation getOperation() {
            return operation;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}

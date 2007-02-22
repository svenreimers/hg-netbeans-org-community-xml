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
 * ElementOrTypeChooserPanel.java
 *
 * Created on August 30, 2006, 1:21 PM
 */

package org.netbeans.modules.xml.wsdl.ui.view;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.netbeans.api.project.Project;
import org.netbeans.modules.xml.wsdl.model.WSDLModel;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.util.NbBundle;

/**
 *
 * @author  radval
 */
public class ElementOrTypeChooserEditorPanel extends javax.swing.JPanel {
    
    private final JTable mPartsTable;
    private final Map<String, String> namespaceToPrefixMap;
    private Project mProject;
    private WSDLModel mModel;
    /** Creates new form ElementOrTypeChooserPanel 
     * @param partsTable */
    public ElementOrTypeChooserEditorPanel(JTable partsTable, Map<String, String> namespaceToPrefixMap, Project project, WSDLModel model ) {
        this.mPartsTable = partsTable;
        this.namespaceToPrefixMap = namespaceToPrefixMap;
        mProject = project;
        mModel = model;
        initComponents();
        initGUI();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        jTextField1.setEditable(false);
        jTextField1.setFocusable(false);
        jTextField1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        add(jTextField1, gridBagConstraints);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setText("...");
        jButton1.setFocusCycleRoot(true);
        jButton1.setMargin(new java.awt.Insets(0, 14, 0, 14));
        jButton1.setMaximumSize(new java.awt.Dimension(16, 16));
        jButton1.setMinimumSize(new java.awt.Dimension(16, 16));
        jButton1.setPreferredSize(new java.awt.Dimension(16, 16));
        jButton1.setSelected(true);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        add(jButton1, new java.awt.GridBagConstraints());

    }// </editor-fold>//GEN-END:initComponents
        
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        openSchemaComponentChooser();
    }//GEN-LAST:event_jButton1ActionPerformed
    
    public void openSchemaComponentChooser() {
        panel = new ElementOrTypeChooserPanel(mProject, namespaceToPrefixMap, mModel);
        final DialogDescriptor descriptor = new DialogDescriptor(panel , NbBundle.getMessage(ElementOrTypeChooserEditorPanel.class, "ElementOrTypeChooserEditorPanel.Dialog.title"), true, null);
        final PropertyChangeListener pcl = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if(evt.getSource()== panel && evt.getPropertyName().
                        equals(ElementOrTypeChooserPanel.PROP_ACTION_APPLY)) {
                    descriptor.setValid(((Boolean) evt.getNewValue()).booleanValue());
                }
            }
        };
        panel.addPropertyChangeListener(pcl);
        // dialog's action listener
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(DialogDescriptor.OK_OPTION) ||
                        evt.getSource().equals(DialogDescriptor.CANCEL_OPTION) ||
                        evt.getSource().equals(DialogDescriptor.CLOSED_OPTION)) {
                    panel.removePropertyChangeListener(pcl);
                }
                if (evt.getSource().equals(DialogDescriptor.OK_OPTION)) {
                    panel.apply();
                    int rowNo = mPartsTable.getSelectedRow();
                    PartAndElementOrTypeTableModel tableModel = ((PartAndElementOrTypeTableModel) mPartsTable.getModel());
                    tableModel.setValueAt(panel.getSelectedComponent(), rowNo, 1);
                    mPartsTable.getColumnModel().getColumn(1).getCellEditor().stopCellEditing();
                    mPartsTable.setEditingRow(rowNo);
                }
            }
        };
        descriptor.setButtonListener(al);
        descriptor.setValid(false);
        Dialog dialog = DialogDisplayer.getDefault().createDialog(descriptor);
        dialog.setVisible(true);
        dialog.toFront();
    }
    
    public JTextField getJTextField() {
        return this.jTextField1;
    }

   
    private void initGUI() {
        SchemaChooserKeyAction action = new SchemaChooserKeyAction();
        this.mPartsTable.getActionMap().put("SCHEMA_CHOOSER", action); //NOI18N
        this.mPartsTable.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,  InputEvent.CTRL_DOWN_MASK), "SCHEMA_CHOOSER");//NOI18N
        
    }
   
    class SchemaChooserKeyAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            openSchemaComponentChooser();
            
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
    private ElementOrTypeChooserPanel panel;
}

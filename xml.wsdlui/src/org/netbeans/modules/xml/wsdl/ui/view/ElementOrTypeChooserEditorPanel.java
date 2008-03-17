/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
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
 * nbbuild/licenses/CDDL-GPL-2-CP.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the GPL Version 2 section of the License file that
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
import org.netbeans.modules.xml.schema.model.SchemaComponent;
import org.netbeans.modules.xml.wsdl.model.WSDLModel;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.util.HelpCtx;
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        elementOrTypeTextField = new javax.swing.JTextField();
        browseBtn = new javax.swing.JButton();

        setName("Form"); // NOI18N
        setLayout(new java.awt.GridBagLayout());

        elementOrTypeTextField.setEditable(false);
        elementOrTypeTextField.setFocusable(false);
        elementOrTypeTextField.setMargin(new java.awt.Insets(0, 0, 0, 0));
        elementOrTypeTextField.setName("elementOrTypeTextField"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        add(elementOrTypeTextField, gridBagConstraints);

        browseBtn.setBackground(new java.awt.Color(255, 255, 255));
        browseBtn.setText("...");
        browseBtn.setFocusCycleRoot(true);
        browseBtn.setMargin(new java.awt.Insets(0, 14, 0, 14));
        browseBtn.setMaximumSize(new java.awt.Dimension(16, 16));
        browseBtn.setMinimumSize(new java.awt.Dimension(16, 16));
        browseBtn.setName("browseBtn"); // NOI18N
        browseBtn.setPreferredSize(new java.awt.Dimension(16, 16));
        browseBtn.setSelected(true);
        browseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseBtnActionPerformed(evt);
            }
        });
        add(browseBtn, new java.awt.GridBagConstraints());
    }// </editor-fold>//GEN-END:initComponents
        
    private void browseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseBtnActionPerformed
        openSchemaComponentChooser();
}//GEN-LAST:event_browseBtnActionPerformed
    
    public void openSchemaComponentChooser() {
        int rowNo = mPartsTable.getSelectedRow();
        PartAndElementOrTypeTableModel tableModel = ((PartAndElementOrTypeTableModel) mPartsTable.getModel());
        ElementOrType eot = (ElementOrType) tableModel.getValueAt(rowNo, 1);
        if (eot != null) {
            SchemaComponent comp = eot.getElement();
            if (comp == null) {
                comp = eot.getType();
            }
            panel = new ElementOrTypeChooserPanel(mProject, namespaceToPrefixMap, mModel, comp);
        } else {
            panel = new ElementOrTypeChooserPanel(mProject, namespaceToPrefixMap, mModel);
        }
        final DialogDescriptor descriptor = new DialogDescriptor(panel , NbBundle.getMessage(ElementOrTypeChooserEditorPanel.class, "ElementOrTypeChooserEditorPanel.Dialog.title"), true, null);
        descriptor.setHelpCtx(new HelpCtx("org.netbeans.modules.xml.wsdl.ui.api.property.ElementOrTypePropertyEditor"));
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
                    int rNo = mPartsTable.getSelectedRow();
                    PartAndElementOrTypeTableModel tModel = ((PartAndElementOrTypeTableModel) mPartsTable.getModel());
                    tModel.setValueAt(panel.getSelectedComponent(), rNo, 1);
                    mPartsTable.getColumnModel().getColumn(1).getCellEditor().stopCellEditing();
                    mPartsTable.setEditingRow(rNo);
                }
            }
        };
        descriptor.setButtonListener(al);
        descriptor.setValid(false);
        Dialog dialog = DialogDisplayer.getDefault().createDialog(descriptor);
        dialog.getAccessibleContext().setAccessibleDescription(descriptor.getTitle());
        dialog.setVisible(true);
        dialog.toFront();
    }
    
    public JTextField getJTextField() {
        return this.elementOrTypeTextField;
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
    private javax.swing.JButton browseBtn;
    private javax.swing.JTextField elementOrTypeTextField;
    // End of variables declaration//GEN-END:variables
    private ElementOrTypeChooserPanel panel;
}

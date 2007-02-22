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

package org.netbeans.modules.xml.wsdl.ui.api.property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;

import org.openide.explorer.propertysheet.PropertyEnv;

/**
 *
 * @author  skini
 */
public class PartsSelectorPanel extends javax.swing.JPanel {
    
    /**
     * 
     */
    private static final long serialVersionUID = 8696139559577226547L;
    private int[] selectedIndices;
    
    /** Creates new form PartsSelectorPanel */
    public PartsSelectorPanel(String[] parts, String[] selectedPartNames, PropertyEnv env) {
        mEnv = env;
        mEnv.setState(PropertyEnv.STATE_INVALID);
        mParts = parts;
        if (selectedPartNames != null && selectedPartNames.length == 1 && selectedPartNames[0].trim().length() == 0) {
            selectedIndices = null;
        } else {
            selectedIndices = new int[selectedPartNames.length];
            Map<String, Integer> map = new HashMap<String, Integer>();
            int j = 0;
            for (String part : parts) {
                map.put(part, new Integer(j++));
            }

            for (int i = 0; i < selectedPartNames.length; i++) {
                String partName = selectedPartNames[i];
                if (map.containsKey(partName)) {
                    selectedIndices[i] = map.get(partName).intValue();
                } else {
                    selectedIndices[i] = -1;
                }
            }
        }
        initComponents();
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();

        ComboBoxModel model = new DefaultComboBoxModel(mParts);
        jList1.setModel(model);
        if (selectedIndices != null)
        jList1.setSelectedIndices(selectedIndices);
        jList1.setToolTipText(org.openide.util.NbBundle.getMessage(PartsSelectorPanel.class, "PartsSelectorPanel.jList1.toolTipText")); // NOI18N
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });

        jScrollPane1.setViewportView(jList1);
        jList1.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(PartsSelectorPanel.class, "PartsSelectorPanel.jList1.AccessibleContext.accessibleName")); // NOI18N
        jList1.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(PartsSelectorPanel.class, "PartsSelectorPanel.jList1.AccessibleContext.accessibleDescription")); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 220, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        enableOK();
        JList list = (JList) evt.getSource();
        if (!list.getValueIsAdjusting()) {
            Object[] sv = list.getSelectedValues();
            if (sv != null && sv.length > 0) {
                
            }
        }
    }//GEN-LAST:event_jList1ValueChanged
    
    private void enableOK() {
        if (!okEnabled) {
            mEnv.setState(PropertyEnv.STATE_VALID);
            okEnabled = true;
        }
        
    }
    
    @Override
    public void removeNotify() {
        if (mEnv.getState().equals(PropertyEnv.STATE_VALID)) {
            Object[] selectedValues  = jList1.getSelectedValues();
            StringBuffer strBuf = new StringBuffer();
            for (Object selectedValue : selectedValues) {
                strBuf.append((String) selectedValue).append(" ");
            }
            this.firePropertyChange(PartsSelectorPropertyEditor.PROP_NAME, null, strBuf.toString().trim());
        }
        super.removeNotify();
    }
    
    private boolean okEnabled = false;
    private PropertyEnv mEnv;
    private String[] mParts;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    
}

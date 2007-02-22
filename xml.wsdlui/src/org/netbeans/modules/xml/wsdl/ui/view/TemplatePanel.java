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
 * TemplatePanel.java
 *
 * Created on August 31, 2006, 11:59 AM
 */

package org.netbeans.modules.xml.wsdl.ui.view;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import org.netbeans.modules.xml.wsdl.ui.view.wizard.ExtensibilityElementTemplateFactory;
import org.netbeans.modules.xml.wsdl.ui.view.wizard.TemplateType;
import org.netbeans.modules.xml.wsdl.ui.view.wizard.localized.LocalizedTemplate;

/**
 *
 * @author  skini
 */
public class TemplatePanel extends javax.swing.JPanel {
    
    private LocalizedTemplate mlt;
    private ButtonGroup btnGroup;
    
    /** Creates new form TemplatePanel */
    public TemplatePanel(LocalizedTemplate lt, ButtonGroup btnGroup) {
        this.mlt = lt;
        this.btnGroup = btnGroup;
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
        jRadioButton1 = new javax.swing.JRadioButton();

        btnGroup.add(jRadioButton1);
        jRadioButton1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jRadioButton1)
                .addContainerGap(202, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jRadioButton1)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton jRadioButton1;
    // End of variables declaration//GEN-END:variables
    
    private void initGUI() {
        jRadioButton1.setText(this.mlt.getName());
        
    }
    
    public boolean isSelected() {
        return jRadioButton1.isSelected();
    }
    
    public JRadioButton getRadioButton() {
    	return this.jRadioButton1;
    }
    
    public LocalizedTemplate getTemplate() {
        return this.mlt;
    }
    
    
}

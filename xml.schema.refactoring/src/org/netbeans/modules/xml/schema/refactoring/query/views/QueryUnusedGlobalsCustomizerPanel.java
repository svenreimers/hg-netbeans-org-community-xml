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
 * QueryUnusedGlobalsCustomizerPanel.java
 *
 * Created on April 14, 2006, 8:49 PM
 */

package org.netbeans.modules.xml.schema.refactoring.query.views;

import javax.swing.UIManager;

/**
 *
 * @author  Jeri Lockhart
 */
public class QueryUnusedGlobalsCustomizerPanel extends javax.swing.JPanel {
    public static final long serialVersionUID = 1L;
    public static final String PROP_RUN_UNUSED_GLOBALS_QUERY =
            "prop-run-unused-globals-query";    // NOI18N
    /**
     * Creates new form QueryUnusedGlobalsCustomizerPanel
     */
    public QueryUnusedGlobalsCustomizerPanel() {
        initComponents();
        
    }
    
    /**
     * programmatically set the Exclude Global Elements
     * check box
     *
     */
    public void setExcludeElements(boolean exclude){
        excludeGEsCheckBox.setSelected(exclude);
    }
    /**
     * programmatically set the Exclude Global Elements
     * check box
     *
     */
    public boolean getExcludeElements(){
        return excludeGEsCheckBox.isSelected();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        excludeGEsCheckBox = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(excludeGEsCheckBox, java.util.ResourceBundle.getBundle("org/netbeans/modules/xml/schema/refactoring/query/views/Bundle").getString("LBL_Exclude_Global_Elements"));
        excludeGEsCheckBox.setToolTipText(org.openide.util.NbBundle.getBundle(QueryUnusedGlobalsCustomizerPanel.class).getString("LBL_Exclude_Global_Elements"));
        excludeGEsCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        excludeGEsCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        excludeGEsCheckBox.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, java.util.ResourceBundle.getBundle("org/netbeans/modules/xml/schema/refactoring/query/views/Bundle").getString("LBL_Find_Unused_Description"));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(excludeGEsCheckBox, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(157, 157, 157)))
                .add(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                .add(24, 24, 24)
                .add(excludeGEsCheckBox, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(64, 64, 64))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox excludeGEsCheckBox;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
    
}

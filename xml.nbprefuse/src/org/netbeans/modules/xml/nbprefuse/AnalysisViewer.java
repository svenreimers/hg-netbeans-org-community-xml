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

package org.netbeans.modules.xml.nbprefuse;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.openide.util.NbBundle;

/**
 *
 * @author  Jeri Lockhart
 */
public class AnalysisViewer extends JPanel implements

        PropertyChangeListener       {
    public static final String PROP_GRAPH_NODE_SELECTION_CHANGE =
            "prop-graph-node-selection-changed";
    public static final String PROP_GRAPH_NODE_SELECTION_CHANGED_RELAY = 
            "prop-graph-node-selection-changed-relay";
    
    public static final long serialVersionUID = -7710166579907141983L;
    transient private View currentView;
    private boolean reshowOnResize = true;
    
    
    /**
     * Creates new form AnalysisViewer
     */
    public AnalysisViewer() {
        initComponents();
        initialize();
    }
    
    private void initialize() {
        setMinimumSize(new Dimension(180,30));
//        addComponentListener(new ComponentAdapter(){
//            public void componentResized(ComponentEvent e) {
//                if (currentView != null && reshowOnResize){
//                    currentView.usePacer(false);
//                    showCurrentView();
//                }
//            }
//        });
    }
    
    public void setCurrentView(final View view){
        this.currentView = view;
    }
    
    public void removeToolBar() {
        remove(toolbarPnl);
        validate();
        repaint();
    }

    private void showCurrentView(){
        if (currentView != null){
            showView(currentView);
        }
    }
    public void addDisplayPanel(JPanel displayPanel) {
        displayContainerPnl.removeAll();
        displayContainerPnl.setLayout(new BorderLayout());
        displayContainerPnl.add(displayPanel, BorderLayout.CENTER);
    }
    
    
    public boolean showView(View view) {
        currentView = view;
        
        if (view.showView(this)){
            validate();
            repaint();
        }
        return true;
    }
    
    /**
     *
     *
     */
    public JPanel getPanel() {
        return this;
    }
    
    /**
     *
     *
     */
//    public Column getColumn(){
//        return this;
//    }
    
    
    public void propertyChange(PropertyChangeEvent evt){
        String propertyName = evt.getPropertyName();
        if (propertyName.equals(
                PROP_GRAPH_NODE_SELECTION_CHANGE)){
            // WhereUsedView's selectionFocusControl(FindUsagesFocusControl)
            //   fired this event
            //   now notify RefactoringPanel so it can select
            //     the corresponding node in the RefactoringPanel explorer
            firePropertyChange(PROP_GRAPH_NODE_SELECTION_CHANGED_RELAY,
                    evt.getOldValue(),evt.getNewValue());
            
        }
    }
    
    /**
     * @return  true if this Viewer will reshow the view
     *     when the JPanel is resized
     *           false if not
     *
     */
    public boolean getReshowOnResize(){
        return this.reshowOnResize;
    }
    
    /**
     * @param reshowOnResize  true if this viewer should
     *    reshow the view when the panel is resized
     *
     */
    public void setReshowOnResize(boolean reshowOnResize){
        this.reshowOnResize = reshowOnResize;
    }
  
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        toolbarPnl = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        layoutBtn = new javax.swing.JButton();
        displayContainerPnl = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        setBackground(java.awt.Color.white);
        setPreferredSize(new java.awt.Dimension(200, 200));
        toolbarPnl.setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(1);
        layoutBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/modules/xml/nbprefuse/resources/relayout.png")));
        layoutBtn.setToolTipText(java.util.ResourceBundle.getBundle("org/netbeans/modules/xml/nbprefuse/Bundle").getString("LBL_Relayout_graph"));
        layoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                layoutBtnActionPerformed(evt);
            }
        });

        jToolBar1.add(layoutBtn);

        toolbarPnl.add(jToolBar1, java.awt.BorderLayout.CENTER);

        add(toolbarPnl, java.awt.BorderLayout.WEST);

        displayContainerPnl.setLayout(null);

        displayContainerPnl.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));
        add(displayContainerPnl, java.awt.BorderLayout.CENTER);

    }// </editor-fold>//GEN-END:initComponents

    private void layoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_layoutBtnActionPerformed
// TODO add your handling code here:
        if (currentView != null){
            showView(currentView);
        }
    }//GEN-LAST:event_layoutBtnActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel displayContainerPnl;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton layoutBtn;
    private javax.swing.JPanel toolbarPnl;
    // End of variables declaration//GEN-END:variables
    
}

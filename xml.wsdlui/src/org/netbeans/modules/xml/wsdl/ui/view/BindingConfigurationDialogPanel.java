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
 * BindingConfigurationDialogPanel.java
 *
 * Created on September 8, 2006, 2:22 PM
 */

package org.netbeans.modules.xml.wsdl.ui.view;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import org.netbeans.modules.xml.wsdl.bindingsupport.template.localized.LocalizedTemplate;
import org.netbeans.modules.xml.wsdl.bindingsupport.template.localized.LocalizedTemplateGroup;
import org.netbeans.modules.xml.wsdl.model.Binding;

import org.netbeans.modules.xml.wsdl.model.Service;
import org.netbeans.modules.xml.wsdl.model.WSDLModel;
import org.netbeans.modules.xml.wsdl.ui.actions.NameGenerator;
import org.netbeans.modules.xml.wsdl.ui.view.treeeditor.newtype.OperationPanel;
import org.openide.DialogDescriptor;
import org.openide.util.NbBundle;

/**
 *
 * @author  skini
 */
public class BindingConfigurationDialogPanel extends javax.swing.JPanel {
    
    private String mErrorMessage = null;
    
    /** Creates new form BindingConfigurationDialogPanel */
    public BindingConfigurationDialogPanel(WSDLModel model) {
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

        bindingConfigurationPanel1 = new org.netbeans.modules.xml.wsdl.ui.view.BindingConfigurationPanel();
        commonMessagePanel1 = new org.netbeans.modules.xml.wsdl.ui.view.common.CommonMessagePanel();

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(commonMessagePanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 301, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(10, 10, 10))
            .add(bindingConfigurationPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(bindingConfigurationPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 228, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(58, 58, 58)
                .add(commonMessagePanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void initGUI() {
        DocumentListener bindingNameListener  = new BindingNameChangeListener();
        DocumentListener serviceNameListener  = new ServiceNameChangeListener();
        DocumentListener servicePortNameListener  = new ServicePortNameChangeListener();
        
        this.bindingConfigurationPanel1.getBindingNameTextField().getDocument().addDocumentListener(bindingNameListener);
        this.bindingConfigurationPanel1.getServiceNameTextField().getDocument().addDocumentListener(serviceNameListener);
        this.bindingConfigurationPanel1.getServicePortTextField().getDocument().addDocumentListener(servicePortNameListener);
    }
    
    
    public String getBindingName() {
        return bindingConfigurationPanel1.getBindingName();
    }
    
    public void setBindingName(String bindingName) {
        bindingConfigurationPanel1.setBindingName(bindingName);
        
    }

    public void setServiceName(String svcName) {
        bindingConfigurationPanel1.setServiceName(svcName);
        
    }

    public void setServicePortName(String string) {
        bindingConfigurationPanel1.setServicePortName(string);
        
    }

    public LocalizedTemplateGroup getBindingType() {
        return bindingConfigurationPanel1.getBindingType();
    }

    public LocalizedTemplate getBindingSubType() {
        return bindingConfigurationPanel1.getBindingSubType();
    }

    public String getServiceName() {
        return bindingConfigurationPanel1.getServiceName();
    }

    public String getServicePortName() {
        return bindingConfigurationPanel1.getServicePortName();
    }
    
    public void setDialogDescriptor(DialogDescriptor dd) {
        this.mDD = dd;
    }
    
    private boolean isValidName(String text) {
        try {
            boolean isValid  = org.netbeans.modules.xml.xam.dom.Utils.isValidNCName(text);
            if(!isValid) {
                mErrorMessage = NbBundle.getMessage(OperationPanel.class, "ERR_MSG_INVALID_NAME" , text);
            } else {
                mErrorMessage = null;
            }
            
        }  catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return mErrorMessage == null;
    }
    
    private void validateAll() {
        boolean validBinding = isValidName(this.bindingConfigurationPanel1.getBindingName());
        if(!validBinding) {
            updateMessagePanel();
            return;
        }
        
        
        boolean isBindingExist = isBindingExists();
        if(isBindingExist) {
            updateMessagePanel();
            return;
        }
        
        boolean validService = isValidName(this.bindingConfigurationPanel1.getServiceName());
        if(!validService) {
            updateMessagePanel();
            return;
        }
        
        
        boolean validPort = isValidName(this.bindingConfigurationPanel1.getServicePortName());
        if(!validPort) {
            updateMessagePanel();
            return;
        }
        
        
        String serviceName = this.bindingConfigurationPanel1.getServiceName();
        String portName = this.bindingConfigurationPanel1.getServicePortName();
        
        boolean isServicePortExist = isServicePortExists(serviceName, portName);
        if(isServicePortExist) {
            updateMessagePanel();
            return;
        }
        
        
        this.mErrorMessage = null;
        updateMessagePanel();
        
    }
    
    private boolean isBindingExists() {
        boolean exist = false;
        
        String text = this.bindingConfigurationPanel1.getBindingName();
        Binding b = mModel.findComponentByName(text, Binding.class);
            
        if(b != null) {
            this.mErrorMessage = "Binding \"" + text + "\" already exists.";
            exist = true;
        } 
                
        return exist;
    }
 
    
    
    public boolean isServicePortExists(String serviceName, String portName) {
        boolean exist = false;
        if(serviceName != null && portName != null) {
            Service service = mModel.findComponentByName(serviceName, Service.class);
            if(service != null) {
                exist = NameGenerator.getInstance().isServicePortExists(getServicePortName(), service);
                if (exist) {
                    this.mErrorMessage = "Service port" + getServicePortName() + " already exists.";
                }
            }
        }
        return exist;
    }
    
    private void updateMessagePanel() {
        if(this.mErrorMessage != null) {
            commonMessagePanel1.setErrorMessage(mErrorMessage);
            if(this.mDD != null) {
                mDD.setValid(false);
            }
            //firePropertyChange(APPLY_CHANGE, !commonMessagePanel1.isStateValid(), commonMessagePanel1.isStateValid());
        } else {
            commonMessagePanel1.setMessage("");
            if (mDD != null) {
                mDD.setValid(true);
            }
            //firePropertyChange(APPLY_CHANGE, !commonMessagePanel1.isStateValid(), commonMessagePanel1.isStateValid());
        }
    }

    class BindingNameChangeListener implements DocumentListener {
        
        public void changedUpdate(DocumentEvent e) {
            validateAll();
        }
        
        public void insertUpdate(DocumentEvent e) {
            validateAll();
        }
        
        public void removeUpdate(DocumentEvent e) {
            validateAll();
        }
    }
    
    class ServiceNameChangeListener implements DocumentListener {
        
        public void changedUpdate(DocumentEvent e) {
            validateAll();
        }
        
        public void insertUpdate(DocumentEvent e) {
            validateAll();
        }
        
        public void removeUpdate(DocumentEvent e) {
            validateAll();
        }
    }
    class ServicePortNameChangeListener implements DocumentListener {
        
        public void changedUpdate(DocumentEvent e) {
            validateAll();
        }
        
        public void insertUpdate(DocumentEvent e) {
            validateAll();
        }
        
        public void removeUpdate(DocumentEvent e) {
            validateAll();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.netbeans.modules.xml.wsdl.ui.view.BindingConfigurationPanel bindingConfigurationPanel1;
    private org.netbeans.modules.xml.wsdl.ui.view.common.CommonMessagePanel commonMessagePanel1;
    // End of variables declaration//GEN-END:variables
    private WSDLModel mModel;
    private DialogDescriptor mDD;
    
//    public void doesBindingExist() {
//        boolean exists = NameGenerator.getInstance().isBindingExists(getBindingName(), mModel);
//        if (commonMessagePanel1.isStateValid()) {
//            if (exists)
//                commonMessagePanel1.setErrorMessage("Binding Name " + getBindingName() + " already exists.");
//        }
//    }
//
//    public boolean doesServiceExists() {
//        return NameGenerator.getInstance().isServiceExists(getServiceName(), mModel);
//    }
//    
//    public void doesServicePortExists() {
//        if (doesServiceExists()) {
//            Service service = mModel.findComponentByName(getServiceName(), Service.class);
//            boolean exists = NameGenerator.getInstance().isServicePortExists(getServicePortName(), service);
//            if (commonMessagePanel1.isStateValid()) {
//                if (exists)
//                    commonMessagePanel1.setErrorMessage("Service port" + getServicePortName() + " already exists.");
//            }
//        }
//    }
}

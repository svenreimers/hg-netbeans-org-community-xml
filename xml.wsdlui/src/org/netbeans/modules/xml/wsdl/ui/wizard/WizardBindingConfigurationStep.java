/*
 * WizardBindingConfigurationStep.java
 *
 * Created on August 31, 2006, 3:24 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.netbeans.modules.xml.wsdl.ui.wizard;

import java.awt.Component;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import org.netbeans.modules.xml.wsdl.model.Binding;
import org.netbeans.modules.xml.wsdl.model.Port;
import org.netbeans.modules.xml.wsdl.model.PortType;
import org.netbeans.modules.xml.wsdl.model.Service;
import org.netbeans.modules.xml.wsdl.model.WSDLModel;
import org.netbeans.modules.xml.wsdl.model.WSDLModelFactory;
import org.netbeans.modules.xml.wsdl.ui.spi.ExtensibilityElementTemplateProvider;
import org.netbeans.modules.xml.wsdl.ui.spi.ValidationInfo;
import org.netbeans.modules.xml.wsdl.ui.view.BindingConfigurationPanel;
import org.netbeans.modules.xml.wsdl.ui.view.wizard.localized.LocalizedTemplate;
import org.netbeans.modules.xml.wsdl.ui.view.wizard.localized.LocalizedTemplateGroup;
import org.netbeans.modules.xml.xam.ModelSource;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.TemplateWizard;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.windows.IOProvider;

/**
 *
 * @author radval
 */
public class WizardBindingConfigurationStep implements WizardDescriptor.FinishablePanel {
    
    public static final String BINDING_NAME = "BINDING_NAME";
    
    public static final String BINDING_TYPE = "BINDING_TYPE";
    
    public static final String BINDING_SUBTYPE = "BINDING_SUBTYPE";

    public static final String SERVICE_NAME = "SERVICE_NAME";

    public static final String SERVICEPORT_NAME = "SERVICEPORT_NAME";
    
    public static final String BINDING = "BINDING";

    public static final String SERVICE = "SERVICE";
    
    public static final String PORT = "PORT";
    
    
    
    private BindingConfigurationPanel mPanel;
    
    private String mErrorMessage;
    
    private String mBindingSubTypeError;
    
    private final List<ChangeListener> listeners = new ArrayList();
    
    private WizardDescriptor wiz = null;
   
    private WSDLModel mTempModel;
    
    private PortType mPortType;
    
    private Binding mBinding;
    
    private Service mService;
    
    private Port mPort;
    
    /** Creates a new instance of WizardBindingConfigurationStep */
    public WizardBindingConfigurationStep() {
    }

    public void addChangeListener(ChangeListener l) {
        listeners.add(l);
    }

    public void removeChangeListener(ChangeListener l) {
        listeners.remove(l);
    }

    public Component getComponent() {
        if (mPanel == null) {
            this.mPanel = new BindingConfigurationPanel();
//            this.mPanel.setPreferredSize(new Dimension(450, 400));
            this.mPanel.setName(NbBundle.getMessage(WizardBindingConfigurationStep.class, "LBL_WizardBindingConfigurationStep"));
            TextChangeListener listener  = new TextChangeListener();
            this.mPanel.getBindingNameTextField().getDocument().addDocumentListener(listener);
            this.mPanel.getServiceNameTextField().getDocument().addDocumentListener(listener);
            this.mPanel.getServicePortTextField().getDocument().addDocumentListener(listener);
            BindingConfigurationListener propListener = new BindingConfigurationListener();
            this.mPanel.addPropertyChangeListener(propListener);
            
        }
        return this.mPanel;
    }

    
    
    public HelpCtx getHelp() {
        return new HelpCtx(WizardBindingConfigurationStep.class);
    }

    public boolean isValid() {
       wiz.putProperty ("WizardPanel_errorMessage", this.mErrorMessage); // NOI18N
        return this.mErrorMessage == null;

    }

    public void readSettings(Object settings) {
        TemplateWizard templateWizard = (TemplateWizard)settings;
        wiz = templateWizard;
        String fileName = (String) templateWizard.getProperty(WsdlPanel.FILE_NAME);
        if(this.mPanel.getBindingName() == null || this.mPanel.getBindingName().trim().equals("")) {
            this.mPanel.setBindingName(fileName + "Binding"); //NOI18N
        }
        if(this.mPanel.getServiceName() == null || this.mPanel.getServiceName().trim().equals("")) {
            this.mPanel.setServiceName(fileName + "Service"); //NOI18N
        }
        if(this.mPanel.getServicePortName() == null || this.mPanel.getServicePortName().trim().equals("")) {
            this.mPanel.setServicePortName(fileName + "Port"); //NOI18N
        }
        
        this.mPortType = (PortType) templateWizard.getProperty(WizardPortTypeConfigurationStep.PORTTYPE);
        this.mTempModel = (WSDLModel) templateWizard.getProperty(WizardPortTypeConfigurationStep.TEMP_WSDLMODEL);
        
        LocalizedTemplate bindingSubType = this.mPanel.getBindingSubType();
        processBindingSubType(bindingSubType);
    }

    public void storeSettings(Object settings) {
        TemplateWizard templateWizard = (TemplateWizard)settings;
        if(templateWizard.getValue() == TemplateWizard.CANCEL_OPTION) {
        	
        	return;
        }
        
        String bindingName = this.mPanel.getBindingName();
        LocalizedTemplateGroup bindingType = this.mPanel.getBindingType();
        LocalizedTemplate bindingSubType = this.mPanel.getBindingSubType();
        String serviceName = this.mPanel.getServiceName();
        String servicePortName = this.mPanel.getServicePortName();
        
        templateWizard.putProperty(BINDING_NAME, bindingName);
        templateWizard.putProperty(BINDING_TYPE, bindingType);
        templateWizard.putProperty(BINDING_SUBTYPE, bindingSubType);
        templateWizard.putProperty(SERVICE_NAME, serviceName);
        templateWizard.putProperty(SERVICEPORT_NAME, servicePortName);
        
        processBindingSubType(bindingSubType);
    }
    
    private boolean isValidName(Document doc) {
        try {
            String text = doc.getText(0, doc.getLength());
            boolean isValid  = org.netbeans.modules.xml.xam.dom.Utils.isValidNCName(text);
            if(!isValid) {
                mErrorMessage = "Name \"" + text + "\" is not a valid NCName";
            } else {
                mErrorMessage = null;
            }
            
            fireChangeEvent();
        }  catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return mErrorMessage == null;
    }
    
    private void validate() {
        boolean isValidBinding = isValidName(this.mPanel.getBindingNameTextField().getDocument());
        if(!isValidBinding) {
            fireChangeEvent();
            return;
        }
        
        boolean isValidService = isValidName(this.mPanel.getServiceNameTextField().getDocument());
        if(!isValidService) {
            fireChangeEvent();
            return;
        }
        
        boolean isValidPort = isValidName(this.mPanel.getServicePortTextField().getDocument());
        if(!isValidPort) {
            fireChangeEvent();
            return;
        }
        
        if(this.mBindingSubTypeError != null) {
            this.mErrorMessage = this.mBindingSubTypeError;
            fireChangeEvent();
            return;
        }
        
        fireChangeEvent();
    }
    
    private void fireChangeEvent() {
        Iterator<ChangeListener> it = this.listeners.iterator();
        ChangeEvent e = new ChangeEvent(this);
        while(it.hasNext()) {
            ChangeListener l = it.next();
            l.stateChanged(e);
        }
    }

    public boolean isFinishPanel() {
        return true;
    }
    
    private void processBindingSubType(LocalizedTemplate bindingSubType) {
        if(bindingSubType != null) {
            String bindingName = this.mPanel.getBindingName();
            LocalizedTemplateGroup bindingType = this.mPanel.getBindingType();
            
            
            //service and port
            String serviceName = this.mPanel.getServiceName();
            String servicePortName = this.mPanel.getServicePortName();
            
            Map configurationMap = new HashMap();
            configurationMap.put(WizardBindingConfigurationStep.BINDING_NAME, bindingName);
            configurationMap.put(WizardBindingConfigurationStep.BINDING_TYPE, bindingType);
           
            //this could be null for a binding which does not have a sub type
            configurationMap.put(WizardBindingConfigurationStep.BINDING_SUBTYPE, bindingSubType);
            
            //service and port
            configurationMap.put(WizardBindingConfigurationStep.SERVICE_NAME, serviceName);
            configurationMap.put(WizardBindingConfigurationStep.SERVICEPORT_NAME, servicePortName);
            
            this.mTempModel.startTransaction();
            if(this.mBinding != null) {
            	this.mTempModel.getDefinitions().removeBinding(this.mBinding);
            }
            
            if(this.mService != null) {
            	this.mTempModel.getDefinitions().removeService(this.mService);
            }
            
            BindingGenerator bGen = new BindingGenerator(this.mTempModel, this.mPortType, configurationMap);
            bGen.execute();
            
            this.mBinding = bGen.getBinding();
            this.mService = bGen.getService();
            this.mPort = bGen.getPort();
            
            if(this.mBinding != null) {
                String targetNamespace = (String) wiz.getProperty(WsdlPanel.WSDL_TARGETNAMESPACE);
                List<ValidationInfo> vAllInfos =new ArrayList<ValidationInfo>();
                
                List<ValidationInfo> vBindingInfos = bindingSubType.getMProvider().validate(this.mBinding);
                if(vBindingInfos != null) {
                	vAllInfos.addAll(vBindingInfos);
                }
                
                if(this.mPort != null) {
                	List<ValidationInfo> vPortInfos = bindingSubType.getMProvider().validate(this.mPort);
                	if(vPortInfos != null) {
                    	vAllInfos.addAll(vPortInfos);
                    }
                }
                if(vAllInfos != null && vAllInfos.size() > 0) {
                    ValidationInfo vInfo = vAllInfos.get(0);
                    this.mBindingSubTypeError =  vInfo.getDescription();
                    IOProvider.getDefault().getStdOut().print(this.mBindingSubTypeError);
                    validate();
                } else {
                    //no errors
                	this.mBindingSubTypeError =  null;
                    validate();
                    if(this.mBinding != null) {
                        bindingSubType.getMProvider().postProcess(targetNamespace, this.mBinding);
                    }
                    if(this.mPort != null) {
                        bindingSubType.getMProvider().postProcess(targetNamespace, this.mPort);
                    }
                    this.wiz.putProperty(BINDING, this.mBinding);
                    this.wiz.putProperty(SERVICE, this.mService);
                    this.wiz.putProperty(PORT, this.mPort);
                }
            }
            
            this.mTempModel.endTransaction();
        }
    }
    
    class TextChangeListener implements DocumentListener {
     
         public void changedUpdate(DocumentEvent e) {
            validate();
         }
         
         public void insertUpdate(DocumentEvent e) {
             validate();
         }

         public void removeUpdate(DocumentEvent e) {
             validate();
         }
    
    }
    
    class BindingConfigurationListener implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            String propertyName = evt.getPropertyName();
            if(BindingConfigurationPanel.PROP_BINDING_SUBTYPE.equals(propertyName)) {
                LocalizedTemplate bindingSubType = (LocalizedTemplate) evt.getNewValue();
                processBindingSubType(bindingSubType);
            } else if(BindingConfigurationPanel.PROP_BINDING_TYPE.equals(propertyName)) {
                processBindingSubType(mPanel.getBindingSubType());
            }
        }
    }
}

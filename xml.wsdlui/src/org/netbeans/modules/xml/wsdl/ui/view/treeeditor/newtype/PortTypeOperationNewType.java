/*
 * PortTypeOperationNewType.java
 *
 * Created on September 8, 2006, 6:19 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.netbeans.modules.xml.wsdl.ui.view.treeeditor.newtype;


import java.awt.Dimension;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.modules.xml.wsdl.model.PortType;
import org.netbeans.modules.xml.wsdl.model.WSDLComponent;
import org.netbeans.modules.xml.wsdl.model.WSDLModel;
import org.netbeans.modules.xml.wsdl.ui.view.OperationConfigurationPanel;
import org.netbeans.modules.xml.wsdl.ui.view.OperationType;
import org.netbeans.modules.xml.wsdl.ui.view.PartAndElementOrTypeTableModel;
import org.netbeans.modules.xml.wsdl.ui.wizard.OperationGenerator;
import org.netbeans.modules.xml.wsdl.ui.wizard.PortTypeGenerator;
import org.netbeans.modules.xml.wsdl.ui.wizard.SchemaImportsGenerator;
import org.netbeans.modules.xml.wsdl.ui.wizard.WizardPortTypeConfigurationStep;
import org.netbeans.modules.xml.xam.ModelSource;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.datatransfer.NewType;

/**
 *
 * @author radval
 */
public class PortTypeOperationNewType extends NewType {
    private PortType mPortType = null;
    
    public PortTypeOperationNewType(WSDLComponent message) {
        mPortType = (PortType) message;
    }
    

    @Override
    public String getName() {
        return NbBundle.getMessage(PortTypeOperationNewType.class, "LBL_NewType_PortTypeOperation");
    }


    @Override
  public void create() throws IOException {
        WSDLModel model = mPortType.getModel();
        

        ModelSource modelSource = model.getModelSource();
        FileObject wsdlFile = (FileObject) modelSource.getLookup().lookup(FileObject.class);
        if(wsdlFile != null) {
            Project project = FileOwnerQuery.getOwner(wsdlFile);
            if(project != null) {
                OperationPanel panel = new OperationPanel(project, model);
                
                panel.setPreferredSize(new Dimension(550, 600));
                DialogDescriptor dd = new DialogDescriptor(panel, 
                                                           NbBundle.getMessage(PortTypeNewType.class, "LBL_Create_New_Operation"), 
                                                           true, 
                                                           DialogDescriptor.OK_CANCEL_OPTION,
                                                           DialogDescriptor.OK_OPTION,
                                                           DialogDescriptor.DEFAULT_ALIGN,
                                                           HelpCtx.DEFAULT_HELP,
                                                           null);
                panel.setDialogDescriptor(dd);
                
                if(DialogDisplayer.getDefault().notify(dd) == DialogDescriptor.OK_OPTION) {
                    OperationConfigurationPanel opPanel = panel.getOperationConfigurationPanel();
                    Map configurationMap = new HashMap();
                    
                    String operationName = opPanel.getOperationName();
                    OperationType ot = opPanel.getOperationType();
                    configurationMap.put(WizardPortTypeConfigurationStep.OPERATION_NAME, operationName);
                    configurationMap.put(WizardPortTypeConfigurationStep.OPERATION_TYPE, ot);
                    
                    String inputMessageName = opPanel.getInputMessageName();
                    String outputMessageName = opPanel.getOutputMessageName();
                    String faultMessageName = opPanel.getFaultMessageName();
                    
                    configurationMap.put(WizardPortTypeConfigurationStep.OPERATION_INPUT_MESSAGE, inputMessageName);
                    configurationMap.put(WizardPortTypeConfigurationStep.OPERATION_OUTPUT_MESSAGE, outputMessageName);
                    configurationMap.put(WizardPortTypeConfigurationStep.OPERATION_FAULT_MESSAGE, faultMessageName);
                    
                    List<PartAndElementOrTypeTableModel.PartAndElementOrType> inputParts = opPanel.getInputMessageParts();
                    List<PartAndElementOrTypeTableModel.PartAndElementOrType> outputParts = opPanel.getOutputMessageParts();
                    List<PartAndElementOrTypeTableModel.PartAndElementOrType> faultParts = opPanel.getFaultMessageParts();
                    Map<String, String> namespaceToPrefixMap = opPanel.getNamespaceToPrefixMap();
                    
                    configurationMap.put(WizardPortTypeConfigurationStep.NAMESPACE_TO_PREFIX_MAP, namespaceToPrefixMap);
                   
                    //if inputMessage Name is new not an existing message name then populate part names as well
                    if(opPanel.isNewInputMessage()) {
                        configurationMap.put(WizardPortTypeConfigurationStep.OPERATION_INPUT, inputParts);
                    }
                    
                    //if outputMessage Name is new not an existing message name then populate part names as well
                    if(opPanel.isNewOutputMessage()) {
                        configurationMap.put(WizardPortTypeConfigurationStep.OPERATION_OUTPUT, outputParts);
                    }
                    
                    //if faultMessage Name is new not an existing message name then populate part names as well
                    if(opPanel.isNewFaultMessage()) {
                        configurationMap.put(WizardPortTypeConfigurationStep.OPERATION_FAULT, faultParts);
                    }
                    model.startTransaction();
                    OperationGenerator opGen = new OperationGenerator(model, this.mPortType, configurationMap);
                    opGen.execute();
                    
//                    SchemaImportsGenerator schemaImportGenerator = new SchemaImportsGenerator(model, configurationMap);
//                    schemaImportGenerator.execute();
                    model.endTransaction();
                }
            }
        }
        
    }

}
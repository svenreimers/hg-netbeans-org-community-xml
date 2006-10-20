/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.

 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

/*
 * Created on May 24, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.netbeans.modules.xml.wsdl.ui.view.treeeditor;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import javax.xml.namespace.QName;

import org.netbeans.modules.xml.wsdl.model.BindingFault;
import org.netbeans.modules.xml.wsdl.model.WSDLComponent;
import org.netbeans.modules.xml.wsdl.ui.commands.NamedPropertyAdapter;
import org.netbeans.modules.xml.wsdl.ui.commands.PropertyAdapter;
import org.netbeans.modules.xml.wsdl.ui.extensibility.model.WSDLExtensibilityElements;
import org.netbeans.modules.xml.wsdl.ui.view.property.BaseAttributeProperty;
import org.netbeans.modules.xml.wsdl.ui.view.treeeditor.newtype.DocumentationNewType;
import org.netbeans.modules.xml.wsdl.ui.view.treeeditor.newtype.ExtensibilityElementNewTypesFactory;
import org.netbeans.modules.xml.wsdl.ui.view.treeeditor.newtype.NewTypesFactory;
import org.openide.ErrorManager;
import org.openide.nodes.Node;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.util.datatransfer.NewType;


/**
 * @author radval
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class BindingOperationFaultNode extends WSDLExtensibilityElementNode {
    
    private BindingFault mWSDLConstruct;
    
    private BindingOperationFaultPropertyAdapter mPropertyAdapter = null;
    
    private static Image ICON  = Utilities.loadImage
            ("org/netbeans/modules/xml/wsdl/ui/view/resources/bindingfault.png");
    
    public BindingOperationFaultNode(BindingFault wsdlConstruct) {
        super(new GenericWSDLComponentChildren(wsdlConstruct), wsdlConstruct, new BindingOperationFaultNewTypesFactory());
        mWSDLConstruct = wsdlConstruct;
        
        
        this.mPropertyAdapter = new BindingOperationFaultPropertyAdapter();
        super.setNamedPropertyAdapter(this.mPropertyAdapter);
    }
    
    @Override
    public String getNameInLayer() {
        return WSDLExtensibilityElements.ELEMENT_BINDING_OPERATION_FAULT;
    }
    
    @Override
    public Image getIcon(int type) {
        return ICON;
    }
    
    @Override
    public Image getOpenedIcon(int type) {
        return ICON;
    }
    
    @Override
    protected Node.Property createAttributeProperty(QName attrQName) {
        Node.Property attrValueProperty = null;
        try {
            String attrName = attrQName.getLocalPart();
            //name
            if(attrName.equals(NAME_PROP)) { //NOT I18N
                //name
                attrValueProperty = createNameProperty();
                
            } else {
                attrValueProperty = super.createAttributeProperty(attrQName);
            }
            
        } catch(Exception ex) {
            mLogger.log(Level.SEVERE, "failed to create property sheet for "+ getWSDLComponent(), ex);
            ErrorManager.getDefault().notify(ex);
        }
        return attrValueProperty;
    }
    
    @Override
    protected List<Node.Property> createAlwaysPresentAttributeProperty() throws Exception {
        ArrayList<Node.Property> alwaysPresentAttrProperties = new ArrayList<Node.Property>();
        alwaysPresentAttrProperties.add(createNameProperty());
        
        return alwaysPresentAttrProperties;
    }
    
    
    private Node.Property createNameProperty() throws NoSuchMethodException {
        Node.Property attrValueProperty;
        attrValueProperty = new BaseAttributeProperty(mPropertyAdapter,
                String.class, NAME_PROP);
        attrValueProperty.setName(NbBundle.getMessage(BindingOperationFaultNode.class, "PROP_NAME_DISPLAYNAME"));
        attrValueProperty.setShortDescription(NbBundle.getMessage(BindingOperationFaultNode.class, "BINDINGOPERATIONFAULT_NAME_DESCRIPTION"));
        
        return attrValueProperty;
    }
    
    
    
    
    
    public class BindingOperationFaultPropertyAdapter extends PropertyAdapter implements NamedPropertyAdapter {
        
        public BindingOperationFaultPropertyAdapter() {
            super(getWSDLComponent());
        }
        
        public void setName(String name) {
            getWSDLComponent().getModel().startTransaction();
            ((BindingFault) getWSDLComponent()).setName(name);
                getWSDLComponent().getModel().endTransaction();
        }
        
        public String getName() {
            if(mWSDLConstruct.getName() == null) {
                return "";
            }
            
            return mWSDLConstruct.getName();
        }
        
        
    }
    
    public static final class BindingOperationFaultNewTypesFactory implements NewTypesFactory{
        
        public NewType[] getNewTypes(WSDLComponent def) {
            ArrayList<NewType> list = new ArrayList<NewType>();
            if (def.getDocumentation() == null) {
                list.add(new DocumentationNewType(def));
            }
            
            list.addAll(Arrays.asList(new ExtensibilityElementNewTypesFactory(WSDLExtensibilityElements.ELEMENT_BINDING_OPERATION_FAULT).getNewTypes(def)));
            return list.toArray(new NewType[]{});
        }
    }

    @Override
    public String getTypeDisplayName() {
        return NbBundle.getMessage(BindingOperationFaultNode.class, "LBL_BindingFaultNode_TypeDisplayName");
    }
}


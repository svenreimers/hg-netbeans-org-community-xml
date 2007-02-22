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
 * AnyAttributePropertyAdapter.java
 *
 * Created on April 14, 2006, 4:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.netbeans.modules.xml.wsdl.ui.commands;

import java.io.IOException;

import javax.xml.namespace.QName;

import org.netbeans.modules.xml.wsdl.model.WSDLComponent;
import org.netbeans.modules.xml.wsdl.ui.api.property.PropertyAdapter;
import org.netbeans.modules.xml.xam.dom.AbstractDocumentComponent;
import org.openide.ErrorManager;

/**
 *
 * @author radval
 */
public class AnyAttributePropertyAdapter extends PropertyAdapter {
    
    private QName mAttrQName;
    
    public AnyAttributePropertyAdapter(QName attrQName, WSDLComponent mWSDLConstruct) {
        super(mWSDLConstruct);
        this.mAttrQName = attrQName;
    }
    
    public void setValue(String value) {
        getDelegate().getModel().startTransaction();
        ((AbstractDocumentComponent)getDelegate()).setAnyAttribute(mAttrQName, value);
            getDelegate().getModel().endTransaction();
    }
    
    public String getValue() {
        String value = ((AbstractDocumentComponent)getDelegate()).getAnyAttribute(this.mAttrQName);
        if(value == null) {
            value = "";
        }
        
        return value;
    }
}

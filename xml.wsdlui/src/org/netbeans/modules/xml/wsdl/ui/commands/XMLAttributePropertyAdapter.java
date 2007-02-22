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
 * Created on Jun 24, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.netbeans.modules.xml.wsdl.ui.commands;

import java.io.IOException;

import org.netbeans.modules.xml.wsdl.model.WSDLComponent;
import org.netbeans.modules.xml.wsdl.ui.model.StringAttribute;
import org.openide.ErrorManager;


/**
 * @author radval
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class XMLAttributePropertyAdapter extends CommonAttributePropertyAdapter {
    
     private String mAttrName;
     
     public XMLAttributePropertyAdapter(String attrName, WSDLComponent mWSDLConstruct) {
         super(mWSDLConstruct);
         this.mAttrName = attrName;
     }
     
     @Override
     public void setValue(String value) {
         getDelegate().getModel().startTransaction();
         if (value == null || value.length() == 0)
             value = null;
         getDelegate().setAttribute(this.mAttrName, new StringAttribute(this.mAttrName), value);
         getDelegate().getModel().endTransaction();
         
     }
     
     @Override
     public String getValue() {
         String value = getDelegate().getAttribute(new StringAttribute(this.mAttrName));
         if(value == null) {
             value = "";
         }
         
         return value;
     }
}

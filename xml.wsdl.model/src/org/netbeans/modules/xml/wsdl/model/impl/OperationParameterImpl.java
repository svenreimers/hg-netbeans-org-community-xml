/*
 *                 Sun Public License Notice
 * 
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 * 
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.xml.wsdl.model.impl;

import org.netbeans.modules.xml.wsdl.model.Message;
import org.netbeans.modules.xml.wsdl.model.OperationParameter;
import org.netbeans.modules.xml.wsdl.model.WSDLModel;
import org.netbeans.modules.xml.xam.GlobalReference;
import org.w3c.dom.Element;

/**
 *
 * @author Nam Nguyen
 */
public abstract class OperationParameterImpl extends NamedImpl implements OperationParameter {
    
    /** Creates a new instance of OperationParameterImpl */
    public OperationParameterImpl(WSDLModel model, Element e) {
        super(model, e);
    }
    
    public GlobalReference<Message> getMessage() {
        return resolveGlobalReference(Message.class, WSDLAttribute.MESSAGE);
    }
    
    public void setMessage(GlobalReference<Message> message) {
        setGlobalReference(MESSAGE_PROPERTY, WSDLAttribute.MESSAGE, message);
    }
}

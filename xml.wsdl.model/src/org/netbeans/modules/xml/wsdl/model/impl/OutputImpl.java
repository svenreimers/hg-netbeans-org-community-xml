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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.xml.wsdl.model.impl;

import org.netbeans.modules.xml.wsdl.model.Operation;
import org.netbeans.modules.xml.wsdl.model.Output;
import org.netbeans.modules.xml.wsdl.model.RequestResponseOperation;
import org.netbeans.modules.xml.wsdl.model.SolicitResponseOperation;
import org.netbeans.modules.xml.wsdl.model.WSDLModel;
import org.netbeans.modules.xml.wsdl.model.visitor.WSDLVisitor;
import org.openide.util.NbBundle;
import org.w3c.dom.Element;

/**
 *
 * @author Nam Nguyen
 */
public class OutputImpl extends OperationParameterImpl implements Output {
    
    /** Creates a new instance of OutputImpl */
    public OutputImpl(WSDLModel model, Element e) {
        super(model, e);
    }
    
    public OutputImpl(WSDLModel model) {
        this(model, createNewElement(WSDLQNames.OUTPUT.getQName(), model));
    }
    
    public void accept(WSDLVisitor visitor) {
        visitor.visit(this);
    }

    public String getName() {
        if (super.getName() == null && getParent() != null) {
            if (getParent() instanceof RequestResponseOperation || 
                getParent() instanceof SolicitResponseOperation) {
                String suffix = NbBundle.getMessage(OutputImpl.class, "LBL_Response");
                return ((Operation)getParent()).getName()+suffix;
            } else {
                return ((Operation)getParent()).getName();
            }
        }
        return super.getName();
    }
}
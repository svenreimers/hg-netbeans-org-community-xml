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

import org.netbeans.modules.xml.wsdl.model.Input;
import org.netbeans.modules.xml.wsdl.model.Output;
import org.netbeans.modules.xml.wsdl.model.SolicitResponseOperation;
import org.netbeans.modules.xml.wsdl.model.WSDLModel;
import org.netbeans.modules.xml.wsdl.model.visitor.WSDLVisitor;
import org.w3c.dom.Element;

/**
 *
 * @author Nam Nguyen
 */
public class SolicitResponseOperationImpl extends OperationImpl implements SolicitResponseOperation {
    
    /** Creates a new instance of SolicitResponseOperationImpl */
    public SolicitResponseOperationImpl(WSDLModel model, Element e) {
        super(model, e);
    }
    
    public SolicitResponseOperationImpl(WSDLModel model) {
        this(model, createNewElement(WSDLQNames.OPERATION.getQName(), model));
    }
    
    public Output getOutput() {
        return getChild(Output.class);
    }

    public void setOutput(Output output) {
        setChildAfter(Output.class, OUTPUT_PROPERTY, output, TypeCollection.DOCUMENTATION.types());
    }

    public Input getInput() {
        return getChild(Input.class);
    }

    public void setInput(Input input) {
        setChildAfter(Input.class, INPUT_PROPERTY, input, TypeCollection.DOCUMENTATION_OUTPUT.types());
    }

    public void accept(WSDLVisitor visitor) {
        visitor.visit(this);
    }
}
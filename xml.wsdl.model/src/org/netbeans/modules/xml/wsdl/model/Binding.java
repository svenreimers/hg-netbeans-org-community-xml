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

package org.netbeans.modules.xml.wsdl.model;

import java.util.Collection;
import org.netbeans.modules.xml.xam.dom.NamedComponentReference;
import org.netbeans.modules.xml.xam.NamedReferenceable;

/**
 *
 * @author rico
 * Represents a binding in the WSDL document
 */
public interface Binding extends ReferenceableWSDLComponent {
    public static final String BINDING_OPERATION_PROPERTY = "operation";
    public static final String TYPE_PROPERTY = "type";
    
    void setType(NamedComponentReference<PortType> portType);
    NamedComponentReference<PortType> getType();
    
    void addBindingOperation(BindingOperation bindingOperation);
    void removeBindingOperation(BindingOperation bindingOperation);
    Collection<BindingOperation> getBindingOperations();
}
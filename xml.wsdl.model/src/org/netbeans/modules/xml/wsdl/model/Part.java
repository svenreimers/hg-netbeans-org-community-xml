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

import javax.xml.namespace.QName;
import org.netbeans.modules.xml.schema.model.GlobalElement;
import org.netbeans.modules.xml.schema.model.GlobalType;
import org.netbeans.modules.xml.xam.Nameable;
import org.netbeans.modules.xml.xam.dom.NamedComponentReference;

/**
 * Represents a message part in the WSDL document
 * @author rico
 * @author Nam Nguyen
 */
public interface Part extends Nameable<WSDLComponent>, ReferenceableWSDLComponent {
    public static final String ELEMENT_PROPERTY = "element";
    public static final String TYPE_PROPERTY = "type";

    /**
     * Sets the element attribute value to a GlobalReference to a schema component 
     * @param elementRef GlobalReference to a schema component
     */
    void setElement(NamedComponentReference<GlobalElement> elementRef);
    
    /**
     * Retrieves the element attribute value. The attribute value is a GlobalReference to
     * a schema component.
     */
    NamedComponentReference<GlobalElement> getElement();
    
    /**
     * Sets the type attribute value to a GlobalReference to a schema component 
     * @param typeRef GlobalReference to a schema component
     */
    void setType(NamedComponentReference<GlobalType> typeRef);
    
    /**
     * Retrieves the type attribute value. The attribute value is a GlobalReference to
     * a schema component.
     */
    NamedComponentReference<GlobalType> getType();

    /**
     * Returns string value of the attribute from different namespace.
     * If given QName has prefix, it will be ignored.
     * @param attr non-null QName represents the attribute name.
     * @return attribute value
     */
    String getAnyAttribute(QName attr);

    /**
     * Set string value of the attribute identified by given QName.
     * This will fire property change event using attribute local name.
     * @param attr non-null QName represents the attribute name.
     * @param value string value for the attribute.
     */
    void setAnyAttribute(QName attr, String value);
}

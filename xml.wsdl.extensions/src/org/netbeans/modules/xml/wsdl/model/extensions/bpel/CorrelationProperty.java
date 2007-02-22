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

package org.netbeans.modules.xml.wsdl.model.extensions.bpel;

import org.netbeans.modules.xml.schema.model.GlobalElement;
import org.netbeans.modules.xml.schema.model.GlobalType;
import org.netbeans.modules.xml.wsdl.model.ReferenceableExtensibilityElement;
import org.netbeans.modules.xml.xam.dom.NamedComponentReference;

/**
 *
 * @author rico
 * 
 * changed by
 * @author ads
 */
public interface CorrelationProperty extends ReferenceableExtensibilityElement, BPELExtensibilityComponent {
    String TYPE_PROPERTY = "type";
    String ELEMENT_PROPERTY = "element";
    
    /**
     * Type of correlation.  This should always be a simple global type.
     */
    NamedComponentReference<GlobalType> getType();
    void setType(NamedComponentReference<GlobalType> type);
    
    NamedComponentReference<GlobalElement> getElement();
    void setElement( NamedComponentReference<GlobalElement> value );
}

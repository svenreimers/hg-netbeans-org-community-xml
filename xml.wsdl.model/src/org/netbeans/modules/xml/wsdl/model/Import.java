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

/*
 * Import.java
 *
 * Created on November 11, 2005, 3:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.netbeans.modules.xml.wsdl.model;

/**
 *
 * @author rico
 * Represents an import wsdl statement to import other namespaces.
 */
public interface Import extends WSDLComponent{
    public static final String NAMESPACE_URI_PROPERTY = "namespaceURI";
    public static final String LOCATION_PROPERTY = "location";

    void setNamespaceURI(String namespaceURI);
    String getNamespaceURI();
    
    void setLocation(String locationURI);
    String getLocation();
}

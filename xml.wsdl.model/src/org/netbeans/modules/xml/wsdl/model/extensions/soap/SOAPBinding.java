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

package org.netbeans.modules.xml.wsdl.model.extensions.soap;

import org.netbeans.modules.xml.wsdl.model.ExtensibilityElement;

/**
 *
 * @author rico
 * Represents the binding element under the wsdl binding element for SOAP binding
 */
public interface SOAPBinding extends SOAPComponent {
    public static final String STYLE_PROPERTY = "style";
    public static final String TRANSPORT_URI_PROPERTY = "transportURI";
    
    Style getStyle();
    void setStyle(Style style); 
    
    String getTransportURI();
    void setTransportURI(String transportURI);

    public enum Style { 
        RPC("rpc"), DOCUMENT("document");
        
        private String tag;
        Style(String tag) {
            this.tag = tag;
        }
        public String toString() {
            return tag;
        }
    }
}
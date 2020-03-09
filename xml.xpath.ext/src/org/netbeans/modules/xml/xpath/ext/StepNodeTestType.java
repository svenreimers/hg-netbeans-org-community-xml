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

package org.netbeans.modules.xml.xpath.ext;

/**
 *
 */
public enum StepNodeTestType {
    NODETYPE_NODE("node"),  // NOI18N
    NODETYPE_TEXT("text"),  // NOI18N 
    NODETYPE_COMMENT("comment"),  // NOI18N
    NODETYPE_PI("processing-instruction");  // NOI18N
    
    private String mXPathText;
    
    StepNodeTestType(String xPathText)  {
        mXPathText = xPathText;
    }
    
    public String getXPathText() {
        return mXPathText;
    }
}

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

package org.netbeans.modules.xml.xpath.ext.impl;

import org.netbeans.modules.xml.xpath.ext.XPathModel;
import org.netbeans.modules.xml.xpath.ext.XPathNumericLiteral;
import org.netbeans.modules.xml.xpath.ext.visitor.XPathVisitor;

/**
 * Represents a numeric literal.
 * 
 * @version 
 */
public class XPathNumericLiteralImpl extends XPathLiteralImpl
    implements XPathNumericLiteral {
    
    /** The numeric literal value. */
    Number mValue;
    
    
    /**
     * Constructor. Instantiates with the given value.
     * @param value the numeric literal value
     */
    public XPathNumericLiteralImpl(XPathModel model, Number value) {
        super(model, value);
        setValue(value);
    }
    
    
    /**
     * Gets the value.
     * @return the numeric literal value
     */
    public Number getValue() {
        return mValue;
    }
    
    
    /**
     * Sets the value.
     * @param value the numeric literal value
     */
    public void setValue(Number value) {
        mValue = value;
    }
    

    /**
     * Calls the visitor.
     * @param visitor the visitor
     */
    @Override
    public void accept(XPathVisitor visitor) {
        visitor.visit(this);
    }
}

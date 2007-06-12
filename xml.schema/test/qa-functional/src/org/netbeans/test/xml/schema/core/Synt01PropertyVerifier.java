/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.

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

package org.netbeans.test.xml.schema.core;

import org.netbeans.test.xml.schema.core.sequential.PropertyVerifier;
import org.netbeans.test.xml.schema.core.lib.sequential.SequentialTestSuite;

/**
 *
 * @author ca@netbeans.org
 */
public class Synt01PropertyVerifier extends PropertyVerifier {
    
    public static junit.framework.TestSuite suite() {
        junit.framework.TestSuite testSuite = new SequentialTestSuite("Synt01 Property Verifier", new Synt01PropertyVerifier());
        
        return testSuite;
    }
    
    protected String getSchemaName() {
        return  "Synt01";
    }
    
    protected int getFirstLine() {
        return 5;
    }
    
}

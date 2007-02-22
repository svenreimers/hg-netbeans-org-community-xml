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

/*
 * TestModelSourceProvider.java
 *
 * Created on January 22, 2007, 7:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.netbeans.modules.xml.wsdl.ui;

import org.netbeans.modules.xml.wsdl.ui.extensibility.model.ModelSourceProvider;
import org.netbeans.modules.xml.xam.ModelSource;
import org.netbeans.modules.xml.xam.locator.CatalogModelException;
import org.openide.filesystems.FileObject;

/**
 *
 * @author radval
 */
public class TestModelSourceProvider implements ModelSourceProvider {
    
    /** Creates a new instance of TestModelSourceProvider */
    public TestModelSourceProvider() {
    }

    public ModelSource getModelSource(FileObject file, boolean editable) throws CatalogModelException {
        //return TestCatalogModel.getDefault().createModelSource(file, editable);
        return TestUtil.createModelSource(file, editable);
    }

}

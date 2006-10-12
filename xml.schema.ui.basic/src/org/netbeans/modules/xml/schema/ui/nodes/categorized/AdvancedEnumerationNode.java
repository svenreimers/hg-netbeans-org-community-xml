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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.xml.schema.ui.nodes.categorized;

import java.io.IOException;
import org.netbeans.modules.xml.schema.model.Enumeration;
import org.netbeans.modules.xml.schema.model.SchemaComponentReference;
import org.netbeans.modules.xml.schema.ui.nodes.SchemaUIContext;
import org.netbeans.modules.xml.schema.ui.nodes.schema.EnumerationNode;
import org.openide.ErrorManager;
import org.openide.nodes.Children;

/**
 *
 * @author  Todd Fast, todd.fast@sun.com
 */
public class AdvancedEnumerationNode extends EnumerationNode {
    /**
     *
     *
     */
    public AdvancedEnumerationNode(SchemaUIContext context,
            SchemaComponentReference<Enumeration> reference,
            Children children) {
        super(context,reference,children);
    }
    
    public void setName(String value) {
        if (isEditable()) {
            try {
                getReference().get().getModel().startTransaction();
                getReference().get().setValue(value);
            } finally {
                getReference().get().getModel().endTransaction();
            }
        }
    }
    
    @Override
    public boolean canRename() {
        return isEditable();
    }
    
    public String getHtmlDisplayName() {
        String retValue = super.getHtmlDisplayName();
        if (retValue == null) retValue = super.getDefaultDisplayName();
        return retValue+ " " + super.getHtmlTypeDisplayName();
    }
}

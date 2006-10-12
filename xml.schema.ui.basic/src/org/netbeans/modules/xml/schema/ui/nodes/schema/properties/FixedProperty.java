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

/*
 * FixedProperty.java
 *
 * Created on January 5, 2006, 3:21 PM
 *
 */

package org.netbeans.modules.xml.schema.ui.nodes.schema.properties;

import java.lang.reflect.InvocationTargetException;
import org.netbeans.modules.xml.schema.model.Element;
import org.netbeans.modules.xml.schema.model.Attribute;
import org.netbeans.modules.xml.schema.model.SchemaComponent;
import org.netbeans.modules.xml.schema.ui.basic.editors.StringEditor;
import org.netbeans.modules.xml.schema.ui.nodes.schema.GlobalElementNode;
import org.openide.ErrorManager;
import org.openide.util.NbBundle;


/**
 *
 * @author Ajit Bhate
 */
public class FixedProperty extends BaseSchemaProperty {
    
    /** Creates a new instance of FixedProperty */
    public FixedProperty(SchemaComponent component, String propName,
            String propDesc) throws NoSuchMethodException {
        super(component,String.class,Element.FIXED_PROPERTY,
                propName,propDesc,StringEditor.class);
    }
    
    public void setValue(Object o) throws 
            IllegalAccessException, InvocationTargetException{
        if(getComponent() instanceof Attribute) {
            if (((Attribute)getComponent()).getDefault()==null) {
                super.setValue(o);
            } else {
                throwError();
            }
        } else if(getComponent() instanceof Element) {
            if (((Element)getComponent()).getDefault()==null) {
                super.setValue(o);
            } else {
                throwError();
            }
        }
    }
    
    private void throwError(){
        String msg = NbBundle.getMessage(GlobalElementNode.class, "MSG_Fixed_DefaultAlreadySet"); //NOI18N
        IllegalArgumentException iae = new IllegalArgumentException(msg);
        ErrorManager.getDefault().annotate(iae, ErrorManager.USER,
                msg, msg, null, new java.util.Date());
        throw iae;
        
    }

}

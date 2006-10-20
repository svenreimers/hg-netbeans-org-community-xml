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

package org.netbeans.modules.xml.schema.abe.nodes.properties;

import java.lang.reflect.InvocationTargetException;
import org.netbeans.modules.xml.axi.Element;
import org.netbeans.modules.xml.axi.Attribute;
import org.netbeans.modules.xml.axi.AXIComponent;
import org.netbeans.modules.xml.schema.abe.nodes.ABEAbstractNode;
import org.openide.ErrorManager;
import org.openide.util.NbBundle;


/**
 *
 * @author Ayub Khan
 */
public class DefaultProperty extends BaseABENodeProperty {

    /** Creates a new instance of DefaultProperty */
    public DefaultProperty(AXIComponent component, String propName,
            String propDesc) throws NoSuchMethodException {
        super(component,String.class,Element.PROP_DEFAULT,
                propName,propDesc,StringEditor.class);
    }
    
    public void setValue(Object o) throws 
            IllegalAccessException, InvocationTargetException{
        if(getComponent() instanceof Attribute) {
            if (((Attribute)getComponent()).getFixed()==null) {
                super.setValue(o);
            } else {
                throwError();
            }
        } else if(getComponent() instanceof Element) {
            if (((Element)getComponent()).getFixed()==null) {
                super.setValue(o);
            } else {
                throwError();
            }
        }
    }
    
    private void throwError(){
        String msg = NbBundle.getMessage(ABEAbstractNode.class, "MSG_Default_FixedAlreadySet"); //NOI18N
        IllegalArgumentException iae = new IllegalArgumentException(msg);
        ErrorManager.getDefault().annotate(iae, ErrorManager.USER,
                msg, msg, null, new java.util.Date());
        throw iae;
        
    }

}
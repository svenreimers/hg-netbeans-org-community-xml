/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */

package org.netbeans.modules.xml.schema.abe.nodes.properties;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.netbeans.modules.xml.axi.AXIComponent;
import org.netbeans.modules.xml.schema.model.SchemaModel;
import org.openide.nodes.PropertySupport;

/**
 *
 * @author Ayub Khan
 */
public class NameProperty extends BaseABENodeProperty {
    
    /**
     * Creates a new instance of BaseABENodeProperty.
     *
     * @param component The schema component which property belongs to.
     * @param valueType The class type of the property.
     * @param property The property name.
     * @param propDispName The display name of the property.
     * @param propDesc Short description about the property.
     * @param propEditorClass The property editor class
     *    if the property needs special property editor.
     *    If no property editor class is provided default editor
     *    (for type of property) will be used.
     *    The property editor class must provide a default constructor.
     *    Subclasses can also override
     *    getPropertyEditor method to provide property editor.
     * @throws java.lang.NoSuchMethodException If no getter and setter for the property are found
     */
    public NameProperty(AXIComponent component,
            Class valueType,
            String property,
            String propDispName,
            String propDesc)
            throws NoSuchMethodException {
        super(component, valueType, property, propDispName, propDesc, null);
    }
    
    
    /**
     * Creates a new instance of BaseABENodeProperty.
     *
     * @param component The schema component which property belongs to.
     * @param valueType The class type of the property.
     * @param property The property name.
     * @param propDispName The display name of the property.
     * @param propDesc Short description about the property.
     * @param propEditorClass The property editor class
     *    if the property needs special property editor.
     *    If no property editor class is provided default editor
     *    (for type of property) will be used.
     *    The property editor class must provide a default constructor.
     *    Subclasses can also override
     *    getPropertyEditor method to provide property editor.
     * @throws java.lang.NoSuchMethodException If no getter and setter for the property are found
     */
    public NameProperty(Object component,
            Class valueType,
            String property,
            String propDispName,
            String propDesc)
            throws NoSuchMethodException {
        super(component, valueType, property, propDispName, propDesc, null);
    }
    
    /**
     * This api determines if this property supports resetting default value.
     * This returns true always.
     * Subclasses can override if different behaviour expected.
     */
    @Override
            public boolean supportsDefaultValue() {
        return false;
    }
    
}

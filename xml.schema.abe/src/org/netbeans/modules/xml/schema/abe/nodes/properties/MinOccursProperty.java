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
import java.lang.reflect.Method;
import org.netbeans.modules.xml.axi.AXIComponent;
import org.netbeans.modules.xml.schema.model.SchemaModel;
import org.openide.nodes.PropertySupport;

/**
 * 
 * @author Ayub Khan
 */
public class MinOccursProperty extends BaseABENodeProperty {
    
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
    public MinOccursProperty(AXIComponent component, 
            Class valueType,
            String property,
            String propDispName, 
            String propDesc)
            throws NoSuchMethodException {
        super(component, valueType, property, propDispName, propDesc, null);
    }
    
    // Methods to support restore to default
    /**
     * This api determines if this property has default value.
     * If the property value is null, its considered as default value.
     * Subclasses can override if different behaviour expected.
     * @return Returns true if the property is default value, false otherwise.
     */
    @Override
    public boolean isDefaultValue () {
        try {
            return getValue()==DEFAULT_VALUE;
        } catch (IllegalArgumentException ex) {
        } catch (InvocationTargetException ex) {
        } catch (IllegalAccessException ex) {
        }
        return false;
    }

    /**
     * This api determines if this property supports resetting default value.
     * This returns true always.
     * Subclasses can override if different behaviour expected.
     */
    @Override
    public boolean supportsDefaultValue () {
        return true;
    }

    /**
     * This api resets the property to its default value.
     * It sets property value to null which is considered as default value.
     * Subclasses can override if different behaviour expected.
     */
    @Override
    public void restoreDefaultValue () throws IllegalAccessException, InvocationTargetException {
        setValue(DEFAULT_VALUE);
    }
    
	public static final String DEFAULT_VALUE = "1";
}
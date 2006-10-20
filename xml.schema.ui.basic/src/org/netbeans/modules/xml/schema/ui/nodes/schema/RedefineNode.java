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

package org.netbeans.modules.xml.schema.ui.nodes.schema;

import java.lang.reflect.InvocationTargetException;
import org.netbeans.modules.xml.schema.model.Redefine;
import org.netbeans.modules.xml.schema.model.SchemaComponentReference;
import org.netbeans.modules.xml.schema.ui.nodes.*;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.NbBundle;
/**
 *
 * @author  Todd Fast, todd.fast@sun.com
 */
public class RedefineNode extends SchemaComponentNode<Redefine>
{
    /**
     *
     *
     */
    public RedefineNode(SchemaUIContext context, 
		SchemaComponentReference<Redefine> reference,
		Children children)
    {
        super(context,reference,children);
		setIconBaseWithExtension(
			"org/netbeans/modules/xml/schema/ui/nodes/resources/import-include-redefine.png");
    }

    @Override
    protected Sheet createSheet() 
    {
        Sheet sheet = super.createSheet();
        Sheet.Set props = sheet.get(Sheet.PROPERTIES);
        if (props == null) {
            props = Sheet.createPropertiesSet();
            sheet.put(props);
        }
		
		// Schema Location property
		Property slProp = new PropertySupport.ReadOnly(
				Redefine.SCHEMA_LOCATION_PROPERTY, String.class,
				NbBundle.getMessage(RedefineNode.class,"PROP_SchemaLocation_DisplayName"),
				NbBundle.getMessage(RedefineNode.class,"HINT_SchemaLocation_ShortDesc")
				)
		{
			public Object getValue() throws
					IllegalAccessException,InvocationTargetException
			{
				return getReference().get().getSchemaLocation();
			}
		};
		props.put(slProp);
        return sheet;
    }

    
	/**
	 *
	 *
	 */
	@Override
	public String getTypeDisplayName()
	{
		return NbBundle.getMessage(RedefineNode.class,
			"LBL_RedefineNode_TypeDisplayName"); // NOI18N
	}

    protected boolean allowReordering() {
        // Due to issue 84879, we must disallow reordering on this node,
        // as it has a mixture of nodes from this model, and ndoes from
        // the redefined schema. Someday we will address the ordering
        // of redefine's nodes by categorizing its children.
        return false;
    }
}
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

package org.netbeans.modules.xml.schema.ui.nodes.schema;

import org.netbeans.modules.xml.schema.model.ComplexContent;
import org.netbeans.modules.xml.schema.model.ComplexContentDefinition;
import org.netbeans.modules.xml.schema.model.ComplexContentRestriction;
import org.netbeans.modules.xml.schema.model.ComplexExtension;
import org.netbeans.modules.xml.schema.model.ComplexTypeDefinition;
import org.openide.nodes.Children;
import org.openide.nodes.Node.Property;
import org.openide.nodes.Sheet;
import org.openide.util.NbBundle;

import org.netbeans.modules.xml.schema.model.GlobalComplexType;
import org.netbeans.modules.xml.schema.model.GlobalType;
import org.netbeans.modules.xml.schema.model.SchemaComponentReference;
import org.netbeans.modules.xml.schema.model.SimpleContent;
import org.netbeans.modules.xml.schema.model.SimpleContentDefinition;
import org.netbeans.modules.xml.schema.model.SimpleContentRestriction;
import org.netbeans.modules.xml.schema.model.SimpleExtension;
import org.netbeans.modules.xml.schema.ui.nodes.*;
import org.netbeans.modules.xml.schema.ui.nodes.schema.properties.BooleanProperty;
import org.netbeans.modules.xml.schema.ui.nodes.schema.properties.DerivationTypeProperty;

/**
 *
 * @author  Todd Fast, todd.fast@sun.com
 */
public class GlobalComplexTypeNode extends SchemaComponentNode<GlobalComplexType>
{
    /**
     *
     *
     */
    public GlobalComplexTypeNode(SchemaUIContext context,
        SchemaComponentReference<GlobalComplexType> reference,
        Children children) {
        super(context,reference,children);
        
        setIconBaseWithExtension(
            "org/netbeans/modules/xml/schema/ui/nodes/resources/"+
            "complextype.png");
    }

    
    /**
     *
     *
     */
    @Override
	public String getTypeDisplayName() {
        return NbBundle.getMessage(GlobalComplexTypeNode.class,
            "LBL_GlobalComplexTypeNode_TypeDisplayName"); // NOI18N
    }
    
	protected GlobalType getSuperDefinition()
	{
		ComplexTypeDefinition definition = getReference().get().getDefinition();
		GlobalType gt = null;
		if(definition instanceof ComplexContent)
		{
			ComplexContentDefinition contentDef =
					((ComplexContent)definition).getLocalDefinition();
			if (contentDef instanceof ComplexContentRestriction)
			{
				ComplexContentRestriction ccr = (ComplexContentRestriction)contentDef;
				if(ccr.getBase()!= null)
				{
					gt=ccr.getBase().get();
				}
			}
			if (contentDef instanceof ComplexExtension)
			{
				ComplexExtension ce = (ComplexExtension)contentDef;
				if(ce.getBase()!= null)
				{
					gt=ce.getBase().get();
				}
			}
		}
		else if(definition instanceof SimpleContent)
		{
			SimpleContentDefinition contentDef =
					((SimpleContent)definition).getLocalDefinition();
			if (contentDef instanceof SimpleContentRestriction)
			{
				SimpleContentRestriction scr = (SimpleContentRestriction)contentDef;
				if(scr.getBase()!= null)
				{
					gt=scr.getBase().get();
				}
			}
			if (contentDef instanceof SimpleExtension)
			{
				SimpleExtension se = (SimpleExtension)contentDef;
				if(se.getBase()!= null)
				{
					gt=se.getBase().get();
				}
			}
		}
		return gt;
	}

        protected String getSuperDefinitionName()
        {
            String rawString = null;
		ComplexTypeDefinition definition = getReference().get().getDefinition();
		if(definition instanceof ComplexContent)
		{
			ComplexContentDefinition contentDef =
					((ComplexContent)definition).getLocalDefinition();
			if (contentDef instanceof ComplexContentRestriction)
			{
				ComplexContentRestriction ccr = (ComplexContentRestriction)contentDef;
				if(ccr.getBase()!= null)
				{
					rawString=ccr.getBase().getRefString();
				}
			}
			if (contentDef instanceof ComplexExtension)
			{
				ComplexExtension ce = (ComplexExtension)contentDef;
				if(ce.getBase()!= null)
				{
					rawString=ce.getBase().getRefString();
				}
			}
		}
		else if(definition instanceof SimpleContent)
		{
			SimpleContentDefinition contentDef =
					((SimpleContent)definition).getLocalDefinition();
			if (contentDef instanceof SimpleContentRestriction)
			{
				SimpleContentRestriction scr = (SimpleContentRestriction)contentDef;
				if(scr.getBase()!= null)
				{
					rawString=scr.getBase().getRefString();
				}
			}
			if (contentDef instanceof SimpleExtension)
			{
				SimpleExtension se = (SimpleExtension)contentDef;
				if(se.getBase()!= null)
				{
					rawString=se.getBase().getRefString();
				}
			}
		}
            int i = rawString!=null?rawString.indexOf(':'):-1;
            if (i != -1 && i < rawString.length()) {
                rawString = rawString.substring(i);
            }
            return rawString;
        }
        
    @Override
    protected Sheet createSheet() {
        Sheet sheet = super.createSheet();
        Sheet.Set set = sheet.get(Sheet.PROPERTIES);
        try {
            // The methods are used because the Node.Property support for 
            // netbeans doesn't recognize the is.. for boolean properties
            
            
            // Abstract property
            Property abstractProp = new BooleanProperty(
                    getReference().get(), // schema component
                    GlobalComplexType.ABSTRACT_PROPERTY, // property name
                    NbBundle.getMessage(GlobalComplexTypeNode.class,"PROP_Abstract_DisplayName"), // display name
                    NbBundle.getMessage(GlobalComplexTypeNode.class,"PROP_Abstract_ShortDescription"),	// descr
                    true // default value is false
                    );
            set.put(new SchemaModelFlushWrapper(getReference().get(), abstractProp));
            
            // Mixed property
            Property mixedProp = new BooleanProperty(
                    getReference().get(), // schema component
                    GlobalComplexType.MIXED_PROPERTY, // property name
                    NbBundle.getMessage(GlobalComplexTypeNode.class,"PROP_Mixed_DisplayName"), // display name
                    NbBundle.getMessage(GlobalComplexTypeNode.class,"PROP_Mixed_ShortDescription"),	// descr
                    true // default value is false
                    );
            set.put(new SchemaModelFlushWrapper(getReference().get(), mixedProp));
            
            // final property
            Property finalProp = new DerivationTypeProperty(
                    getReference().get(),
                    GlobalComplexType.FINAL_PROPERTY,
                    NbBundle.getMessage(GlobalComplexTypeNode.class,"PROP_Final_DisplayName"), // display name
                    NbBundle.getMessage(GlobalComplexTypeNode.class,"HINT_Final_ShortDesc"),	// descr
                    getTypeDisplayName()
                    );
            set.put(new SchemaModelFlushWrapper(getReference().get(), finalProp));
            
            // block property
            Property blockProp = new DerivationTypeProperty(
                    getReference().get(),
                    GlobalComplexType.BLOCK_PROPERTY,
                    NbBundle.getMessage(GlobalComplexTypeNode.class,"PROP_Block_DisplayName"), // display name
                    NbBundle.getMessage(GlobalComplexTypeNode.class,"HINT_Block_ShortDesc"),	// descr
                    getTypeDisplayName()
                    );
            set.put(new SchemaModelFlushWrapper(getReference().get(), blockProp));
        } catch (NoSuchMethodException nsme) {
            assert false : "properties should be defined";
        }
        
        return sheet;
    }

}

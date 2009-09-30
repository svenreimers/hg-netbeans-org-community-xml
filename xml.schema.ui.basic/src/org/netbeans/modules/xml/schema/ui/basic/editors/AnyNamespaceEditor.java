/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2009 Sun Microsystems, Inc. All rights reserved.
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
 * nbbuild/licenses/CDDL-GPL-2-CP.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the GPL Version 2 section of the License file that
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

/*
 * AnyNamespaceEditor.java
 *
 * Created on December 22, 2005, 12:58 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.netbeans.modules.xml.schema.ui.basic.editors;

import java.beans.FeatureDescriptor;
import java.beans.PropertyEditorSupport;
import org.openide.explorer.propertysheet.ExPropertyEditor;
import org.openide.explorer.propertysheet.PropertyEnv;
import org.openide.util.NbBundle;

/**
 *
 * @author Ajit Bhate
 *
 */
public class AnyNamespaceEditor  extends PropertyEditorSupport
        implements ExPropertyEditor{
		
	/**
     * Creates a new instance of AnyNamespaceEditor
     */
	public AnyNamespaceEditor() {
	}
	
	public String[] getTags() {
		return new String[] {NbBundle.getMessage(BooleanDefaultFalseEditor.class,"LBL_ANY"),
            NbBundle.getMessage(BooleanDefaultFalseEditor.class,"LBL_Other"),
            NbBundle.getMessage(BooleanDefaultFalseEditor.class,"LBL_TargetNamespace"),
			NbBundle.getMessage(BooleanDefaultFalseEditor.class,"LBL_Local")};
	}

	public void setAsText(String text) throws IllegalArgumentException {
		if (text.equalsIgnoreCase(NbBundle.getMessage(BooleanDefaultFalseEditor.class,
                "LBL_ANY"))){
			setValue("##any");
		}
		else if (text.equalsIgnoreCase(NbBundle.getMessage(BooleanDefaultFalseEditor.class,
                "LBL_Other"))){
			setValue("##other");
		}
		else if (text.equalsIgnoreCase(NbBundle.getMessage(BooleanDefaultFalseEditor.class,
                "LBL_TargetNamespace"))){
			setValue("##targetNamespace");
		}
		else if (text.equalsIgnoreCase(NbBundle.getMessage(BooleanDefaultFalseEditor.class,
                "LBL_Local"))){
			setValue("##local");
		}
		else {
			setValue(text);
		}
		
	}

	public String getAsText() {
		Object obj = getValue();
		if (obj == null){
			return NbBundle.getMessage(BooleanDefaultFalseEditor.class,"LBL_ANY");
		}
		if (obj instanceof String){
            String  val = (String)obj;
            if(val.equals("##any"))
                return NbBundle.getMessage(BooleanDefaultFalseEditor.class,"LBL_ANY");
            if(val.equals("##other"))
                return NbBundle.getMessage(BooleanDefaultFalseEditor.class,"LBL_Other");
            if(val.equals("##targetNamespace"))
                return NbBundle.getMessage(BooleanDefaultFalseEditor.class,"LBL_TargetNamespace");
            if(val.equals("##local"))
                return NbBundle.getMessage(BooleanDefaultFalseEditor.class,"LBL_Local");
            return val;
		}
		// TODO how to display invalid values?
		return NbBundle.getMessage(BooleanDefaultFalseEditor.class,"LBL_ANY");
	}

    /**
     *
     *  implement ExPropertyEditor
     *
     */
    public void attachEnv(PropertyEnv env ) {
        FeatureDescriptor desc = env.getFeatureDescriptor();
        // make this an editable combo tagged editor  
        desc.setValue("canEditAsText", Boolean.TRUE); // NOI18N
    }
}

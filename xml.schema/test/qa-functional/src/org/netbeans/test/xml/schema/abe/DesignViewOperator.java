/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2009-2017 Oracle and/or its affiliates. All rights reserved.
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
 * Software is Sun Microsystems, Inc. Portions Copyright 2009-2010 Sun
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
package org.netbeans.test.xml.schema.abe;

import java.awt.event.KeyEvent;
import org.netbeans.test.xml.schema.lib.*;
import org.netbeans.jellytools.TopComponentOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;

/**
 *
 * @author Mikhail Matveev
 */
public class DesignViewOperator extends TopComponentOperator{
    
    private DVGenericNodeOperator m_elementsRoot;    
    private DVGenericNodeOperator m_complexTypesRoot;
    private DVNamespaceOperator m_namespace;
    
    public static final String defaultElementName="newElement";
    public static final String defaultComplexTypeName="newComplexType";
    public static final String defaultAttributeName="newComplexType";

    public static DesignViewOperator createDesignViewOperator(String schemaName){
        return new DesignViewOperator(new SchemaMultiView(schemaName).getTopComponentOperator());        
    }
    
    public DesignViewOperator(TopComponentOperator op){
        super(op);
        m_namespace=new DVNamespaceOperator(this);
        m_complexTypesRoot=new DVGenericNodeOperator(this, "Complex Types");
        m_elementsRoot=new DVGenericNodeOperator(this, "Elements");        
    }
    
    public DVGenericNodeOperator getElementsRoot(){
        return m_elementsRoot;        
    }

    public DVGenericNodeOperator getComplexTypesRoot(){
        return m_complexTypesRoot;        
    }
    
    public DVNamespaceOperator getNameSpace(){
        return m_namespace;
    }
    
    public DVNodeOperator addElement(String name){
        return getNameSpace().addElement(name);
    }    
    
    public DVNodeOperator addComplexType(String name){
        getNameSpace().clickForPopupRobot();
        new JPopupMenuOperator().pushMenu("Add|Complex Type");
        DVNodeOperator res=new DVNodeOperator(this, defaultComplexTypeName, true, true);
        res.getLabel().setText(name);
        pushKey(KeyEvent.VK_ENTER);
        return res;
    }    
    
}

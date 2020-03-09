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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.test.xml.schema.abe;

import org.netbeans.test.xml.schema.*;
import java.awt.Container;
import java.awt.event.KeyEvent;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.netbeans.modules.xml.schema.abe.ElementPanel;
import org.netbeans.test.xml.schema.lib.util.Helpers;

/**
 *
 */
public class DVNodeOperator extends DVGenericNodeOperator{
    
    private ElementPanel m_panel=null;
    
    public DVNodeOperator(Container comp){
        super(comp);
        m_panel=(ElementPanel)comp;
    }
    
    public DVNodeOperator(ContainerOperator parent, String text){
        
        this(parent,text,null);
    }
    
    public DVNodeOperator(ContainerOperator parent, String text, boolean ce, boolean ccs){
        
        this(parent,text,new DefaultStringComparator(ce,ccs));
    }    
    
    public DVNodeOperator(ContainerOperator parent, String text, StringComparator comparator){
        
	super(parent,text,comparator);
        m_panel=(ElementPanel)m_label.getSource().getParent().getParent();
    }    
    
    public boolean isExpanded() {
        return m_panel.isExpanded();
    }
    
   public void setExpanded(boolean value) {
        m_panel.setExpanded(value);
        if (value){
            m_panel.expandChild();
        }
    }

    public ElementPanel getElementPanel() {
        return m_panel;
    }
    
    public void renameRefactor(String name){
        clickForPopupRobot();
        Helpers.waitNoEvent();
        new JPopupMenuOperator().pushMenuNoBlock("Refactor|Rename...");
        Helpers.waitNoEvent();
        new RefactoringDialogOperator(name).refactorImmediately();        
    }
    
    public void deleteRefactor(){
        clickForPopupRobot();
        Helpers.waitNoEvent();
        new JPopupMenuOperator().pushMenuNoBlock("Refactor|Safe Delete...");
        Helpers.waitNoEvent();
        new RefactoringDialogOperator(null).refactorImmediately();        
    }    

    public void delete(){
        clickForPopupRobot();
        Helpers.waitNoEvent();
        new JPopupMenuOperator().pushMenuNoBlock("Delete");
        Helpers.waitNoEvent();
        new RefactoringDialogOperator(null).refactorImmediately();        
    }    
    
    public DVGenericNodeOperator addAttribute(String name){
        clickForPopupRobot();
        new JPopupMenuOperator().pushMenu("Add|Attribute");
        
        DVGenericNodeOperator res=new DVGenericNodeOperator(this, DesignViewOperator.defaultAttributeName, true, true);
        res.getLabel().setText(name);
        res.pushKey(KeyEvent.VK_ENTER);
        return res;
    }    
    
    
}

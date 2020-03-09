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

import java.awt.Container;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import org.netbeans.jemmy.Timeout;
import org.netbeans.jemmy.drivers.input.MouseRobotDriver;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JLabelOperator;
import org.netbeans.jemmy.operators.JLabelOperator.JLabelByLabelFinder;
import org.netbeans.jemmy.operators.JPopupMenuOperator;

/**
 *
 */
public class DVGenericNodeOperator extends ContainerOperator{
    
    protected JLabelOperator m_label=null;
    protected MouseRobotDriver drv=new MouseRobotDriver(new Timeout("",  50));
    
    public DVGenericNodeOperator(Container comp){
        super(comp);
        m_label=new JLabelOperator((JLabel)waitComponent(comp, new JLabelByLabelFinder(comp.getName(), getComparator()),0));
    }
    
    public DVGenericNodeOperator(ContainerOperator parent, String text){
        
        this(parent,text,null);
    }
    
    public DVGenericNodeOperator(ContainerOperator parent, String text, boolean ce, boolean ccs){
        
        this(parent,text,new DefaultStringComparator(ce,ccs));
    }    
    
    public DVGenericNodeOperator(ContainerOperator parent, String text, StringComparator comparator){
        
	super(waitComponent(parent, new JLabelByLabelFinder(text, comparator==null?parent.getComparator():comparator),0).getParent().getParent());
        m_label=new JLabelOperator((JLabel)waitComponent(this, new JLabelByLabelFinder(text, this.getComparator()),0));
	copyEnvironment(parent);        
    }    
    
    public JLabelOperator getLabel(){
        return m_label;
    }
    
    public void clickMouseRobot(){
        drv.clickMouse(m_label, m_label.getCenterXForClick(), m_label.getCenterYForClick(), 1, InputEvent.BUTTON1_MASK, 0, new Timeout("",  30));
    }    
    
    public void dblClickMouseRobot(){
        drv.clickMouse(m_label, m_label.getCenterXForClick(), m_label.getCenterYForClick(), 2, InputEvent.BUTTON1_MASK, 0, new Timeout("",  30));
    }        
    
    public void clickForPopupRobot(){
        drv.clickMouse(m_label, m_label.getCenterXForClick(), m_label.getCenterYForClick(), 1, InputEvent.BUTTON3_MASK, 0, new Timeout("",  30));
    }
    
    public void renameInplace(String name){
        dblClickMouseRobot();
        m_label.setText(name);
    }
    
    public DVNodeOperator addElement(String name){
        clickForPopupRobot();
        new JPopupMenuOperator().pushMenu("Add|Element");
        DVNodeOperator res=new DVNodeOperator(this, DesignViewOperator.defaultElementName, true, true);
        res.getLabel().setText(name);
        res.pushKey(KeyEvent.VK_ENTER);
        return res;
    }    
    
}

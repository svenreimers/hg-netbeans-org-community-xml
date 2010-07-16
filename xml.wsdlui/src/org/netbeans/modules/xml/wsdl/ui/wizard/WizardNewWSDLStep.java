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

package org.netbeans.modules.xml.wsdl.ui.wizard;

import java.awt.Component;

import javax.swing.event.ChangeListener;

import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

/**
 *
 * @author radval
 */
public class WizardNewWSDLStep implements WizardDescriptor.FinishablePanel {
    private WizardDescriptor.Panel mPanel;

    private WsdlPanel bottomPanel;
    
    public WizardNewWSDLStep(WizardDescriptor.Panel panel, WsdlPanel bottomPanel) {
        this.mPanel = panel;
        this.bottomPanel = bottomPanel;
    }
    
    public boolean isFinishPanel() {
        return bottomPanel.isFinishPanel();
    }

    public Component getComponent() {
        Component comp = mPanel.getComponent();
        return comp;
    }
    
    public String getName() {
        return getComponent().getName();
    }

    public HelpCtx getHelp() {
        return mPanel.getHelp();
    }

    public void readSettings(Object settings) {
        mPanel.readSettings(settings);
    }

    public void storeSettings(Object settings) {
    	//hack calling mPanel.storeSettings null out the title
    	String title = (String) ((WizardDescriptor)settings).getProperty ("NewFileWizard_Title"); // NOI18N
        mPanel.storeSettings(settings);
        ((WizardDescriptor)settings).putProperty ("NewFileWizard_Title", title); // NOI18N
    }

    public boolean isValid() {
        return mPanel.isValid();
    }

    public void addChangeListener(ChangeListener l) {
        mPanel.addChangeListener(l);
    }

    public void removeChangeListener(ChangeListener l) {
        mPanel.removeChangeListener(l);
    }
    
}

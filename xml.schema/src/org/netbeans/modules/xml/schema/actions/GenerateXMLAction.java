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
package org.netbeans.modules.xml.schema.actions;

import org.netbeans.modules.xml.schema.SchemaDataObject;
import org.netbeans.modules.xml.schema.ui.basic.SchemaModelCookie;
import org.netbeans.modules.xml.schema.wizard.SampleXMLGeneratorWizardIterator;
import org.netbeans.modules.xml.wizard.SchemaParser;
import org.openide.DialogDisplayer;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import org.openide.NotifyDescriptor;

public final class GenerateXMLAction extends CookieAction {
    
    private static final Class[] COOKIE_ARRAY =
            new Class[] {SchemaModelCookie.class};

    protected void performAction(Node[] activatedNodes) {
         assert activatedNodes.length==1:
            "Length of nodes array should be 1";
        
         if(activatedNodes[0] == null)
             return;
         
         SchemaDataObject sdo = activatedNodes[0].getCookie(SchemaDataObject.class);
         if(sdo == null)
             return;
         
         if(SchemaParser.getRootElements(sdo.getPrimaryFile()).roots.size() == 0) {
             //no root elements; cannot generate XML
            NotifyDescriptor desc = new NotifyDescriptor.Message
                                    (NbBundle.getMessage(GenerateXMLAction.class, "MSG_cannot_generate_XML_file"), NotifyDescriptor.ERROR_MESSAGE);
            DialogDisplayer.getDefault().notify (desc);
            return;
         }
        
         SampleXMLGeneratorWizardIterator wizard = new SampleXMLGeneratorWizardIterator(sdo);
         wizard.show();
               
    }

    protected int mode() {
        return CookieAction.MODE_EXACTLY_ONE;
    }

    public String getName() {
        return NbBundle.getMessage(GenerateXMLAction.class, "CTL_GenerateXMLAction");
    }

    protected Class[] cookieClasses() {
        return COOKIE_ARRAY;
    }

    @Override
    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() Javadoc for more details
        putValue("noIconInMenu", Boolean.TRUE);
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }
}


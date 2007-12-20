/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 * 
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
 */


package org.netbeans.modules.xml.wsdl.ui.view.treeeditor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.Action;

import org.netbeans.modules.xml.wsdl.model.Input;
import org.openide.actions.DeleteAction;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.util.actions.SystemAction;



/**
 *
 * @author Ritesh Adval
 *
 */
public class OperationInputNode extends OperationParameterNode<Input> {

    
    public OperationInputNode(Input wsdlConstruct) {
        super(wsdlConstruct);
        ICON  =Utilities.loadImage
        ("org/netbeans/modules/xml/wsdl/ui/view/resources/input.png");
    }
    
    @Override
    public String getTypeDisplayName() {
        return NbBundle.getMessage(OperationInputNode.class, "LBL_OperationInputNode_TypeDisplayName");
    }
    
    @Override
    public Action[] getActions(boolean context) {
        ArrayList<Action> actionsList = new ArrayList<Action>();
        Collections.addAll(actionsList, super.getActions(context));
        actionsList.remove(SystemAction.get(DeleteAction.class));
        return actionsList.toArray(new Action[actionsList.size()]);
        
    }
    
    @Override
    public boolean canDestroy() {
       return false;
    }
    
}

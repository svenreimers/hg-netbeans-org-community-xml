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

package org.netbeans.modules.xml.schema.actions;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import org.netbeans.modules.xml.axi.AXIModel;
import org.netbeans.modules.xml.axi.AXIModelFactory;
import org.netbeans.modules.xml.schema.abe.wizard.SchemaTransformWizard;
import org.netbeans.modules.xml.schema.SchemaDataObject;
import org.netbeans.modules.xml.schema.SchemaEditorSupport;
import org.netbeans.modules.xml.schema.model.SchemaModel;
import org.netbeans.modules.xml.schema.ui.basic.SchemaModelCookie;
import org.netbeans.modules.xml.xam.ui.XAMUtils;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;

/**
 * An action on the SchemaDataObject node (SchemaNode)
 * to "Transform" the schema (from one design pattern to another)
 *
 * @author Ayub Khan
 */
public class SchemaTransformAction extends CookieAction {
    private static final long serialVersionUID = 1L;
    
    private static final Class[] COOKIE_ARRAY =
            new Class[] {SchemaModelCookie.class};
    
    private static final String EMPTY_DOC =
            "<schema xmlns=\"http://www.w3.org/2001/XMLSchema\"/>";
    
    SchemaDataObject sdo = null;
    
    /** Creates a new instance of SchemaViewOpenAction */
    public SchemaTransformAction() {
        putValue("noIconInMenu", Boolean.TRUE); // NOI18N
    }
    
    protected void performAction(Node[] node) {
        assert node.length==1:
            "Length of nodes array should be 1";
        try {
            //at this point forceResetDoc
            SchemaModel sm = getSchemaModel(node, true);
            if(sm != null) {
                AXIModel am = AXIModelFactory.getDefault().getModel(sm);
                SchemaTransformWizard wizard = new SchemaTransformWizard(sm);
                wizard.show();
                if(!wizard.isCancelled() && sdo != null)
                    sdo.setModified(true);
            }
        } catch (IOException ex) {
//            ex.printStackTrace();
        }
    }
    
    private SchemaModel getSchemaModel(final Node[] node, boolean forceResetDoc) throws IOException {
        if(node == null || node.length == 0 || node[0] == null)
            return null;
        
        sdo = node[0].getLookup().lookup(
                SchemaDataObject.class);
        if (sdo != null){
            SchemaEditorSupport editor = sdo.getSchemaEditorSupport();
            SchemaModel model = null;
            if(editor != null) {
                model = editor.getModel();
                StyledDocument doc = editor.getDocument();
                //Do this only when the document is not opened
                if(forceResetDoc && 
                        model != null && doc != null && 
                            (editor.getOpenedPanes() == null || 
                                editor.getOpenedPanes().length == 0)) {
                    try {
                        // Reset the model state by forcing it to sync again.
                        boolean saveState = sdo.isModified();
                        String saved = doc.getText(0, doc.getLength());
                        String emptyDoc = EMPTY_DOC;
                        if(saved != null) {
                            int schemaStart = saved.indexOf("schema");
                            int schemaEnd = saved.lastIndexOf("schema");
                            if(schemaStart != -1 && schemaEnd != -1) {
                                int ss = saved.indexOf(">", schemaStart);                                
                                emptyDoc = saved.substring(0, ss+1);
                                emptyDoc += "\n";
                                int se = saved.lastIndexOf("<", schemaEnd);
                                emptyDoc += saved.substring(se, saved.length());
                            }
                        }
                        // Remove undo manager as a listener (IZ# 96476).
                        boolean undoValue = editor.suspendUndoRedo();
                        doc.remove(0, doc.getLength());
                        doc.insertString(0, emptyDoc, null);
                        model.sync();
                        doc.remove(0, doc.getLength());
                        doc.insertString(0, saved, null);
                        model.sync();
                        AXIModel am = AXIModelFactory.getDefault().getModel(model);
                        am.sync();
                        sdo.setModified(saveState);
                        editor.resumeUndoRedo(undoValue);
                    } catch(BadLocationException e) {
                        Logger.getLogger(SchemaTransformAction.class.getName()).log(
                                Level.FINE, "forceResetDocument", e);
                    } catch(IOException e) {
                        Logger.getLogger(SchemaTransformAction.class.getName()).log(
                                Level.FINE, "forceResetDocument", e);
                    }
                }
            }
            return model;
        }
        return null;
    }
        
    public String getName() {
        return NbBundle.getMessage(
                SchemaViewOpenAction.class, "LBL_ApplyDesignPattern_Name");
    }
    
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }
    
    protected boolean asynchronous() {
        
        return false;
    }
    
    protected int mode() {
        return CookieAction.MODE_EXACTLY_ONE;
    }
    
    protected Class[] cookieClasses() {
        return COOKIE_ARRAY;
    }
}
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
 * WSDLRefactoringElement.java
 *
 * Created on December 30, 2006, 4:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.netbeans.modules.xml.wsdl.refactoring;

import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.text.Document;
import javax.swing.text.Position.Bias;
import org.netbeans.modules.refactoring.spi.SimpleRefactoringElementImplementation;
import org.netbeans.modules.refactoring.spi.ui.TreeElement;
import org.netbeans.modules.refactoring.spi.ui.TreeElementFactory;
import org.netbeans.modules.xml.refactoring.XMLRefactoringTransaction;
import org.netbeans.modules.xml.refactoring.spi.SharedUtils;
import org.netbeans.modules.xml.schema.model.SchemaComponent;
import org.netbeans.modules.xml.wsdl.model.WSDLModel;
import org.netbeans.modules.xml.wsdl.ui.netbeans.module.WSDLDataObject;
import org.netbeans.modules.xml.wsdl.ui.view.treeeditor.NodesFactory;
import org.netbeans.modules.xml.xam.Component;
import org.netbeans.modules.xml.xam.Model;
import org.netbeans.modules.xml.xam.ModelSource;
import org.netbeans.modules.xml.xam.Referenceable;
import org.netbeans.modules.xml.xam.dom.AbstractDocumentModel;
import org.netbeans.modules.xml.xam.dom.DocumentComponent;
import org.netbeans.modules.xml.xam.dom.DocumentModelAccess;
import org.netbeans.modules.xml.xam.ui.actions.ShowSourceAction;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.text.CloneableEditorSupport;
import org.openide.text.PositionBounds;
import org.openide.text.PositionRef;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.Lookups;
import org.w3c.dom.Element;

/**
 *
 */
public class WSDLRefactoringElement extends SimpleRefactoringElementImplementation {
    
    private FileObject source;
    Model model;
    Component comp;
    Node node;
    XMLRefactoringTransaction transaction;
    /**
     * Creates a new instance of WSDLRefactoringElement
     */
    public WSDLRefactoringElement(Model model, Referenceable target, Component comp) {
        this.model=model;
        this.comp=comp;
        
        try {
            if(model instanceof WSDLModel) {
                ModelSource ms = model.getModelSource();
                source = (FileObject) ms.getLookup().lookup(FileObject.class);
                    if(source != null) {
                        DataObject dObj = DataObject.find(source);
                        if(dObj != null && dObj instanceof WSDLDataObject) {
                            this.node = NodesFactory.getInstance().create(comp);
                        } 
                }
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }

    public Lookup getLookup() {
       return Lookups.singleton(comp);
       
    }

    public FileObject getParentFile() {
       if (source == null) {
            assert model != null : "Invalid object, expecting non-null model."; //NOI18N
            source = (FileObject)model.getModelSource().getLookup().lookup(FileObject.class);
            assert source != null : "ModelSource should have FileObject in lookup"; //NOI18N
        }
        return source;
    }

    public String getText() {
        if(node == null){
            //in case of embedded SchemaComponent, use the TreeElementFactory as the UIHelper 
           TreeElement elem = TreeElementFactory.getTreeElement(comp);
           return elem.getText(true);
        }
        return node.getName();
    }

    public String getDisplayText() {
        if(node == null){
            //in case of embedded Schema, the component is a SchemaComponent
            //The NodeFactory returns a null node. use the TreeElementFactory as the UIHelper 
            // to locate the factory that handles schema components
           TreeElement elem = TreeElementFactory.getTreeElement(comp);
           return elem.getText(true);
        }
        return node.getHtmlDisplayName();
    }

    public void performChange() {
    }

   public PositionBounds getPosition() {
      if(comp.getModel() instanceof AbstractDocumentModel ) {
          DocumentModelAccess docAcc =null;
          Element elem = null;
          int startPos = 0;
          if(comp instanceof SchemaComponent)
              docAcc = ((AbstractDocumentModel)model).getAccess();
          else
              docAcc  = ((AbstractDocumentModel)comp.getModel()).getAccess();
             
          elem = ((DocumentComponent)comp).getPeer();
          startPos = ((DocumentComponent)comp).findPosition();
           
          String txt = docAcc.getXmlFragmentInclusive(elem);
          int endPos = startPos + txt.length();
          DataObject dob = null;
          try {
              FileObject source = (FileObject)comp.getModel().getModelSource().getLookup().lookup(FileObject.class);
              dob = DataObject.find(source);
           } catch (DataObjectNotFoundException ex) {
              ex.printStackTrace();
           }
           CloneableEditorSupport ces = SharedUtils.findCloneableEditorSupport(dob);
           if(ces == null)
                return null;
        
           PositionRef ref1 = ces.createPositionRef(startPos, Bias.Forward);
           PositionRef ref2 = ces.createPositionRef(endPos, Bias.Forward);
           PositionBounds bounds = new PositionBounds(ref1, ref2);
       
           return bounds;
       }else 
           return null;
       
    }
    
    public void openInEditor(){
         Action preferredAction = SystemAction.get(ShowSourceAction.class);
         String command = (String)preferredAction.getValue(Action.ACTION_COMMAND_KEY);
         if(node == null) {
             node  = new AbstractNode(Children.LEAF);
         }
         ActionEvent ae = new ActionEvent(node, 0, command);
         preferredAction.actionPerformed(ae);
     }
    
    void addTransactionObject(XMLRefactoringTransaction transaction) {
        this.transaction = transaction;
    }
    
    protected String getNewFileContent() {
       if(comp.getModel() instanceof AbstractDocumentModel && transaction != null) {
             try {
                 
                String refactoredString = transaction.refactorForPreview(comp.getModel());
                return refactoredString;
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
               return null;
           }
         }
         return null;
    }
}

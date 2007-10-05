/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
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

package org.netbeans.modules.xml.wsdl.ui.view.treeeditor;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.netbeans.modules.xml.schema.model.Schema;
import org.netbeans.modules.xml.schema.model.SchemaModel;
import org.netbeans.modules.xml.schema.ui.nodes.SchemaNodeFactory;
import org.netbeans.modules.xml.schema.ui.nodes.categorized.CategorizedSchemaNodeFactory;
import org.netbeans.modules.xml.wsdl.model.Import;
import org.netbeans.modules.xml.wsdl.model.WSDLComponent;
import org.netbeans.modules.xml.wsdl.ui.actions.ActionHelper;
import org.netbeans.modules.xml.wsdl.ui.cookies.DataObjectCookieDelegate;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.util.lookup.InstanceContent;



/**
 * @author radval
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class XSDImportNode extends ImportNode {

    Image ICON  = Utilities.loadImage
            ("org/netbeans/modules/xml/wsdl/ui/view/resources/import-include-redefine.png");

    public XSDImportNode(Import wsdlConstruct) {
        super(new XSDImportNodeChildren(wsdlConstruct),
                wsdlConstruct);
    }


    @Override
    public Image getIcon(int type) {
        return ICON;
    }

    @Override
    public Image getOpenedIcon(int type) {
        return ICON;
    }

    @Override
    public String getTypeDisplayName() {
        return NbBundle.getMessage(XSDImportNode.class, "LBL_XSDImportNode_TypeDisplayName");
    }

    @Override
    protected void updateDisplayName() {
        Import imp = getWSDLComponent();
        setDisplayName(imp.getLocation());
    }

    public static class XSDImportNodeChildren extends RefreshableChildren {

        private Import imp;
        public XSDImportNodeChildren(Import wsdlConstruct) {
            super();
            imp = wsdlConstruct;
        }

        @Override
        protected Node[] createNodes(Object key) {
            if (key instanceof Schema) {
                Schema schema = (Schema) key;
                SchemaModel model = schema.getModel();
                SchemaNodeFactory factory = new CategorizedSchemaNodeFactory(
                        model, Lookup.EMPTY);
                Node node = factory.createRootNode();
                
                //To enable save, when schema nodes are selected, get the save cookie.
                List<Object> list = new ArrayList<Object>();
                list.add(new DataObjectCookieDelegate(ActionHelper.getDataObject(imp)));
                node.setDisplayName(imp.getNamespace());
                node = new ReadOnlyNode(node, new InstanceContent(), list);
                return new Node[] { node };
            } else if (key instanceof WSDLComponent) {
                Node node = NodesFactory.getInstance().create((WSDLComponent) key);
                if (node != null)
                    return new Node[]{node};
            }
            return null;
        }

        @Override
        public final Collection getKeys() {
            ArrayList keys = new ArrayList();
            List list = imp.getModel().findSchemas(imp.getNamespace());
            if (list != null && list.size() > 0) {
                Schema schema = (Schema) list.get(0);
                if (schema != null) {
                    keys.add(schema);
                }
            }
            keys.addAll(imp.getChildren());
            return keys;
        }
    }
    
    @Override
    public boolean hasChildren() {
        ArrayList keys = new ArrayList();
        List list = getWSDLComponent().getModel().findSchemas(getWSDLComponent().getNamespace());
        if (list != null && list.size() > 0) {
            Schema schema = (Schema) list.get(0);
            if (schema != null) {
                return true;
            }
        }
        return super.hasChildren();
    }
}

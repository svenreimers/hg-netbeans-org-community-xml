/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.

 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.xml.schema.ui.basic.navigator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.netbeans.modules.xml.schema.model.SchemaComponent;
import org.netbeans.modules.xml.schema.model.SchemaModel;
import org.netbeans.modules.xml.schema.ui.basic.SchemaModelCookie;
import org.netbeans.modules.xml.schema.ui.basic.UIUtilities;
import org.netbeans.modules.xml.schema.ui.nodes.DefaultExpandedCookie;
import org.netbeans.modules.xml.schema.ui.nodes.SchemaNodeFactory;
import org.netbeans.modules.xml.schema.ui.nodes.ReadOnlyCookie;
import org.netbeans.modules.xml.schema.ui.nodes.categorized.CategorizedSchemaNodeFactory;
import org.netbeans.modules.xml.xam.Model.State;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.BeanTreeView;
import org.openide.explorer.view.TreeView;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.lookup.Lookups;
import org.openide.windows.TopComponent;

/**
 * XML Schema Navigator component containing a tree of schema components.
 *
 * @author  Nathan Fiedler
 */
public class SchemaNavigatorContent extends JPanel
        implements ExplorerManager.Provider, Runnable, PropertyChangeListener {
    /** silence compiler warnings */
    private static final long serialVersionUID = 1L;
    /** The lookup for our component tree. */
    private static Lookup lookup;
    /** Explorer manager for the tree view. */
    private ExplorerManager explorerManager;
    /** Our schema component node tree view. */
    private TreeView treeView;
    /** Indicates that the tree view is not in the component hierarchy. */
    private boolean treeInHierarchy;
    /** indicator that currently listening to topcomponent.registry.activatednodes **/
    private boolean listeningOnActivatedNodes = false;
    private final javax.swing.JLabel notAvailableLabel = new javax.swing.JLabel(
            NbBundle.getMessage(SchemaNavigatorContent.class, "MSG_NotAvailable")); //NOI18N
    
    static {
        // Present a read-only view of the schema components.
        lookup = Lookups.singleton(new ReadOnlyCookie(true));
    }
    
    /**
     * Creates a new instance of SchemaNavigatorContent.
     */
    public SchemaNavigatorContent() {
        setLayout(new BorderLayout());
        explorerManager = new ExplorerManager();
        treeView = new BeanTreeView();
        explorerManager.addPropertyChangeListener(this);
        //initialize the notAvailableLabel
        notAvailableLabel.setHorizontalAlignment(SwingConstants.CENTER);
        notAvailableLabel.setEnabled(false);
        Color usualWindowBkg = UIManager.getColor("window"); //NOI18N
        notAvailableLabel.setBackground(usualWindowBkg != null ? usualWindowBkg : Color.white);
        // to ensure our background color will have effect
        notAvailableLabel.setOpaque(true);
    }
    
    /**
     * Expand the nodes which should be expanded by default.
     */
    protected void expandDefaultNodes() {
        Node rootNode = getExplorerManager().getRootContext();
        // Need to prevent looping on malformed trees, so avoid going too
        // deep when expanding the children of nodes with only one child.
        int depth = 0;
        do {
            Node[] children = rootNode.getChildren().getNodes();
            if (children.length == 1) {
                // Expand all nodes that have only a single child.
                treeView.expandNode(children[0]);
                rootNode = children[0];
                depth++;
            } else {
                // Expand all first-level children that are meant to be shown
                // expanded by default.
                for (Node child : children) {
                    DefaultExpandedCookie cookie = (DefaultExpandedCookie)
                    child.getCookie(DefaultExpandedCookie.class);
                    if (cookie != null && cookie.isDefaultExpanded()) {
                        treeView.expandNode(child);
                    }
                }
                rootNode = null;
            }
        } while (rootNode != null && depth < 5);
        
        // The following code addresses two issues:
        //
        // 1. When viewing large schemas, expanding the default set of nodes
        //    generally means that the contents of the column are so long that
        //    copious amounts of scrolling are necessary to see it all. This is
        //    not desirable for the user's first experience with the document.
        //
        // 2. Because BasicTreeUI essentially ignores the scrollsOnExpand
        //    setting (or at least it does not work as documented), the tree
        //    is left scrolled to some random position.
        //
        // So, if scrolling is necessary, then collapse root's children.
        JTree tree = (JTree) treeView.getViewport().getView();
        if (tree.getRowCount() > tree.getVisibleRowCount()) {
            rootNode = getExplorerManager().getRootContext();
            Enumeration kids = rootNode.getChildren().nodes();
            while (kids.hasMoreElements()) {
                Node kid = (Node) kids.nextElement();
                treeView.collapseNode(kid);
            }
        }
    }
    
    public ExplorerManager getExplorerManager() {
        return explorerManager;
    }
    
    private SchemaModel getSchemaModel(DataObject dobj) {
        try {
            SchemaModelCookie modelCookie = (SchemaModelCookie)
            dobj.getCookie(SchemaModelCookie.class);
            assert modelCookie != null;
            SchemaModel model = modelCookie.getModel();
            if(model != null) {
                model.removePropertyChangeListener(this);
                model.addPropertyChangeListener(this);
            }
            return model;
        } catch (IOException ioe) {
            //will show blank page if there is an error.
        }
        
        return null;
    }
    
    
    /**
     * Show the data object in the navigator.
     *
     * @param  dobj  data object to show.
     */
    public void navigate(DataObject dobj) {
        SchemaModel model = getSchemaModel(dobj);
        if (model == null || model.getState() != SchemaModel.State.VALID) {
            showError();
        } else {
            show(model);
        }
    }
    
    public boolean requestFocusInWindow() {
        return treeView.requestFocusInWindow();
    }
    
    public void run() {
        expandDefaultNodes();
        selectActivatedNodes();
    }
    
    public void propertyChange(PropertyChangeEvent event) {
        String property = event.getPropertyName();
        if(SchemaModel.STATE_PROPERTY.equals(property)) {
            onModelStateChanged(event);
            return;
        }
        TopComponent tc = (TopComponent) SwingUtilities.
                getAncestorOfClass(TopComponent.class,this);
        if (ExplorerManager.PROP_SELECTED_NODES.equals(property) &&
                tc == TopComponent.getRegistry().getActivated()) {
            Node[] filteredNodes = (Node[])event.getNewValue();
            if (filteredNodes != null && filteredNodes.length >= 1) {
                // Set the active nodes for the parent TopComponent.
                tc.setActivatedNodes(filteredNodes);
            }
        } else if(TopComponent.getRegistry().PROP_ACTIVATED_NODES.equals(property) &&
                tc != null && tc !=TopComponent.getRegistry().getActivated()) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    selectActivatedNodes();
                }
            });
        } else if(TopComponent.getRegistry().PROP_ACTIVATED.equals(property) &&
                tc == TopComponent.getRegistry().getActivated()) {
            tc.setActivatedNodes(getExplorerManager().getSelectedNodes());
        }
    }
    
    private void selectActivatedNodes() {
        Node[] activated = TopComponent.getRegistry().getActivatedNodes();
        List<Node> selNodes = new ArrayList<Node>();
        for(Node n:activated) {
            SchemaComponent sc = (SchemaComponent) n.getLookup().
                    lookup(SchemaComponent.class);
            if(sc!=null) {
                List<Node> path = UIUtilities.findPathFromRoot(
                        getExplorerManager().getRootContext(),sc);
                if(path!=null&&!path.isEmpty())
                    selNodes.add(path.get(path.size()-1));
            }
        }
        try {
            getExplorerManager().setSelectedNodes(
                    selNodes.toArray(new Node[0]));
        } catch (PropertyVetoException ex) {
        }
    }
    
    public void onModelStateChanged(PropertyChangeEvent evt) {
        State newState = (State)evt.getNewValue();
        if(newState == SchemaModel.State.VALID) {
            SchemaModel model = (SchemaModel)evt.getSource();
            show(model);
            return;
        }
        
        //model is broken
        showError();
        return;
    }
    
    private void showError() {
        if (notAvailableLabel.isShowing()) {
            return;
        }
        remove(treeView);
        add(notAvailableLabel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    private void show(SchemaModel model) {
        remove(notAvailableLabel);
        add(treeView, BorderLayout.CENTER);
        SchemaNodeFactory factory = new CategorizedSchemaNodeFactory(
                model, lookup);
        Node node = factory.createRootNode();
        getExplorerManager().setRootContext(node);
        // Expand the default nodes.
        EventQueue.invokeLater(this);
        revalidate();
        repaint();
    }
}
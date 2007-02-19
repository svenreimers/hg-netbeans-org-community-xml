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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.xml.xam.ui.customizer;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.modules.xml.catalogsupport.DefaultProjectCatalogSupport;
import org.netbeans.modules.xml.xam.Component;
import org.netbeans.modules.xml.xam.Model;
import org.netbeans.modules.xml.xam.locator.CatalogModelException;
import org.netbeans.modules.xml.xam.ui.ModelCookie;
import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.openide.ErrorManager;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.TreeTableView;
import org.openide.explorer.view.Visualizer;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.PropertySupport;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;

/**
 * Base class for external reference creators. Unlike a customizer, a
 * creator may create mulitple new components at a time.
 *
 * @author  Ajit Bhate
 * @author  Nathan Fiedler
 */
public abstract class ExternalReferenceCreator<T extends Component>
        extends AbstractReferenceCustomizer<T>
        implements ExplorerManager.Provider, PropertyChangeListener {
    /** silence compiler warnings */
    private static final long serialVersionUID = 1L;
    /** Map of registered nodes, keyed by their representative DataObject. */
    private Map<DataObject, NodeSet> registeredNodes;
    /** The file being modified (where the import will be added). */
    private transient FileObject sourceFO;
    /** The file selected by the user. */
    private transient FileObject referencedFO;
    /** Used to deal with project catalogs. */
    private transient DefaultProjectCatalogSupport catalogSupport;

    /**
     * Creates new form ExternalReferenceCreator
     *
     * @param  component  component in which to create new components.
     * @param  model      model in which to create components.
     */
    public ExternalReferenceCreator(T component, Model model) {
        super(component);
        registeredNodes = new HashMap<DataObject, NodeSet>();
        initComponents();
        sourceFO = (FileObject) component.getModel().getModelSource().
                getLookup().lookup(FileObject.class);
        catalogSupport = DefaultProjectCatalogSupport.getInstance(sourceFO);
        init(component, model);
        initializeUI();
        Node root = createRootNode();
        explorerManager.setRootContext(root);
    }

    public void applyChanges() throws IOException {
        if (referencedFO != null) {
            try {
                if (catalogSupport.needsCatalogEntry(sourceFO, referencedFO)) {
                    // Remove the previous catalog entry, then create new one.
                    URI uri = catalogSupport.getReferenceURI(sourceFO, referencedFO);
                    catalogSupport.removeCatalogEntry(uri);
                    catalogSupport.createCatalogEntry(sourceFO, referencedFO);
                }
            } catch (URISyntaxException urise) {
                ErrorManager.getDefault().notify(urise);
            } catch (IOException ioe) {
                ErrorManager.getDefault().notify(ioe);
            } catch (CatalogModelException cme) {
                ErrorManager.getDefault().notify(cme);
            }
        }
    }

    /**
     * Return the target namespace of the given model.
     *
     * @param  model  the model for which to get the namespace.
     * @return  target namespace, or null if none.
     */
    protected abstract String getTargetNamespace(Model model);

    /**
     * Retrieve the list of nodes that the user selected.
     *
     * @return  list of selected nodes (empty if none).
     */
    protected List<Node> getSelectedNodes() {
        List<Node> results = new LinkedList<Node>();
        Collection<NodeSet> sets = registeredNodes.values();
        for (NodeSet set : sets) {
            if (set.isSelected()) {
                List<ExternalReferenceDataNode> nodes = set.getNodes();
                if (nodes.size() > 0) {
                    // Use just one of the corresponding nodes, as the
                    // others are basically duplicates.
                    results.add(nodes.get(0));
                }
            }
        }
        return results;
    }

    /**
     * Determine the number of nodes that the user selected, useful for
     * knowing if any nodes are selected or not.
     *
     * @return  number of selected nodes.
     */
    private int countSelectedNodes() {
        int results = 0;
        Collection<NodeSet> sets = registeredNodes.values();
        for (NodeSet set : sets) {
            if (set.isSelected()) {
                List<ExternalReferenceDataNode> nodes = set.getNodes();
                if (nodes.size() > 0) {
                    results++;
                }
            }
        }
        return results;
    }

    /**
     * Return the existing external reference prefixes for the given model.
     *
     * @param  model  the model for which to get the namespace.
     * @return  set of prefixes; empty if none.
     */
    protected abstract Map<String, String> getPrefixes(Model model);

    /**
     * Returns the NodeDecorator for this customizer, if any.
     *
     * @return  node decorator for files nodes, or null if none.
     */
    protected abstract ExternalReferenceDecorator getNodeDecorator();

    /**
     * Indicates if the namespace value must be different than that of
     * the model containing the component being customized. If false,
     * then the opposite must hold - the namespace must be the same.
     * The one exception is if the namespace is not defined at all.
     *
     * @return  true if namespace must differ, false if same.
     */
    public abstract boolean mustNamespaceDiffer();

    /**
     * Called from constructor, after the interface components have been
     * constructed, but before they have been initialized. Gives subclasses
     * a chance to perform initialization based on the given component.
     *
     * @param  component  the reference to be customized.
     * @param  model      the model passed to the constructor (may be null).
     */
    protected void init(T component, Model model) {
        // Note, do not place any code here, as there is no guarantee
        // that the subclasses will delegate to this method at all.
    }

    protected void initializeUI() {
        // View for selecting an external reference.
        TreeTableView locationView = new LocationView();
        locationView.setDefaultActionAllowed(false);
        locationView.setPopupAllowed(false);
        locationView.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        locationView.setRootVisible(false);
        locationView.getAccessibleContext().setAccessibleName(locationLabel.getToolTipText());
        locationView.getAccessibleContext().setAccessibleDescription(locationLabel.getToolTipText());
        Node.Property[] columns = new Node.Property[] {
            new Column(ExternalReferenceDataNode.PROP_NAME, String.class, true),
            new Column(ExternalReferenceDataNode.PROP_SELECTED, Boolean.TYPE, false),
            new Column(ExternalReferenceDataNode.PROP_PREFIX, String.class, false),
        };
        locationView.setProperties(columns);
        locationView.setTreePreferredWidth(200);
        locationView.setTableColumnPreferredWidth(0, 25);
        locationView.setTableColumnPreferredWidth(1, 25);
        locationPanel.add(locationView, BorderLayout.CENTER);
        explorerManager = new ExplorerManager();
        explorerManager.addPropertyChangeListener(this);
        if (!mustNamespaceDiffer()) {
            namespaceLabel.setVisible(false);
            namespaceTextField.setVisible(false);
        }
    }

    public ExternalReferenceDataNode createExternalReferenceNode(Node original) {
        DataObject dobj = (DataObject) original.getLookup().lookup(DataObject.class);
        NodeSet set = registeredNodes.get(dobj);
        if (set == null) {
            set = new NodeSet(this);
            registeredNodes.put(dobj, set);
        }
        ExternalReferenceDataNode erdn = new ExternalReferenceDataNode(
                original, getNodeDecorator());
        set.add(erdn);
        if (set.isSelected() && erdn.canSelect()) {
            erdn.setSelected(true);
        }
        erdn.addPropertyChangeListener(this);
        return erdn;
    }

    /**
     * Determine if the user's input is valid or not. This will enable
     * or disable the save/reset controls based on the results, as well
     * as issue error messages.
     *
     * @param  node  selected node.
     */
    private void validateInput(ExternalReferenceNode node) {
        String msg = null;
        if (mustNamespaceDiffer() && node instanceof ExternalReferenceDataNode) {
            ExternalReferenceDataNode erdn = (ExternalReferenceDataNode) node;
            Map<String, String> prefixMap = getPrefixes(getModelComponent().getModel());
            String ep = erdn.getPrefix();
            if (erdn.isPrefixChanged() && (ep.length() == 0 || prefixMap.containsKey(ep))) {
                msg = NbBundle.getMessage(ExternalReferenceCreator.class,
                        "LBL_ExternalReferenceCreator_InvalidPrefix");
            }
        }
        if (node instanceof RetrievedFilesChildren.RetrievedFileNode) {
            RetrievedFilesChildren.RetrievedFileNode rNode =
                    (RetrievedFilesChildren.RetrievedFileNode) node;
            if (!rNode.isValid()) {
                msg = NbBundle.getMessage(ExternalReferenceCreator.class,
                        "LBL_ExternalReferenceCreator_InvalidCatalogEntry");
            }
        }
        if (msg != null) {
            showMessage(msg);
        }
        int selected = countSelectedNodes();
        // As long as there are (valid) nodes selected, save is enabled.
        setSaveEnabled(selected > 0);
    }

    protected void showMessage(String msg) {
        if (msg == null) {
            messageLabel.setText(" ");
            messageLabel.setIcon(null);
        } else {
            messageLabel.setText(msg);
            // Image is in openide/dialogs module.
            messageLabel.setIcon(new ImageIcon(Utilities.loadImage(
                    "org/openide/resources/error.gif"))); // NOI18N
        }
    }

    /**
     * A TreeTableView that toggles the selection of the external reference
     * data nodes using a single mouse click.
     */
    private static class LocationView extends TreeTableView {
        /** silence compiler warnings */
        private static final long serialVersionUID = 1L;

        /**
         * Creates a new instance of LocationView.
         */
        public LocationView() {
            super();
            tree.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    // Invert the selection of the data node, if such a
                    // node was clicked on.
                    TreePath path = tree.getPathForLocation(e.getX(), e.getY());
                    if (path != null) {
                        Object comp = path.getLastPathComponent();
                        Node node = Visualizer.findNode(comp);
                        if (node instanceof ExternalReferenceDataNode) {
                            ExternalReferenceDataNode erdn =
                                    (ExternalReferenceDataNode) node;
                            if (erdn.canSelect()) {
                                erdn.setSelected(!erdn.isSelected());
                            }
                        }
                    }
                }
            });
        }
    }

    protected Node createRootNode() {
        Set refProjects = null;
        if (catalogSupport.supportsCrossProject()) {
            refProjects = catalogSupport.getProjectReferences();
        }
        ExternalReferenceDecorator decorator = getNodeDecorator();
        Node[] rootNodes = new Node[1 + (refProjects == null ? 0: refProjects.size())];
        Project prj = FileOwnerQuery.getOwner(sourceFO);
        LogicalViewProvider viewProvider = (LogicalViewProvider) prj.getLookup().
                lookup(LogicalViewProvider.class);
        rootNodes[0] = decorator.createExternalReferenceNode(
                viewProvider.createLogicalView());
        int i = 1;
        if (refProjects != null) {
            for (Object o : refProjects) {
                Project refPrj = (Project) o;
                viewProvider = (LogicalViewProvider) refPrj.getLookup().
                        lookup(LogicalViewProvider.class);
                rootNodes[i++] = decorator.createExternalReferenceNode(
                        viewProvider.createLogicalView());
            }
        }
        FileObject[] roots = new FileObject [] {
            prj.getProjectDirectory(),
            // add ref project directories as well?
        };
        Children fileChildren = new Children.Array();
        fileChildren.add(rootNodes);
        Node byFilesNode = new FolderNode(fileChildren);
        byFilesNode.setDisplayName(NbBundle.getMessage(
                ExternalReferenceCreator.class,
                "LBL_ExternalReferenceCreator_Category_By_File"));

        // Construct the By Namespace node.
        Children nsChildren = new NamespaceChildren(roots, decorator);
        Node byNsNode = new FolderNode(nsChildren);
        byNsNode.setDisplayName(NbBundle.getMessage(
                ExternalReferenceCreator.class,
                "LBL_ExternalReferenceCreator_Category_By_Namespace"));
// Hide the Retrieved node tree until we are sure the runtime can handle
// URLs with respect to the catalog.
//        Node retrievedNode;
//        CatalogWriteModel cwm = getCatalogWriteModel();
//        if (cwm != null) {
//            Children rChildren = new RetrievedFilesChildren(cwm , decorator);
//            retrievedNode = new ExternalReferenceNode(projectNode, rChildren, decorator);
//        } else {
//            retrievedNode = new ExternalReferenceNode(projectNode, Children.LEAF, decorator);
//        }
//        retrievedNode.setDisplayName(NbBundle.getMessage(
//                ExternalReferenceCreator.class,
//                "LBL_ExternalReferenceCreator_Category_By_Retrieved"));
        Children categories = new Children.Array();
//        categories.add(new Node[]{ byFilesNode, byNsNode, retrievedNode });
        categories.add(new Node[] { byFilesNode, byNsNode });
        return new AbstractNode(categories);
    }

//    private CatalogWriteModel getCatalogWriteModel() {
//        try {
//            FileObject myFobj = (FileObject) getModelComponent().getModel().
//                    getModelSource().getLookup().lookup(FileObject.class);
//            CatalogWriteModel cwm = CatalogWriteModelFactory.getInstance().
//                    getCatalogWriteModelForProject(myFobj);
//            return cwm;
//        } catch (CatalogModelException cme) {
//        }
//        return null;
//    }

    public void propertyChange(PropertyChangeEvent event) {
        String pname = event.getPropertyName();
        if (ExplorerManager.PROP_SELECTED_NODES.equals(pname)) {
            showMessage(null);
            String ns = null;
            referencedFO = null;
            Node[] nodes = (Node[]) event.getNewValue();
            // Validate the node selection.
            if (nodes != null && nodes.length > 0 &&
                    nodes[0] instanceof ExternalReferenceNode) {
                ExternalReferenceNode node = (ExternalReferenceNode) nodes[0];
                Model model = node.getModel();
                // Without a model, the selection is completely invalid.
                if (model != null) {
                    ns = getTargetNamespace(model);
                    if (model != getModelComponent().getModel()) {
                        referencedFO = (FileObject) model.getModelSource().
                                getLookup().lookup(FileObject.class);
                    }
                    // Ask decorator if selection is valid or not.
                    String msg = getNodeDecorator().validate(node);
                    if (msg != null) {
                        showMessage(msg);
                    } else {
                        // If node is okay, validate the rest of the input.
                        validateInput(node);
                    }
                }
            }
            namespaceTextField.setText(ns);
        } else if (pname.equals(ExternalReferenceDataNode.PROP_PREFIX)) {
            ExternalReferenceNode ern = (ExternalReferenceNode) event.getSource();
            validateInput(ern);
        } else if (pname.equals(ExternalReferenceDataNode.PROP_SELECTED)) {
            ExternalReferenceDataNode erdn =
                    (ExternalReferenceDataNode) event.getSource();
            // Look up the node in the map of sets, and ensure they are all
            // selected as a unit.
            boolean selected = ((Boolean) event.getNewValue()).booleanValue();
            DataObject dobj = (DataObject) erdn.getLookup().lookup(DataObject.class);
            NodeSet set = registeredNodes.get(dobj);
            // Ideally the set should already exist, but cope gracefully.
            assert set != null : "node not created by customizer";
            if (set == null) {
                set = new NodeSet(this);
                set.add(erdn);
            }
            set.setSelected(selected);
            // Check if the current selection is valid.
            validateInput(erdn);
        }
    }

    /**
     * Get the URI location for the given node.
     *
     * @param  node  Node from which to retrieve location value.
     * @return  location for given Node, or null.
     */
    protected String getLocation(Node node) {
        String location = null;
        if (node instanceof RetrievedFilesChildren.RetrievedFileNode) {
            RetrievedFilesChildren.RetrievedFileNode rNode =
                    (RetrievedFilesChildren.RetrievedFileNode) node;
            if (rNode.isValid()) {
                String ns = rNode.getNamespace();
                if (ns == null || mustNamespaceDiffer() !=
                        ns.equals(getTargetNamespace())) {
                    location = rNode.getLocation();
                }
            }
        } else {
            DataObject dobj = (DataObject) node.getLookup().
                    lookup(DataObject.class);
            if (dobj != null && dobj.isValid()) {
                FileObject fileObj = dobj.getPrimaryFile();
                ModelCookie cookie = (ModelCookie) dobj.getCookie(
                        ModelCookie.class);
                Model model;
                try {
                    if (cookie != null && (model = cookie.getModel()) !=
                            getModelComponent().getModel()) {
                        String ns = getTargetNamespace(model);
                        if (ns == null || mustNamespaceDiffer() !=
                                ns.equals(getTargetNamespace())) {
                            return catalogSupport.getReferenceURI(
                                    sourceFO, fileObj).toString();
                        }
                    }
                } catch (URISyntaxException urise) {
                    ErrorManager.getDefault().notify(urise);
                } catch (IOException ioe) {
                    ErrorManager.getDefault().notify(ioe);
                }
            }
        }
        if (location != null) {
            try {
                URI uri = new URI("file", location, null);
                uri = uri.normalize();
                location = uri.getRawSchemeSpecificPart();
            } catch (URISyntaxException use) {
                showMessage(use.toString());
                // Despite this, we can still use the location we have.
            }
        }
        return location;
    }

    /**
     * Get the namespace for the given node.
     *
     * @param  node  Node from which to retrieve namespace value.
     * @return  namespace for given Node, or null.
     */
    protected String getNamespace(Node node) {
        String ns = null;
        if (node instanceof RetrievedFilesChildren.RetrievedFileNode) {
            RetrievedFilesChildren.RetrievedFileNode rNode =
                    (RetrievedFilesChildren.RetrievedFileNode) node;
            if (!rNode.isValid()) {
                return null;
            }
            ns = rNode.getNamespace();
        } else {
            DataObject dobj = (DataObject) node.getLookup().
                    lookup(DataObject.class);
            if (dobj != null && dobj.isValid()) {
                FileObject fileObj = dobj.getPrimaryFile();
                ModelCookie cookie = (ModelCookie) dobj.getCookie(
                        ModelCookie.class);
                Model model;
                try {
                    if (cookie != null && (model = cookie.getModel()) !=
                            getModelComponent().getModel()) {
                        ns = getTargetNamespace(model);
                    }
                } catch (IOException ioe) {
                    // Fall through and return null.
                }
            }
        }
        return ns;
    }

    public ExplorerManager getExplorerManager() {
        return explorerManager;
    }

    /**
     * A column for the reference customizer table.
     *
     * @author  Nathan Fiedler
     */
    protected class Column extends PropertySupport.ReadOnly {
        /** The keyword for this column. */
        private String key;

        /**
         * Constructs a new instance of Column.
         *
         * @param  key   keyword for this column.
         * @param  type  type of the property (e.g. String.class).
         * @param  tree  true if this is the 'tree' column.
         */
        public Column(String key, Class type, boolean tree) {
            super(key, type,
                  NbBundle.getMessage(Column.class,
                    "CTL_ExternalReferenceCreator_Column_Name_" + key),
                  NbBundle.getMessage(Column.class,
                    "CTL_ExternalReferenceCreator_Column_Desc_" + key));
            this.key = key;
            setValue("TreeColumnTTV", Boolean.valueOf(tree));
        }

        public Object getValue()
                throws IllegalAccessException, InvocationTargetException {
            return key;
        }
    }

    /**
     * Manages the state of a set of nodes.
     */
    private static class NodeSet {
        /** The property change listener for each node. */
        private PropertyChangeListener listener;
        /** Nodes in this set. */
        private List<ExternalReferenceDataNode> nodes;
        /** True if this set is selected, false otherwise. */
        private boolean selected;

        /**
         * Creates a new instance of NodeSet.
         *
         * @param  listener  listens to the Node.
         */
        public NodeSet(PropertyChangeListener listener) {
            this.listener = listener;
        }

        /**
         * Add the given node to this set.
         *
         * @param  node  node to be added to set.
         */
        public void add(ExternalReferenceDataNode node) {
            if (nodes == null) {
                nodes = new LinkedList<ExternalReferenceDataNode>();
            }
            nodes.add(node);
        }

        /**
         * Returns the list of nodes in this set.
         *
         * @return  list of nodes.
         */
        public List<ExternalReferenceDataNode> getNodes() {
            return nodes;
        }

        /**
         * Indicates if this set is selected or not.
         *
         * @return  true if selected, false otherwise.
         */
        public boolean isSelected() {
            return selected;
        }

        /**
         * Set this group of Nodes as being selected.
         *
         * @param  select  true to select, false to de-select.
         */
        public void setSelected(boolean select) {
            selected = select;
            for (ExternalReferenceDataNode node : nodes) {
                if (node.canSelect()) {
                    node.removePropertyChangeListener(listener);
                    node.setSelected(select);
                    node.addPropertyChangeListener(listener);
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to
     * initializeTypeView the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        locationLabel = new javax.swing.JLabel();
        locationPanel = new javax.swing.JPanel();
        namespaceLabel = new javax.swing.JLabel();
        namespaceTextField = new javax.swing.JTextField();
        messageLabel = new javax.swing.JLabel();

        locationLabel.setLabelFor(locationPanel);
        org.openide.awt.Mnemonics.setLocalizedText(locationLabel, java.util.ResourceBundle.getBundle("org/netbeans/modules/xml/xam/ui/customizer/Bundle").getString("LBL_ExternalReferenceCreator_Location"));
        locationLabel.setToolTipText(java.util.ResourceBundle.getBundle("org/netbeans/modules/xml/xam/ui/customizer/Bundle").getString("TIP_ExternalReferenceCreator_Location"));

        locationPanel.setLayout(new java.awt.BorderLayout());

        locationPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        namespaceLabel.setLabelFor(namespaceTextField);
        org.openide.awt.Mnemonics.setLocalizedText(namespaceLabel, java.util.ResourceBundle.getBundle("org/netbeans/modules/xml/xam/ui/customizer/Bundle").getString("LBL_ExternalReferenceCreator_Namespace"));
        namespaceLabel.setToolTipText(java.util.ResourceBundle.getBundle("org/netbeans/modules/xml/xam/ui/customizer/Bundle").getString("TIP_ExternalReferenceCreator_Namespace"));

        namespaceTextField.setEditable(false);

        messageLabel.setForeground(new java.awt.Color(255, 0, 0));
        org.openide.awt.Mnemonics.setLocalizedText(messageLabel, " ");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(locationPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(namespaceLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(namespaceTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE))
                    .add(locationLabel)
                    .add(messageLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(locationLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(locationPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(namespaceLabel)
                    .add(namespaceTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(messageLabel)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel locationLabel;
    public javax.swing.JPanel locationPanel;
    public javax.swing.JLabel messageLabel;
    public javax.swing.JLabel namespaceLabel;
    public javax.swing.JTextField namespaceTextField;
    // End of variables declaration//GEN-END:variables
}
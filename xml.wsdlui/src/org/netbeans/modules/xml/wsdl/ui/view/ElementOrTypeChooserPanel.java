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

/*
 * ElementOrTypeChooserPanel.java
 *
 * Created on September 1, 2006, 2:37 PM
 */

package org.netbeans.modules.xml.wsdl.ui.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.xml.XMLConstants;

import org.netbeans.api.project.Project;
import org.netbeans.modules.xml.catalogsupport.DefaultProjectCatalogSupport;
import org.netbeans.modules.xml.schema.model.GlobalComplexType;
import org.netbeans.modules.xml.schema.model.GlobalElement;
import org.netbeans.modules.xml.schema.model.GlobalSimpleType;
import org.netbeans.modules.xml.schema.model.GlobalType;
import org.netbeans.modules.xml.schema.model.Schema;
import org.netbeans.modules.xml.schema.model.SchemaComponent;
import org.netbeans.modules.xml.schema.model.SchemaComponentReference;
import org.netbeans.modules.xml.schema.model.SchemaModel;
import org.netbeans.modules.xml.schema.model.SchemaModelFactory;
import org.netbeans.modules.xml.schema.ui.nodes.categorized.CategorizedSchemaNodeFactory;
import org.netbeans.modules.xml.wsdl.model.Definitions;
import org.netbeans.modules.xml.wsdl.model.WSDLModel;
import org.netbeans.modules.xml.wsdl.ui.api.property.ElementOrTypePropertyEditor;
import org.netbeans.modules.xml.wsdl.ui.view.treeeditor.NodesFactory;
import org.netbeans.modules.xml.wsdl.ui.wsdl.nodes.BuiltInTypeFolderNode;
import org.netbeans.modules.xml.wsdl.ui.wsdl.nodes.XSDTypesNode;
import org.netbeans.modules.xml.xam.ModelSource;
import org.netbeans.modules.xml.xam.ui.customizer.FolderNode;
import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.propertysheet.PropertyEnv;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

/**
 *
 * @author  skini
 */
public class ElementOrTypeChooserPanel extends javax.swing.JPanel implements ExplorerManager.Provider {
    
    private Map<String, String> namespaceToPrefixMap;
    private Project mProject;
    private WSDLModel mModel;
    private SchemaComponent mPreviousSelectedComponent;
    
    /** Creates new form ElementOrTypeChooserPanel */
    public ElementOrTypeChooserPanel(Project project, Map<String, String> namespaceToPrefixMap, WSDLModel model) {
        this.namespaceToPrefixMap = namespaceToPrefixMap;
        this.mProject = project;
        this.mModel = model;
        initComponents();
        initGUI();
    }
    
    public ElementOrTypeChooserPanel(Project project, Map<String, String> namespaceToPrefixMap, WSDLModel model, SchemaComponent previousSelectedComponent) {
        this.namespaceToPrefixMap = namespaceToPrefixMap;
        this.mProject = project;
        this.mModel = model;
        this.mPreviousSelectedComponent = previousSelectedComponent;
        initComponents();
        initGUI();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        beanTreeView1 = new org.openide.explorer.view.BeanTreeView();

        beanTreeView1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        beanTreeView1.setAutoscrolls(true);
        beanTreeView1.setDefaultActionAllowed(false);
        beanTreeView1.setDragSource(false);
        beanTreeView1.setDropTarget(false);
        beanTreeView1.setPopupAllowed(false);
        beanTreeView1.setRootVisible(false);
        beanTreeView1.setSelectionMode(1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(beanTreeView1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(beanTreeView1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void initGUI() {
        manager = new ExplorerManager();
        manager.addPropertyChangeListener(new ExplorerPropertyChangeListener());
        Node rootNode = new AbstractNode(new Children.Array());
        manager.setRootContext(rootNode);
        populateRootNode(rootNode);
    }
    
    private void populateRootNode(Node rootNode) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        Node builtInTypes = new BuiltInTypeFolderNode();
        Node inlineTypesFolderNode = null;
        if (mModel != null) {
            Definitions def = mModel.getDefinitions();
            if (def.getTypes() != null) {
                Collection<Schema> schemas = def.getTypes().getSchemas();
                if (schemas != null && !schemas.isEmpty()) {
                    List<Schema> filteredSchemas = new ArrayList<Schema>();
                    for (Schema schema : schemas) {
                        Collection<SchemaComponent> children = schema.getChildren();
                        for (SchemaComponent comp : children) {
                            if (comp instanceof GlobalElement ||
                                    comp instanceof GlobalSimpleType ||
                                    comp instanceof GlobalComplexType) {
                                filteredSchemas.add(schema);
                                break;
                            }
                        }
                    }
                    if (filteredSchemas.size() > 0) {
                        inlineTypesFolderNode = new InlineTypesFolderNode(NodesFactory.getInstance().create(def.getTypes()), filteredSchemas);
                    }
                }
            }
        }
        Node externalSchemaNode  = null;
        if (mProject != null) {
            catalogSupport = new DefaultProjectCatalogSupport(mProject);
            List<Node> projectNodes = new ArrayList<Node>();
            
            externalSchemaNode = new FolderNode(new Children.Array()); 
            externalSchemaNode.setDisplayName(NbBundle.getMessage(ElementOrTypeChooserPanel.class, "LBL_ByFile_DisplayName"));
            
            Node projectFolderNode = createProjectFolderNode(mProject);
            if (projectFolderNode != null) {
                projectNodes.add(projectFolderNode);
            }
            Set refProjects = catalogSupport.getProjectReferences();
            if (refProjects != null && refProjects.size() > 0) {
                for (Object o : refProjects) {
                    Project refPrj = (Project) o;
                    projectFolderNode = createProjectFolderNode(refPrj);
                    if (projectFolderNode != null) {
                        projectNodes.add(projectFolderNode);
                    }
                }
            }
            
            externalSchemaNode.getChildren().add(projectNodes.toArray(new Node[projectNodes.size()]));
        }
        
        if (inlineTypesFolderNode != null) {
            nodes.add(inlineTypesFolderNode);
        }
        
        if (externalSchemaNode != null) {
            nodes.add(externalSchemaNode);
        }
        if (inlineTypesFolderNode != null) {
            nodes.add(inlineTypesFolderNode);
        }
        
        if (externalSchemaNode != null) {
            nodes.add(externalSchemaNode);
        }
        //builtin is last
        nodes.add(builtInTypes);
        
        Node[] nodesArr = nodes.toArray(new Node[nodes.size()]);
        rootNode.getChildren().add(nodesArr);
        for (int i = 1; i < nodesArr.length; i++) {
            beanTreeView1.expandNode(nodesArr[i]);
        }
        beanTreeView1.expandNode(nodesArr[0]);
        
        if (mPreviousSelectedComponent != null) {
            String tns = mPreviousSelectedComponent.getModel().getSchema().getTargetNamespace();
            boolean selected = false;
            if (tns != null) {
                if (XMLConstants.W3C_XML_SCHEMA_NS_URI.equals(tns)) {
                    selected = selectNode(builtInTypes, mPreviousSelectedComponent);
                } else {
                    if (inlineTypesFolderNode == null || !selectNode(inlineTypesFolderNode, mPreviousSelectedComponent)) {
                        selected = externalSchemaNode != null ? selectNode(externalSchemaNode, mPreviousSelectedComponent) : false;
                    } else {
                        selected = true;
                    }
                }
            } else {
                // must be inline.
                if (inlineTypesFolderNode != null) {
                    selected = selectNode(inlineTypesFolderNode, mPreviousSelectedComponent);
                }
            }
            if (selected) {
                firePropertyChange(ElementOrTypeChooserPanel.PROP_ACTION_APPLY, false, true);
            }
        }
        
    }
    
    private ProjectFolderNode createProjectFolderNode(Project project) {
        LogicalViewProvider viewProvider = (LogicalViewProvider) project.getLookup().
        lookup(LogicalViewProvider.class);
        return new ProjectFolderNode(viewProvider.createLogicalView(), project);
    }

    private boolean selectNode(Node parentNode, SchemaComponent element) {
        Children children = parentNode.getChildren();
        for (Node node : children.getNodes()) {
            SchemaComponent sc = null;
            SchemaComponentReference reference = (SchemaComponentReference) node.getLookup().lookup(SchemaComponentReference.class);
            if (reference != null) {
                sc = reference.get();
            }
            if (sc == null) {
                sc = (SchemaComponent) node.getLookup().lookup(SchemaComponent.class);
            }
            
            if (sc == element) {
                final Node finalNode = node;
                Runnable run = new Runnable() {
                    public void run() {
                        if(manager != null) {
                                try {
                                    manager.setExploredContextAndSelection(finalNode, new Node[] {finalNode});
                                    beanTreeView1.expandNode(finalNode);
                                } catch(PropertyVetoException ex) {
                                    //ignore this
                                }
                            
                        }
                    }
                };
                SwingUtilities.invokeLater(run);
                return true;
            }
            
            if (selectNode(node, element)) {
                return true;
            }
        }
        return false;
    }
    
    
    private File[] recursiveListFiles(File file, FileFilter filter) {
        List<File> files = new ArrayList<File>();
        File[] filesArr = file.listFiles(new SchemaFileFilter());
        files.addAll(Arrays.asList(filesArr));
        File[] dirs = file.listFiles(new DirFileFilter());
        for (File dir : dirs) {
            files.addAll(Arrays.asList(recursiveListFiles(dir, filter)));
        }
        return files.toArray(new File[files.size()]);
    }
    
    public ExplorerManager getExplorerManager() {
        return manager;
    }
    
    
    
    public static final void main(String[] args) {
/*        JFrame frame = new JFrame();
        frame.add(new ElementOrTypeChooserEditorPanel());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);*/
    }

    public void apply() {
        if (selectedComponent != null) {
            if (selectedComponent instanceof GlobalType) {
                selectedElementOrType = new ElementOrType((GlobalType)selectedComponent, namespaceToPrefixMap);
            } else if (selectedComponent instanceof GlobalElement) {
                selectedElementOrType = new ElementOrType((GlobalElement)selectedComponent, namespaceToPrefixMap);
            }
        }
    }
    
    @Override
    public void removeNotify() {
        if (mEnv != null && mEnv.getState().equals(PropertyEnv.STATE_VALID)) {
            if (selectedComponent != null) {
                this.firePropertyChange(ElementOrTypePropertyEditor.PROP_NAME, null, selectedComponent);
            }
        }
        
        super.removeNotify();
    }
    
    private ExplorerManager manager;
    public static String PROP_ACTION_APPLY = "APPLY";
    private ElementOrType selectedElementOrType;
    private SchemaComponent selectedComponent;
    private transient DefaultProjectCatalogSupport catalogSupport;
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.openide.explorer.view.BeanTreeView beanTreeView1;
    // End of variables declaration//GEN-END:variables
    class ExplorerPropertyChangeListener implements PropertyChangeListener {
        
        public void propertyChange(PropertyChangeEvent evt) {
            if(evt.getPropertyName().equals(ExplorerManager.PROP_SELECTED_NODES)) {
                Node[] nodes = (Node[]) evt.getNewValue();
                if(nodes.length > 0) {
                    Node node = nodes[0];
                    //set the selected node to null and state as invalid by default
                    firePropertyChange(PROP_ACTION_APPLY, true, false);
                    SchemaComponent sc = null;
                    SchemaComponentReference reference = (SchemaComponentReference) node.getLookup().lookup(SchemaComponentReference.class);
                    if (reference != null) {
                        sc = reference.get();
                    }
                    if (sc == null) {
                        sc = (SchemaComponent) node.getLookup().lookup(SchemaComponent.class);
                    }

                    if (sc != null && (sc instanceof GlobalType || sc instanceof GlobalElement)) {
                        selectedComponent = sc;
                        firePropertyChange(PROP_ACTION_APPLY, false, true);
                    }
                }
            }
        }
    }

    public ElementOrType getSelectedComponent() {
        return selectedElementOrType;
    }
    
    public SchemaComponent getSelectedSchemaComponent() {
        return selectedComponent;
    }

    public void setSelectedComponent(ElementOrType selectedComponent) {
        this.selectedElementOrType = selectedComponent;
    }
    
    
    static class SchemaFileFilter implements FileFilter {
        
        public boolean accept(File pathname) {
            boolean result = false;
            String fileName = pathname.getName();
            String fileExtension = null;
            int dotIndex = fileName.lastIndexOf('.');
            if(dotIndex != -1) {
                fileExtension = fileName.substring(dotIndex +1);
            }
            
            if(fileExtension != null 
                    && (fileExtension.equalsIgnoreCase(SCHEMA_FILE_EXTENSION))) {
                result = true;
            }
            
            return result;
        }
    }
    
    static class DirFileFilter implements FileFilter {
        
        public boolean accept(File pathname) {
            return pathname.isDirectory();
        }
    }
    
    public static final String SCHEMA_FILE_EXTENSION = "xsd";
    
    class ExternalSchemaFolderNode extends AbstractNode {

        public ExternalSchemaFolderNode(Children children) {
            super(children);
            setDisplayName(NbBundle.getMessage(ElementOrTypeChooserPanel.class, "LBL_ByFile_DisplayName"));
        }
        
    }
    
    
    class ProjectFolderNode extends FilterNode {
        public ProjectFolderNode(Node original, Project project) {
            super(original, new ProjectFolderChildren(project));
        }
    }
    
    class ProjectFolderChildren extends Children.Keys {
        
        private final FileObject projectDir;
        private final Project project;

        public ProjectFolderChildren (Project project) {
            this.project = project;
            this.projectDir = project.getProjectDirectory();;
        }
        
        @Override
        public Node[] createNodes(Object key) {
            FileObject fo = (FileObject) key;
            ModelSource modelSource = org.netbeans.modules.xml.retriever.catalog.Utilities.getModelSource(fo, false); 
            SchemaModel model = SchemaModelFactory.getDefault().getModel(modelSource);
            List<Class<? extends SchemaComponent>> filters = new ArrayList<Class<? extends SchemaComponent>>();
            filters.add(GlobalSimpleType.class);
            filters.add(GlobalComplexType.class);
            filters.add(GlobalElement.class);
            CategorizedSchemaNodeFactory factory = new CategorizedSchemaNodeFactory(
                    model, filters, Lookup.EMPTY);
            return new Node[] {new SchemaFileNode(
                    factory.createNode(model.getSchema()), 
                    FileUtil.getRelativePath(projectDir, fo))};
            
        }

        @Override
        protected void addNotify() {
            resetKeys();
        }
        
        @Override
        protected void removeNotify() {
            this.setKeys(Collections.EMPTY_SET);
            
        }
        
        @SuppressWarnings("unchecked")
        private void resetKeys() {
            ArrayList keys = new ArrayList();
            LogicalViewProvider viewProvider = (LogicalViewProvider) project.getLookup().
            lookup(LogicalViewProvider.class);
            Node node = viewProvider.createLogicalView();
            Children children = node.getChildren();
            for (Node child : children.getNodes()) {
                DataObject dobj = (DataObject) child.getCookie(DataObject.class);
                if (dobj != null) {
                    File[] files = recursiveListFiles(FileUtil.toFile(dobj.getPrimaryFile()), new SchemaFileFilter());
                    for (File file : files) {
                        FileObject fo = FileUtil.toFileObject(file);
                        keys.add(fo);
                    }
                }
            }
            this.setKeys(keys);
        }
        
        @Override
        public boolean remove(final Node[] arr) {
            return super.remove(arr);
        }
    }
    
    class SchemaFileNode extends FilterNode {

        String displayName;
        
        public SchemaFileNode(Node original, String path) {
            super(original, new SchemaFileNodeChildren(original));
            displayName = path;
            
        }

        @Override
        public String getDisplayName() {
            return displayName;
        }
        
        
        
    }
    
     static class SchemaFileNodeChildren extends FilterNode.Children {
        
         public SchemaFileNodeChildren(Node node) {
            super(node);
        }
        
        @Override
        protected Node[] createNodes(Node n) {
            return new Node[] {new CategoryFilterNode(n)};
        }
        
    }
     
     static class CategoryFilterNode extends FilterNode {
         
         public CategoryFilterNode(Node node) {
             super(node, new CategoryFilterChildren(node));
         }
         
         
     }

      static class CategoryFilterChildren extends FilterNode.Children {
         
         public CategoryFilterChildren(Node node) {
             super(node);
         }
         
         @Override
         protected Node[] createNodes(Node n) {
             return new Node[] {new ChildLessNode(n)};
         }
         
     }
     
     static class ChildLessNode extends FilterNode {
         
        
         public ChildLessNode(Node node) {
             super(node, Children.LEAF);
         }
         
     }
     
     static class InlineTypesFolderNode extends FilterNode {
         private Collection<Schema> mSchemas;
         
         public InlineTypesFolderNode(Node node, Collection<Schema> schemas) {
             super(node);
             mSchemas = schemas;
             setDisplayName(NbBundle.getMessage(XSDTypesNode.class, "INLINE_SCHEMATYPE_NAME"));
             setChildren(new TypesChildren());
         }
         
         
         class TypesChildren extends Children.Keys {
    
        public TypesChildren() {
            
        }
        
        @Override
        protected Node[] createNodes(Object key) {
            List<Class<? extends SchemaComponent>> filters = new ArrayList<Class<? extends SchemaComponent>>();
            filters.add(GlobalSimpleType.class);
            filters.add(GlobalComplexType.class);
            filters.add(GlobalElement.class);
            CategorizedSchemaNodeFactory factory = new CategorizedSchemaNodeFactory(
                    ((Schema)key).getModel(), filters, Lookup.EMPTY);
            Node node = factory.createNode((Schema) key);
            return new Node[] { node };

        }
        
        
        @Override
        protected void addNotify() {
            resetKeys();
        }
        
        @Override
        protected void removeNotify() {
            this.setKeys(Collections.EMPTY_SET);
            
        }
        
        private void resetKeys() {
            this.setKeys(mSchemas);
        }
        
        @Override
        public boolean remove (final Node[] arr) {
            //HACK: we want to reset the keys
            //and also want to call super.remove
            //so that tree gets refreshed.
            //we need to add resetkeys
            //because when nodes are created from 
            //persisted bpel info
            //super.remove() does not delete a node.(it is not in nodes collection)
            //supper.remove() removes node when user create
            //a new node for the first time. So we need both here.
            resetKeys();
            return super.remove(arr);
        }
    }
     }

    public void setEnvForPropertyEditor(PropertyEnv env) {
        mEnv = env;
    }
    
    PropertyEnv mEnv;
}

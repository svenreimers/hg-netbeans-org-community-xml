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
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import org.netbeans.modules.xml.schema.model.GlobalComplexType;
import org.netbeans.modules.xml.schema.model.GlobalElement;
import org.netbeans.modules.xml.schema.model.GlobalSimpleType;
import org.netbeans.modules.xml.schema.model.GlobalType;
import org.netbeans.modules.xml.schema.model.SchemaComponent;
import org.netbeans.modules.xml.schema.model.SchemaComponentReference;
import org.netbeans.modules.xml.schema.model.SchemaModel;
import org.netbeans.modules.xml.schema.model.SchemaModelFactory;
import org.netbeans.modules.xml.schema.ui.nodes.categorized.CategorizedSchemaNodeFactory;
import org.netbeans.modules.xml.wsdl.ui.netbeans.module.Utility;
import org.netbeans.modules.xml.wsdl.ui.wsdl.nodes.BuiltInTypeFolderNode;
import org.netbeans.modules.xml.xam.ModelSource;
import org.netbeans.modules.xml.xam.ui.ProjectConstants;
import org.openide.explorer.ExplorerManager;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

/**
 *
 * @author  skini
 */
public class ElementOrTypeChooserPanel extends javax.swing.JPanel implements ExplorerManager.Provider {
    
    private Map<String, String> namespaceToPrefixMap;
    private Project mProject;
    
    /** Creates new form ElementOrTypeChooserPanel */
    public ElementOrTypeChooserPanel(Project project, Map<String, String> namespaceToPrefixMap) {
        this.namespaceToPrefixMap = namespaceToPrefixMap;
        this.mProject = project;
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

        beanTreeView1.setDefaultActionAllowed(false);
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
        BuiltInTypeFolderNode builtInTypes = new BuiltInTypeFolderNode();
        
        if (mProject != null) {
            FileObject projectDir = mProject.getProjectDirectory();
            Node projectNode  = null;
            try {
                projectNode = new ProjectFolderNode(DataObject.find(projectDir).getNodeDelegate(), projectDir);
            } catch (DataObjectNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (projectNode != null) {
                Node[] nodes = new Node[2];
                nodes[0] = projectNode;
                nodes[1] = builtInTypes;
                rootNode.getChildren().add(nodes);
                beanTreeView1.expandNode(builtInTypes);
                beanTreeView1.expandNode(projectNode);
            } else {
                rootNode.getChildren().add(new Node[] {builtInTypes});
                beanTreeView1.expandNode(builtInTypes);
            }
        } else {
            rootNode.getChildren().add(new Node[] {builtInTypes});
            beanTreeView1.expandNode(builtInTypes);
        }
        
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
    
    private ExplorerManager manager;
    public static String PROP_ACTION_APPLY = "APPLY";
    private ElementOrType selectedElementOrType;
    private SchemaComponent selectedComponent;
    
    
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
    
    class ProjectFolderNode extends FilterNode {
        public ProjectFolderNode(Node original, FileObject projectDir) {
            super(original, new ProjectFolderChildren(projectDir));
        }
    }
    
    class ProjectFolderChildren extends Children.Keys {
        
        private final FileObject projectDir;

        public ProjectFolderChildren (FileObject projectDir) {
            this.projectDir = projectDir;
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
            Sources sources = ProjectUtils.getSources(mProject);
            SourceGroup[] srcGroups = sources.getSourceGroups(ProjectConstants.JAVA_SOURCES_TYPE);
            if(srcGroups == null || srcGroups.length == 0) {
                srcGroups = sources.getSourceGroups(Sources.TYPE_GENERIC);
            }
            for (SourceGroup srcGroup : srcGroups) {
                File projectDirFile = FileUtil.toFile(srcGroup.getRootFolder());
                File[] files = recursiveListFiles(projectDirFile, new SchemaFileFilter());
                for (File file : files) {
                    FileObject fo = FileUtil.toFileObject(file);
                    keys.add(fo);
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
        protected Node[] createNodes(Node key) {
            return new Node[] {new CategoryFilterNode(key)};
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
         protected Node[] createNodes(Node key) {
             return new Node[] {new ChildLessNode(key)};
         }
         
     }
     
     static class ChildLessNode extends FilterNode {
         
        
         public ChildLessNode(Node node) {
             super(node, Children.LEAF);
         }
         
     }

}

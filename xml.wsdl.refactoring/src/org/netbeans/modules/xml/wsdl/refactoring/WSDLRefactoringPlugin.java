package org.netbeans.modules.xml.wsdl.refactoring;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.netbeans.modules.refactoring.api.Problem;
import org.netbeans.modules.refactoring.api.RefactoringSession;
import org.netbeans.modules.refactoring.spi.ProgressProviderAdapter;
import org.netbeans.modules.refactoring.spi.RefactoringElementImplementation;
import org.netbeans.modules.refactoring.spi.RefactoringPlugin;
import org.netbeans.modules.xml.refactoring.ErrorItem;
import org.netbeans.modules.xml.refactoring.XMLRefactoringPlugin;
import org.netbeans.modules.xml.refactoring.XMLRefactoringTransaction;
import org.netbeans.modules.xml.refactoring.spi.SharedUtils;
import org.netbeans.modules.xml.schema.model.ReferenceableSchemaComponent;
import org.netbeans.modules.xml.schema.model.SchemaModel;
import org.netbeans.modules.xml.wsdl.model.Definitions;
import org.netbeans.modules.xml.wsdl.model.Import;
import org.netbeans.modules.xml.wsdl.model.ReferenceableWSDLComponent;
import org.netbeans.modules.xml.wsdl.model.WSDLModel;
import org.netbeans.modules.xml.wsdl.model.WSDLModelFactory;
import org.netbeans.modules.xml.wsdl.refactoring.xsd.FindSchemaUsageVisitor;
import org.netbeans.modules.xml.wsdl.refactoring.xsd.SchemaUsageRefactoringEngine;
import org.netbeans.modules.xml.xam.Component;
import org.netbeans.modules.xml.xam.Model;
import org.netbeans.modules.xml.xam.ModelSource;
import org.netbeans.modules.xml.xam.Referenceable;
import org.netbeans.modules.xml.xam.locator.CatalogModelException;
import org.netbeans.modules.xml.schema.model.Schema;
import org.netbeans.modules.xml.schema.model.SchemaModelReference;
import org.openide.ErrorManager;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

public abstract class WSDLRefactoringPlugin extends ProgressProviderAdapter implements RefactoringPlugin, XMLRefactoringPlugin {
    
    List<ErrorItem> findErrors;
    RefactoringSession session;
    XMLRefactoringTransaction transaction;
    public static final String WSDL_MIME_TYPE = "text/x-wsdl+xml";
    
    public WSDLRefactoringPlugin() {
    }
    
    public List<WSDLRefactoringElement> find(Referenceable target, Component searchRoot){
        if (searchRoot == null) {
            return null;
        }
        if (target instanceof Model) {
            return findUsages((Model) target, searchRoot);
        } else if (target instanceof Component) {
            return findUsages((Component) target, searchRoot);
        } else {
            return null;
        }
    }
                    
    public List<WSDLRefactoringElement> findUsages(Model target, Component searchRoot) {
//System.out.println();
//System.out.println("FIND");
//System.out.println("    " + target.getModelSource().getLookup().lookup(FileObject.class));
//System.out.println("    " + searchRoot.getModel().getModelSource().getLookup().lookup(FileObject.class));
//System.out.println();
        List<WSDLRefactoringElement> elements = new ArrayList<WSDLRefactoringElement>();

        if (target instanceof WSDLModel && searchRoot instanceof Definitions) {
            Definitions definitions = (Definitions) searchRoot;
            String namespace = ((WSDLModel) target).getDefinitions().getTargetNamespace();

            for (Import i : definitions.getImports()) {
                Model imported = null;
                if (namespace.equals(i.getNamespace())) { 
                    try {
                        imported = i.getImportedWSDLModel();
                    } catch(CatalogModelException ex) {
                        findErrors.add(new ErrorItem(searchRoot, ex.getMessage()));
                    }
                }
                if (imported == target) {
                    elements.add(new WSDLRefactoringElement(searchRoot.getModel(), target, i));
                }
            }
        }
        // # 172444
        if (target instanceof WSDLModel && target == searchRoot.getModel()) {
            Definitions definitions = ((WSDLModel) target).getDefinitions();

            // wsdl
            for (Import _import : definitions.getImports()) {
                elements.add(new WSDLRefactoringElement(target, target, _import));
//System.out.println("   add wsdl: " + _import.getLocation());
            }
            // schema
            Iterator<Schema> schemas = definitions.getTypes().getSchemas().iterator();

            while (schemas.hasNext()) {
                Iterator<SchemaModelReference> references = schemas.next().getSchemaReferences().iterator();

                while (references.hasNext()) {
                    SchemaModelReference reference = references.next();
                    elements.add(new WSDLRefactoringElement(target, target, reference));
//System.out.println("   add schema ref: " + reference.getSchemaLocation());
                }
            }
        }
//System.out.println();
//System.out.println();
        SchemaUsageRefactoringEngine engine = new SchemaUsageRefactoringEngine();
        List<WSDLRefactoringElement> elem = engine.findUsages(target, searchRoot);

        if(elem != null)
            elements.addAll(elem);
        
        if(elements.size() > 0 )
            return elements;
        else
            return Collections.emptyList();
     }

     public List<WSDLRefactoringElement> findUsages(Component target, Component searchRoot) {
        List<WSDLRefactoringElement> elements = new ArrayList<WSDLRefactoringElement>();
        List<WSDLRefactoringElement> temp = null;
              
        if (target instanceof ReferenceableWSDLComponent && searchRoot instanceof Definitions) {
            temp = new FindWSDLUsageVisitor().findUsages((ReferenceableWSDLComponent)target, (Definitions)searchRoot) ;
            if(temp != null && temp.size() > 0 )
                elements.addAll(temp);
        }
       
        if (target instanceof ReferenceableSchemaComponent && searchRoot instanceof Definitions) {
            temp = new FindSchemaUsageVisitor().findUsages( (ReferenceableSchemaComponent)target, (Definitions)searchRoot, session, transaction);
            if(temp != null && temp.size() > 0)
                elements.addAll(temp);
        }
        
        if (elements.size() == 0) {
            return Collections.emptyList();
        } else {
            return elements;
        }
    }
     
     public List<Model> getModels(List<WSDLRefactoringElement> elements){
         List<Model> models = new ArrayList<Model>();
         for(WSDLRefactoringElement element:elements){
             models.add( ((Component)element.getLookup().lookup(Component.class)).getModel());
         }
         return models;
     }
     
     public Set<Component> getSearchRoots(Referenceable target) {
//System.out.println();
//System.out.println("get search root");
         Set<Component> searchRoots = new HashSet<Component>();
         Set<FileObject> files = SharedUtils.getSearchFiles(target);

         for (FileObject file : files) {
//System.out.println("++ file: " + file);
            WSDLRefactoringEngine engine = new WSDLRefactoringEngine();           
            try {
                Component root = engine.getSearchRoot(file);
//System.out.println("       : " + root);
                searchRoots.add(root);
            }
            catch (IOException ex) {
                ErrorManager.getDefault().log(ErrorManager.INFORMATIONAL, ex.getMessage());
            }  
         }
//System.out.println();
         return searchRoots;
     }
    
     public Map<Model, Set<RefactoringElementImplementation>> getModelMap(List<RefactoringElementImplementation> elements){
        Map<Model, Set<RefactoringElementImplementation>> results = new HashMap<Model, Set<RefactoringElementImplementation>>();
        for(RefactoringElementImplementation element:elements){
           Component comp = element.getLookup().lookup(Component.class);
           Model model = null;
           if(comp instanceof org.netbeans.modules.xml.schema.model.Import){
               //special case of embedded schema import statements in WSDLModel
               //for embedded schema, group the RE impls by Foreign Model
                  SchemaModel mod=  (SchemaModel)comp.getModel();
                  if(mod != null) {
                      Component wsdlImport =mod.getSchema().getForeignParent();
                      if(wsdlImport != null) {
                          model = wsdlImport.getModel();
                      } 
               }
           } else 
               model = comp.getModel();
           if(model == null)
               continue;
           Set<RefactoringElementImplementation> elementsInModel = results.get(model);
           if(elementsInModel == null){
               elementsInModel = new HashSet<RefactoringElementImplementation>();
               elementsInModel.add(element);
               results.put(model, elementsInModel);
           } else
               elementsInModel.add(element);
        }
        return results;
    }
     
     public boolean isFatal(ErrorItem error){
        if(error.getLevel() == ErrorItem.Level.FATAL)
            return true;
        else
            return false;
     } 

     public Problem processErrors(List<ErrorItem> errorItems){
        
        if (errorItems == null || errorItems.size()== 0){
            return null;
        }
        Problem parent = null;
        Problem child = null;
        Problem head = null;
        Iterator<ErrorItem> iterator = errorItems.iterator();
                
        while(iterator.hasNext()) {
            ErrorItem error = iterator.next();
            if(parent == null ){
                parent = new Problem(isFatal(error), error.getMessage());
                child = parent;
                head = parent;
                continue;
            }
            child = new Problem(isFatal(error), error.getMessage());
            parent.setNext(child);
            parent = child;
            
        }
        return head;
    }

    public String getModelReference(Component component) {
        if (component instanceof Import) {
            return ((Import)component).getLocation();
        }
        return null;
    }

    public void setModelReference(Component component, String location) {
        //do nothing
    }
      
    public Collection<Component> getExternalReferences(Model model) {
        Collection<Component> refs = new ArrayList<Component>();
        if(model instanceof WSDLModel){
            refs.addAll(((WSDLModel)model).getDefinitions().getImports());
        }
        return refs;
    }
    
    public Model getModel(ModelSource source) {
       FileObject fo = source.getLookup().lookup(FileObject.class);
       if ( WSDL_MIME_TYPE.equals(FileUtil.getMIMEType(fo))) {
           WSDLModel model = WSDLModelFactory.getDefault().getModel(source);
           return model;
       }
       return null;
    }
}

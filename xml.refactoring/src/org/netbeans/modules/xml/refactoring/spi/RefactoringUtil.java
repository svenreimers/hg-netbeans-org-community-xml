/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
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
package org.netbeans.modules.xml.refactoring.spi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import org.netbeans.api.project.ui.OpenProjects;
import org.netbeans.modules.refactoring.api.AbstractRefactoring;
import org.netbeans.modules.refactoring.api.RenameRefactoring;
import org.netbeans.modules.refactoring.api.SafeDeleteRefactoring;
import org.netbeans.modules.xml.refactoring.ErrorItem;
import org.netbeans.modules.xml.refactoring.ui.GraphHelper;
import org.netbeans.modules.xml.retriever.catalog.ProjectCatalogSupport;
import org.netbeans.modules.xml.xam.Component;
import org.netbeans.modules.xml.xam.Model;
import org.netbeans.modules.xml.xam.ModelSource;
import org.netbeans.modules.xml.xam.Nameable;
import org.netbeans.modules.xml.xam.Referenceable;
import org.netbeans.modules.xml.xam.dom.Utils;
import org.netbeans.spi.project.SubprojectProvider;
import org.openide.cookies.SaveCookie;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.util.NbBundle;

/**
 *
 * @author Nam Nguyen
 */
public class RefactoringUtil {
    
    public static final String BUILD = "build";
    
    public static Project findCurrentProject(Referenceable referenced) {
        Model model = referenced instanceof Model ? (Model) referenced : ((Component)referenced).getModel();
        if (model == null) return null;
        return FileOwnerQuery.getOwner((FileObject)
            model.getModelSource().getLookup().lookup(FileObject.class));
    }
    
    public static Set<Project> getReferencingProjects(Project project) {
        Set<Project> result = new HashSet<Project>();
        for (Project p : OpenProjects.getDefault().getOpenProjects()) {
            if (p.getLookup().lookup(ProjectCatalogSupport.class) == null) {
                continue;
            }
        
            SubprojectProvider spp = (SubprojectProvider) p.getLookup().lookup(SubprojectProvider.class);
            if (spp == null) continue;
            for (Object o : spp.getSubprojects()) {
                Project sp = (Project) o;
                if (sp.equals(project) || project.equals(sp)) {
                    result.add(p);
                    break;
                }
            }
        }

        return result;
    }
    
    public static List<SourceGroup> findSourceRoots(Project project) {
	// get the generic roots so that all roots will be identified
	SourceGroup[] groups =
	        ProjectUtils.getSources(project).getSourceGroups(Sources.TYPE_GENERIC);
        return Arrays.asList(groups);
    }
    
    public static List<FileObject> findSourceFiles(FileObject folder) {
        Enumeration children = folder.getChildren(true);           
        List<FileObject> ret = new ArrayList<FileObject>();
        while (children.hasMoreElements()) {
            FileObject fo = (FileObject) children.nextElement();
            //need to screen out files under the build directory
            //if a project is build, then all the files under build are also included in the
            //search resulting in dup. usages
            //not sure how to look for files under build dir; 
            //so scanning the complete file path
            if (fo.isData() && !isBuildDirFile(fo)) {
                ret.add(fo);
            }
        }
        return ret;
    }
    
    public static boolean isBuildDirFile(FileObject fo) {
        while(fo != null ){
            if(fo.getName().equals(BUILD)){
                return true;
            }
            fo = fo.getParent();
        }
        return false;
    }
     
    public static ErrorItem precheckTarget(Model model, boolean autosave) {
        //Fix for NPE 125763         
        if(model == null)
            return null;
        if (model.getState() != Model.State.VALID) {
            String msg = NbBundle.getMessage(RefactoringUtil.class, "MSG_ModelSourceNotWelformed");
            return new ErrorItem(model, msg);
        }
        if (autosave && ! RefactoringUtil.isWritable(model)) {
            String msg = NbBundle.getMessage(RefactoringUtil.class, "MSG_ModelSourceNotWritable");
            return new ErrorItem(model, msg);
        }
        return null;
    }

       
    public static List<ErrorItem> precheckUsageModels(List<Model> models, boolean autosave) {
        //Set<Model> models = request.getUsages().getModels();
        List<ErrorItem> error = new ArrayList<ErrorItem>();
        for (Model model : models) {
            if (model.getState() != Model.State.VALID) {
                String msg = NbBundle.getMessage(RefactoringUtil.class, "MSG_ModelSourceNotWelformed");
               error.add( new ErrorItem(model, msg) );
            }
            if (autosave && ! RefactoringUtil.isWritable(model)) {
                String msg = NbBundle.getMessage(RefactoringUtil.class, "MSG_ModelSourceNotWritable");
                error.add( new ErrorItem(model, msg) );
            }
        }
        if(error.size() > 0 )
            return error;
        else 
            return null;
    }
    
   /* public static void precheckForUnsafeDelete(DeleteRequest request) {
        if (! request.getUsages().getUsages().isEmpty()) {
            String msg = NbBundle.getMessage(RefactoringUtil.class, "MSG_UnsafeDelete");
            request.addError(new ErrorItem(request.getTarget(), msg));
        }
    }*/
    
    public static String getDescription(AbstractRefactoring request) {
        Referenceable target = request.getRefactoringSource().lookup(Referenceable.class);
        if (request instanceof RenameRefactoring && target instanceof Nameable) {
            return NbBundle.getMessage(RefactoringUtil.class, "LBL_Rename"); //NOI18N
        } else if (request instanceof SafeDeleteRefactoring) {
            return NbBundle.getMessage(RefactoringUtil.class, "LBL_Safe_Delete"); //NOI18N
        } else if (request instanceof RenameRefactoring && target instanceof Model ) {
            return NbBundle.getMessage(RefactoringUtil.class, "LBL_File_Rename"); //NOI18N
        } else {
            return "";  //NOI18N
        }
    }

    public static boolean isDirty(Model model) {
        DataObject obj = (DataObject) model.getModelSource().getLookup().lookup(DataObject.class);
        if (obj != null) {
            return obj.isModified();
        }
        return false;
    }

    public static DataObject getDataObject(Model model) {
        if(model != null)
            return (DataObject) model.getModelSource().getLookup().lookup(DataObject.class);
        
        return null;
    }
    
    public static void saveTargetFile(Model target, Set<Model> all) {
        Set<Model> excludeds = new HashSet<Model>();
        excludeds.addAll(all);
        excludeds.remove(target);
        save(all, target, excludeds);
    }
    
       
     public static void save(Set<Model> allModels, Model targetModel, Set<Model> excludeds) {
        Set<Model> all = new HashSet<Model>();
        all.addAll(allModels);
        all.add(targetModel);
        for (Model model : all) {
            if (excludeds.contains(model)) {
                continue;
            }
            DataObject obj = getDataObject(model);
            if (obj == null) {
                String msg = NbBundle.getMessage(RefactoringUtil.class, "MSG_CannotFindDataObject");
                //    request.addError(new ErrorItem(model, msg));
                continue;
            }
            
            SaveCookie save = (SaveCookie) obj.getCookie(SaveCookie.class);
            FileObject fo = obj.getPrimaryFile();
            if (save != null) {
                try {
                    save.save();
                    obj.setModified(false);
                } catch (IOException ioe) {
                    String msg = NbBundle.getMessage(RefactoringUtil.class, "MSG_ErrorSave", fo.getPath(), ioe.getMessage());
               //    request.addError(new ErrorItem(model, msg));
                    continue;
                }
            } else {
                String msg = NbBundle.getMessage(RefactoringUtil.class, "MSG_CannotSave", fo.getPath());
             //   request.addError(new ErrorItem(model, msg));
                continue;
            }
        }
            
    }
    
    public static String getDescription(GraphHelper.Type type) {
        switch(type) {
            case GENERALIZATION:
                return NbBundle.getMessage(RefactoringUtil.class, "LBL_Generalization");
            case REFERENCE:
                return NbBundle.getMessage(RefactoringUtil.class, "LBL_Reference");
            default:
                assert false : "Invalid type " + type;
                return "";
        }
    }
    
        
     public static ErrorItem precheck(Nameable target, String newName) {
        if (newName == null || !Utils.isValidNCName(newName)) {
            return new ErrorItem(target,
                    NbBundle.getMessage(RefactoringUtil.class, "MSG_NewNameNullEmpty"),
                    ErrorItem.Level.FATAL);
        } else if (! checkDuplicateName(target, newName)) {
            return new ErrorItem(target,
                    NbBundle.getMessage(RefactoringUtil.class, "MSG_NewNameDuplicate"),
                    ErrorItem.Level.FATAL);
        }
        return null;
    }

  
    public static ErrorItem precheck(Model model, String newName) {
        FileObject current = model.getModelSource().getLookup().lookup(FileObject.class);
        assert current != null : "Failed to lookup for file object in model source";
        FileObject parent = current.getParent();
        assert (parent != null) : "Source file has no parent folder";
        
        boolean fileExists = false; 
        FileObject obj = parent.getFileObject(newName, current.getExt());
        if (obj != null) {
            if (obj != current) {
                fileExists = true;
            } else if (obj.getName().equals(newName)) {
                fileExists = true;
            }
        }
            
        if (newName == null || newName.trim().length() == 0 || fileExists) 
        {
            return new ErrorItem(model,
                    NbBundle.getMessage(RefactoringUtil.class, "MSG_NewNameDuplicate"),
                    ErrorItem.Level.FATAL);
        } else if ( !Utils.isValidNCName(newName)){
            return new ErrorItem(model,
                    NbBundle.getMessage(RefactoringUtil.class, "MSG_NewNameNullEmpty"),
                    ErrorItem.Level.FATAL);
        }
        return null;
    }
    
    
    

    /**
     * Returns true if the check result is OK.
     */
        public static boolean checkDuplicateName(Nameable component, String newName) {
       // Nameable component = request.getNameableTarget();
        Component parent = component.getParent();
        Collection<Component> siblings = parent.getChildren(component.getClass());
        for (Component c : siblings) {
            Nameable nameable = (Nameable)c;
            if (nameable.getName() != null && nameable.getName().equals(newName)) {
                return false;
            }
        }
        return true;
    }


    public static boolean isWritable(Model model) {
        if (model != null) {
            ModelSource ms = model.getModelSource();
            if (ms.isEditable()) {
                FileObject fo = (FileObject) ms.getLookup().lookup(FileObject.class);
                if (fo != null) {
                    return fo.canWrite();
                }
            }
        }
        return false;
    }
    
      public static Set<Model> getDirtyModels(Set<Model> models, Model targetModel ) {
        HashSet<Model> dirties = new HashSet<Model>();
        if (RefactoringUtil.isDirty(targetModel) ) {
            dirties.add(targetModel);
        }
        for (Model model : models) {
            if (RefactoringUtil.isDirty(model)) {
                dirties.add(model);
            }
        }
       
        return dirties;
    }        
}

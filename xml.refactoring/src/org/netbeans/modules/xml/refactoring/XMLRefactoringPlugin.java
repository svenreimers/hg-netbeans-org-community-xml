/*
 * XMLRefactoringPlugin.java
 *
 * Created on March 6, 2007, 11:02 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.netbeans.modules.xml.refactoring;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import org.netbeans.modules.refactoring.spi.RefactoringElementImplementation;
import org.netbeans.modules.xml.xam.Component;
import org.netbeans.modules.xml.xam.Model;
import org.netbeans.modules.xml.xam.ModelSource;
import org.netbeans.modules.xml.xam.Referenceable;

/**
 * Base class for all refactoring plugins that want to participate in the single global transaction
 *
 * @author Sonali
 */
public interface XMLRefactoringPlugin {
    
    /** Does the change for a given refactoring.
     * @param refactoringElements Collection of refactoring elements 
     */
    public void doRefactoring(List<RefactoringElementImplementation> elements)throws IOException;
    
    /**
     * @param component the component to check for model reference.
     * @return the reference string if this component is a reference to an 
     * external model, for example, the schema <import> component, 
     * otherwise returns null.
     */
    public String getModelReference(Component component) ;
    
    /**
     * Sets the model reference location of the component
     * @param component the component to set the model reference for.
     * @param string the string to set the model reference to.
     */    
    public void setModelReference(Component component, String location);
    
    /**
     * For a given model, returns all references to external models
     * @param model 
     * @return A collection of components that reference external models
     */
    public Collection<Component> getExternalReferences(Model model); 
    
    /**
     * Gets the new model from each domain model factory class using the model source
     * @param source the source for which the model is returned 
     * @return model 
     */
    public Model getModel(ModelSource source);
}

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

package org.netbeans.modules.xml.xpath.ext.impl;

import javax.xml.namespace.NamespaceContext;
import org.netbeans.modules.xml.xpath.ext.LocationStep;
import org.netbeans.modules.xml.xpath.ext.XPathLocationPath;
import org.netbeans.modules.xml.xpath.ext.XPathModel;
import org.netbeans.modules.xml.xpath.ext.schema.resolver.XPathSchemaContext;
import org.netbeans.modules.xml.xpath.ext.visitor.XPathVisitor;
import org.netbeans.modules.xml.xpath.ext.visitor.impl.PathExpressionWriter;


/**
 * @author Enrico Lelina
 * @version 
 */
public class XPathLocationPathImpl
    extends XPathExpressionImpl
    implements XPathLocationPath {
        
    /** The steps. */
    LocationStep[] mSteps;
    
    /** The absolute flag; defaults to false. */
    boolean mAbsolute;

    /** Flag to figure out if it is a simple path 
     * Recognized paths formatted as foo/bar[3]/baz[@name = 'biz'] .
     */
    private boolean mIsSimplePath;
    
    /**
     * Constructor.
     * @param steps the steps
     */
    public XPathLocationPathImpl(XPathModel model, LocationStep[] steps) {
        this(model, steps, false, true);
    }
    
    
    /**
     * Constructor.
     * @param steps the steps
     * @param absolute flag
     * @param isSimplePath flag whether path is simple
     */
    public XPathLocationPathImpl(XPathModel model, LocationStep[] steps, 
            boolean absolute, boolean isSimplePath) {
        super(model);
        setSteps(steps);
        setAbsolute(absolute);
        mIsSimplePath = isSimplePath;
    }
    
    
    /**
     * Gets the flag the tells whether this is an absolute path or not.
     * @return flag
     */
    public boolean getAbsolute() {
        return mAbsolute;
    }
    
    
    /**
     * Sets the flag that tells whether this is an absolute path or not.
     * @param absolute flag
     */
    public void setAbsolute(boolean absolute) {
        mAbsolute = absolute;
    }
    
    
    /**
     * Gets the steps of the location path.
     * @return the steps
     */
    public LocationStep[] getSteps() {
        return mSteps;
    }
    
    
    /**
     * Sets the steps of the location path.
     * @param steps the steps
     */
    public void setSteps(LocationStep[] steps) {
        mSteps = steps;
    }
    
    /**
     * Describe <code>isSimplePath</code> method here.
     *
     * @return a <code>boolean</code> value
     */
    public boolean isSimplePath() {
        return mIsSimplePath;
    }

    /**
     * Describe <code>setSimplePath</code> method here.
     *
     * @param isSimplePath a <code>boolean</code> value
     */
    public void setSimplePath(boolean isSimplePath) {
        mIsSimplePath = isSimplePath;
    }

    /**
     * Calls the visitor.
     * @param visitor the visitor
     */
    @Override
    public void accept(XPathVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * The schema context for the expression path means the schema context 
     * of the last location step. It is convenient method to escase such 
     * calculations in users' code.
     * @return schema context
     */
    public XPathSchemaContext getSchemaContext() {
        if (mSteps != null && mSteps.length != 0) {
            LocationStep lastStep = mSteps[mSteps.length - 1];
            if (lastStep != null) {
                return lastStep.getSchemaContext();
            }
        }
        return null;
    }

    public void setSchemaContext(XPathSchemaContext newContext) {
        throw new UnsupportedOperationException("It's not implemented because" +
                " it is dangerous to specify a schema such way!"); // NOI18N
    }
    
    public String getExpressionString(int lastStepIndex) {
        PathExpressionWriter visitor = new PathExpressionWriter(mModel);
        visitor.processPath(this, lastStepIndex);
        return visitor.getString();
    }

    public String getExpressionString(int lastStepIndex, NamespaceContext nc) {
        if (mModel.getNamespaceContext() == nc) {
            // optimization
            return getExpressionString(lastStepIndex);
        }
        PathExpressionWriter visitor = new PathExpressionWriter(nc);
        visitor.processPath(this, lastStepIndex);
        return visitor.getString();
    }
}

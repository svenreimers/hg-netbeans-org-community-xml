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

/*
 * Created on Mar 10, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.netbeans.modules.xml.wsdl.ui.fastmodel.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.netbeans.modules.xml.wsdl.model.Import;
import org.netbeans.modules.xml.wsdl.ui.fastmodel.FastWSDLDefinitions;






/**
 * @author radval
 *
 * A FastWSDLDefinitions represent a wsdl document
 * with only some content of the wsdl document parsed in it.
 */
public class FastWSDLDefinitionsImpl implements FastWSDLDefinitions {
	
	private String targetNamespace; 
	
	private String parseErrorMessage;
	
	private List imports = new ArrayList();
	
	private boolean isWSDL = false;
	
	public FastWSDLDefinitionsImpl() {
		
	}
	
	public String getTargetNamespace() {
		return this.targetNamespace;
	}
	
	public void setTargetNamespace(String tNamespace) {
		this.targetNamespace = tNamespace;
	}
	
	public String getParseErrorMessage() {
		return this.parseErrorMessage;
	}
	
	public void setParseErrorMessage(String errorMessage) {
		this.parseErrorMessage = errorMessage;
	}
	
	public void addImport(Import imp) {
		this.imports.add(imp);
	}
	
	public List getImports() {
		return this.imports;
	}
	
	public Import getImport(String namespace) {
		if(namespace == null) {
			return null;
		}
		
		Import imp = null;
		Iterator it = this.imports.iterator();
		
		while(it.hasNext()) {
			Import im = (Import) it.next();
			if(namespace.equals(im.getNamespace()/*im.getNamespaceAttr()*/)) {
				imp = im;
				break;
			}
		}
		
		return imp;
	}
	
	public Import createImport() {
		return null;//TODO:SKINI new ImportImpl();
	}
	
	public boolean isWSDL() {
		return isWSDL;
	}
	
    void setWSDL(boolean wsdl) {
    	this.isWSDL = wsdl;
	}
}

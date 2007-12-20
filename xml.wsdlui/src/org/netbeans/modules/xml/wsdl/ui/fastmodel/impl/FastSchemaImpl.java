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
 * Created on Mar 10, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.netbeans.modules.xml.wsdl.ui.fastmodel.impl;

import java.util.ArrayList;
import java.util.List;
import org.netbeans.modules.xml.wsdl.ui.fastmodel.FastSchema;









/**
 * @author radval
 *
 * A FastWSDLDefinitions represent a wsdl document
 * with only some content of the wsdl document parsed in it.
 */
public class FastSchemaImpl implements FastSchema {
	
	private String targetNamespace; 
	
	private String parseErrorMessage;
	
	private List imports = new ArrayList();
	
	public FastSchemaImpl() {
		
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
	
}

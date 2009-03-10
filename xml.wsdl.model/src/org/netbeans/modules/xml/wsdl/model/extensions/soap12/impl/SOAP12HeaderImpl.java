/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
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
 * nbbuild/licenses/CDDL-GPL-2-CP.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the GPL Version 2 section of the License file that
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

package org.netbeans.modules.xml.wsdl.model.extensions.soap12.impl;

import java.util.Collection;
import org.netbeans.modules.xml.wsdl.model.BindingInput;
import org.netbeans.modules.xml.wsdl.model.BindingOutput;
import org.netbeans.modules.xml.wsdl.model.WSDLModel;
import org.netbeans.modules.xml.wsdl.model.extensions.soap12.SOAP12Component;
import org.netbeans.modules.xml.wsdl.model.extensions.soap12.SOAP12Header;
import org.netbeans.modules.xml.wsdl.model.extensions.soap12.SOAP12HeaderFault;
import org.netbeans.modules.xml.wsdl.model.extensions.soap12.SOAP12QName;
import org.netbeans.modules.xml.xam.Component;
import org.w3c.dom.Element;

/**
 * @author Sujit Biswas
 *
 */
public class SOAP12HeaderImpl extends SOAP12HeaderBaseImpl implements SOAP12Header {
    
    /** Creates a new instance of SOAPHeaderImpl */
    public SOAP12HeaderImpl(WSDLModel model, Element e) {
        super(model, e);
    }
    
    public SOAP12HeaderImpl(WSDLModel model){
        this(model, createPrefixedElement(SOAP12QName.HEADER.getQName(), model));
    }
    
    public void accept(SOAP12Component.Visitor visitor) {
        visitor.visit(this);
    }

    public void removeSOAPHeaderFault(SOAP12HeaderFault soapHeaderFault) {
        removeChild(HEADER_FAULT_PROPERTY, soapHeaderFault);
    }

    public void addSOAPHeaderFault(SOAP12HeaderFault soapHeaderFault) {
        appendChild(HEADER_FAULT_PROPERTY, soapHeaderFault);
    }

    public Collection<SOAP12HeaderFault> getSOAPHeaderFaults() {
        return getChildren(SOAP12HeaderFault.class);
    }

    @Override
    public boolean canBeAddedTo(Component target) {
        if (target instanceof BindingInput || target instanceof BindingOutput) {
            return true;
        }
        return false;
    }
}
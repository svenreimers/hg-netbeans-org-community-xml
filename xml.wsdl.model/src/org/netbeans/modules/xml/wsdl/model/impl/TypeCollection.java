/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2009 Sun Microsystems, Inc. All rights reserved.
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

package org.netbeans.modules.xml.wsdl.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import org.netbeans.modules.xml.wsdl.model.Binding;
import org.netbeans.modules.xml.wsdl.model.BindingInput;
import org.netbeans.modules.xml.wsdl.model.BindingOutput;
import org.netbeans.modules.xml.wsdl.model.Documentation;
import org.netbeans.modules.xml.wsdl.model.ExtensibilityElement;
import org.netbeans.modules.xml.wsdl.model.Import;
import org.netbeans.modules.xml.wsdl.model.Input;
import org.netbeans.modules.xml.wsdl.model.Message;
import org.netbeans.modules.xml.wsdl.model.Output;
import org.netbeans.modules.xml.wsdl.model.PortType;
import org.netbeans.modules.xml.wsdl.model.Types;
import org.netbeans.modules.xml.wsdl.model.WSDLComponent;

/**
 *
 * @author nn136682
 */
public enum TypeCollection {
    ALL(createAll()),
    DOCUMENTATION(createDocumentation()),
    DOCUMENTATION_EE(createDocumentationEE()),
    FOR_IMPORT(createListForImport()),
    FOR_TYPES(createListForTypes()),
    FOR_MESSAGE(createListForMessage()),
    FOR_PORTTYPE(createListForPortType()),
    FOR_BINDING(createListForBinding()),
    FOR_SERVICE(createListForService()),
    DOCUMENTATION_OUTPUT(createDocumentationOutputList()),
    DOCUMENTATION_INPUT(createDocumentationInputList()),
    DOCUMENTATION_EXTENSIBILITY_BINDINGINPUT(createListForBindingInput()),
    DOCUMENTATION_EXTENSIBILITY_BINDINGOUTPUT(createListForBindingOutput());
    
    private Collection<Class<? extends WSDLComponent>> types;
    TypeCollection(Collection<Class<? extends WSDLComponent>> types) {
        this.types = types;
    }
    public Collection<Class<? extends WSDLComponent>> types() { return types; }
    
    static Collection <Class<? extends WSDLComponent>> createAll() {
        Collection<Class<? extends WSDLComponent>> c = new ArrayList<Class<? extends WSDLComponent>>();
        c.add(WSDLComponent.class);
        return c;
    }
    
    static Collection<Class<? extends WSDLComponent>> createDocumentation() {
        Collection<Class<? extends WSDLComponent>> c = new ArrayList<Class<? extends WSDLComponent>>();
        c.add(Documentation.class);
        return c;
    }
    
    static Collection<Class<? extends WSDLComponent>> createDocumentationEE() {
        Collection<Class<? extends WSDLComponent>> c = new ArrayList<Class<? extends WSDLComponent>>();
        c.add(Documentation.class);
        c.add(ExtensibilityElement.class);
        return c;
    }
    
    static Collection<Class<? extends WSDLComponent>> createListForImport() {
        Collection<Class<? extends WSDLComponent>> c = new ArrayList<Class<? extends WSDLComponent>>();
        c.add(Documentation.class);
        return c;
    }

    static Collection<Class<? extends WSDLComponent>> createListForTypes() {
        Collection<Class<? extends WSDLComponent>> c = new ArrayList<Class<? extends WSDLComponent>>();
        c.add(Documentation.class);
        c.add(Import.class);
        return c;
    }

    static Collection<Class<? extends WSDLComponent>> createListForMessage() {
        Collection<Class<? extends WSDLComponent>> c = new ArrayList<Class<? extends WSDLComponent>>();
        c.add(Documentation.class);
        c.add(Import.class);
        c.add(Types.class);
        return c;
    }

    static Collection<Class<? extends WSDLComponent>> createListForPortType() {
        Collection<Class<? extends WSDLComponent>> c = new ArrayList<Class<? extends WSDLComponent>>();
        c.add(Documentation.class);
        c.add(Import.class);
        c.add(Types.class);
        c.add(Message.class);
        return c;
    }

    static Collection<Class<? extends WSDLComponent>> createListForBinding() {
        Collection<Class<? extends WSDLComponent>> c = new ArrayList<Class<? extends WSDLComponent>>();
        c.add(Documentation.class);
        c.add(Import.class);
        c.add(Types.class);
        c.add(Message.class);
        c.add(PortType.class);
        return c;
    }

    static Collection<Class<? extends WSDLComponent>> createListForService() {
        Collection<Class<? extends WSDLComponent>> c = new ArrayList<Class<? extends WSDLComponent>>();
        c.add(Documentation.class);
        c.add(Import.class);
        c.add(Types.class);
        c.add(Message.class);
        c.add(PortType.class);
        c.add(Binding.class);
        return c;
    }

    static Collection<Class<? extends WSDLComponent>> createDocumentationOutputList() {
        Collection<Class<? extends WSDLComponent>> c = new ArrayList<Class<? extends WSDLComponent>>();
        c.add(Documentation.class);
        c.add(Output.class);
        return c;
    }

    static Collection<Class<? extends WSDLComponent>> createDocumentationInputList() {
        Collection<Class<? extends WSDLComponent>> c = new ArrayList<Class<? extends WSDLComponent>>();
        c.add(Documentation.class);
        c.add(Input.class);
        return c;
    }

    static Collection<Class<? extends WSDLComponent>> createListForBindingInput() {
        Collection<Class<? extends WSDLComponent>> c = new ArrayList<Class<? extends WSDLComponent>>();
        c.add(Documentation.class);
        c.add(ExtensibilityElement.class);
        c.add(BindingInput.class);
        return c;
    }
    
    static Collection<Class<? extends WSDLComponent>> createListForBindingOutput() {
        Collection<Class<? extends WSDLComponent>> c = new ArrayList<Class<? extends WSDLComponent>>();
        c.add(Documentation.class);
        c.add(ExtensibilityElement.class);
        c.add(BindingInput.class);
        c.add(BindingOutput.class);
        return c;
    }
}

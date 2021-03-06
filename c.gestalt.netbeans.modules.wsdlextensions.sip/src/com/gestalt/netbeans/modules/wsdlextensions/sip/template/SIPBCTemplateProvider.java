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

package com.gestalt.netbeans.modules.wsdlextensions.sip.template;

import java.io.InputStream;
import org.netbeans.modules.xml.wsdl.bindingsupport.spi.ExtensibilityElementTemplateProvider;
import org.openide.util.NbBundle;

@org.openide.util.lookup.ServiceProvider(service=org.netbeans.modules.xml.wsdl.bindingsupport.spi.ExtensibilityElementTemplateProvider.class)
public class SIPBCTemplateProvider extends ExtensibilityElementTemplateProvider {

    static final String templateUrl = "/com/gestalt/netbeans/modules/wsdlextensions/sip/template/template.xml";

    public InputStream getTemplateInputStream() {
        return SIPBCTemplateProvider.class.getResourceAsStream(templateUrl);
    }

    public String getLocalizedMessage(String str, Object[] objects) {
        return NbBundle.getMessage(SIPBCTemplateProvider.class, str, objects);
    }



}

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

package org.netbeans.modules.xml.wsdl.model.extensions.bpel.impl;

import org.netbeans.modules.xml.wsdl.model.extensions.bpel.BPELExtensibilityComponent;
import org.netbeans.modules.xml.wsdl.model.extensions.bpel.CorrelationProperty;
import org.netbeans.modules.xml.wsdl.model.extensions.bpel.Documentation;
import org.netbeans.modules.xml.wsdl.model.extensions.bpel.PartnerLinkType;
import org.netbeans.modules.xml.wsdl.model.extensions.bpel.PropertyAlias;
import org.netbeans.modules.xml.wsdl.model.extensions.bpel.Role;
import org.netbeans.modules.xml.xam.Component;
import org.netbeans.modules.xml.xam.ComponentUpdater;
import org.netbeans.modules.xml.xam.dom.AbstractDocumentComponent;

/**
 * @author Nam Nguyen
 * 
 * changed by
 * @author ads
 */
public class BPELComponentUpdater implements
        BPELExtensibilityComponent.Visitor,
        ComponentUpdater<BPELExtensibilityComponent>/*, 
        ComponentUpdater.Query<BPELExtensibilityComponent>*/
{

    private BPELExtensibilityComponent parent;

    private ComponentUpdater.Operation operation;
    
    private int index;
    
    private boolean canAdd;

    /** Creates a new instance of BPELComponentUpdater */
    public BPELComponentUpdater() {
    }

    public boolean canAdd(BPELExtensibilityComponent target, Component child) {
        if (!(child instanceof BPELExtensibilityComponent)) {
            return false;
        }
        update(target, (BPELExtensibilityComponent) child, null);
        return canAdd;
    }

    public void update( BPELExtensibilityComponent target,
            BPELExtensibilityComponent child,
            ComponentUpdater.Operation operation )
    {
        update(target, child, -1, operation);
    }

    public void update( BPELExtensibilityComponent target,
            BPELExtensibilityComponent child, int index,
            ComponentUpdater.Operation operation )
    {
        parent = target;
        this.operation = operation;
        this.index = index;
        child.accept(this);
    }

    public void visit( PropertyAlias c ) {
        // never
    }

    public void visit( CorrelationProperty c ) {
        // never
    }

    public void visit( Role child ) {
        if (parent instanceof PartnerLinkTypeImpl) {

            // Have to use sub-api level calls, not role1/role2 calls.
            // Note: this might cause role2 become role1 after sync if source
            // view
            // has lines of role2 revoved. There supposed to be no role2 if
            // there is
            // no role1 and source editing is not main supported usage, so this
            // is fine.

            PartnerLinkTypeImpl target = (PartnerLinkTypeImpl) parent;
            if (operation == ComponentUpdater.Operation.ADD) {
                target.addRole(child);
            }
            else if (operation == ComponentUpdater.Operation.REMOVE) {
                target.removeRole(child);
            } else {
                canAdd = true;
            }
        }
    }

    public void visit( PartnerLinkType c ) {
        // never
    }

    public void visit( org.netbeans.modules.xml.wsdl.model.extensions.bpel.Query c ) {
        if ( parent instanceof PropertyAliasImpl ){
            PropertyAliasImpl propertyAlias = ( PropertyAliasImpl )parent;
            if (operation == ComponentUpdater.Operation.ADD) {
                /* TODO : this is actually wrong. There could be incorrectly added
                 * second query element via editor. In this case we need
                 * to distinguish position that was used for addition
                 * and either insert element or add to the end......
                 */  
                propertyAlias.setQuery( c );
            } else if (operation == ComponentUpdater.Operation.REMOVE) {
                propertyAlias.removeQuery( c );
            } else {
                canAdd = true;
            }
        }
        
    }

    public void visit(Documentation c) {
        if ( parent instanceof PartnerLinkTypeImpl ){
            PartnerLinkTypeImpl partnerLinkType = ( PartnerLinkTypeImpl )parent;
            if (operation == ComponentUpdater.Operation.ADD) {
                //index is greater than -1, then insert with that index
                if (index > -1)
                    partnerLinkType.insertPartnerLinkTypeDocumentationAt( c, index);
                else 
                    partnerLinkType.addPartnerLinkTypeDocumentation(c);
            }
            else if (operation == ComponentUpdater.Operation.REMOVE) {
                partnerLinkType.removePartnerLinkTypeDocumentation( c );
            } else {
                canAdd = true;
            }
        } else if ( parent instanceof RoleImpl ){
            RoleImpl role = ( RoleImpl )parent;
            if (operation == ComponentUpdater.Operation.ADD) {
                //index is greater than -1, then insert with that index
                if (index > -1) {
                    ((AbstractDocumentComponent)role).insertAtIndex(Role.ROLE_DOCUMENTATION_PROPERTY,
                            c, index);
                } else { 
                    role.addRoleDocumentation( c );
                }
            }
            else if (operation == ComponentUpdater.Operation.REMOVE) {
                role.removeRoleDocumentation( c );
            } else {
                canAdd = true;
            }
        }
        
    }

}

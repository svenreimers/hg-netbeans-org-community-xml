/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.

 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.xml.xam.ui.cookies;

import java.util.Collection;
import org.netbeans.modules.xml.xam.Component;
import org.openide.nodes.Node;

/**
 * ViewComponentCookie displays a xam component in a view, opening the
 * editor as necessary, and switching to the desired view, then
 * scrolling to or otherwise showing the component.
 * 
 * 
 * @author Ajit Bhate
 * @author Todd Fast, todd.fast@sun.com
 * @author Nathan Fiedler
 */
public interface ViewComponentCookie extends Node.Cookie {

    /**
     * Indicates a type of view.
     */
    public enum View {
        /** Source text */
        SOURCE,
        /** Schema view */
        SCHEMA,
        /** Instance view */
        INSTANCE,
        /** WSDL view */
        WSDL,
        /** SUPER view */
        SUPER,
    }

    /**
     * Show the xam component in one view or another.
     *
     * @param  view        the view to be used.
     * @param  component   the xam component to show.
     * @param  parameters  the optional parameters for the viewer.
     */
    public void view(View view, Component component,
            Object... parameters);
	
	/**
     * Determines if referenced xam component can be view in given view.
     *
     * @param  view        the view to be used.
	 * @return true if referenced component can be seen in view, false otherwise.
     */
	public boolean canView(View view, Component component);
}
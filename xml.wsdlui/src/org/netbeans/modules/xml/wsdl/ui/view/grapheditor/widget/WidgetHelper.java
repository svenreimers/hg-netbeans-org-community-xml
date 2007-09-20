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

package org.netbeans.modules.xml.wsdl.ui.view.grapheditor.widget;

import java.util.ArrayList;
import java.util.List;

import org.netbeans.api.visual.model.ObjectScene;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.modules.xml.wsdl.model.WSDLComponent;

public class WidgetHelper {

    /**
     * Remove this widget and all children abstract widgets and updates the object-widget mappings.
     * If there are no more widgets associated with this object, then the object is removed. 
     * @param scn
     * @param widget
     */
    public static void removeWidgetFromScene(Scene scn, AbstractWidget widget) {
        if (scn instanceof ObjectScene) {
            ObjectScene scene = (ObjectScene) scn;
            List<Widget> widgetChildren = new ArrayList<Widget>(widget.getChildren());
            for (Widget w : widgetChildren) {
                if (w instanceof AbstractWidget) {
                    removeWidgetFromScene(scene, (AbstractWidget) w);
                } else {
                    removeWidgetFromScene(scene, w);
                }
            }
            WSDLComponent comp = widget.getWSDLComponent();
            if (comp != null) {
                // Add the object-widget mapping in the scene.
                List<Widget> widgets = scene.findWidgets(comp);

                if (widgets != null) {
                    // The List that comes back is immutable...
                    widgets = new ArrayList<Widget>(widgets);
                    widgets.remove(widget);
                    if (widgets.isEmpty()) {
                        scene.removeObject(comp);
                    } else {
                        scene.removeObject(comp);
                        // Remove the original mapping.
                        scene.addObject(comp, widgets.toArray(
                                new Widget[widgets.size()]));

                    }
                }
            }
        }
        widget.removeFromParent();

    }
    
    
    /**
     * Removes all widgets and all abstractwidgets contained in the widgets associated with this object from scene.
     * 
     * @param scn the Scene
     * @param obj the WSDLComponet that is to be deleted.
     */
    public static void removeObjectFromScene(Scene scn, Object obj) {
        if (!(scn instanceof ObjectScene)) return;
        
        ObjectScene scene = (ObjectScene) scn;
        if (obj instanceof WSDLComponent) {
            List<Widget> widgets = new ArrayList<Widget>(scene.findWidgets(obj));
            for (Widget w : widgets) {
                if (w instanceof AbstractWidget) {
                    removeWidgetFromScene(scene, (AbstractWidget) w);
                }
            }
        }
    }
    
    
    /**
     * Removes all AbstractWidget's from this widget and its children. Does not remove the passed on widget from the parent.
     * @param scn
     * @param widget
     */
    public static void removeWidgetFromScene(Scene scn, Widget widget) {
        List<Widget> widgets = new ArrayList<Widget>(widget.getChildren());
        for (Widget w : widgets) {
            if (w instanceof AbstractWidget) {
                removeWidgetFromScene(scn, (AbstractWidget) w);
            } else {
                removeWidgetFromScene(scn, w);
            }
        }
    }
}

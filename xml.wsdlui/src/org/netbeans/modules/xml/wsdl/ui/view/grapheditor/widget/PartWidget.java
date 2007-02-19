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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.xml.wsdl.ui.view.grapheditor.widget;

import java.awt.Color;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.EnumSet;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.InplaceEditorProvider;
import org.netbeans.api.visual.action.TextFieldInplaceEditor;
import org.netbeans.api.visual.border.Border;
import org.netbeans.api.visual.layout.Layout;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.modules.visual.action.TextFieldInplaceEditorProvider;
import org.netbeans.modules.xml.wsdl.model.Part;
import org.netbeans.modules.xml.wsdl.model.WSDLModel;
import org.netbeans.modules.xml.wsdl.ui.view.grapheditor.border.BgBorder;
import org.netbeans.modules.xml.wsdl.ui.view.grapheditor.layout.TableLayout;
import org.netbeans.modules.xml.xam.ui.XAMUtils;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;


public class PartWidget extends AbstractWidget<Part> {
    
    private LabelWidget nameWidget;
    private PartTypeChooserWidget typeWidget;
    
    public PartWidget(Scene scene, Part part, Lookup lookup) {
        super(scene, part, lookup);

        setLayout(ROW_LAYOUT);
        setBorder(BORDER);
        
        createContent();
    }
    
    
    private void removeContent() {
        removeChildren();
    }
    
    PartTypeChooserWidget getPartChooserWidget() {
        return typeWidget;
    }
    
    private void createContent() {
        Part part = getWSDLComponent();
        String name = part.getName();
        
        if (name == null) {
            name = NbBundle.getMessage(PartWidget.class, "LBL_Undefined"); // NOI18N
        } else if (name.trim().equals("")) { // NOI18N
            name = NbBundle.getMessage(PartWidget.class, "LBL_Empty"); // NOI18N
        }

        nameWidget = createLabelWidget(getScene(), name);
        nameWidget.getActions().addAction(ActionFactory
                .createInplaceEditorAction((InplaceEditorProvider<JTextField>) 
                new PartNameInplaceEditorProvider()));
        typeWidget = new PartTypeChooserWidget(getScene(), part);
        
        addChild(nameWidget);
        addChild(typeWidget);
    }
    
    
    public void updateContent() {
        removeContent();
        createContent();
    }    
    
    
    private LabelWidget createLabelWidget(Scene scene, String text) {
        LabelWidget result = new LabelWidget(scene, text);
        result.setBorder(CELL_BORDER);
        result.setFont(scene.getDefaultFont());
        result.setAlignment(LabelWidget.Alignment.LEFT);
        result.setVerticalAlignment(LabelWidget.VerticalAlignment.CENTER);
        return result;
    }


    protected void notifyStateChanged(ObjectState previousState, ObjectState state) {
        super.notifyStateChanged(previousState, state);

        if (state.isSelected()) {
            MessageWidget messageWidget = getMessageWidget();
            if (messageWidget != null) {
                messageWidget.updateButtonState();
            }
        } else {
            MessageWidget messageWidget = getMessageWidget();
            if (messageWidget != null) {
                messageWidget.updateButtonState();
            }
        }
    }
    

    private MessageWidget getMessageWidget() {
        for (Widget w = this; w != null; w = w.getParentWidget()) {
            if (w instanceof MessageWidget) return (MessageWidget) w;
        }
        return null;
    }
    
    
    public static final Layout ROW_LAYOUT = new TableLayout(2, 1, 0, 100);
    public static final Border CELL_BORDER = new BgBorder(0, 0, 1, 8, null, 
            Color.WHITE);

    
    private static class PartNameInplaceEditorProvider implements 
            InplaceEditorProvider<JTextField>, 
            TextFieldInplaceEditor 
    {
        private TextFieldInplaceEditorProvider editorProvider;
        
        public PartNameInplaceEditorProvider() {
            this.editorProvider = new TextFieldInplaceEditorProvider(this, 
                    null);
        }

        
        public boolean isEnabled(Widget widget) {
            Part part = getPart(widget);
            if (part != null) {
                return XAMUtils.isWritable(part.getModel());
            }
            return false;
        }

        
        public String getText(Widget widget) {
            Part part = getPart(widget);
            String name = (part != null) ? part.getName() : null;
            return (name == null) ? "" : name; // NOI18N
        }

        
        public void setText(Widget widget, String text) {
            Part part = getPart(widget);
            if (part != null) {
                WSDLModel model = part.getModel();
                try {
                    if (model.startTransaction()) {
                        part.setName(text);
                    }
                } finally {
                    model.endTransaction();
                }
            }
        }
        
        
        private Part getPart(Widget widget) {
            PartWidget partWidget = getPartWidget(widget);
            return (partWidget == null) ? null : partWidget.getWSDLComponent();
        }
        
        
        private PartWidget getPartWidget(Widget widget) {
            for (Widget w = widget; w != null; w = w.getParentWidget()) {
                if (w instanceof PartWidget) {
                    return (PartWidget) w;
                }
            }
            return null;
        }
        
        
        public void notifyOpened(
                InplaceEditorProvider.EditorController controller, 
                Widget widget, JTextField component) 
        {
            editorProvider.notifyOpened(controller, widget, component);
        }

        
        public void notifyClosing(
                InplaceEditorProvider.EditorController controller, 
                Widget widget, 
                JTextField component, 
                boolean commit) 
        {
            editorProvider.notifyClosing(controller, widget, component, commit);
        }

        
        public JTextField createEditorComponent(
                InplaceEditorProvider.EditorController controller, 
                Widget widget) 
        {
            return editorProvider.createEditorComponent(controller, widget);
        }
        

        public Rectangle getInitialEditorComponentBounds(
                InplaceEditorProvider.EditorController controller, 
                Widget widget, 
                JTextField component, 
                Rectangle bounds) 
        {
            double k = widget.getScene().getZoomFactor();
            
            Widget parent = widget.getParentWidget();
            
            Rectangle widgetBounds = widget.convertLocalToScene(widget.getBounds());
            Rectangle parentBounds = parent.convertLocalToScene(parent.getBounds());
            
            int x1 = parentBounds.x + 24;
            int y1 = parentBounds.y + 24;
            
            int x2 = x1 + widgetBounds.width + 1;
            int y2 = y1 + parentBounds.height + 1;
            
            int x = (int) Math.floor(k * x1);
            int y = (int) Math.floor(k * y1);
            
            int w = (int) Math.ceil(k * x2) - x;
            int h = (int) Math.ceil(k * y2) - y;
            
            int preferredHeight = component.getPreferredSize().height;
            
            if (preferredHeight > h) {
                y -= (preferredHeight - h) / 2;
                h = preferredHeight;
            }
            
            return new Rectangle(x, y, w, h);
        }

        
        public EnumSet<InplaceEditorProvider.ExpansionDirection> 
                getExpansionDirections(
                        InplaceEditorProvider.EditorController controller, 
                        Widget widget, 
                        JTextField component) 
        {
            return editorProvider.getExpansionDirections(controller, widget,
                    component);
        }
    }
    

    private static final Border BORDER = new BgBorder(
            new Insets(1, 0, 0, 0), new Insets(0, 0, 0, 0),
            new Color(0x999999), null);
    

    protected Shape createSelectionShape() {
        Rectangle rect = getBounds();
        return new Rectangle2D.Double(rect.x + 1, rect.y + 2, rect.width - 2, 
                rect.height - 3);
    }
}
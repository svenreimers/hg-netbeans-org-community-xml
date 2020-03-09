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

package org.netbeans.modules.xml.xam.ui.column;

import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JList;
import org.netbeans.modules.xml.xam.ui.cookies.CountChildrenCookie;
import org.openide.awt.HtmlRenderer;
import org.openide.explorer.view.NodeRenderer;
import org.openide.explorer.view.Visualizer;
import org.openide.nodes.Node;

/**
 * Renders the components in the list view.
 *
 */
public class ColumnListCellRenderer extends NodeRenderer {
    private static final long serialVersionUID = 1L;

    public ColumnListCellRenderer() {
        super();
    }

    public Component getListCellRendererComponent(JList list,
            Object value, int index, boolean selected, boolean focused) {
        Component c = super.getListCellRendererComponent(
                list, value, index, selected, focused);
        if (c instanceof JComponent) {
            // Add our child indicator to the node renderer.
            // Testing if the value is a leaf is useless as the nodes
            // usually have non-leaf Children instances.
            Node node = Visualizer.findNode(value);
            boolean hasChildren = true;
            CountChildrenCookie ccc = (CountChildrenCookie) node.getCookie(
                        CountChildrenCookie.class);
            if (ccc != null) {
                hasChildren = ccc.getChildCount() > 0;
            }
            ArrowBorder border = new ArrowBorder(hasChildren);
            JComponent jc = (JComponent) c;
            jc.setBorder(border);
        }
        if (c instanceof HtmlRenderer.Renderer) {
            // Prefer the ... over simply clipping the label.
            HtmlRenderer.Renderer hr = (HtmlRenderer.Renderer) c;
            hr.setRenderStyle(HtmlRenderer.STYLE_TRUNCATE);
        }
        return c;
    }
}

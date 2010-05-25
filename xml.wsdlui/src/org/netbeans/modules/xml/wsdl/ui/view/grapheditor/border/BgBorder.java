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

package org.netbeans.modules.xml.wsdl.ui.view.grapheditor.border;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Rectangle;
import org.netbeans.api.visual.border.Border;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author anjeleevich
 */
public class BgBorder implements Border {

    private Insets borderWidth;
    private Insets padding;
    
    private Color borderColor;
    private Color fillColor;
    
    public BgBorder(int vBorderWidth, int hBorderWidth, 
            int vPadding, int hPadding, Color borderColor, Color fillColor) 
    {
        this.borderWidth = new Insets(vBorderWidth, hBorderWidth, 
                vBorderWidth, hBorderWidth);
        this.padding = new Insets(vPadding, hPadding, vPadding, hPadding);
        
        this.borderColor = borderColor;
        this.fillColor = fillColor;
    }
    
    
    public BgBorder(Insets borderWidth, Insets padding, 
            Color borderColor, Color fillColor) 
    {
        this.borderWidth = (Insets) borderWidth.clone();
        this.padding = (Insets) padding.clone();
        
        this.borderColor = borderColor;
        this.fillColor = fillColor;
    }

    
    public Insets getInsets() {
        return new Insets(
                borderWidth.top    + padding.top, 
                borderWidth.left   + padding.left,
                borderWidth.bottom + padding.bottom, 
                borderWidth.right  + padding.right);
    }

    public void paint(Graphics2D g2, Rectangle rect) {
        Paint oldPaint = g2.getPaint();
        
        if (borderColor != null) {
            g2.setPaint(borderColor);
            g2.fill(rect);
        }
        
        if (fillColor != null) {
            g2.setPaint(fillColor);
            g2.fillRect(rect.x + borderWidth.left, rect.y + borderWidth.top,
                    rect.width - borderWidth.left - borderWidth.right,
                    rect.height - borderWidth.top - borderWidth.bottom);
        }
        
        g2.setPaint(oldPaint);
    }
    
    public boolean isOpaque() {
        return true;
    }
}

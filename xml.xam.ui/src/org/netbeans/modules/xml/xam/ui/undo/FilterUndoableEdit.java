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

package org.netbeans.modules.xml.xam.ui.undo;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

/**
 * Generic undoable edit that delegates to the given undoable edit.
 */
public class FilterUndoableEdit implements UndoableEdit {
    protected UndoableEdit delegate;

    // Copied from CloneableEditorSupport in openide/text

    FilterUndoableEdit() {
    }

    public void undo() throws CannotUndoException {
        if (delegate != null) {
            delegate.undo();
        }
    }

    public boolean canUndo() {
        if (delegate != null) {
            return delegate.canUndo();
        } else {
            return false;
        }
    }

    public void redo() throws CannotRedoException {
        if (delegate != null) {
            delegate.redo();
        }
    }

    public boolean canRedo() {
        if (delegate != null) {
            return delegate.canRedo();
        } else {
            return false;
        }
    }

    public void die() {
        if (delegate != null) {
            delegate.die();
        }
    }

    public boolean addEdit(UndoableEdit anEdit) {
        if (delegate != null) {
            return delegate.addEdit(anEdit);
        } else {
            return false;
        }
    }

    public boolean replaceEdit(UndoableEdit anEdit) {
        if (delegate != null) {
            return delegate.replaceEdit(anEdit);
        } else {
            return false;
        }
    }

    public boolean isSignificant() {
        if (delegate != null) {
            return delegate.isSignificant();
        } else {
            return true;
        }
    }

    public String getPresentationName() {
        if (delegate != null) {
            return delegate.getPresentationName();
        } else {
            return ""; // NOI18N
        }
    }

    public String getUndoPresentationName() {
        if (delegate != null) {
            return delegate.getUndoPresentationName();
        } else {
            return ""; // NOI18N
        }
    }

    public String getRedoPresentationName() {
        if (delegate != null) {
            return delegate.getRedoPresentationName();
        } else {
            return ""; // NOI18N
        }
    }
}

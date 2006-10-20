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

package org.netbeans.modules.xml.schema.ui.nodes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.xml.schema.model.SchemaComponent;
import org.netbeans.modules.xml.schema.ui.nodes.categorized.CategoryNode;
import org.netbeans.modules.xml.xam.Model;
import org.openide.ErrorManager;
import org.openide.nodes.Children;
import org.openide.nodes.Index;
import org.openide.nodes.Node;

/**
 * Represents a Children that can be rebuilt on demand. Also implements
 * the Index cookie to allow re-ordering of the children. To enable, the
 * instance must be added to the lookup (cookie set) of the parent Node.
 *
 * @author  Todd Fast, todd.fast@sun.com
 * @author  Nathan Fiedler
 */
public abstract class RefreshableChildren extends Children.Keys implements Index {
    /** Permits changing order of children. */
    private Index indexSupport;

    /**
     * Creates a new instance of RefreshableChildren.
     */
    public RefreshableChildren() {
        super();
        indexSupport = new IndexSupport();
    }

    /**
     * Refresh the children.
     */
    public abstract void refreshChildren();

    public void addChangeListener(ChangeListener l) {
        indexSupport.addChangeListener(l);
    }

    public void removeChangeListener(ChangeListener l) {
        indexSupport.removeChangeListener(l);
    }

    public void exchange(int x, int y) {
        indexSupport.exchange(x, y);
    }

    public int indexOf(Node node) {
        return indexSupport.indexOf(node);
    }

    public void moveUp(int i) {
        indexSupport.moveUp(i);
    }

    public void moveDown(int i) {
        indexSupport.moveDown(i);
    }

    public void move(int x, int y) {
        indexSupport.move(x, y);
    }

    public void reorder() {
        indexSupport.reorder();
    }

    public void reorder(int[] i) {
        indexSupport.reorder(i);
    }

    /**
     * Allows re-ordering of the child nodes.
     */
    private class IndexSupport extends Index.Support {

        public void reorder(int[] perm) {
            // Moving the last node of five to the second position results
            // in an array that looks like: [0, 2, 3, 4, 1]
            // This means that the first node stays first, the second
            // node is now third, and so on, while the last node is
            // now in the second position.

            // Because some nodes present the children of their only child
            // (e.g. simple type node), we need to get the node children
            // and ask the first one for its schema component. We assume
            // there is at least one child (otherwise this method would not
            // be invoked) and that all of the children have a common parent.
            Node[] nodes = getNodes();
            SchemaComponentNode scn = (SchemaComponentNode) nodes[0].
                    getCookie(SchemaComponentNode.class);
            SchemaComponent parent = null;
            if (scn != null) {
                parent = scn.getReference().get().getParent();
            } else {
                // Not a schema component node? May be a category node.
                CategoryNode cn = (CategoryNode) getNode().
                        getCookie(CategoryNode.class);
                if (cn != null) {
                    parent = cn.getReference().get();
                }
                // Else, it is unknown and we cannot reorder its children.
            }
            if (parent != null) {
                // Re-order the children in the model and let the nodes get
                // refreshed via the listeners.
                Model model = parent.getModel();
                try {
                    model.startTransaction();
                    List<SchemaComponent> children = parent.getChildren();
                    // Need to create a copy of the list since we would
                    // otherwise be mutating it locally and via the model.
                    children = new ArrayList<SchemaComponent>(children);
                    SchemaComponent[] arr = (SchemaComponent[]) children.toArray(
                            new SchemaComponent[children.size()]);
                    for (int i = 0; i < arr.length; i++) {
                        children.set(perm[i], arr[i]);
                    }
                    // Make copies of the children. Need to make a copy,
                    // otherwise model says we are adding a node that is
                    // already a part of the tree.
                    List<SchemaComponent> copies = new ArrayList<SchemaComponent>();
                    for (SchemaComponent child : children) {
                        copies.add((SchemaComponent)child.copy(parent));
                    }
                    // Cannot remove children until after they are copied.
                    for (SchemaComponent child : children) {
                        model.removeChildComponent(child);
                    }
                    // Now add the copies back to the parent.
                    for (SchemaComponent copy : copies) {
                        model.addChildComponent(parent, copy, -1);
                    }
                } catch (IndexOutOfBoundsException ioobe) {
                    // This occurs for redefine node when user drags and drops.
                    // Need to silently fail, as with reordering category nodes.
                    return;
                } finally {
                    model.endTransaction();
                }
                // Notify listeners of the change.
                fireChangeEvent(new ChangeEvent(this));
            }
            // Else silently fail, as with reordering within category nodes.
        }

        public int getNodesCount() {
            return RefreshableChildren.this.getNodesCount();
        }

        public Node[] getNodes() {
            return RefreshableChildren.this.getNodes();
        }
    }
}
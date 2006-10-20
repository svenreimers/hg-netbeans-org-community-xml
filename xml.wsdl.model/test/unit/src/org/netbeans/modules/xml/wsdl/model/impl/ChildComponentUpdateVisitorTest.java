/*
 * ChildComponentUpdateVisitorTest.java
 * JUnit based test
 *
 * Created on August 23, 2006, 10:55 AM
 */

package org.netbeans.modules.xml.wsdl.model.impl;

import java.util.ArrayList;
import junit.framework.*;
import org.netbeans.modules.xml.wsdl.model.*;

/**
 *
 * @author nn136682
 */
public class ChildComponentUpdateVisitorTest extends TestCase {
    private WSDLModel model;
    private Definitions definitions;
    
    public ChildComponentUpdateVisitorTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
        TestCatalogModel.getDefault().clearDocumentPool();
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(ChildComponentUpdateVisitorTest.class);
        return suite;
    }

    public void testRemoveAll_Travel() throws Exception {
        model = TestCatalogModel.getDefault().getWSDLModel(NamespaceLocation.TRAVEL);
        definitions = model.getDefinitions();
    }

    public void testRemoveAll_Airline() throws Exception {
        model = TestCatalogModel.getDefault().getWSDLModel(NamespaceLocation.AIRLINE);
        definitions = model.getDefinitions();
    }

    public void testRemoveAll_Hotel() throws Exception {
        model = TestCatalogModel.getDefault().getWSDLModel(NamespaceLocation.HOTEL);
        definitions = model.getDefinitions();
    }

    public void testRemoveAll_Vehicle() throws Exception {
        model = TestCatalogModel.getDefault().getWSDLModel(NamespaceLocation.VEHICLE);
        definitions = model.getDefinitions();
    }

    static void checkRemoveAll(WSDLComponent target) throws Exception {
        target.getModel().startTransaction();
        recursiveRemoveChildren(target);
        assertEquals("children removed", 0, target.getChildren().size());
        target.getModel().endTransaction();
    }
    
    static void recursiveRemoveChildren(WSDLComponent target) {
        WSDLModel model = target.getModel();
        ArrayList<WSDLComponent> children = new ArrayList<WSDLComponent>(target.getChildren());
        for (WSDLComponent child : children) {
            recursiveRemoveChildren(child);
        }
        if (target.getParent() != null) {
            model.removeChildComponent(target);
        }
    }

    public void testCanPasteAll_Travel() throws Exception {
        model = TestCatalogModel.getDefault().getWSDLModel(NamespaceLocation.TRAVEL);
        recursiveCanPasteChildren(model.getDefinitions());
        recursiveCannotPasteChildren(model.getDefinitions());
    }
    
    public void testCanPasteAll_Airline() throws Exception {
        model = TestCatalogModel.getDefault().getWSDLModel(NamespaceLocation.AIRLINE);
        recursiveCanPasteChildren(model.getDefinitions());
        recursiveCannotPasteChildren(model.getDefinitions());
    }
    
    public void testCanPasteAll_Hotel() throws Exception {
        model = TestCatalogModel.getDefault().getWSDLModel(NamespaceLocation.HOTEL);
        recursiveCanPasteChildren(model.getDefinitions());
        recursiveCannotPasteChildren(model.getDefinitions());
    }
    
    public void testCanPasteAll_Vehicle() throws Exception {
        model = TestCatalogModel.getDefault().getWSDLModel(NamespaceLocation.VEHICLE);
        recursiveCanPasteChildren(model.getDefinitions());
        recursiveCannotPasteChildren(model.getDefinitions());
    }
    
    public static void recursiveCanPasteChildren(WSDLComponent target) {
        WSDLModel model = target.getModel();
        ArrayList<WSDLComponent> children = new ArrayList<WSDLComponent>(target.getChildren());
        for (WSDLComponent child : children) {
            recursiveCanPasteChildren(child);
        }
        if (target.getParent() != null) {
            assertTrue(target.getParent().canPaste(target));
        }
    }

    public static void recursiveCannotPasteChildren(WSDLComponent target) {
        WSDLModel model = target.getModel();
        ArrayList<WSDLComponent> children = new ArrayList<WSDLComponent>(target.getChildren());
        for (WSDLComponent child : children) {
            recursiveCannotPasteChildren(child);
        }
        if (target.getParent() != null) {
            String msg = target.getClass().getName() + " canPaste " + target.getParent().getClass().getName();
            if (target.getParent() instanceof ExtensibilityElement) {
                assertTrue(msg, target.canPaste(target.getParent()));
            } else {
                assertFalse(msg, target.canPaste(target.getParent()));
            }
        }
    }
}
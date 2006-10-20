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

package org.netbeans.modules.xml.xam.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.swing.text.Document;
import org.netbeans.modules.xml.retriever.catalog.impl.CatalogFileWrapperDOMImpl;
import org.netbeans.modules.xml.retriever.catalog.impl.CatalogWriteModelImpl;
import org.netbeans.modules.xml.schema.model.SchemaModel;
import org.netbeans.modules.xml.schema.model.SchemaModelFactory;
import org.netbeans.modules.xml.xam.locator.CatalogModel;
import org.netbeans.modules.xml.xam.locator.CatalogModelException;
import org.netbeans.modules.xml.xam.ModelSource;
import org.openide.cookies.SaveCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author girix
 */

public class TestCatalogModel extends CatalogWriteModelImpl{
    private TestCatalogModel(File file) throws IOException{
        super(file);
    }
    
    static TestCatalogModel singletonCatMod = null;
    public static TestCatalogModel getDefault(){
        if (singletonCatMod == null){
            CatalogFileWrapperDOMImpl.TEST_ENVIRONMENT = true;
            try {
                singletonCatMod = new TestCatalogModel(Util.getTempDir("schematest/catalog"));
                FileObject catalogFO = singletonCatMod.getCatalogFileObject();
                File catFile = FileUtil.toFile(catalogFO);
                catFile.deleteOnExit();
                initCatalogFile();
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return singletonCatMod;
    }
    
    
    /**
     * This method could be overridden by the Unit testcase to return a special
     * ModelSource object for a FileObject with custom impl of classes added to the lookup.
     * This is optional if both getDocument(FO) and createCatalogModel(FO) are overridden.
     */
    protected ModelSource createModelSource(final FileObject thisFileObj, boolean editable) throws CatalogModelException{
        assert thisFileObj != null : "Null file object.";
        final CatalogModel catalogModel = createCatalogModel(thisFileObj);
        final DataObject dobj;
        try {
            dobj = DataObject.find(thisFileObj);
        } catch (DataObjectNotFoundException ex) {
            throw new CatalogModelException(ex);
        }
        Lookup proxyLookup = Lookups.proxy(
                new Lookup.Provider() {
            public Lookup getLookup() {
                Document document = null;
                document = getDocument(thisFileObj);
                return Lookups.fixed(new Object[] {
                    FileUtil.toFile(thisFileObj),
                    thisFileObj,
                    document,
                    dobj,
                    catalogModel
                });
            }
        }
        );
        return new ModelSource(proxyLookup, editable);
    }
    
    private Document getDocument(FileObject fo){
        Document result = null;
        if (documentPooling) {
            result = documentPool().get(fo);
        }
        if (result != null) return result;
        try {
            
            File file = FileUtil.toFile(fo);
            FileInputStream fis = new FileInputStream(file);
            byte buffer[] = new byte[fis.available()];
            result = new org.netbeans.editor.BaseDocument(
                    org.netbeans.modules.xml.text.syntax.XMLKit.class, false);
            result.remove(0, result.getLength());
            fis.read(buffer);
            fis.close();
            String str = new String(buffer);
            result.insertString(0,str,null);
            
        } catch (Exception dObjEx) {
            return null;
        }
        if (documentPooling) {
            documentPool().put(fo, result);
        }
        return result;
    }
    
    protected CatalogModel createCatalogModel(FileObject fo) throws CatalogModelException{
        return getDefault();
    }
    
    public ModelSource createTestModelSource(FileObject thisFileObj, boolean editable) throws CatalogModelException{
        super.createModelSource(thisFileObj, editable);
        Lookup lookup = Lookups.fixed(new Object[]{
            thisFileObj,
            getDocument(thisFileObj),
            createCatalogModel(thisFileObj)
        });
        return new ModelSource(lookup, editable);
    }
    
    private static void initCatalogFile() throws Exception {
        for (NamespaceLocation nl : NamespaceLocation.values()) {
            singletonCatMod.addNamespace(nl);
        }
    }
    
    public void addNamespace(NamespaceLocation nl) throws Exception {
        this.addURI(nl.getLocationURI(), nl.getResourceURI());
    }
    
    public SchemaModel getSchemaModel(NamespaceLocation nl) throws Exception {
        if (nl.getResourceFile().exists()) {
            ModelSource source = singletonCatMod.getModelSource(nl.getLocationURI());
            DataObject dobj = (DataObject) source.getLookup().lookup(DataObject.class);
            SaveCookie save = (SaveCookie) dobj.getCookie(SaveCookie.class);
            if (save != null) save.save();
            FileObject fo = (FileObject) source.getLookup().lookup(FileObject.class);
            fo.delete();
        }
        nl.refreshResourceFile();
        return getSchemaModel(nl.getLocationURI());
    }
    
    public SchemaModel getSchemaModel(URI lcationURI) throws Exception {
        ModelSource source = singletonCatMod.getModelSource(lcationURI);
        SchemaModel model = SchemaModelFactory.getDefault().getModel(source);
        return model;
    }
    
    private Map<FileObject,Document> fileToDocumentMap;
    private Map<FileObject,Document> documentPool() {
        if (fileToDocumentMap == null) {
            fileToDocumentMap = new HashMap<FileObject,Document>();
        }
        return fileToDocumentMap;
    }
    private boolean documentPooling = true;
    
    public void setDocumentPooling(boolean v) {
        documentPooling = v;
        if (! documentPooling) {
            clearDocumentPool();
        }
    }

    public void clearDocumentPool() {
        fileToDocumentMap = null;
    }
}

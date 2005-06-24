/* 
 * Copyright (c) 2005 FlexDock Development Team. All rights reserved. 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of 
 * this software and associated documentation files (the "Software"), to deal in the 
 * Software without restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the
 * to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all 
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE.
 */
package org.flexdock.perspective.persist.xml;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.flexdock.docking.state.DockingPath;
import org.flexdock.docking.state.DockingState;
import org.flexdock.docking.state.FloatingGroup;
import org.flexdock.docking.state.LayoutNode;
import org.flexdock.docking.state.tree.SplitNode;
import org.flexdock.perspective.Layout;
import org.flexdock.perspective.LayoutSequence;
import org.flexdock.perspective.Perspective;
import org.flexdock.perspective.persist.Persister;
import org.flexdock.perspective.persist.PerspectiveModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created on 2005-06-03
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: XMLPersister.java,v 1.11 2005-06-24 14:35:54 winnetou25 Exp $
 */
public class XMLPersister implements Persister {
    
    /**
     * @see org.flexdock.perspective.persist.Persister#store(java.lang.String, org.flexdock.perspective.persist.PerspectiveInfo)
     */
    public boolean store(OutputStream os, PerspectiveModel perspectiveModel) throws IOException {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            ISerializer perspectiveModelSerializer = SerializerRegistry.getSerializer(PerspectiveModel.class);
            Element perspectiveModelElement = perspectiveModelSerializer.serialize(document, perspectiveModel);
            
            document.appendChild(perspectiveModelElement);
            
            return true;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * @see org.flexdock.perspective.persist.Persister#load(java.lang.String)
     */
    public PerspectiveModel load(InputStream is) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }
    
    private void registerSerializers() {
        SerializerRegistry.registerSerializer(Perspective.class, new PerspectiveSerializer());
        SerializerRegistry.registerSerializer(Layout.class, new LayoutSerializer());
        SerializerRegistry.registerSerializer(LayoutNode.class, new LayoutNodeSerializer());
        SerializerRegistry.registerSerializer(LayoutSequence.class, new LayoutSequenceSerializer());
        SerializerRegistry.registerSerializer(DockingState.class, new DockingStateSerializer());
        SerializerRegistry.registerSerializer(Point.class, new PointSerializer());
        SerializerRegistry.registerSerializer(Dimension.class, new DimensionSerializer());
        SerializerRegistry.registerSerializer(Rectangle.class, new RectangleSerializer());
        SerializerRegistry.registerSerializer(FloatingGroup.class, new FloatingGroupSerializer());
        SerializerRegistry.registerSerializer(DockingPath.class, new DockingPathSerializer());
        SerializerRegistry.registerSerializer(SplitNode.class, new SplitNodeSerializer());
        SerializerRegistry.registerSerializer(PerspectiveModel.class, new PerspectiveModelSerializer());
    }
    
    public static XMLPersister newDefaultInstance() {
        XMLPersister persister = new XMLPersister();
        persister.registerSerializers();

        return persister; 
    }
    
}

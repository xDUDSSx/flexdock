/*
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.flexdock.docking.drag;

import org.flexdock.docking.Dockable;
import org.flexdock.docking.DockingConstants;
import org.flexdock.docking.DockingManager;
import org.flexdock.docking.DockingPort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.EventListener;

public class DragOperation implements DockingConstants {
    public static final String DRAG_IMAGE = "DragOperation.DRAG_IMAGE";

    private Component dragSource;
    private Component dockable;
    private DockingPort parentDockingPort;
    private Point mouseOffset;
    private Point currentMouse;
    private EventListener[] cachedListeners;
    private DragManager dragListener;
    private DockingPort targetPort;
    private String targetRegion;
    private boolean overWindow;
    private boolean pseudoDrag;
    private long started;
    private Dockable dockableRef;
    private DockingPort sourcePort;


    public DragOperation(Component dockable, Point dragOrigin, MouseEvent evt) {
        if(dockable==null) {
            throw new NullPointerException("'dockable' parameter cannot be null.");
        }
        if(evt==null) {
            throw new NullPointerException("'evt' parameter cannot be null.");
        }
        if(!(evt.getSource() instanceof Component)) {
            throw new IllegalArgumentException("'evt.getSource()' must be an instance of java.awt.Component.");
        }

        if(dragOrigin==null) {
            dragOrigin = evt.getPoint();
        }
        init(dockable, (Component)evt.getSource(), dragOrigin, false);
    }

    public DragOperation(Component dockable, Component dragSource, Point currentMouse) {
        init(dockable, dragSource, currentMouse, true);
    }

    private void init(Component dockable, Component dragSource, Point currentMouse, boolean fakeDrag) {
        this.dockable = dockable;
        this.dragSource = dragSource;
        this.currentMouse = currentMouse;
        mouseOffset = calculateMouseOffset(currentMouse);
        pseudoDrag = fakeDrag;
        if(!fakeDrag) {
            parentDockingPort = (DockingPort)SwingUtilities.getAncestorOfClass(DockingPort.class, dockable);
        }

        sourcePort = DockingManager.getDockingPort(dockable);
        started = -1;
    }

    private Point calculateMouseOffset(Point evtPoint) {
        if(evtPoint==null) {
            return null;
        }
        //TODO: Added try catch, add logging or solve otherwise. Happens to stacked tabbed panes.
        Point dockableLoc;
        try {
            dockableLoc = dockable.getLocationOnScreen();
        } catch (IllegalComponentStateException ex) {
            dockableLoc = new Point(0, 0);
        }
        SwingUtilities.convertPointToScreen(evtPoint, dragSource);
        Point offset = new Point();
        offset.x = dockableLoc.x - evtPoint.x;
        offset.y = dockableLoc.y - evtPoint.y;
        return offset;
    }

    public Component getDockable() {
        return dockable;
    }

    public Dockable getDockableReference() {
        if(dockableRef==null) {
            dockableRef = DockingManager.getDockable(dockable);
        }
        return dockableRef;
    }

    public Point getMouseOffset() {
        return (Point)mouseOffset.clone();
    }

    public void updateMouse(MouseEvent me) {
        if(me!=null && me.getSource()==dragSource) {
            currentMouse = me.getPoint();
        }
    }

    public Point getCurrentMouse() {
        return getCurrentMouse(false);
    }

    public Point getCurrentMouse(boolean relativeToScreen) {
        Point p = (Point)currentMouse.clone();
        if(relativeToScreen) {
            SwingUtilities.convertPointToScreen(p, dragSource);
        }
        return p;
    }

    public Rectangle getDragRect(boolean relativeToScreen) {
        Point p = getCurrentMouse(relativeToScreen);
        Point offset = getMouseOffset();
        p.x += offset.x;
        p.y += offset.y;

        Rectangle r = new Rectangle(getDragSize());
        r.setLocation(p);
        return r;

    }

    public Point getCurrentMouse(Component target) {
        if(target==null || !target.isVisible()) {
            return null;
        }
        return SwingUtilities.convertPoint(dragSource, currentMouse, target);
    }

    public Dimension getDragSize() {
        return dockable.getSize();
    }

    public Component getDragSource() {
        return dragSource;
    }

    public void setTarget(DockingPort port, String region) {
        targetPort = port;
        targetRegion = region==null? UNKNOWN_REGION: region;
    }

    public DockingPort getTargetPort() {
        return targetPort;
    }

    public String getTargetRegion() {
        return targetRegion;
    }

    public EventListener[] getCachedListeners() {
        return cachedListeners==null? new EventListener[0]: cachedListeners;
    }

    public void setCachedListeners(EventListener[] listeners) {
        cachedListeners = listeners;
    }

    public DragManager getDragListener() {
        return dragListener;
    }

    public void setDragListener(DragManager listener) {
        this.dragListener = listener;
    }

    public boolean isOverWindow() {
        return overWindow;
    }

    public void setOverWindow(boolean overWindow) {
        this.overWindow = overWindow;
    }

    public boolean isPseudoDrag() {
        return pseudoDrag;
    }

    public DockingPort getParentDockingPort() {
        return parentDockingPort;
    }

    public void start() {
        if(started==-1) {
            started = System.currentTimeMillis();
        }
    }

    public long getStartTime() {
        return started;
    }

    public DockingPort getSourcePort() {
        return sourcePort;
    }
}
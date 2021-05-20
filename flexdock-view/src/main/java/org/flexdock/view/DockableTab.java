package org.flexdock.view;

import org.flexdock.docking.Dockable;
import org.flexdock.docking.DockingConstants;
import org.flexdock.docking.DockingPort;
import org.flexdock.docking.event.DockingEvent;
import org.flexdock.docking.event.DockingListener;
import org.flexdock.docking.props.DockablePropertySet;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

public class DockableTab extends JComponent implements Dockable, DockingConstants {

    @Override
    public Component getComponent() {
        return null;
    }

    @Override
    public DockingPort getDockingPort() {
        return null;
    }

    @Override
    public List getDragSources() {
        return null;
    }

    @Override
    public Set getFrameDragSources() {
        return null;
    }

    @Override
    public String getPersistentId() {
        return null;
    }

    @Override
    public DockablePropertySet getDockingProperties() {
        return null;
    }

    @Override
    public boolean dock(Dockable dockable) {
        return false;
    }

    @Override
    public boolean dock(Dockable dockable, String relativeRegion) {
        return false;
    }

    @Override
    public boolean dock(Dockable dockable, String relativeRegion, float ratio) {
        return false;
    }

    @Override
    public void dockingComplete(DockingEvent evt) {

    }

    @Override
    public void dockingCanceled(DockingEvent evt) {

    }

    @Override
    public void dragStarted(DockingEvent evt) {

    }

    @Override
    public void dropStarted(DockingEvent evt) {

    }

    @Override
    public void undockingComplete(DockingEvent evt) {

    }

    @Override
    public void undockingStarted(DockingEvent evt) {

    }

    @Override
    public void addDockingListener(DockingListener listener) {

    }

    @Override
    public void removeDockingListener(DockingListener listener) {

    }

    @Override
    public DockingListener[] getDockingListeners() {
        return new DockingListener[0];
    }
}

package org.flexdock.view.actions;

import org.flexdock.docking.Dockable;
import org.flexdock.docking.DockingManager;
import org.flexdock.view.View;

import java.awt.event.ActionEvent;

public class DefaultMaximizeAction extends ViewAction {
    public DefaultMaximizeAction() {}

    @Override
    public void actionPerformed(View view, ActionEvent evt) {
        DockingManager.toggleMaximized((Dockable) view);
    }
}

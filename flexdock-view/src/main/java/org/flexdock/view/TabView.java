package org.flexdock.view;

import org.flexdock.docking.Dockable;
import org.flexdock.docking.DockingManager;
import org.flexdock.util.SwingUtility;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.util.ArrayList;
import java.util.HashSet;

public class TabView extends View {
    public TabView(String persistentId) {
        this(persistentId, null);
    }

    public TabView(String persistentId, String title) {
        this(persistentId, title, null);
    }

    public TabView(String persistentId, String title, String tabText) {
        super(persistentId, title, tabText);
        if (persistentId == null) {
            throw new IllegalArgumentException(
                    "The 'persistentId' parameter cannot be null.");
        }

        this.persistentId = persistentId;

        dragSources = new ArrayList(1);
        frameDragSources = new HashSet(1);
        dockingListeners = new ArrayList(1);

        setContentPane(createContentPane());
        setTitlebar(createTitlebar());
        setLayout(createLayout());
        setContentPaneCheckingEnabled(true);

        if (title == null) {
            title = "";
        }
        setTitle(title);

        if (tabText == null) {
            tabText = title;
        }
        setTabText(tabText);

        addHierarchyListener(new HierarchyListener() {
            @Override
            public void hierarchyChanged(HierarchyEvent e) {
                clearButtonRollovers();
            }
        });

        updateUI();

        DockingManager.registerDockable((Dockable) this);

        getActionMap().put(ACTION_TOGGLE_NEXT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtility.toggleFocus(+1);
            }
        });
        getActionMap().put(ACTION_TOGGLE_PREVIOUS, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtility.toggleFocus(-1);
            }
        });
    }
}

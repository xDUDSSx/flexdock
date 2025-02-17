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
package org.flexdock.view.components;

import org.flexdock.plaf.PlafManager;
import org.flexdock.plaf.theme.TitlebarUI;
import org.flexdock.view.View;
import org.flexdock.view.actions.ViewAction;
import org.flexdock.view.model.ViewButtonModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author Christopher Butler
 */
public class Titlebar extends JComponent {
    public static final String UI_CLASS_ID = "Flexdock.titlebar";

    private Icon titleIcon;

    private String titleText;

    private List actionList;

    private HashMap actionButtons;

    private Button[] buttonList;

    private View parentView;

    public Titlebar() {
        this(null, null);
    }

    public Titlebar(String title) {
        this(title, null);
    }

    public Titlebar(Action[] actions) {
        this(null, actions);
    }

    public Titlebar(String title, Action[] actions) {
        setText(title);
        setActions(actions);
        updateUI();
    }

    /**
     * Sets the text for this titlebar to {@code text} or empty string if text
     * is {@code null}.
     *
     * @param text
     *            the text to set.
     */
    public void setText(String text) {
        titleText = text == null ? "" : text;
        repaint();
    }

    protected void setActions(Action[] actions) {
        if (actions == null) {
            actions = new Action[0];
            actionList = new ArrayList(3);
            actionButtons = new HashMap(3);
        }

        removeAllActions();
        for (int i = 0; i < actions.length; i++) {
            addAction(actions[i]);
        }
    }

    public synchronized void addAction(String actionName) {
        if (actionName == null || !(ui instanceof TitlebarUI)) {
            return;
        }

        TitlebarUI tbarUI = (TitlebarUI) ui;
        Action action = tbarUI.getAction(actionName);
        addAction(action);
    }

    public synchronized void addAction(Action action) {
        if (action == null) {
            return;
        }

        String key = getKey(action);
        if (key == null) {
            throw new IllegalArgumentException(
                "Cannot add an Action that has no Name associated with it.");
        }

        // don't add the same action more than once
        if (hasAction(key)) {
            return;
        }

        // create the button
        Button button = createActionButton(action);
        // cache the button
        actionButtons.put(key, button);
        // add the button to the container
        add(button);

        // add the action to our list
        actionList.add(action);
        regenerateButtonList();
        updateButtonModels();
    }

    private void regenerateButtonList() {
        Button[] list = new Button[actionList.size()];
        for (int i = 0; i < list.length; i++) {
            Action action = (Action) actionList.get(i);
            String key = getKey(action);
            list[i] = getButton(key);
        }

        synchronized (this) {
            buttonList = list;
        }
    }

    public Action getAction(String key) {
        if (key == null) {
            return null;
        }

        for (Iterator it = actionList.iterator(); it.hasNext();) {
            Action action = (Action) it.next();
            String actionName = (String) action.getValue(Action.NAME);
            if (key.equals(actionName)) {
                return action;
            }
        }
        return null;
    }

    public Action[] getActions() {
        return (Action[]) actionList.toArray(new Action[0]);
    }

    protected Button getButton(String key) {
        return (Button) actionButtons.get(key);
    }

    public AbstractButton getActionButton(String actionName) {
        return getButton(actionName);
    }

    protected boolean hasAction(String key) {
        return actionButtons.containsKey(key);
    }

    public Icon getIcon() {
        return titleIcon;
    }

    public String getText() {
        return titleText;
    }

    public void removeAction(Action action) {
        if (action == null) {
            return;
        }

        String key = getKey(action);
        removeAction(key);
    }

    public synchronized void removeAction(String key) {
        if (!hasAction(key)) {
            return;
        }

        // Remove button associated with this action.
        Button button = getButton(key);
        remove(button);
        actionButtons.remove(key);
        // remove the action
        Action action = getAction(key);
        actionList.remove(action);
        regenerateButtonList();
        updateButtonModels();
    }

    public synchronized void removeAllActions() {
        if (actionList == null) {
            return;
        }

        while (actionList.size() > 0) {
            Action action = (Action) actionList.get(0);
            String key = getKey(action);
            // Remove button associated with this action.
            Button button = getButton(key);
            remove(button);
            actionButtons.remove(key);
            // remove the action
            actionList.remove(0);
        }
        regenerateButtonList();
    }

    protected String getKey(Action action) {
        Object obj = action == null ? null : action.getValue(Action.NAME);
        return obj instanceof String ? (String) obj : null;
    }

    protected Icon getIcon(Action action) {
        Object obj = action == null ? null : action.getValue(Action.SMALL_ICON);
        return obj instanceof Icon ? (Icon) obj : null;
    }

    public void setIcon(Icon icon) {
        titleIcon = icon;
    }

    public boolean isActive() {
        return parentView == null ? false : parentView.isActive();
    }

    public void setView(View view) {
        setParentView(view);
    }

    protected void setParentView(View view) {
        parentView = view;
        updateButtonModels();
    }

    public Button createActionButton(Action action) {
        Button button = new Button(action);
        if (ui instanceof TitlebarUI) {
            ((TitlebarUI) ui).configureAction(action);
        }

        // sync up the button model
        if (action instanceof ViewAction) {
            ButtonModel model = ((ViewAction) action).createButtonModel();
            if (model != null) {
                button.setModel(model);
            }
        }

        return button;
    }

    private void updateButtonModels() {
        String viewId = parentView == null ? null : parentView
                        .getPersistentId();
        Component[] comps = getComponents();
        for (int i = 0; i < comps.length; i++) {
            Button button = comps[i] instanceof Button ? (Button) comps[i]
                            : null;
            if (button == null) {
                continue;
            }

            ButtonModel bm = button.getModel();
            if (bm instanceof ViewButtonModel) {
                ((ViewButtonModel) bm).setViewId(viewId);
            }
        }
    }

    @Override
    public void doLayout() {
        if (ui instanceof TitlebarUI) {
            ((TitlebarUI) ui).layoutComponents(this);
        }
    }

    @Override
    public void updateUI() {
        setUI(PlafManager.getUI(this));
    }

    @Override
    public String getUIClassID() {
        return UI_CLASS_ID;
    }

    public View getView() {
        return (View) SwingUtilities.getAncestorOfClass(View.class, this);
    }
}

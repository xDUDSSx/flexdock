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
package org.flexdock.plaf.theme;

import org.flexdock.plaf.IFlexViewComponentUI;
import org.flexdock.plaf.PropertySet;
import org.flexdock.plaf.icons.IconResource;
import org.flexdock.view.components.Button;
import org.flexdock.view.components.Titlebar;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author Christopher Butler
 */
public class ButtonUI extends BasicButtonUI implements IFlexViewComponentUI {
    public static final String BORDER = "border";
    public static final String BORDER_HOVER = "border.hover";
    public static final String BORDER_ACTIVE = "border.active";
    public static final String BORDER_ACTIVE_HOVER = "border.active.hover";
    public static final String BORDER_PRESSED = "border.pressed";

    protected PropertySet creationParameters;
    protected Border borderDefault;
    protected Border borderDefaultHover;
    protected Border borderActive;
    protected Border borderActiveHover;
    protected Border borderPressed;

    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        ButtonModel model = b.getModel();
        updateTooltip(b);

        boolean active = isParentActive(b);
        boolean pressed = isPressed(b);
        boolean hover = pressed || model.isRollover();

        paintBackground(g, b, active, hover, pressed);
        paintIcon(g, b);
        paintBorder(g, b);
    }

    protected void paintBackground(Graphics g, AbstractButton b, boolean active, boolean hover, boolean pressed) {
        // do nothing
    }

    protected void paintBorder(Graphics g, AbstractButton b) {
        Border border = getBorder(b);
        if(border!=null) {
            border.paintBorder(b, g, 0, 0, b.getWidth(), b.getHeight());
        }
    }

    protected Border getBorder(AbstractButton button) {
        if(isPressed(button)) {
            return getPressedBorder(button);
        }

        boolean active = isParentActive(button);
        if(button.getModel().isRollover()) {
            return getHoverBorder(button, active);
        }

        return getDefaultBorder(button, active);
    }

    protected Border getPressedBorder(AbstractButton button) {
        Border border = borderPressed;
        if(border==null) {
            border = getHoverBorder(button, true);
        }
        return border;
    }

    protected Border getHoverBorder(AbstractButton button, boolean active) {
        Border border = active? borderActiveHover: borderDefaultHover;
        if(border==null) {
            border = getDefaultBorder(button, active);
        }
        return border;
    }

    protected Border getDefaultBorder(AbstractButton button, boolean active) {
        return active? borderActive: borderDefault;
    }

    protected void paintIcon(Graphics g, AbstractButton b) {
        Icon icon = getIcon(b);
        if(icon==null) {
            return;
        }

        int h = icon.getIconHeight();
        int w = icon.getIconWidth();
        int x = b.getWidth()/2 - w/2;
        int y = b.getHeight()/2 - h/2;

        icon.paintIcon(b, g, x, y);
    }

    protected Icon getIcon(AbstractButton button) {
        boolean active = isParentActive(button);

        if(!button.isEnabled() || !button.getModel().isEnabled()) {
            return getDisabledIcon(button, active);
        }

        if(isPressed(button)) {
            return getPressedIcon(button);
        }

        if(button.getModel().isRollover()) {
            return getHoverIcon(button, active);
        }

        return getDefaultIcon(button, active);
    }

    protected Icon getDisabledIcon(AbstractButton button, boolean active) {
        Icon icon = button.getDisabledIcon();
        if(icon==null) {
            icon = getActionIcon(button, false, active, false);
        }
        if(icon==null) {
            icon = getDefaultIcon(button, active);
        }
        return icon;
    }

    protected Icon getPressedIcon(AbstractButton button) {
        Icon icon = button.getPressedIcon();
        if(icon==null) {
            icon = getActionIcon(button, true, true, true);
        }
        if(icon==null) {
            icon = getHoverIcon(button, true);
        }
        return icon;
    }

    protected Icon getHoverIcon(AbstractButton button, boolean active) {
        Icon icon = button.getRolloverIcon();
        if(icon==null) {
            icon = getActionIcon(button, false, active, true);
        }
        if(icon==null) {
            icon = getDefaultIcon(button, active);
        }
        return icon;
    }

    protected Icon getDefaultIcon(AbstractButton button, boolean active) {
        Icon icon = button.getIcon();
        if(icon==null) {
            icon = getActionIcon(button, false, active, false);
        }
        return icon;
    }

    protected Icon getActionIcon(AbstractButton button, boolean pressed, boolean active, boolean hover) {
        Action action = button.getAction();
        IconResource resource = action==null? null: (IconResource)action.getValue(ICON_RESOURCE);
        if(resource==null) {
            return null;
        }

        boolean disabled = !button.isEnabled() || !button.getModel().isEnabled();
        boolean selected = button.isSelected();


        if(pressed && !disabled) {
            Icon icon = selected? resource.getIconSelectedPressed(): null;
            if(icon==null) {
                icon = resource.getIconPressed();
            }
            return icon;
        }

        if(active) {
            if(disabled) {
                Icon icon = selected? resource.getIconSelectedActiveDisabled(): null;
                if(icon==null) {
                    icon = resource.getIconActiveDisabled();
                }
                return icon==null? resource.getIconActive(): icon;
            }

            if(hover) {
                Icon icon = selected? resource.getIconSelectedActiveHover(): null;
                if(icon==null) {
                    icon = resource.getIconActiveHover();
                }
                return icon;
            }

            Icon icon = selected? resource.getIconSelectedActive(): null;
            if(icon==null) {
                icon = resource.getIconActive();
            }
            return icon;
        }

        if(disabled) {
            Icon icon = selected? resource.getIconSelectedDisabled(): null;
            if(icon==null) {
                icon = resource.getIconDisabled();
            }
            return icon==null? resource.getIcon(): icon;
        }

        if(hover) {
            Icon icon = selected? resource.getIconSelectedHover(): null;
            if(icon==null) {
                icon = resource.getIconHover();
            }
            return icon;
        }

        Icon icon = selected? resource.getIconSelected(): null;
        if(icon==null) {
            icon = resource.getIcon();
        }
        return icon;
    }

    protected boolean isPressed(AbstractButton button) {
        ButtonModel model = button.getModel();
        return model.isArmed() && model.isPressed();
    }

    protected boolean isParentActive(AbstractButton button) {
        Container parent = button.getParent();
        return parent instanceof Titlebar? ((Titlebar)parent).isActive(): false;
    }


    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton)c;
        button.setRolloverEnabled(true);
        button.setRequestFocusEnabled(false);
        button.setOpaque(false);
        button.setBorder(null);

        // add the toggle listener
        button.addItemListener(new ToggleListener());
        updateTooltip(button);
    }

    @Override
    public void uninstallUI(JComponent c) {
        AbstractButton button = (AbstractButton)c;
        super.uninstallUI(c);
    }

    @Override
    protected void installKeyboardActions(AbstractButton b) {
        // do nothing
    }

    @Override
    protected BasicButtonListener createButtonListener(AbstractButton b) {
        return new ButtonListener(b);
    }

    protected static class ButtonListener extends BasicButtonListener {
        protected ButtonListener(AbstractButton b) {
            super(b);
        }
        @Override
        public void focusGained(FocusEvent e) {
        }
        @Override
        public void focusLost(FocusEvent e) {
        }
    }

    public void setBorderActive(Border borderActive) {
        this.borderActive = borderActive;
    }

    public void setBorderActiveHover(Border borderActiveHover) {
        this.borderActiveHover = borderActiveHover;
    }

    public void setBorderDefault(Border borderDefault) {
        this.borderDefault = borderDefault;
    }

    public void setBorderDefaultHover(Border borderDefaultHover) {
        this.borderDefaultHover = borderDefaultHover;
    }

    public void setBorderPressed(Border borderPressed) {
        this.borderPressed = borderPressed;
    }

    @Override
    public PropertySet getCreationParameters() {
        return creationParameters;
    }
    @Override
    public void setCreationParameters(PropertySet creationParameters) {
        this.creationParameters = creationParameters;
        initializeCreationParameters();
    }

    @Override
    public void initializeCreationParameters() {
        setBorderDefault(creationParameters.getBorder(BORDER));
        setBorderDefaultHover(creationParameters.getBorder(BORDER_HOVER));
        setBorderActive(creationParameters.getBorder(BORDER_ACTIVE));
        setBorderActiveHover(creationParameters.getBorder(BORDER_ACTIVE_HOVER));
        setBorderPressed(creationParameters.getBorder(BORDER_PRESSED));
    }

    private class ToggleListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if(e.getStateChange()!=ItemEvent.SELECTED && e.getStateChange()!=ItemEvent.DESELECTED) {
                return;
            }

            Button button = (Button)e.getSource();
            updateTooltip(button);
        }
    }

    private void updateTooltip(AbstractButton button) {
        Action action = button.getAction();
        if (action == null) {
            return;
        }

        String toolTip = (String) action.getValue(Action.SHORT_DESCRIPTION);
        if (toolTip != null) {
            return;
        }

        IconResource resource = action == null ? null : (IconResource) action.getValue(ICON_RESOURCE);
        if (resource == null) {
            String s = button.getToolTipText();
            return;
        }

        toolTip = button.isSelected() ? resource.getTooltipSelected() : resource.getTooltip();
        if (toolTip == null) {
            toolTip = resource.getTooltip();
        }

        if (toolTip != null) {
            button.setToolTipText(toolTip);
        }
    }
}
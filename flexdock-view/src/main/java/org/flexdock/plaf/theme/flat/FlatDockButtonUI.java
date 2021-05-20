package org.flexdock.plaf.theme.flat;

import org.flexdock.docking.DockingConstants;
import org.flexdock.plaf.icons.FlexIcons;
import org.flexdock.plaf.theme.ButtonUI;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class FlatDockButtonUI extends ButtonUI {
    @Override
    protected void paintBackground(Graphics g, AbstractButton b, boolean active, boolean hover, boolean pressed) {
        super.paintBackground(g, b, active, hover, pressed);
        Color background;
        if (active)
            background = UIManager.getColor("InternalFrame.activeTitleBackground");
        else
            background = UIManager.getColor("InternalFrame.inactiveTitleBackground");
        g.setColor(background);

        if (hover) {
            g.setColor(Optional.ofNullable(UIManager.getColor("Button.toolbar.hoverBackground")).orElse(background.brighter()));
        }

        int arc = Optional.ofNullable((Integer) UIManager.get("Button.arc")).orElse(5);
        g.fillRoundRect(0, 0, b.getWidth() - 1, b.getHeight() - 1, arc, arc);
    }

    @Override
    protected void paintIcon(Graphics g, AbstractButton b) {
        super.paintIcon(g, b);
    }

    @Override
    protected Icon getActionIcon(AbstractButton button, boolean pressed, boolean active, boolean hover) {
        Action action = button.getAction();

        boolean disabled = !button.isEnabled() || !button.getModel().isEnabled();
        boolean selected = button.isSelected();

        Icon icon = null;

        switch ((String) action.getValue(Action.NAME)) {
            case DockingConstants.PIN_ACTION:
                icon = FlexIcons.minimizeIcon;
                break;
            case DockingConstants.CLOSE_ACTION:
                icon = FlexIcons.closeIcon;
                break;
            case DockingConstants.MAXIMIZE_ACTION:
                icon = FlexIcons.maximizeIcon;
        }

        return icon;

        /*if(pressed && !disabled) {
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
        return icon;*/
    }
}

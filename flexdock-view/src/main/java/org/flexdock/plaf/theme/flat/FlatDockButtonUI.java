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

    /**
     * Flat theme icons are loaded directly in code,
     * as opposed to loading from IconResources themselves (and further from the theme xml)
     */
    @Override
    protected Icon getActionIcon(AbstractButton button, boolean pressed, boolean active, boolean hover) {
        Action action = button.getAction();

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
    }
}

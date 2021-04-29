package org.flexdock.plaf.theme.flat;

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
        g.fillRoundRect(0, 0, b.getWidth(), b.getHeight(), arc, arc);
    }
}

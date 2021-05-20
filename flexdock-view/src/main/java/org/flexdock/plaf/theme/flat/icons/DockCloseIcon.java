package org.flexdock.plaf.theme.flat.icons;

import com.formdev.flatlaf.ui.FlatUIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

public class DockCloseIcon implements Icon {
    protected final float crossPlainSize = 8f;
    protected final float crossFilledSize = crossPlainSize;
    protected final float closeCrossLineWidth = 1.5f;

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;

        int width = c.getWidth();
        int height = c.getHeight();

        // set cross color
        Color fg = UIManager.getColor("Button.foreground");
        g.setColor(FlatUIUtils.deriveColor(fg, c.getForeground()));

        float mx = width / 2;
        float my = height / 2;
        float r = crossFilledSize / 2;

        // paint cross
        Path2D path = new Path2D.Float(Path2D.WIND_EVEN_ODD);
        path.append(new Line2D.Float(mx - r, my - r, mx + r, my + r), false);
        path.append(new Line2D.Float(mx - r, my + r, mx + r, my - r), false);
        g2d.setStroke(new BasicStroke(closeCrossLineWidth));
        //g2d.draw(path);
        g2d.drawLine((int) Math.round(mx - r), (int) Math.round(my - r), (int) Math.round(mx + r), (int) Math.round(my + r));
        g2d.drawLine((int) Math.round(mx - r), (int) Math.round(my + r), (int) Math.round(mx + r), (int) Math.round(my - r));
    }

    @Override
    public int getIconWidth() {
        return 16;
    }

    @Override
    public int getIconHeight() {
        return 16;
    }
}

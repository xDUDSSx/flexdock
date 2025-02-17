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
package org.flexdock.plaf.theme.metal;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import javax.swing.plaf.metal.MetalLookAndFeel;

import org.flexdock.plaf.theme.TitlebarUI;
import org.flexdock.view.components.Button;
import org.flexdock.view.components.Titlebar;

/**
 * @author Claudio Romano
 */
public class MetalTitlebarUI extends TitlebarUI {

    @Override
    protected void paintBackground(Graphics g, Titlebar titlebar) {
        Rectangle paintArea = getPaintRect( titlebar);
        g.translate(paintArea.x, paintArea.y);
        g.setColor(getBackgroundColor( titlebar.isActive()));
        g.fillRect(0, 0, paintArea.width, paintArea.height);
        g.translate(-paintArea.x, -paintArea.y);

        Rectangle paintAreaer = getPainterRect( g, titlebar);
        g.translate(paintAreaer.x, paintAreaer.y);
        painter.paint(g, paintAreaer.width, paintAreaer.height, titlebar.isActive(), titlebar);
        g.translate(-paintAreaer.x, -paintAreaer.y);
    }

    @Override
    protected Color getBackgroundColor(boolean active) {
        return active ? MetalLookAndFeel.getPrimaryControl() : MetalLookAndFeel.getControl();
    }

    protected int getPainterX(Graphics g, Titlebar titlebar) {
        int paintX = getTextLocation( getIconRect( titlebar));
        Rectangle2D rect = g.getFontMetrics(super.getFont()).getStringBounds(titlebar.getText(),g);
        paintX += (int)rect.getWidth();
        paintX +=10;
        return paintX;
    }

    protected int getPainterWidth(Graphics g, Titlebar titlebar) {
        int buttonWidth = 0;
        Component[] c = titlebar.getComponents();
        for (int i = 0; i < c.length; i++) {
            if (!(c[i] instanceof Button)) {
                continue;
            }

            Button b = (Button) c[i];
            buttonWidth = b.getHeight();
            break;
        }

        int paintY = (getButtonMargin() + buttonWidth) * 2;
        paintY += 5;
        return paintY;
    }

    private Rectangle getPainterRect( Graphics g, Titlebar titlebar) {
        Rectangle painterRectangle = getPaintRect( titlebar);
        painterRectangle.x = getPainterX( g, titlebar);

        painterRectangle.width = titlebar.getWidth()-painterRectangle.x;
        painterRectangle.width -= getPainterWidth( g, titlebar);

        painterRectangle.y +=3;
        painterRectangle.height -= 6;
        return painterRectangle;
    }
}

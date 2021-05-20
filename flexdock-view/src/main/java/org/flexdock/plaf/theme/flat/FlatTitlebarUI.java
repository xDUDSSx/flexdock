package org.flexdock.plaf.theme.flat;

import com.formdev.flatlaf.ui.FlatUIUtils;
import org.flexdock.plaf.theme.TitlebarUI;
import org.flexdock.view.components.Titlebar;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class FlatTitlebarUI extends TitlebarUI {
    @Override
    public Action getAction(String actionKey) {
        return super.getAction(actionKey);
    }

    @Override
    public void configureAction(Action action) {
        //Action icons are configured in the FlatDockButtonUI
        return;
    }

    @Override
    public void paint(Graphics g, JComponent jc) {
        Titlebar titlebar = (Titlebar) jc;
        super.paint(g, jc);
        try {
            if (titlebar.getView().getContentPane() instanceof JComponent) {
                JComponent cp = (JComponent) titlebar.getView().getContentPane();
                if (cp.getBorder() == null) {
                    cp.setBorder(new MatteBorder(0, 1, 1, 1, UIManager.getColor("InternalFrame.activeBorderColor")));
                }
            }
        } catch (NullPointerException e) {}
    }

    @Override
    protected void paintBackground(Graphics g, Titlebar titlebar) {
        Rectangle paintArea = getPaintRect(titlebar);
        g.setColor(getBackgroundColor(titlebar.isActive()));
        g.fillRect(paintArea.x, paintArea.y, paintArea.width, paintArea.height);
    }

    @Override
    protected void paintIcon(Graphics g, Titlebar titlebar) {
        super.paintIcon(g, titlebar);
    }

    @Override
    protected void paintTitle(Graphics g, Titlebar titlebar) {
        if (titlebar.getText() == null) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g;

        g2.setFont(getFont());

        Rectangle iconRect = getIconRect(titlebar);
        Rectangle paintRect = getPaintRect(titlebar);

        FontMetrics fm = g.getFontMetrics();
        int x = getTextLocation(iconRect);
        int y = (paintRect.height + fm.getAscent() - fm.getLeading() -
                fm.getDescent()) / 2;

        Color c = getFontColor(titlebar.isActive());
        g2.setColor(c);
        g.translate(paintRect.x, paintRect.y);
        FlatUIUtils.drawString(titlebar, g, titlebar.getText(), x, y);
        g.translate(-paintRect.x, -paintRect.y);
    }

    @Override
    protected void paintBorder(Graphics g, Titlebar titlebar) {
        super.paintBorder(g, titlebar);
    }

    @Override
    protected Border getBorder(Titlebar titlebar) {
        return new LineBorder(UIManager.getColor("InternalFrame.activeBorderColor"), 1);
    }

    @Override
    public Font getFont() {
        return UIManager.getFont("InternalFrame.titleFont");
    }

    @Override
    protected Color getFontColor(boolean active) {
        return UIManager.getColor("InternalFrame.activeTitleForeground");
    }

    @Override
    protected Color getBackgroundColor(boolean active) {
        if (active)
            return UIManager.getColor("InternalFrame.activeTitleBackground");
        else
            return UIManager.getColor("InternalFrame.inactiveTitleBackground");
    }

    public String getPreferredButtonUI() {
        return "flat";
    }

    public int getButtonMargin() {
        if (UIManager.get("Button.toolbar.margin") != null) {
            return ((Insets) UIManager.get("Button.toolbar.margin")).top;
        }
        return 3;
    }
}

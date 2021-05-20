package org.flexdock.plaf.icons;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;

public class FlexIcons {
    public static FlatSVGIcon closeIcon;
    public static FlatSVGIcon minimizeIcon;
    public static FlatSVGIcon maximizeIcon;

    static {
        closeIcon = loadFlatSVGDockIcon("svg/close_notification.svg");
        minimizeIcon = loadFlatSVGDockIcon("svg/minimize_notification.svg");
        maximizeIcon = loadFlatSVGDockIcon("svg/maximize.svg");
    }

    protected static FlatSVGIcon loadFlatSVGDockIcon(String path) {
        FlatSVGIcon icon = new FlatSVGIcon(path, 16, 16);
        icon.setColorFilter(new FlatSVGIcon.ColorFilter(color -> UIManager.getColor("Button.foreground")));
        return icon;
    }
}

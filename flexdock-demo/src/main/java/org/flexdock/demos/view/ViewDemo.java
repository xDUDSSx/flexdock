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
package org.flexdock.demos.view;

import org.flexdock.demos.util.DemoUtility;
import org.flexdock.demos.util.VSNetStartPage;
import org.flexdock.docking.DockingConstants;
import org.flexdock.docking.DockingManager;
import org.flexdock.plaf.icons.FlexIcons;
import org.flexdock.util.SwingUtility;
import org.flexdock.view.View;
import org.flexdock.view.Viewport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Christopher Butler
 */
public class ViewDemo extends JFrame implements DockingConstants {

    public static void main(String[] args) {
        /*SwingUtility.setPlaf("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//                SwingUtility.setPlaf("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
//                SwingUtility.setPlaf("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");*/
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                startup();
            }
        });
    }

    private static void startup() {
        // turn on floating support
        DockingManager.setFloatingEnabled(true);
        //EffectsManager.setPreview(new AlphaPreview());

        JFrame f = new ViewDemo();
        f.setSize(800, 600);
        SwingUtility.centerOnScreen(f);
        DemoUtility.setCloseOperation(f);
        f.setVisible(true);
    }

    public ViewDemo() {
        super("Viewport Demo");
        setContentPane(createContentPane());
    }

    private JPanel createContentPane() {
        JPanel p = new JPanel(new BorderLayout(0, 0));
        //p.setBorder(new EmptyBorder(5, 5, 5, 5));

        Viewport viewport = new Viewport();
        p.add(viewport, BorderLayout.CENTER);

        View startPage = createStartPage();
        View view1 = createView("solution.explorer", "Solution Explorer");
        View view2 = createView("task.list", "Task List");
        View view3 = createView("class.view", "Class View");
        View view4 = createView("message.log", "Message Log");

        view1.setIcon(FlexIcons.heartIcon);
        view2.setIcon(FlexIcons.homeIcon);
        view3.setIcon(FlexIcons.helmetIcon);
        view4.setIcon(FlexIcons.settingsIcon);

        viewport.dock(startPage);
        startPage.dock(view1, WEST_REGION, .3f);
        startPage.dock(view2, SOUTH_REGION, .3f);
        startPage.dock(view4, EAST_REGION, .3f);
        view1.dock(view3, SOUTH_REGION, .3f);

        return p;
    }

    private View createView(String id, String text) {
        View view = new View(id, text);
        view.addAction(CLOSE_ACTION);
        view.addAction(MAXIMIZE_ACTION);
        view.addAction(PIN_ACTION);

        AbstractAction e = new AbstractAction("CustomViewAction", FlexIcons.homeIcon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Custom flexdock view action executed at " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + "!");
            }
        };
        e.putValue(Action.SHORT_DESCRIPTION, "Custom action tooltip");
        view.addAction(e);

        JPanel p = new JPanel();
        //p.setBackground(Color.WHITE);
        //p.setBorder(new LineBorder(Color.GRAY, 1));

        JTextField t = new JTextField(text);
        t.setPreferredSize(new Dimension(100, 20));
        p.add(t);

        view.setContentPane(p);

        //view.setTerritoryBlocked(CENTER_REGION, true);
        //view.setTitlebar(null);

        return view;
    }

    private View createStartPage() {
        String id = "startPage";
        View view = new View(id, "Welcome");
        view.setTerritoryBlocked(CENTER_REGION, true);
        //view.setTitlebar(null);
        view.setContentPane(new VSNetStartPage());
        return view;
    }

}
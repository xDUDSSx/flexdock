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
package org.flexdock.docking;

import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

/**
 * A collection of constants used by flexdock.
 *
 * @author Christopher Butler
 */
public interface DockingConstants {
    String DOCKING_ID = "docking-id";

    /**
     * Used when a sibling does not specify a size preference when docking
     * relative to another dockable.
     */
    float UNSPECIFIED_SIBLING_PREF = -1F;

    /**
     * A constant for "initializing" an {@code int} to a safe (ie unusable
     * value).
     */
    int UNINITIALIZED = -1;

    /**
     * A constant for "initializing" an {@code float} to a safe (ie unusable
     * value).
     */
    // TODO should this be Float.NaN?
    float UNINITIALIZED_RATIO = -1F;

    String PERMANENT_FOCUS_OWNER = "permanentFocusOwner";

    /**
     * The property name for handling window activation changes.
     *
     * @see java.beans.PropertyChangeListener
     * @see Dockable#addPropertyChangeListener(java.beans.PropertyChangeListener)
     */
    String ACTIVE_WINDOW = "activeWindow";

    String MOUSE_PRESSED = "mousePressed";

    /**
     * A constant representing a "pin/unpin" action. Such actions are added to
     * title bars to enable the pinning/unpinning effect. This is also used as
     * the name for the property when a {@code Dockable} has been
     * pinned/unpinned.
     *
     * @see java.beans.PropertyChangeListener
     * @see Dockable#addPropertyChangeListener(java.beans.PropertyChangeListener)
     */
    String PIN_ACTION = "pin";

    /**
     * A constant representing a "close" action. Such actions are added to title
     * bars to enable the close effect.
     */
    String CLOSE_ACTION = "close";

    /**
     * A constant represnting a "maximize" action. Such actions are added to title
     * bars to enable the maximize effect.
     */
    String MAXIMIZE_ACTION = "maximize";

    /**
     * Box-orientation constant used to specify the top of a box.
     *
     * @see SwingConstants#TOP
     */
    int TOP = SwingConstants.TOP;

    /**
     * Box-orientation constant used to specify the left side of a box.
     *
     * @see SwingConstants#LEFT
     */
    int LEFT = SwingConstants.LEFT;

    /**
     * Box-orientation constant used to specify the bottom of a box.
     *
     * @see SwingConstants#BOTTOM
     */
    int BOTTOM = SwingConstants.BOTTOM;

    /**
     * Box-orientation constant used to specify the right side of a box.
     *
     * @see SwingConstants#RIGHT
     */
    int RIGHT = SwingConstants.RIGHT;

    /**
     * The central position in an area. Used for both compass-direction
     * constants (NORTH, etc.).
     *
     * @see SwingConstants#CENTER
     */
    int CENTER = SwingConstants.CENTER;

    /**
     * Horizontal split indicates the {@code Component}s are split along the x
     * axis. For example the two {@code Component}s will be split one to the
     * left of the other.
     *
     * @see JSplitPane#HORIZONTAL_SPLIT
     */
    int HORIZONTAL = JSplitPane.HORIZONTAL_SPLIT;

    /**
     * Vertical split indicates the {@code Component}s are split along the y
     * axis. For example the two {@code Component}s will be split one on top of
     * the other.
     *
     * @see JSplitPane#VERTICAL_SPLIT
     */
    int VERTICAL = JSplitPane.VERTICAL_SPLIT;

    /**
     * A constant representing the "region" property used when a
     * {@code Dockable} region has been changed.
     *
     * @see java.beans.PropertyChangeListener
     * @see Dockable#addPropertyChangeListener(java.beans.PropertyChangeListener)
     */
    String REGION = "region";

    /**
     * The central region. The regional equivalent for {@linkplain #CENTER}.
     *
     * @see #CENTER
     */
    String CENTER_REGION = "CENTER";

    /**
     * The east region. The regional equivalent for {@linkplain #RIGHT}.
     *
     * @see #RIGHT
     */
    String EAST_REGION = "EAST";

    /**
     * The north region. The regional equivalent for {@linkplain #TOP}.
     *
     * @see #TOP
     */
    String NORTH_REGION = "NORTH";

    /**
     * The south region. The regional equivalent for {@linkplain #BOTTOM}.
     *
     * @see #BOTTOM
     */
    String SOUTH_REGION = "SOUTH";

    /**
     * The west region. The regional equivalent for {@linkplain #LEFT}.
     *
     * @see #LEFT
     */
    String WEST_REGION = "WEST";

    /**
     * An unknown region. This constant is typically used to initialize regions.
     */
    String UNKNOWN_REGION = "UNKNOWN";

    /**
     * A constant for enabling/disabling heavyweight dockables. If a system
     * property exists with this constant as a key and "true" as the value, then
     * heavyweight dockables are enabled.
     */
    String HEAVYWEIGHT_DOCKABLES = "heavyweight.dockables";

    /**
     * A system key whose value is a fully-qualified class name that is used to
     * create a persistence class for storing or loading persisted perspectives.
     */
    String DEFAULT_PERSISTENCE_KEY = "default.persist.key";
}

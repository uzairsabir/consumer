/**
 * Copyright (c) 2013, Redsolution LTD. All rights reserved.
 * <p/>
 * This file is part of Xabber project; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License, Version 3.
 * <p/>
 * Xabber is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License,
 * along with this program. If not, see http://www.gnu.org/licenses/.
 */
package org.vanguardmatrix.engine.android.data.notification;

import android.net.Uri;

import java.util.Collection;

/**
 * Provides list of notifications first of which should be shown.
 *
 * @param <T>
 * @author alexander.ivanov
 */
public interface NotificationProvider<T extends NotificationItem> {

    /**
     * @return List of notifications.
     */
    Collection<T> getNotifications();

    /**
     * @return Whether notification can be cleared.
     */
    boolean canClearNotifications();

    /**
     * Clear notifications.
     */
    void clearNotifications();

    /**
     * @return Sound for notification.
     */
    Uri getSound();

    /**
     * @return Audio stream type for notification.
     */
    int getStreamType();

    /**
     * @return Resource id with icon for notification bar.
     */
    int getIcon();

}

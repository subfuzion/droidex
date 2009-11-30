/*
 * Copyright (c) 2008-2009 CommonsWare, LLC
 * Portions Copyright (C) 2007-2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.commonsware.droidex.action;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OpenRecordingAction extends AbstractAction {
	/**
	 * Defines an <code>Action</code> object with a default
	 * description string and default icon.
	 */
	public OpenRecordingAction() {
	}

	/**
	 * Defines an <code>Action</code> object with the specified
	 * description string and a default icon.
	 */
	public OpenRecordingAction(String name) {
		super(name);
	}

	/**
	 * Defines an <code>Action</code> object with the specified
	 * description string and a the specified icon.
	 */
	public OpenRecordingAction(String name, Icon icon) {
		super(name, icon);
	}

	/**
	 * Invoked when an action occurs.
	 */
	public void actionPerformed(ActionEvent e) {
	}
}

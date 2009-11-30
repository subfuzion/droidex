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

package com.commonsware.droidex.ui;

import com.commonsware.droidex.Actions;

import javax.swing.*;

public class DroidExMenuBar extends JMenuBar {
	public DroidExMenuBar()
	{
		initFileMenu();
		initEditMenu();
		initHelpMenu();
	}

	void initFileMenu()
	{
		JMenu menu = new JMenu("File");
		menu.setMnemonic('F');
		this.add(menu);

		JMenuItem item;

		item = menu.add(Actions.OpenDeviceAction);
		item = menu.add(Actions.OpenRecordingAction);
		menu.addSeparator();

		item = menu.add(Actions.SettingsAction);
		menu.addSeparator();

		item = menu.add(Actions.ExitAction);
	}

	void initEditMenu()
	{
		JMenu menu = new JMenu("Edit");
		menu.setMnemonic('E');
		this.add(menu);

	}

	void initHelpMenu()
	{
		JMenu menu = new JMenu("Help");
		menu.setMnemonic('H');
		this.add(menu);

	}
}

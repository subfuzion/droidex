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

package com.commonsware.droidex;

import com.commonsware.droidex.action.*;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public final class Actions {
	public static Action OpenDeviceAction;
	public static Action OpenRecordingAction;
	public static Action SettingsAction;
	public static Action ExitAction;

	static
	{
		OpenDeviceAction = new OpenDeviceAction("Open Device...");
		OpenDeviceAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		OpenDeviceAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));

		OpenRecordingAction = new OpenRecordingAction("Open Recording...");
		OpenRecordingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
		OpenRecordingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));

		SettingsAction = new SettingsAction("Settings...");
		SettingsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		SettingsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));

		ExitAction = new ExitAction("Exit");
		ExitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		ExitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
	}
}

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

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.commonsware.droidex.image.*;
import com.commonsware.droidex.ui.DeviceScreenView;
import com.commonsware.droidex.ui.DroidExMenuBar;
import com.commonsware.droidex.ui.DroidExToolBar;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * Displays a copy of the device's screen.
 */
public class DroidEx extends JFrame {
	private IDevice device = null;
	private StartupOptions options;
	private RenderSettings settings;
	private DeviceScreenView deviceScreenView;

	public DroidEx(StartupOptions options) throws IOException {
		this.options = options;

		AndroidDebugBridge bridge = AndroidDebugBridge.createBridge();
		sleep(1000); // allow sync

		try {
			initDevice(bridge);
			initRenderSettings();
			initDeviceScreenView();
			initFrame(deviceScreenView);
		}
		finally {
			AndroidDebugBridge.disconnectBridge();
		}
	}

	private void initDevice(AndroidDebugBridge bridge) {
		IDevice[] devices = bridge.getDevices();
		device = devices[0];
	}

	private void initRenderSettings() {
		settings = new RenderSettings();
		settings.setScale(options.scale);
		settings.setOrientation(options.orientation);
	}

	private void initDeviceScreenView() {
		IImageProvider imageProvider = new DeviceScreenImageProvider(device);
		imageProvider.addImageFilter(new ScaleImageFilter(settings));
		imageProvider.addImageFilter(new RotateImageFilter(settings));
		imageProvider.addImageFilter(new RecordImageFilter(settings));

		deviceScreenView = new DeviceScreenView(imageProvider);
		deviceScreenView.setFrameRate(options.frameRate);
		deviceScreenView.play();

		settings.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				deviceScreenView.repaint();
			}
		});
	}

	private void initFrame(JPanel panel) {
		initLookAndFeel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(new DroidExMenuBar());
		initChildComponents(panel);
		this.pack();
		centerFrame(this);
	}

	private void initLookAndFeel()
	{
		try {
			try {
				JFrame.setDefaultLookAndFeelDecorated(true);
				for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					if ("Nimbus".equals(info.getName())) {
						UIManager.setLookAndFeel(info.getClassName());
						break;
					}
				}
			} catch (Exception e) {
				UIManager.setLookAndFeel(
						UIManager.getSystemLookAndFeelClassName());
			}
		} catch (Exception e) {
			JFrame.setDefaultLookAndFeelDecorated(false);
		}
	}

	private void initChildComponents(JPanel panel) {
		setLayout(new BorderLayout());
		add(new DroidExToolBar(), BorderLayout.NORTH);
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setSize(panel.getPreferredSize());
		this.add(new JScrollPane(panel));
	}

	private static void centerFrame(Component component) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		component.setLocation(
				(screenSize.width - component.getWidth()) / 2,
				(screenSize.height - component.getHeight()) / 2);
	}

	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (Throwable t) {
		}
	}

	public static void main(String[] args) throws IOException {
		StartupOptions options = new StartupOptions();
		CmdLineParser parser = new CmdLineParser(options);

		try {
			// options contain the parsed args
			parser.parseArgument(args);
			DroidEx droidex = new DroidEx(options);
			droidex.setVisible(true);

		} catch (CmdLineException e) {
			System.err.println(e.getMessage());
			System.err.println("droidex [options...]");
			parser.printUsage(System.err);
			return;
		}
	}
}

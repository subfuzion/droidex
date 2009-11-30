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

import com.commonsware.droidex.image.IImageProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class DeviceScreenView extends JPanel {
	public static final int MAX_FRAMERATE = 20;
	private IImageProvider imageProvider;
	private int frameRate;
	private boolean stopRequested;
	private Thread playThread;
	private Object monitor = new Object();

	public DeviceScreenView() {
	}

	public DeviceScreenView(IImageProvider imageProvider) {
		setImageProvider(imageProvider);
	}

	public int getFrameRate() {
		synchronized (monitor) {
			return this.frameRate;
		}
	}

	public void setFrameRate(int frameRate) {
		if (frameRate >= 0 && frameRate <= MAX_FRAMERATE && frameRate != this.frameRate) {
			synchronized (monitor) {
				this.frameRate = frameRate;
				monitor.notify();
			}
		}
	}

	public void play() {
		stopRequested = false;

		if (playThread == null) {
			playThread = new Thread(new Runnable() {
				public void run() {
					while (!stopRequested) {
						if (getFrameRate() == 0) {
							try {
								synchronized (monitor) {
									monitor.wait();
								}
							} catch (InterruptedException e) {
								// notified
							}
						}
						int frameRate = getFrameRate();
						if (frameRate > 0 && !stopRequested) {
							repaint();
							try {
								Thread.sleep(1000L / frameRate); // frameRate guarranted != 0
							} catch (Throwable t) {
								// woken up
							}
						}
					}
					playThread = null;
				}
			});

			playThread.start();
		}
	}

	public void stop() {
		stopRequested = true;
	}

	public void setImageProvider(IImageProvider imageProvider) {
		this.imageProvider = imageProvider;

		try {
			BufferedImage image = imageProvider.getImage();
			setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		}
		catch (IOException e) {
			System.err.println("Exception fetching image: " + e.toString());
		}
	}

	protected void paintComponent(Graphics g) {
		if (imageProvider == null) {
			super.paintComponent(g);
			return;
		}

		try {
			BufferedImage image = imageProvider.getImage();

			if (image != null) {
				setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
				int x = (getWidth() - image.getWidth()) / 2;
				int y = (getHeight() - image.getHeight()) / 2;
				g.drawImage(image, x, y, this);
			}
		}
		catch (IOException e) {
			System.err.println("Exception fetching image: " + e.toString());
		}
	}
}

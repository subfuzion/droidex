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

import com.commonsware.droidex.image.Orientation;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Startup options parsed from command line arguments.
 */
public class StartupOptions {
	/**
	 * The device/emulator to connect to.
	 */
	@Option(name = "-d", aliases = "--device", usage = "the device or emulator to connect to")
	public String device;

	/**
	 * The scaling factor.
	 */
	@Option(name = "-s", aliases = "--scale", usage = "the amount to scale the image by")
	public Double scale = 1.5d;

	/**
	 * The initial rotation, should be "portrait" or "landscape".
	 */
	@Option(name = "-o", aliases = "--orientation", usage = "portrait | landscape")
	public Orientation orientation;

	@Option(name = "-f", aliases = "--framerate", usage = "the screen refresh rate (frames per second)")
	public int frameRate = 10;

	/**
	 * The directory to record images to.
	 */
	@Option(name = "-r", aliases = "--record", usage = "the directory to record images to (recording begins immediately)")
	public File record;

	/**
	 * For other command line arguments aside from options
	 */
	@Argument
	private List<String> arguments = new ArrayList<String>();
}

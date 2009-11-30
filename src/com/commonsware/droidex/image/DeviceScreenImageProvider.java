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

package com.commonsware.droidex.image;

import com.android.ddmlib.IDevice;
import com.android.ddmlib.RawImage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Provides device screen capture images after applying any specified image processing filters.
 */
public class DeviceScreenImageProvider implements IImageProvider {
	private IDevice device;
	private ArrayList<IImageFilter> imageFilters;

	/**
	 * Initializes this provider for image capture from the specified device.
	 *
	 * @param device The device (or device emulator).
	 */
	public DeviceScreenImageProvider(IDevice device) {
		assert device != null;
		this.device = device;
	}

	/**
	 * Adds an image filter to the list of filters to be applied (in add order).
	 *
	 * @param imageFilter A filter for image processing; can be added more than once
	 */
	public void addImageFilter(IImageFilter imageFilter) {
		if (imageFilters == null) {
			imageFilters = new ArrayList<IImageFilter>();
		}

		if (imageFilter != null) {
			imageFilters.add(imageFilter);
		}
	}

	/**
	 * Removes all occurrences of the image filter from the list of filters, if present.
	 *
	 * @param imageFilter The filter to be removed.
	 */
	public void removeImageFilter(IImageFilter imageFilter) {
		if (imageFilters == null || imageFilter == null) return;

		boolean removed = false;

		do {
			removed = imageFilters.remove(imageFilter);
		} while (removed);

		if (imageFilters.isEmpty()) {
			imageFilters = null;
		}
	}

	/**
	 * Captures a device screen shot, applying any filters that have been added to this provider.
	 *
	 * @return BufferedImage, or null if the device frame buffer was only partially read
	 * @throws IOException
	 */
	public BufferedImage getImage() throws IOException {
		RawImage rawImage = device.getScreenshot();

		if (rawImage == null) {
			// Indicates other problems, such as partial replies from the ADB frame-buffer,
			// or an unsupported version of the image format for the current ddmlib
			// (have to check log)
			return null;
		}

		BufferedImage image = new BufferedImage(rawImage.width, rawImage.height, BufferedImage.TYPE_INT_ARGB);

		int index = 0;
		int indexInc = rawImage.bpp >> 3;

		for (int y = 0; y < rawImage.height; y++) {
			for (int x = 0; x < rawImage.width; x++, index += indexInc) {
				int value = rawImage.getARGB(index);
				image.setRGB(x, y, value);
			}
		}

		if (imageFilters != null) {
			image = processFilters(image);
		}

		return image;
	}

	private BufferedImage processFilters(BufferedImage image) throws IOException {
		for (IImageFilter filter : imageFilters) {
			image = filter.render(image);
		}

		return image;
	}
}

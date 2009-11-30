/*
 * Copyright (c) 2008-2009 CommonsWare, LLC
 * Portions Copyright (C) 2007-2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.commonsware.droidex.image;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This filter copies the image to a record location for subsequent playback.
 */
public class RecordImageFilter extends AbstractImageFilter {
	private boolean enabled;
	private String recordPath;

	public RecordImageFilter(RenderSettings renderSettings) {
		super(renderSettings);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public BufferedImage render(BufferedImage srcImage) throws IOException {
		// TODO: implement
		return srcImage;
	}

	@Override
	protected void updateFilter() {
		RenderSettings renderSettings = getRenderSettings();
		if (renderSettings == null) return;

		// this.recordPath = renderSettings.getRecordPath();
	}
}

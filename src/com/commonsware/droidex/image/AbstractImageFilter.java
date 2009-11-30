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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public abstract class AbstractImageFilter implements IImageFilter {
	private RenderSettings renderSettings;

	public AbstractImageFilter(RenderSettings renderSettings) {
		setRenderSettings(renderSettings);
	}

	private PropertyChangeListener renderSettingsChangeListener = new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
			updateFilter();
		}
	};

	public RenderSettings getRenderSettings() {
		return renderSettings;
	}

	public void setRenderSettings(RenderSettings renderSettings) {
		if (this.renderSettings != renderSettings) {
			if (this.renderSettings != null) {
				this.renderSettings.removePropertyChangeListener(renderSettingsChangeListener);
			}

			this.renderSettings = renderSettings;
			this.renderSettings.addPropertyChangeListener(renderSettingsChangeListener);
			updateFilter();
		}
	}

	public abstract BufferedImage render(BufferedImage srcImage) throws IOException;

	/**
	 * Invoked whenever the RenderSettings are updated.
	 */
	protected abstract void updateFilter();
}

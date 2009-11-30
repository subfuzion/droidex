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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class RenderSettings {

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	private double scale = 1.5d;

	private Orientation orientation = Orientation.PORTRAIT;

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public PropertyChangeListener[] getPropertyChangeListeners() {
		return this.propertyChangeSupport.getPropertyChangeListeners();
	}

	public double getScale() {
		return this.scale;
	}

	public void setScale(double scale) {
		if (this.scale != scale) {
			double oldValue = this.scale;
			this.scale = scale;
			propertyChangeSupport.firePropertyChange("scale", oldValue, scale);
		}
	}

	public Orientation getOrientation() {
		return this.orientation;
	}

	public void setOrientation(Orientation orientation) {
		if (this.orientation != orientation) {
			Orientation oldValue = this.orientation;
			this.orientation = orientation;
			propertyChangeSupport.firePropertyChange("orientation", oldValue, orientation);
		}
	}
}

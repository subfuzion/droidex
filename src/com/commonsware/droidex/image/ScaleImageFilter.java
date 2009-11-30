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

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This filter scales an image as specified by RenderSettings scale.
 */
public class ScaleImageFilter extends AbstractImageFilter {
	private AffineTransformOp op;

	public ScaleImageFilter(RenderSettings renderSettings) {
		super(renderSettings);
	}

	@Override
	public BufferedImage render(BufferedImage srcImage) throws IOException {
		return (op != null) ? op.filter(srcImage, null) : srcImage;
	}

	@Override
	protected void updateFilter() {
		RenderSettings renderSettings = getRenderSettings();
		if (renderSettings == null) return;

		double scale = renderSettings.getScale();
		AffineTransform tx = AffineTransform.getScaleInstance(scale, scale);

		op = new AffineTransformOp(
				tx,
				new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC));
	}
}

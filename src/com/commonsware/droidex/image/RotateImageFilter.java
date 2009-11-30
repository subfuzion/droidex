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
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This filter rotates an image as specified by RenderSettings orientation.
 */
public class RotateImageFilter extends AbstractImageFilter {
	private AffineTransformOp op;

	public RotateImageFilter(RenderSettings renderSettings) {
		super(renderSettings);
	}

	@Override
	public BufferedImage render(BufferedImage srcImage) throws IOException {
		if (op == null) return srcImage;

		return srcImage;
	}

	@Override
	protected void updateFilter() {
		RenderSettings renderSettings = getRenderSettings();
		if (renderSettings == null) {
			op = null;
			return;
		}
	}

	private AffineTransform findTranslation(AffineTransform at, BufferedImage bi) {
		if (bi == null) return null;

		Point2D p2din, p2dout;

		p2din = new Point2D.Double(0.0, 0.0);
		p2dout = at.transform(p2din, null);
		double ytrans = p2dout.getY();

		p2din = new Point2D.Double(0, bi.getHeight());
		p2dout = at.transform(p2din, null);
		double xtrans = p2dout.getX();

		AffineTransform tat = new AffineTransform();
		tat.translate(-xtrans, -ytrans);
		return tat;
	}

	private double orientationToRadians(Orientation orientation) {
		double degrees = orientation == orientation.LANDSCAPE ? -90 : 0;
		return degreesToRadians(degrees);
	}

	/**
	 * Convert degrees to radians.
	 *
	 * @param rotationAngle in degrees. Positive degrees rotate in a clockwise direction; negative, counter-clockewise.
	 * @return
	 */
	private double degreesToRadians(double rotationAngle) {
		return rotationAngle * Math.PI / 180.0;
	}
}

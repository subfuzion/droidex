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

import java.awt.*;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.geom.AffineTransform;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.RawImage;

public class DroidEx extends JPanel {
	private BufferedImage image=null;
	private Dimension size=new Dimension();
	private IDevice device=null;
	private JFrame f=null;
	private boolean firstRun=true;
	private double scale=1.5;
	private BufferedImageOp op=null;
	
	public DroidEx(double scale) throws IOException {
		this.scale=scale;
		
		AffineTransform tx=AffineTransform.getScaleInstance(scale, scale);
		
		op=new AffineTransformOp(tx,
				new RenderingHints(RenderingHints.KEY_INTERPOLATION,
													 RenderingHints.VALUE_INTERPOLATION_BICUBIC));
		
		AndroidDebugBridge bridge=AndroidDebugBridge.createBridge();
		
		try {
			try { Thread.sleep(1000); } catch(Throwable t) {}		// allow sync
			
			IDevice[] devices=bridge.getDevices();
			
			device=devices[0];
			
			new Thread(new Runnable() {
				public void run() {
					pollForever();
				}
			}).start();
			
		}
		finally {
			AndroidDebugBridge.disconnectBridge();
		}
	}
	
	void pollForever() {
		while(true) {
			try {
				fetchImage();
				repaint();
			}
			catch (IOException e) {
				System.err.println("Exception fetching image: "+e.toString());
			}
			
			try { Thread.sleep(150); } catch(Throwable t) {}		// allow sync
		}
	}
	
	private void fetchImage() throws IOException {
		RawImage rawImage=device.getScreenshot();
		
		if (rawImage!=null) {
			image=new BufferedImage(rawImage.width, rawImage.height,
															BufferedImage.TYPE_INT_ARGB);
			
			int index = 0;
      int IndexInc = rawImage.bpp >> 3;

      for (int y = 0 ; y < rawImage.height ; y++) {
	      for (int x = 0 ; x < rawImage.width ; x++) {
					int value=rawImage.getARGB(index);

					index += IndexInc;
					image.setRGB(x, y, value);
				}
			}
			            
			image=op.filter(image, null);
			size.setSize(image.getWidth(), image.getHeight());
			
			if (firstRun) {
				JScrollPane pane=new JScrollPane(this);
				
				f=new JFrame();
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				pane.setSize(size);
				f.add(pane);
				f.setLocation(50, 50);
				f.setVisible(true);
				f.setSize(image.getWidth()+16, image.getHeight()+32);
				firstRun=false;
			}
		}
		else {
			System.err.println("Received invalid image");
		}
	}
	
	protected void paintComponent(Graphics g) {
		int x = (getWidth()-size.width)/2;
		int y = (getHeight()-size.height)/2;
		g.drawImage(image, x, y, this);
	}
	
	public Dimension getPreferredSize() {
		return(size);
	}
	
	public static void main(String[] args) throws IOException {
		double scale=(args.length>0 ? Double.valueOf(args[0]).doubleValue() : 1.0d);
		
		DroidEx droidex=new DroidEx(scale);
	}
}

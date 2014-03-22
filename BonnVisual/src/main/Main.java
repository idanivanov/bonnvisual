package main;

import java.io.IOException;

import org.opencv.core.Core;

import preprocessing.ImageProcessor;

public class Main {

	public static void main(String[] args) {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		ImageProcessor proc = new ImageProcessor("./resources/source.jpg");

		try {
			proc.convertToGrayscale();
			proc.enhanceContrast();
			proc.enhanceBrightness();
			proc.enhanceSharpness();
			proc.blur();
			proc.gaussianBlur();
			proc.createPyramid();
			proc.resize(400);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

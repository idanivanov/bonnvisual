package preprocessing;

import java.io.IOException;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;


/**
 * @author Revaz
 * This class has hard-coded behavior for demonstration purpose.
 * Feel free to modify / generalize for real usage.
 *
 */
public class ImageProcessor {

	private Mat source;
	private Mat destination;

	public ImageProcessor(String filePath) {

		this.source = Highgui.imread(filePath, Highgui.CV_LOAD_IMAGE_COLOR);
		this.destination = createMatFrom(this.source);
	}

	public void convertToGrayscale() throws IOException {

		destination = getGrayscale();

		Highgui.imwrite("./resources/001_grayscale.jpg", destination);
	}

	private Mat getGrayscale() {

		Mat gray = createMatFrom(source);
		Imgproc.cvtColor(source, gray, Imgproc.COLOR_RGB2GRAY);

		return gray;
	}

	public void enhanceContrast() {

		Mat grayscale = getGrayscale();
		Imgproc.equalizeHist(grayscale, destination);
		Highgui.imwrite("./resources/002_contrast.jpg", destination);
	}

	public void enhanceBrightness() {

		source.convertTo(destination, -1, 2.0, -50.0);
		Highgui.imwrite("./resources/003_bright.jpg", destination);
	}

	public void enhanceSharpness() {

		Imgproc.GaussianBlur(source, destination, new Size(0, 0), 10);
		Core.addWeighted(source, 1.5, destination, -0.5, 0, destination);
		Highgui.imwrite("./resources/004_sharp.jpg", destination);
	}

	public Mat createMatFrom(Mat sample) {

		return createMatFrom(sample, 1.0);
	}

	public Mat createMatFrom(Mat sample, double scale) {

		int rows = (int) (sample.rows() * scale);
		int cols = (int) (sample.cols() * scale);
		Mat destination = new Mat(rows, cols, sample.type());

		return destination;
	}

	public void createPyramid() {

		downscale(4);
	}

	private void downscale(int times) {
		Mat temp = createMatFrom(source);
		source.copyTo(temp);
		int downscale = 1;
		
		for (int i = 0; i < times; ++i) {
			destination = createMatFrom(temp, 0.5);
			
			downscale *= 2;

			Imgproc.pyrDown(temp, destination, new Size(destination.cols(), destination.rows()));
			Highgui.imwrite("./resources/007_downscaledBy" + downscale + ".jpg", destination);
			
			temp = destination;
		}
	}

	public void blur() {
		
		Imgproc.blur(source, destination, new Size(8, 8));
		Highgui.imwrite("./resources/005_blur.jpg", destination);
	}
	
	public void gaussianBlur() {
		
		Imgproc.GaussianBlur(source, destination, new Size(0, 0), 10);
		Highgui.imwrite("./resources/006_GaussianBlur.jpg", destination);
	}

	public void resize(int size) {
		
		double ratio = (double)(source.rows()) / (double)(source.cols());
		Imgproc.resize(source, destination, new Size(size, size * ratio));
		Highgui.imwrite("./resources/008_resized.jpg", destination);
	}

}

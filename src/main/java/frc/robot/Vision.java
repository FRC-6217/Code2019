/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.opencv.core.Point;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Core;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

/**
 * Add your docs here.
 */
public class Vision implements Runnable {


public void run() {
	//Create Camera object
    UsbCamera camera = new UsbCamera("cam1", 1);

	//Set brightness and exposure-
	camera.setBrightness(1);
    camera.setExposureManual(50);

	//Start capturing video
	camera = CameraServer.getInstance().startAutomaticCapture();

	//Set resolution
    camera.setResolution(320, 240); //use 320, 240 for usb camera so that the roborio does not have to convert the image before sending it

	//takes the video and makes a new output to the smart dashboard(switch to "FindYellow" in the properties of the camera viewer
	CvSink cvSink = CameraServer.getInstance().getVideo();
	CvSource outputStreamStd = CameraServer.getInstance().putVideo("FindGreen", 320, 240);

	Mat src = new Mat();
	Mat gray = new Mat();
	Mat hsv = new Mat();
	Mat threshOutput = new Mat();

    java.util.List<MatOfPoint> contours = new ArrayList<>();
	Mat hierarchy = new Mat();

	while(true) {
		cvSink.grabFrame(src);
		if(src.empty()){
			continue;}
		else if(src.channels()>1){
		    Imgproc.cvtColor(src, hsv, Imgproc.COLOR_BGR2HSV);}
		else {gray = src;}

//		cvtColor(src, hsv, CV_BGR2HSV);

		//find green from hsv mat-put to threshOutput mat
		Core.inRange(hsv, new Scalar(70, 160, 220), new Scalar(90, 250, 255), threshOutput);

		//group pixels into contours
        Imgproc.findContours(threshOutput, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE, new org.opencv.core.Point(0, 0));
        
		int contoursFound = 0;

		for(int i = 0; i < contours.size(); i++) {

			//if the area of the contour is too small, it will not be drawn
			double contourArea = Imgproc.contourArea(contours.get(i));
			if(contourArea < 40){
				continue;
			}
			else {
				contoursFound++;
			}
			// Imgproc.drawContours(src, contours, i, new Scalar(255, 0, 255), 3, 8, hierarchy, 0, new Point() );
		}

		 //Create some lists for circles and stuff
		 java.util.List<MatOfPoint> contours_poly = new java.util.ArrayList<>(contours.size());
		 Point center = new Point();
		 float[] radius = new float[1];

		 Mat centerLarge = new Mat(new Size(100,10), 5);
		 java.util.List<MatOfFloat> radiusLarge = new java.util.ArrayList<>();

		 //Set up sme variable for finding the topmost circles. maxY and secY will contain the Y coordinates
		 //of the top two circles so far. maxIndex and secIndex will be the index they are in the array.
		 int maxY = src.cols();
		 int secY = src.cols();
		 int maxIndex = -1;
		 int secIndex = -1;
		 //Create a circle around contours, by looping through each one.
		 for( int i = 0; i < contours.size(); i++ ) {
			MatOfPoint c = contours.get(i);
			if (Imgproc.contourArea(c) > 40) {
				MatOfPoint2f c2f = new MatOfPoint2f(c.toArray());
				Imgproc.minEnclosingCircle(c2f, center, radius);
			}
			// Imgproc.circle(src, center, (int)radius[0], new Scalar(255, 0, 255), 2);

			 //If they are big enough, draw them and find the top two.
			 if (radius[i] > 10.0) {
				 if (center.get(i).y < maxY) {
					 secY = maxY;
					 secIndex = maxIndex;
					 maxY = center.get(i).row(y);
					 maxIndex = i;
				 } else if (center.get(i).row(y) < secY) {
					 secY = center.get(i).row(y);
					 secIndex = i;
				 }
				//  Scalar color = new Scalar(50, 100, 200);
				 //cv::drawContours(src, contours, i, color, 2, 8, hierarchy, 0, cv::Point());
				//  Imgproc.drawContours(src, contours_poly, i, color, 1, 8, hierarchy, 0, new Point());
				 //cv::rectangle(src, boundRect[i].tl(), boundRect[i].br(), color, 2, 8, 0);

		 }

		 //draw a line down the middle.
		 Imgproc.line(src, new Point(src.cols()/2,0), new Point(src.cols()/2,src.rows()), new Scalar(0,0,255), 1);

		 //draw the circles.
		 Imgproc.circle(src, center, (int)radius[maxIndex], new Scalar(50, 100, 200), 2);
		 Imgproc.circle(src, center, (int)radius[secIndex], new Scalar(50, 100, 200), 2);
		  //Find the average of the x coordinates of the largest two circles.
		//   Mat mean = new Mat();
		//   int width = src.cols();
		//   int pixelCenter = width / 2;

		//   //only process if there are two circles found.
		//   if (maxIndex > -1 && secIndex > -1) {
		// 	  //draw the circles.
		// 	  Imgproc.circle(src, center, (int)radius[maxIndex], new Scalar(50, 100, 200), 2, 8, 0);
		// 	  Imgproc.circle(src, center, (int)radius[secIndex], new Scalar(50, 100, 200), 2, 8, 0);

		// 	  //This function finds the average.
		// 	  Core.reduce(centerLarge, mean, 1, Core.REDUCE_AVG);

		// 	  //This creates converts the matrix to a point, and draws a dot.
		// 	  List<MatOfPoint2f> meanPoint = new ArrayList<>(1);
		// 	  Imgproc.circle(src, meanPoint, 3, new Scalar(0, 0, 255), -1, 8, 0);

		// 	  //Set the input to the PID to the current offset.
		// 	  visionSource.setInput((double)meanPoint.x - (double)pixelCenter);



		outputStreamStd.putFrame(src);
	}
}


public boolean GetSeeGreen() {
	return seeGreen;
}

private boolean seeGreen = false;
}

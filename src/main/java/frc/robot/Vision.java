/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.opencv.core.Point;
import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
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
	camera.setBrightness(2);
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
//
		if(src.empty()){
		    continue;}
		else if(src.channels()>1){
		    Imgproc.cvtColor(src, hsv, Imgproc.COLOR_BGR2HSV);}
		else {gray = src;}

//		cvtColor(src, hsv, CV_BGR2HSV);

		//find green from hsv mat-put to threshOutput mat
		Core.inRange(hsv, new Scalar(100, 15, 15), new Scalar(180, 255, 255), threshOutput);

		//group pixels into contours
        Imgproc.findContours(threshOutput, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE, new org.opencv.core.Point(0, 0));
        
		int contoursFound = 0;

		for(int i = 0; i < contours.size(); i++) {

			//if the area of the contour is too small, it will not be drawn
			double contourArea = Imgproc.contourArea(contours.get(i));
			if(contourArea < 20){
				continue;
			}
			else {
				contoursFound++;
			}
			Imgproc.drawContours(src, contours, i, new Scalar(255, 0, 255), 3, 8, hierarchy, 0, new Point() );
		}

		if(contoursFound > 0){
			seeGreen = true;
		}
		else{
			seeGreen = false;
		}

		outputStreamStd.putFrame(src);
	}
}

public boolean GetSeeGreen() {
	return seeGreen;
}

private boolean seeGreen = false;
}

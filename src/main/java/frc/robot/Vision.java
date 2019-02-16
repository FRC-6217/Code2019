/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

/**
 * Add your docs here.
 */
public class Vision implements Runnable{

    public void run(){
        UsbCamera cam1 = new UsbCamera("usb0", 1);
        //cam1.setBrightness(5);

        UsbCamera cam2 = new UsbCamera("usb1", 0);
        //cam2.setBrightness(5);

        CameraServer.getInstance().startAutomaticCapture(cam1);
        CameraServer.getInstance().startAutomaticCapture(cam2);

        cam1.setResolution(640, 480);
        cam2.setResolution(630, 480);

        CvSink cv1 = CameraServer.getInstance().getVideo(cam1);
        CvSink cv2 = CameraServer.getInstance().getVideo(cam2);

        CvSource outputStreamStd = CameraServer.getInstance().putVideo("Output", 640, 480);

        Mat src = new Mat();

        while(true){
            if(Robot.cameraToggle){
                cv1.grabFrame(src);
            }
            else{
                cv2.grabFrame(src);       
            }
            outputStreamStd.putFrame(src);
        }
    }
}

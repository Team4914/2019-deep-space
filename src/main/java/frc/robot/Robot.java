/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.*;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    /* 
     * 'temporary' code
     */

    public static Lift m_lift;
    public static Drivetrain m_drivetrain;
    public static Intake m_intake;
    public static Climber m_climber;

    public static OI m_oi;

    //private DigitalInput m_bigSwitch1;
   // private DigitalInput m_bigSwitch2;
    private DigitalInput[] m_DIO;
    static Thread m_visionThread;
    
    //Operating variables
    boolean limitState = true;
    public static double leftDriveSpeed = 0;
    public static double rightDriveSpeed = 0;
    public static double liftSpeed = 0;
    public static double climbSpeed = 0;
    public static double intakeSpeed = 0;

    public static boolean passiveIntake = false;


    //temporary testing code
    boolean aPress = false;

    @Override
    public void robotInit() {
        RobotMap.init();

        m_drivetrain = new Drivetrain();
        m_lift = new Lift();
        m_intake = new Intake();
        m_climber = new Climber();

        m_oi = new OI();

        m_DIO = new DigitalInput[10];


        for(int i = 0; i < m_DIO.length; i++){
            m_DIO[i] = new DigitalInput(i);
        }
        //m_bigSwitch1 = new DigitalInput(3);
        //aam_bigSwitch2 = new DigitalInput(1);
        

        /*
        m_visionThread = new Thread(() -> {
            UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
            camera.setFPS(30);
            camera.setResolution(200, 200);

            CvSink cvSink = CameraServer.getInstance().getVideo();

            CvSource outputStream = CameraServer.getInstance().putVideo("Video", 500, 500);

            Mat mat = new Mat();

            while(!Thread.interrupted()){
                //grab frame from cvSink, if there's an error (time out), notify output
                if(cvSink.grabFrame(mat) == 0){
                    outputStream.notifyError(cvSink.getError());
                    continue;
                }
                //img processing goes here

                outputStream.putFrame(mat);
            }
        });
            */

/*
        //test code
        m_visionThread = new Thread(() -> {
            //cam0
            UsbCamera camera0 = CameraServer.getInstance().startAutomaticCapture();
            camera0.setFPS(30);
            camera0.setResolution(200, 200);

            CvSink cvSink0 = CameraServer.getInstance().getVideo();

            CvSource outputStream0 = CameraServer.getInstance().putVideo("Video", 500, 500);

            Mat mat = new Mat();

            //cam1
            UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture();
            camera1.setFPS(30);
            camera1.setResolution(200, 200);

            CvSink cvSink1 = CameraServer.getInstance().getVideo();

            CvSource outputStream1 = CameraServer.getInstance().putVideo("Video", 500, 500);



            while(!Thread.interrupted()){
                //grab frame from cvSink, if there's an error (time out), notify output
                if(cvSink0.grabFrame(mat) == 0){
                    outputStream0.notifyError(cvSink0.getError());
                }
                else{
                    outputStream0.putFrame(mat);
                }
                if(cvSink1.grabFrame(mat) == 0){
                    outputStream1.notifyError(cvSink0.getError());
                }
                else{
                    outputStream1.putFrame(mat);
                }

            }
        });*//*
        m_visionThread.setDaemon(true);
        m_visionThread.start();
        */
        CameraServer.getInstance().startAutomaticCapture();
    }

    @Override
    public void robotPeriodic() {
       // System.out.println("3: " + m_bigSwitch1.get();
        boolean cLimitState = m_DIO[1].get();
        if(cLimitState != limitState){
            limitState = cLimitState;
            System.out.println("Limit Switch state changed: " + m_DIO[1].get());
        }
        if(m_oi.getA() != aPress){
            System.out.println("A switch swapped");
            aPress = m_oi.getA();
        }
    }

    @Override
    public void autonomousInit() {
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();

        operateLift();
        operateDrivetrain();
        operateIntake();

        flushOVars();
    }

    public void disabledPeriodic(){
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        /*
        double mainRightT = this.mainJoystick.getRawAxis(3);
        double coRightT = this.coJoystick.getRawAxis(3);
        */
        Scheduler.getInstance().run();

        operateLift();
        operateDrivetrain();
        operateIntake();

        flushOVars();

        //m_climber.set(m_oi.getMainTRight() - m_oi.getMainTLeft());
        //m_intake.set(m_oi.getMainTRight() - m_oi.getMainTLeft());
    }

    public void flushOVars(){
        leftDriveSpeed = 0;
        rightDriveSpeed = 0;
        liftSpeed = 0;
        climbSpeed = 0;
        intakeSpeed = 0;
    }

    /**
     * Operates robot lift
     */
    public void operateLift(){
        liftSpeed += m_oi.getCoTRight() - m_oi.getCoTLeft();
        m_lift.lift(liftSpeed);
    }

    /**
     * Operates robot drivetrain
     */
    public void operateDrivetrain(){
        leftDriveSpeed += m_oi.getMainYLeft() + m_oi.getCoYLeft() * 0.5;
        rightDriveSpeed += m_oi.getMainYRight() + m_oi.getCoYRight() * 0.5;
        m_drivetrain.tankDrive(leftDriveSpeed, rightDriveSpeed);
    }

    /**
     * Operates robot intake
     */
    public void operateIntake(){
        intakeSpeed += m_oi.getMainTRight() - m_oi.getMainTLeft();
        if(passiveIntake) intakeSpeed += 0.2;
        m_intake.set(intakeSpeed *0.8);
    }
    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }

    static public double safety(double speed){
        return Math.min(speed, 1.0);
    }
    
    static public double safety(double speed, double max){
        return Math.min(speed, max);
    }
}

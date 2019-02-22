/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.VictorSP;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    
    //static public VictorSP leftFront;
    //static public VictorSP leftBack;
    static public VictorSP right;
    static public VictorSP left;
    
    static public TalonSRX liftTalon;

    static public Spark intakeSpark;

    //Pneumatics
    public static Compressor compressor;
    public static DoubleSolenoid liftDoubleSolenoid;
    public static DoubleSolenoid climbDoubleSolenoid;
    public static DoubleSolenoid clampDoubleSolenoid;

    //Climb
    public static Spark climberSparkLeft;
    public static Spark climberSparkRight;

    public static void init(){
        //todo
        //Drivetrain
        right = new VictorSP(3);
        left = new VictorSP(1);
        
        //Lift
        liftTalon = new TalonSRX(2);

        //Intake
        intakeSpark = new Spark(2);

        //Pneumatics
        compressor = new Compressor();

        liftDoubleSolenoid = new DoubleSolenoid(0, 1);
        
        climbDoubleSolenoid = new DoubleSolenoid(2, 3);
        clampDoubleSolenoid = new DoubleSolenoid(4, 5);
        //Climb
        climberSparkLeft = new Spark(8);
        climberSparkLeft.setInverted(false);

        climberSparkRight = new Spark(7);
        climberSparkRight.setInverted(true);
        /*
        rightFront = new VictorSP(1);
        rightFront.setInverted(true);
        
        rightBack = new VictorSP(9);

        rightFront.setInverted(true);
        */
    }
}

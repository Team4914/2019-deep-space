/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Drivetrain extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    //private VictorSP leftFront = RobotMap.leftFront;
    //private VictorSP leftBack = RobotMap.leftBack;
    private VictorSP right = RobotMap.right;
    private VictorSP left = RobotMap.left;
    @Override
    public void initDefaultCommand() {
        
    }

    public void tankDrive(double leftSpeed, double rightSpeed){
        //leftFront.setSpeed(Robot.safety(leftSpeed));
        //leftBack.setSpeed(Robot.safety(leftSpeed));
        right.setSpeed(Robot.safety(rightSpeed));
        left.setSpeed(Robot.safety(-leftSpeed));
    }
}

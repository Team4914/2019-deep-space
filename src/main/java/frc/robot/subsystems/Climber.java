/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * Currently not in use
 */
public class Climber extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private Spark leftSpark = RobotMap.climberSparkLeft;
  private Spark rightSpark = RobotMap.climberSparkRight;

  private DoubleSolenoid climbFold = RobotMap.climbDoubleSolenoid;
  private DoubleSolenoid climbClamp = RobotMap.clampDoubleSolenoid;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void set(double speed){
    speed = Robot.safety(speed);
    leftSpark.set(speed);
    rightSpark.set(speed);
  }
  
  public void toggleFold(){
    if(climbFold.get() == Value.kForward){
      climbFold.set(Value.kReverse);
    }
    else{
      climbFold.set(Value.kForward);
    }
  }
  public void toggleClamp(){
    if(climbClamp.get() == Value.kForward){
      climbClamp.set(Value.kReverse);
    }
    else{
      climbClamp.set(Value.kForward);
    }
  }
}

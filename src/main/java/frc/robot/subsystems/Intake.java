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
 * Add your docs here.
 */
public class Intake extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private Spark motor = RobotMap.intakeSpark;

  private DoubleSolenoid dumpPiston = RobotMap.dumpDoubleSolenoid;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void set(double speed){
      //System.out.println("outtake running at " + speed);
      speed = Robot.safety(speed);
      motor.set(-speed);
  }

  public void toggleDump(){
      if(dumpPiston.get() == Value.kForward){
        dumpPiston.set(Value.kReverse);
      }
      else{
          dumpPiston.set(Value.kForward);
      }
  }

  public void resetPistons(){
      dumpPiston.set(Value.kReverse);
  }

  public void stop(){
      set(0);
  }
}

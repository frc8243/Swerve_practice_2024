// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import javax.naming.LimitExceededException;

import edu.wpi.first.units.Distance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Vision;

public class MoveToAprilTag extends Command {
  private final DriveSubsystem swerveDrive;
  private final Vision limelight;
  public static final double kP = 0.01;
  private static final double targetDistance = .8; //meters away
  private static final double aprilTagHeight = 0.23; //Meters tag height
  private double ty;
  private double distance;
  
  /** Creates a new TurnToAprilTag. */
  public MoveToAprilTag(DriveSubsystem swerveDrive, Vision limelight) {
    this.swerveDrive = swerveDrive;
    this.limelight = limelight;
    addRequirements(swerveDrive);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (limelight.isTargetVisible()){
      ty = limelight.getTargetY();
      distance = aprilTagHeight/(Math.tan(Math.toRadians(ty)));
      

      System.out.println("MOVING TO APRIL TAG");
      swerveDrive.drive(0.2,0, 0, false,true);
    } else{
      System.out.println("NO TARGET");
      swerveDrive.drive(0,0,0, false, true);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    swerveDrive.drive(0, 0, 0, false, true);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return limelight.isTargetVisible() && distance < targetDistance;
  }
}


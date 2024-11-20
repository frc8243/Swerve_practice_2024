// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {
  private final NetworkTable limelightTable;
  /** Creates a new Vision. */
  public Vision() {
    limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
    SmartDashboard.putString("Limelight Camera Feed", "http://limelight.local:5800/stream.mjpg");

  }
  public double getTargetX(){
    return limelightTable.getEntry("tx").getDouble(0.0);
  }
  public double getTargetY(){
    return limelightTable.getEntry("ty").getDouble(0.0);
  }
  public double getArea(){
    return limelightTable.getEntry("ta").getDouble(0.0);
  }
  public boolean isTargetVisible(){
    return limelightTable.getEntry("tv").getDouble(0.0) == 1.0;
  }
  public void setPipeline(int pipeline){
    limelightTable.getEntry("pipeline").setNumber(pipeline);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Lime X", getTargetX());
    SmartDashboard.putNumber("Lime Y", getTargetY());
    SmartDashboard.putNumber("LimeLight Area", getArea());
    SmartDashboard.putBoolean("target visible", isTargetVisible());
  }
}

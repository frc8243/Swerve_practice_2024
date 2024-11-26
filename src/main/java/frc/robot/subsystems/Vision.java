// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.SparkMaxRelativeEncoder;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;

public class Vision extends SubsystemBase {
  private final NetworkTable limelightTable;
  private final DriveSubsystem driveSubsystem;
  private boolean doRejectUpdate;
  private final Field2d m_field = new Field2d();
  
  public Pose2d limePose2d;  
  /** Creates a new Vision. */
  public Vision(DriveSubsystem driveSubsystem) {
    limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
    SmartDashboard.putString("Limelight Camera Feed", "http://limelight.local:5800/stream.mjpg");
    int[] validIDs = {7,8};
    LimelightHelpers.SetFiducialIDFiltersOverride("limelight", validIDs);
    this.driveSubsystem = driveSubsystem;
    


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
    SmartDashboard.putData("Field",m_field);
    
    

     LimelightHelpers.SetRobotOrientation("limelight", driveSubsystem.m_odometry.getPoseMeters().getRotation().getDegrees() + 180, 0, 0, 0, 0, 0);
     LimelightHelpers.PoseEstimate mt2 = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight");
     limePose2d =  mt2.pose;
     SmartDashboard.putNumber("mt2 Fiducial 0", mt2.rawFiducials[0].ambiguity);
     SmartDashboard.putNumber("mt2 Fiducial 1", mt2.rawFiducials[1].ambiguity);
     SmartDashboard.putNumber("mt2 CamDistance 0", mt2.rawFiducials[0].distToCamera);     
     SmartDashboard.putNumber("mt2 CamDistance 1", mt2.rawFiducials[1].distToCamera);

      if(Math.abs(driveSubsystem.m_gyro.getRate()) > 720) // if our angular velocity is greater than 720 degrees per second, ignore vision updates
      {
        doRejectUpdate = true;
      }
      if(mt2.tagCount == 0)
      {
        doRejectUpdate = true;
      }
      if(!doRejectUpdate)
      {
        // driveSubsystem.m_odometry.setVisionMeasurementStdDevs(VecBuilder.fill(.7,.7,9999999));
        // m_poseEstimator.addVisionMeasurement(
        //     mt2.pose,
        //     mt2.timestampSeconds);
      }
      m_field.setRobotPose(limePose2d);
  }
}

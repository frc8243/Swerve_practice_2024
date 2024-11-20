// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdleConfiguration;
import com.ctre.phoenix.led.FireAnimation;
import com.ctre.phoenix.led.RainbowAnimation;
import com.ctre.phoenix.led.StrobeAnimation;
import com.ctre.phoenix.led.CANdle.LEDStripType;

import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDSubsystem extends SubsystemBase {
  private final CANdle candle;

  //private RollerClawIO rollerClawIO;
  private DigitalInput ledSwitch = new DigitalInput(0);
  private static boolean notePresent = false;

  /** Creates a new LEDSubsytem. */
  public LEDSubsystem() {
    candle = new CANdle(10); 
    CANdleConfiguration ConfigAll = new CANdleConfiguration();
    //ConfigAll.stripType = LEDStripType.RGB;
    candle.configAllSettings(ConfigAll);
  }
  public void setRed() {
    //FireAnimation(0.5,0.7,20,0.8,0.5,)
    //candle.animate(new , 0);
    //candle.setLEDs(255,0,0);
  }
  public void setBlue(){
    candle.clearAnimation(0);

    candle.setLEDs(64,224,208); 
  }

  public void setPurple(){
        candle.clearAnimation(0);

    candle.setLEDs(148, 0, 211);
  }

  public void setGreen(){
        candle.clearAnimation(0);

    candle.setLEDs(0,255,0);
  }

  public void turnOff (){
    candle.setLEDs(0, 0, 0);
    candle.clearAnimation(0);

  }


  public void startFireAnimation (){
   // candle.clearAnimation(0);
    
    FireAnimation fireAnimation = new FireAnimation(
      0.5,//brightness
      0.7,
      700,
      0.8,
      0.7,
      false, 
      0
    );

    candle.animate(fireAnimation);

  }

  public void rainbowAnimation(){
    RainbowAnimation rainbowAnimation= new RainbowAnimation(
      1.0,
      0.7,
      500,
      false,
      0
    );
    candle.animate(rainbowAnimation);
  }

  public void StrobeAnimation(){
    StrobeAnimation strobeanimation = new StrobeAnimation(
      0,255,0,
      0, 0.5, 500, 0

    );
    candle.animate(strobeanimation);
  }

  @Override
  public void periodic() {
    //SmartDashboard.putNumber("Switch Speed", rollerClawIO.getRollerClawSpeed());
    SmartDashboard.putBoolean("Switch on", notePresent);
    if (ledSwitch.get()==true){
      notePresent = true;
      //turnOff();
    } else {
      notePresent = false;
      //setRed();
    }
    // This method will be called once per scheduler run
  }
}

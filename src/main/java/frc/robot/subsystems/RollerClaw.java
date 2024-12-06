package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClawConstants;
import frc.robot.Constants.NeoMotorConstants;

public class RollerClaw extends SubsystemBase {
    private final CANSparkMax rollerClawMotor;
    private final RelativeEncoder rollerClawEncoder;
    private final DigitalInput rollerClawSwitch;

    private static boolean notePresent = false;

    public RollerClaw (){
        rollerClawMotor = new CANSparkMax(ClawConstants.kRollerClawMortorID, MotorType.kBrushless);
        rollerClawEncoder = rollerClawMotor.getEncoder();
        rollerClawSwitch = new DigitalInput(0);

        rollerClawMotor.restoreFactoryDefaults();
        rollerClawMotor.setIdleMode(IdleMode.kBrake);
        rollerClawMotor.setSmartCurrentLimit(NeoMotorConstants.kNeo550CurrentLimit);
        rollerClawMotor.burnFlash();
    }

    public void setRollerClawMotor (double speed){
        rollerClawMotor.set(speed);
    }
    public void stopMotor(){
        rollerClawMotor.set(0);
    }
    public Command clawIntakeCommand(){
        System.out.println("Claw Intaking" + rollerClawMotor.get());
        return new RunCommand(
            ()->{setRollerClawMotor(-ClawConstants.kRollerClawSpeed); 
                System.out.println("Claw Intaking");},
            this
            ).finallyDo(interrupted->stopMotor());
    }

     public Command clawDumpCommand(){
        return new RunCommand(
            ()->setRollerClawMotor(ClawConstants.kRollerClawSpeed),
            this
            ).finallyDo(interrupted->stopMotor());

}

}
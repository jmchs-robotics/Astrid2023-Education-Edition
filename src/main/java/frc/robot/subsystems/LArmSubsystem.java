package frc.robot.subsystems;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 *
 */
public class LArmSubsystem extends SubsystemBase {

    //private DoubleSolenoid intakePiston;
    private DoubleSolenoid dumpPiston;
        
    /**
    *
    */
    public LArmSubsystem() {

        //intakePiston = RobotMap.intakePiston;
        //addChild("intakePiston", intakePiston);
        dumpPiston = RobotMap.dumpPiston;
        addChild("dumpPiston", dumpPiston);  

    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    public void setDumpSolenoid (Value val) {
        dumpPiston.set(val);
    }

    /*
    public void setIntakeSolenoid (Value val) {
        intakePiston.set(val);
    }
    
    public void setBothSolenoids (Value val) {
        dumpPiston.set(val);
        intakePiston.set(val);
    }
    */

    public String getPistonValue() {
        return "Dump: " + dumpPiston.get();
    }

}
 

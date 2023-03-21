package frc.robot.subsystems;

import frc.robot.RobotMap;
import frc.robot.Constants.Hook;
import frc.robot.commands.RetractHook;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

/**
 *
 */
public class HookSubsystem extends SubsystemBase {

private WPI_TalonFX leftHookMotor;
private WPI_TalonFX rightHookMotor;
private MotorControllerGroup bothHooks;
private DifferentialDrive hookDrive;
private double hookDifference; 
private double deadband = Hook.deadband;

    public HookSubsystem() {
        leftHookMotor = RobotMap.leftHookMotor;
        addChild("leftHookMotor",leftHookMotor);

        rightHookMotor = RobotMap.rightHookMotor;
        addChild("rightHookMotor",rightHookMotor);

        bothHooks = RobotMap.bothHooks;
        addChild("Motor Controller Group 1",bothHooks);

        hookDrive = RobotMap.hookDrive;
        addChild("Hook Drive", hookDrive);
    }

    @Override
    public void periodic() {
        //positive is left higher and negative is right higher
        hookDifference = getLeftEncoderValue() - getRightEncoderValue();
        SmartDashboard.putNumber("Hook Difference: ", hookDifference);
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    /**
     * @desc sets the speed of the hook motors
     * @param speed
     */

    public void setBoth(double speed) {
        bothHooks.set(speed);  
    }

    public void setLeft(double speed) {
        leftHookMotor.set(speed);
    }

    public void setRight(double speed) {
        rightHookMotor.set(speed);
    }

    /**
     * @desc check hook limits
     * @param speed
     * @return true if within limits; false if limits are broken
     */
    public boolean checkUpperRightLimit() {
        return getRightEncoderValue() < Hook.maxPos;
    }
    public boolean checkUpperLeftLimit() {
        return getLeftEncoderValue() < Hook.maxPos;
    }

    public boolean checkUpperLimits() {
        return checkUpperLeftLimit() && checkUpperRightLimit();
    }

    public boolean checkLowerLeftLimit() {
        return getLeftEncoderValue() > Hook.minPos;
    }

    public boolean checkLowerRightLimit() {
        return getRightEncoderValue() > Hook.minPos;
    }

    public boolean checkLowerLimits() {
        return checkLowerLeftLimit() && checkLowerRightLimit();
    }

    //Going up with left/right correction
    //hookDifference    +:left higher     -:right higher
    public void upHookCorrection(double vBus) {
        if (hookDifference > 1500) {
            setRight(vBus); //right up to meet left
        }
        else if (hookDifference < -1500) {
            setLeft(vBus); //left up to meet right
        }
        else {
            setBoth(vBus);  //both up if hook difference is tolerable
        }
    }

    //Going down with left/right correction
    //hookDifference    +:left higher     -:right higher
    public void downHookCorrection(double vBus) {
        if (hookDifference > 1500) {
            setLeft(-vBus);  //left down to meet right
        }
        else if (hookDifference < -1500) {
            setRight(-vBus); //right down to meet left
        }
        else {
            setBoth(-vBus);   //both up if hook difference is tolerable
        }
    }
    
    //Hook Control
    //Control + is up, - is down
    public void hookLimiter(double control){
        
        if((control > deadband) && checkUpperLimits()) { //both up
            upHookCorrection(0.6);
        }
        else if((control < -deadband) && checkLowerLimits()) { //both down
            downHookCorrection(0.6);
        }
        else {
            stopMotors();
        }
    }
    

    public void hookArcadeLimiter(double control, double offset){

        if((control > deadband) && checkUpperLimits()) { //both up
            setBoth(0.7);
        }
        
        else if(control < -deadband) { //both down
            setBoth(-0.7);
        }
        
        else if (offset < -deadband && checkLowerRightLimit() && checkUpperLeftLimit()) {
            setLeft(0.15);  //goes up
            setRight(-0.15);  //goes down
        }
        
        else if (offset > deadband && checkUpperRightLimit() && checkLowerLeftLimit()) {
            setLeft(-0.15);   //goes down
            setRight(0.15); //goes up
        }    
        
        else {
            stopMotors();
        }     
    }   

    public void hookTankLimiter(double left, double right){
        if((left < -deadband) && checkLowerLeftLimit()) {
            setLeft(-0.4);
        }
        else if((right < -deadband) && checkLowerRightLimit()) {
            setRight(-0.4);
        }
        else if((left > deadband) && checkUpperLeftLimit()) {
            setLeft(0.4);
        }
        else if((right > deadband) && checkUpperRightLimit()) {
            setRight(0.4);
        }
        else {
            stopMotors();
        }
    }

    /**
     * @desc stops the hook motors
     */

    public void stopMotors() {
        bothHooks.stopMotor();
    }

    public void stopLeft() {
        leftHookMotor.stopMotor();
    }

    public void stopRight() {
        rightHookMotor.stopMotor();
    }

    public void resetEncoderValue() {
        leftHookMotor.setSelectedSensorPosition(0);
		rightHookMotor.setSelectedSensorPosition(0);
	}

    public double getLeftEncoderValue() {
        return leftHookMotor.getSelectedSensorPosition();
    }

    public double getRightEncoderValue() {
        return rightHookMotor.getSelectedSensorPosition();
    }

    public double getAvgEncoderValue() {
        return (getLeftEncoderValue() + getRightEncoderValue()) / 2;
    }

    public double getHookDifference() {
        return hookDifference;
    }

}


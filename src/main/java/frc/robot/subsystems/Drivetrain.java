package frc.robot.subsystems;

import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.RobotMap;
import frc.robot.Constants.Drive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

/**
 * Drivetrain is responsible for all robot driving and translational movement.
 * In this class is defined all of ASTRID's motors, drive constants and methods
 * that assist with driving the robot.
 */
public class Drivetrain extends SubsystemBase {

	private final WPI_TalonFX left1;
	private final WPI_TalonFX left2;
	private final WPI_TalonFX left3;
	private final MotorControllerGroup leftMotors;
	private final WPI_TalonFX right1;
	private final WPI_TalonFX right2;
	private final WPI_TalonFX right3;
	private final MotorControllerGroup rightMotors;
	private final DifferentialDrive drive;

	
	public final AHRS gyro = RobotMap.roborioGyro;
	public final double minVBusOutVal = 0.2;

    public Drivetrain() {

		//MICROBOT MOTORS
		left1 = RobotMap.left1;
		addChild("left1",left1);

		left2 = RobotMap.left2;
		addChild("left2",left2);

		left3 = RobotMap.left3;
		addChild("left3", left3);

		right1 = RobotMap.right1;
		addChild("right1",right1);

		right2 = RobotMap.right2;
		addChild("right2",right2);

		right3 = RobotMap.right3;
		addChild("right3", right3);

		leftMotors = RobotMap.leftMotors;
		addChild("leftMotors",leftMotors);

		rightMotors = RobotMap.rightMotors;
		addChild("rightMotors",rightMotors);

		drive = RobotMap.drive;
		addChild("Drive",drive);

    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

    }

	/**
	 * @return
	 */

    public DifferentialDrive getDrive() {
        return drive;
    }

	/**
	 * @desc a form of driving that uses the joystick y-axis for speed and the x-axis for rotation
	 * @param forward the forward power that the robot has with arcadeDrive
	 * @param rotation the rotational power that the robot has with arcadeDrive
	 */

    public void arcadeDrive(double forward, double rotation) {
		drive.arcadeDrive(forward, rotation);
	}

	/**
	 * @desc a form of driving that controls left and right sides via the left and right joystick y-axis respectively.
	 * @param leftVal the power that is passed to Astrid's left wheels
	 * @param rightVal the power that is passed to Astrid's right wheels
	 */

    public void tankDrive(double leftVal, double rightVal) {
		leftMotors.set(leftVal);
		rightMotors.set(rightVal);
	}

	/**
	 * @desc stops Astrid's motors
	 */

	public void stopMotors() {
		leftMotors.set(0);
		rightMotors.set(0);
	}

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	/**
	 * @param pidIdx
	 * @return
	 */

    public double getLeftEncoderPos(int pidIdx) {
		return left2.getSelectedSensorPosition(pidIdx);
	}

	/**
	 * @param pidIdx
	 * @return
	 */

	public double getRightEncoderPos(int pidIdx) {
		return right2.getSelectedSensorPosition(pidIdx);
	}

	public double nativeUnitsToInches(double sensorCounts) {
		double motorRotations = sensorCounts / Drive.kEncoderCPR;
		double wheelRotations = motorRotations / Drive.kGearRatio;
		double posInches = wheelRotations * Drive.kWheelCircumference;
		return posInches;
	}

	public double inchesToNativeUnits(double inches) {
		double wheelRotations = inches / Drive.kWheelCircumference;
		double motorRotations = wheelRotations * Drive.kGearRatio;
		int sensorCounts = (int)(motorRotations * Drive.kEncoderCPR);
		return sensorCounts;
	}

	private void initGyro() {
		gyro.calibrate();
	}

	public void resetGyro() {
		gyro.reset();
	}

	/**
	 * @return
	 */

	public double getGyroYaw() {
		return gyro.getYaw();
	}

	public double getGyroRate() {
		return gyro.getRate();
	}

	public double getGyroPitch() {
		return gyro.getPitch();
	}

	public double getGyroRoll() {
		return gyro.getRoll();
	}

	/**
	 * @param val
	 * @return
	 */

	public double thresholdVBus(double val) {
		if(Math.abs(val) < minVBusOutVal) {
			val = Math.signum(val) * minVBusOutVal;
		}
		return val;
	}

	public void resetEncoders() {
		left2.setSelectedSensorPosition(0, 0, 20);
		right2.setSelectedSensorPosition(0, 0, 20);
	}

}

/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Salamander extends SimpleRobot {

    //Joysticks
    Joystick controlStick = new Joystick(2);
    Joystick driveStick = new Joystick(1);
    JoystickButton retractButton = new JoystickButton(controlStick, 2);
    JoystickButton expandButton = new JoystickButton(controlStick, 3);

    //Drive
    RobotDrive myDrive = new RobotDrive(1, 2, 3, 4);

    //Gyro
    Gyro itsAGyro = new Gyro(1);
    
    //Motors
    Jaguar wis = new Jaguar(5);

    //Pneumatics
    //DigitalInput pressureSwitch = new DigitalInput(1);                          //the digital input for the pressure switch on the pneumatics board
    Compressor compressor = new Compressor(1, 1);                               //the compressor which charges air for the storage tanks
    MySolenoid Kicker = new MySolenoid(1, 2, 3);                                //an arbitrary piston that uses the MySolenoid system
    //Relay compressorRelay = new Relay(1);

    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public double rampmotor(double req, double cur) { //init variables Requested speed and Current speed
        double error = Math.abs(req - cur); //sets variable error to Requested speed minus Current speed
        double output = 0.0;

        if (error >= 0.1) {
            output = ((0.1) * (req - cur));
        } else {
            output = req;
        }
        return output;
    }

    public void autonomous() {
        myDrive.mecanumDrive_Cartesian(0.5, 0, 0, 0);                           //drive forward at half speed
        Timer.delay(1);                                                         //wait one second
        myDrive.mecanumDrive_Cartesian(0, 0, 0, 0);                             //do not move
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        //Inintial Compressor Values
        boolean pressureSwitchVal = true, pressureSwitchPreval = true;
        compressor.start();
        //compressorRelay.set(Relay.Value.kForward);
        //Solenoid system value
        boolean retractedState = true, extendedState = false, retractButtonVal, expandButtonVal;
        boolean pistonPrevalueR = true, pistonPrevalueE = false;

        double gyroStatis;
        
        double wisVal = 0;

        while (isOperatorControl() && isEnabled()) {
            Timer.delay(0.01);
            
            
            double z = driveStick.getZ()*-1;
            if (Math.abs(z) < 0.5) {
                z = 0;
            } else if (z > 0) {
                z -= 0.5;
            } else {
                z += 0.5;
            }
            gyroStatis = itsAGyro.getAngle();
            myDrive.mecanumDrive_Cartesian(driveStick.getX(), z, driveStick.getY()*-1, gyroStatis);

            //Compressor
            pressureSwitchVal = compressor.getPressureSwitchValue();

            if (pressureSwitchVal && !pressureSwitchPreval) {                                          //
                compressor.stop();

            } else if (!pressureSwitchVal && pressureSwitchPreval) {
                compressor.start();

            }

            pressureSwitchPreval = pressureSwitchVal;

//Solenoid Actuating
            retractButtonVal = retractButton.get();
            expandButtonVal = expandButton.get();

//determines the desired state of the system based on buttons
            if (retractButtonVal && !expandButtonVal) {
                retractedState = true;
                extendedState = false;
                SmartDashboard.putString("Kicker:", " Retracted");
            } else if (!retractButtonVal && expandButtonVal) {
                retractedState = false;
                extendedState = true;
                SmartDashboard.putString("Kicker:", " Extended");
            } else if (retractButtonVal && expandButtonVal) {
                SmartDashboard.putString("Kicker:", " Both bottons pressed");
            }

//Extends and Retracts the piston only when not previously extended or retracted
            if (retractedState && !pistonPrevalueR) {
                Kicker.retract();
            } else if (extendedState && !pistonPrevalueE) {
                Kicker.extend();
            } else {
    //nothing
            }

            pistonPrevalueE = extendedState;
            pistonPrevalueR = retractedState;
            wisVal = rampmotor(controlStick.getThrottle(), wisVal);
            wis.set(wisVal);
        }

    }

    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {

    }
}

/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
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
    
    //Jags
      Jaguar leftDrive1 = new Jaguar(1);
      Jaguar rightDrive1 = new Jaguar(2);
    //Joystick
      Joystick rightJoy = new Joystick(1);
      Joystick leftJoy = new Joystick(2);
    //Buttons
      JoystickButton rightTrigger = new JoystickButton(rightJoy,1);


    
    
    public void autonomous() 
        {
        SmartDashboard.putString("Mode:", " Auto");
        leftDrive1.set((0.2)*(-1));
        rightDrive1.set((0.2));
        Timer.delay(1);
        leftDrive1.set(0);
        rightDrive1.set(0);  
        }
    
    
    
    public void operatorControl() 
        {
           SmartDashboard.putString("Mode:"," Enabled");
           while(isEnabled() && isOperatorControl())
           {
               Timer.delay(0.1);

               leftDrive1.set((leftJoy.getY()*.75));
               rightDrive1.set((rightJoy.getY()*.75)*(-1));
           }

       }
    
    
    
    
        public void disabled() 
        {
        SmartDashboard.putString("Mode:"," Disabled");
        //Print Disabled to dashboard
        }
     
     
}

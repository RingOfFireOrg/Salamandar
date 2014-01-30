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
     boolean gearboxstate = false, b, a, prevalue = false;
    //Jags
      Jaguar leftDrive1 = new Jaguar(2);
      Jaguar rightDrive1 = new Jaguar(1);
    //Joystick
      Joystick rightJoy = new Joystick(1);
      Joystick leftJoy = new Joystick(2);
    //Buttons
      
      JoystickButton leftTrig = new JoystickButton(leftJoy,1);    
      JoystickButton rightTrig = new JoystickButton(rightJoy,1);
    //int
      double speedVal =0.5;

    
    
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

               leftDrive1.set((leftJoy.getY())*(speedVal)*(-1));
               rightDrive1.set((rightJoy.getY())*(speedVal));
               
               
               
               
 //Software Gearbox
                    if (leftTrig.get() && rightTrig.get())
                      {
                          a = true;          // b if both buttons pressed
                      } 
                    else    
                      {
                          a = false;
                      }
                    if (!prevalue && a){
                        gearboxstate=!gearboxstate;
                    }
                    if (gearboxstate){
                        SmartDashboard.putString("Gear:", " HIGH");
                        speedVal =1;
                    } else {
                        SmartDashboard.putString("Gear:", " LOW");
                       speedVal =0.5;
                    }
                    if (a){
                        prevalue = true;
                    } else {
                        prevalue = false;
                    }
               
             
               
               
               
               
               
               
               
           }

       }
    
    
    
    
        public void disabled() 
        {
        SmartDashboard.putString("Mode:"," Disabled");
        speedVal =0.5;
        //Print Disabled to dashboard
        }
     
     
}

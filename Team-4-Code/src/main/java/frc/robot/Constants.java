/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public final class kDrive {
      public static final int kLeftMotorPort = 0;
      public static final int kRightMotorPort = 0;
      public static final int COUNTS_PER_REV = 2048;
    }

    public static final class kIntake{
        public static final int kIntakeMotorPort = -1;
        /* replace this with the actual port later*/

        public static final int intakeSpeed = 10;
        /* change this to actual speed later */

        public static final int intakeDislodgeSpeed = -2;
        /* change this to actual speed later */
    }

    public static final class kOuttake{
        public static final int kOuttakeMotorPort = -1;
        /* replace this with the actual port later*/
        
        public static final int outtakeSpeed = 5;
        /* change this to actual speed later */
    }

    public static final class kJoystick{
        public static final int kControllerPort = 0;
        public static final int kA = 1;
        public static final int kB = 2;
        public static final int kY = 3;

        public static final int kLeftJoyAxis = 1;
        public static final int kRightJoyAxis = 4;
    }

    public static final class kArdunio {
        public static final int port = -1; //TODO: set port
        public static final byte[] startSend = "align".getBytes();
		public static final int numData = 2; //two bytes (-125 to 127), one for left motor power, one for right
    }

    public static final class kAutonoumous {
        public static final int distToTable = 10;
        public static final int turningTime = 10;
    }
}

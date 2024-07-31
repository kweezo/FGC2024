package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;



@TeleOp(name = "FGC2024")
public class Main extends LinearOpMode{

    Camera cam;

    @Override
    public void runOpMode(){
        cam = new Camera(640, 480, hardwareMap, telemetry); //CAMERA BEST CALIBRATED FOR 640X480 (recommended res)

        while(!isStopRequested()){
            cam.Update();

            telemetry.update();
        }
    }
}

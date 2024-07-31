package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;



@TeleOp(name = "FGC2024")
public class Main extends LinearOpMode{

    Camera cam;
    ScissorLift lift;

    @Override
    public void runOpMode(){
        cam = new Camera(640, 480, hardwareMap, telemetry); //CAMERA BEST CALIBRATED FOR 640X480 (recommended res)
        lift = new ScissorLift("lift1", "lift2", 0.5f, 0.2f, gamepad1, hardwareMap);

        while(!isStopRequested()){
            lift.Update();
            cam.Update();

            telemetry.update();
        }
    }
}

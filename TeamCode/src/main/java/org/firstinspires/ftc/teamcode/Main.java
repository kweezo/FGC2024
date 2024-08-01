package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;


@TeleOp(name = "FGC2024")
public class Main extends LinearOpMode{

    Camera cam;
    ScissorLift lift;
    Base base;

    @Override
    public void runOpMode(){
        cam = new Camera(640, 480, hardwareMap, telemetry); //CAMERA BEST
        // CALIBRATED FOR 640X480 (recommended res)
  //      lift = new ScissorLift("lift1", "lift2", 0.5f, 0.2f, gamepad1, hardwareMap);



        String[] names = {"tl", "tr", "bl", "br", "", ""};

        base = new Base(names, gamepad1, hardwareMap);
        Aimbot aimbot = new Aimbot(0.01, 0, 0.0075, 0.90, 640, base, gamepad1, telemetry);

        while(!isStopRequested()){
           // lift.Update();
            AprilTagDetection[] tags = cam.Update();
            aimbot.Update(tags);
            base.Update();


            telemetry.update();
        }
    }
}

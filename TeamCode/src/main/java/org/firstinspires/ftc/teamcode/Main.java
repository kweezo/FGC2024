package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utils.Mat2;
import org.firstinspires.ftc.teamcode.utils.MiniPID;
import org.firstinspires.ftc.teamcode.utils.Vec2;
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

        while(!isStopRequested()){
           // lift.Update();
            AprilTagDetection tag = cam.Update();
            base.Update();

            if(gamepad1.square && tag != null){
                //telemetry.addData("P1 Y", p1.y());
                //telemetry.addData("P2 Y", p2.y());
                telemetry.addData("ROLL", tag.ftcPose.roll);

                //MiniPID pid = new MiniPID()

                //base.GoTowardsBearing(force, 3);
            }

            telemetry.update();
        }
    }
}

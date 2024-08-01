package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utils.MiniPID;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

public class Aimbot {
    private double force;
    private MiniPID pid;
    private ElapsedTime timeSincePIDUpdate;
    private Base base;
    private double margin;
    private Gamepad gamepad;
    private int currTagId;
    Telemetry telemetry;
    int camResX;
    boolean gamepadCircleToggle = false;

    public Aimbot(double p, double i, double d, double margin, int camResX, Base base, Gamepad gamepad, Telemetry telemetry){
        force = 0;
        currTagId = 0;
        pid = new MiniPID(p, i, d);
        timeSincePIDUpdate = new ElapsedTime();
        this.base = base;
        this.margin = margin;
        this.gamepad = gamepad;
        this.telemetry = telemetry;
        this.camResX = camResX;
    }

    private int GetIndexOfElement(AprilTagDetection[] tags, int id){
        for(int i = 0; i < tags.length; i++){
            if(tags[i].id == id){
                return i;
            }
        }

        return -1;
    }

    public void Update(AprilTagDetection[] tags){
        if(tags == null || tags.length == 0){
            if(timeSincePIDUpdate.milliseconds() < 500){
                base.ApplyPID(force, margin);
            }

            return;
        }

        int currTagIndex = GetIndexOfElement(tags, currTagId);


        if(gamepad.circle && !gamepadCircleToggle){
            gamepadCircleToggle = true;

            if(currTagIndex == -1){
                currTagId = tags[0].id;
                currTagIndex = 0;
            }else {
                currTagIndex = (currTagIndex + 1) % tags.length;
                currTagId = tags[currTagIndex].id;
            }

        }else if(!gamepad.circle){
            gamepadCircleToggle = false;
        }



        if(gamepad.square){
            //validate and pick a new tagID if current one isn't available
            if(currTagId == 0){
                currTagId = tags[0].id;
                currTagIndex = 0;
            }else{
                if(currTagIndex == -1){
                    if(timeSincePIDUpdate.milliseconds() > 300){
                        currTagId = tags[0].id;
                        currTagIndex = 0;
                    }else{
                        base.ApplyPID(force, margin);
                    }

                }
            }

            timeSincePIDUpdate = new ElapsedTime();

            //actually calculate the force and move
            force = pid.getOutput(tags[currTagIndex].center.x, camResX/2.0f);
            base.ApplyPID(force, margin);
        }


        telemetry.addData("CURRENT TAG (0 means none): ", currTagId);
        telemetry.addData("TAG CENTER_X: ", tags[currTagIndex].center.x);
        telemetry.addData("FORCE: ", force);
    }

}

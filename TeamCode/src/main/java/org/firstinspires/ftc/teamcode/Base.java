package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Base {
    DcMotor[] motors = new DcMotor[4];
    Gamepad gamepad;

    HardwareMap hardwareMap;

    public Base(String[] motorNames, Gamepad gamepad, HardwareMap hardwareMap){

        this.hardwareMap = hardwareMap;
        this.gamepad = gamepad;

        for(int i = 0; i < 4/*TODO: change to 6*/; i++){
            motors[i] = hardwareMap.get(DcMotor.class, motorNames[i]);
            motors[i].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
        motors[0].setDirection(DcMotorSimple.Direction.FORWARD);
        motors[1].setDirection(DcMotorSimple.Direction.FORWARD);
        motors[2].setDirection(DcMotorSimple.Direction.REVERSE);
        motors[3].setDirection(DcMotorSimple.Direction.REVERSE);
    }

    private void Strafe(float power){
        motors[0].setPower(power);
        motors[2].setPower(power);
        motors[1].setPower(-power);
        motors[3].setPower(-power);
    }

    private void Straight(float power){
        motors[0].setPower(power);
        motors[1].setPower(power);
        motors[2].setPower(power);
        motors[3].setPower(power);
    }

    private void TurnClockwise(float power){

        motors[0].setPower(power);
        motors[1].setPower(power);
        motors[3].setPower(-power);
        motors[2].setPower(-power);
    }

    private void ResetMotors(){
        for(int i = 0; i < 4; i++){
            motors[i].setPower(0);
        }
    }

    public void Update(){
        ResetMotors();

        Strafe(gamepad.left_stick_x);
        Straight(-gamepad.right_stick_y);

        if(gamepad.dpad_up){
            TurnClockwise(1);
        }
        else if(gamepad.dpad_down){
            TurnClockwise(-1);
        }
    }

    public void ApplyPID(double force, double margin){
        if(force > -margin && force < margin){
            Straight(1);
        }

        else{
            if(force < 0){
                Strafe((float)force);
            }
            else{
                Strafe((float)force);
            }
        }
    }
}

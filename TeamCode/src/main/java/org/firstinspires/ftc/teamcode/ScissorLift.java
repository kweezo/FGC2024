package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ScissorLift {
    private DcMotor motor1;
    private DcMotor motor2;

    private HardwareMap hardwareMap;
    private Gamepad gamepad;

    float basePower;
    float adjustmentPowerModifier;

    public ScissorLift(String motor1Name, String motor2Name, float basePower, float adjustmentPowerModifier, Gamepad gamepad, HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
        this.gamepad = gamepad;
        this.basePower = basePower;
        this.adjustmentPowerModifier = adjustmentPowerModifier;


        motor1 = hardwareMap.get(DcMotor.class, motor1Name);
        motor2 = hardwareMap.get(DcMotor.class, motor2Name);

        motor1.setDirection(DcMotorSimple.Direction.FORWARD);
        motor2.setDirection(DcMotorSimple.Direction.REVERSE);

        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void Update(){
        motor1.setPower(gamepad.left_stick_y * adjustmentPowerModifier);
        motor2.setPower(gamepad.right_stick_y * adjustmentPowerModifier);

        if (gamepad.dpad_down) {
            motor1.setPower(basePower);
            motor2.setPower(basePower);
        }
        else if(gamepad.dpad_up){
            motor1.setPower(-basePower);
            motor2.setPower(-basePower);
        }
    }
}

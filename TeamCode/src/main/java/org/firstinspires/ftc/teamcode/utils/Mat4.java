package org.firstinspires.ftc.teamcode.utils;

public class Mat4 {
    double[][] matrix;

    public Mat4(double val){
        matrix = new double[4][];

        matrix[0] = new double[]{val, 0.0, 0.0, 0.0};
        matrix[1] = new double[]{0.0, val, 0.0, 0.0};
        matrix[2] = new double[]{0.0, 0.0, val, 0.0};
        matrix[3] = new double[]{0.0, 0.0, 0.0, val};
    }

    public void Rotate(double rad, Boolean x, Boolean y, Boolean z){

    }
}

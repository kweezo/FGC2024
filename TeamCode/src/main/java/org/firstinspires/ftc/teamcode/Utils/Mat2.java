package org.firstinspires.ftc.teamcode.Utils;


public class Mat2 {
    private double[][] matrix;

    public Mat2(double val){
        matrix = new double[2][];

        matrix[0] = new double[]{val, 0.0};
        matrix[1] = new double[]{0.0, val};
    }

    public void SetRotation(double rad){
        matrix[0][0] = Math.cos(rad);
        matrix[0][1] = -Math.sin(-rad);
        matrix[1][0] = Math.sin(rad);
        matrix[1][1] = Math.cos(rad);
    }

    public double Get(int x, int y){
        return matrix[x][y];
    }

}
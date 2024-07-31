package org.firstinspires.ftc.teamcode.utils;

public class Vec2 {
    double x;
    double y;

    public Vec2(){
        x = 0;
        y = 0;
    }

    public Vec2(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vec2(double[] points){
        x = points[0];
        y = points[1];
    }

    public void Multiply(Mat2 mat){
        x = x * mat.Get(0, 0) - y * mat.Get(0, 1);
        y = x * mat.Get(0, 1) + y * mat.Get(1, 1);
    }

    public double x(){
        return x;
    }

    public double y(){
        return y;
    }
}

package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

@TeleOp(name = "TestTest")
public class CamTest1 extends LinearOpMode {

    private AprilTagProcessor tagProcessor;
    private VisionPortal visionPortal;
    private TfodProcessor tfodProcessor;
    int camFOV = 60; //hardcoded FOV

    private void InitCam() {
        VisionPortal.Builder visionPortalBuilder = new VisionPortal.Builder();

        visionPortalBuilder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));

        visionPortalBuilder.setCameraResolution(new Size(640, 480));
        visionPortalBuilder.setStreamFormat(VisionPortal.StreamFormat.YUY2);
        visionPortalBuilder.enableLiveView(true);
        visionPortalBuilder.setAutoStopLiveView(true);

        visionPortalBuilder.addProcessor(tagProcessor);

        visionPortal = visionPortalBuilder.build();
    }

    private void InitTFProcessor() {
        TfodProcessor.Builder tfodProcessorBuilder = new TfodProcessor.Builder();

        tfodProcessorBuilder.setMaxNumRecognitions(6); //number of filed QR codes
        tfodProcessorBuilder.setUseObjectTracker(true);
        tfodProcessorBuilder.setTrackerMaxOverlap(0.2f);
        tfodProcessorBuilder.setTrackerMinSize(16);
        tfodProcessorBuilder.setTrackerMinCorrelation(0.005f);
        tfodProcessorBuilder.setNumExecutorThreads(8);
        tfodProcessorBuilder.setNumDetectorThreads(8);

        tfodProcessor = tfodProcessorBuilder.build();
    }

    private void InitTagProcessor() {
        AprilTagProcessor.Builder tagProcessorBuilder = new AprilTagProcessor.Builder();

        tagProcessorBuilder.setDrawTagID(true);
        tagProcessorBuilder.setDrawTagOutline(true);
        tagProcessorBuilder.setDrawAxes(true);
        tagProcessorBuilder.setDrawCubeProjection(true);
        tagProcessorBuilder.setNumThreads(8);
        tagProcessorBuilder.setOutputUnits(DistanceUnit.CM, AngleUnit.DEGREES);
        tagProcessorBuilder.build();

        tagProcessor = tagProcessorBuilder.build();
    }

    @Override
    public void runOpMode() {
        InitTFProcessor();
        InitTagProcessor();
        InitCam();

        while (!isStopRequested()) {
            List<AprilTagDetection> tagDetections;

            tagDetections = tagProcessor.getDetections();

            telemetry.addLine("DEBUG DATA");

            for (AprilTagDetection tag : tagDetections) {
                telemetry.addData(
                        String.valueOf(tag.id),
                        "(" + tag.center.x + "," + tag.center.y + ")"
                );
            }
            telemetry.update();
        }

    }
}

package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

public class Camera{

    private AprilTagProcessor tagProcessor;
    private VisionPortal visionPortal;
    private TfodProcessor tfodProcessor;

    private int camFOV = 60; //hardcoded FOV
    private int camResX = 1280;
    private int camResY = 720;

    private HardwareMap hardwareMap;
    private Telemetry telemetry;

    private void InitCam() {
        VisionPortal.Builder visionPortalBuilder = new VisionPortal.Builder();

        visionPortalBuilder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));

        visionPortalBuilder.setCameraResolution(new Size(camResX, camResY));
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
        tagProcessorBuilder.setOutputUnits(DistanceUnit.CM, AngleUnit.RADIANS);

        tagProcessorBuilder.build();

        tagProcessor = tagProcessorBuilder.build();


    }

    public Camera(int camResX, int camResY, HardwareMap hardwareMap, Telemetry telemetry) {
        this.camResX = camResX;
        this.camResY = camResY;
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        InitTFProcessor();
        InitTagProcessor();
        InitCam();
    }

    public AprilTagDetection Update() {
        List<AprilTagDetection> tagDetections;

        tagDetections = tagProcessor.getDetections();

        telemetry.addLine("DEBUG DATA");

        for (AprilTagDetection tag : tagDetections) {

            telemetry.addData(
                    String.valueOf(tag.id),
                    "TAG CENTRE: (" + (int) tag.center.x + "," + (int) tag.center.y + ")\n" +
                            "HEADING: " + tag.ftcPose.bearing + "\n" +
                            "DIST: " + tag.ftcPose.range
            );

            return tag;
        }

        return null;
    }
}


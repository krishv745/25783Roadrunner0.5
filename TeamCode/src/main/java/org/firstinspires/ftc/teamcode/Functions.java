package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Timer;

public class Functions {
    private Outtake outtake;
    private Intake intake;
    private Hang hang;
    private Differential slides;

    public enum SpecimenPickupState {
        START_SHOULDER,
        START_ELBOW,
        START_WRIST,
        START_CLAW
    }

    SpecimenPickupState specimenPickup = SpecimenPickupState.START_SHOULDER;

    public enum SpecimenPulldownState {
        PULLDOWN_SHOULDER,
        PULLDOWN_ELBOW,
        PULLDOWN_WRIST,
        PULLDOWN_CLAW
    }

    SpecimenPulldownState specimenPulldown = SpecimenPulldownState.PULLDOWN_SHOULDER;

    public enum SpecimenScoreState {
        SCORE_SHOULDER,
        SCORE_ELBOW,
        SCORE_WRIST,
        SCORE_CLAW
    }

    SpecimenScoreState specimenScore = SpecimenScoreState.SCORE_SHOULDER;

    public final String[] MAKESHIFT = {"DOWN", "CLOSE", "UP", "SCORE"};
    ElapsedTime timer = new ElapsedTime();

    boolean x, a, b = false;

    public Functions(HardwareMap map) {


        outtake = new Outtake(map);
    }

    public void init() {


        outtake.init();
        timer.reset();
    }

    //left bumper - claw open
    //right bumper - claw close
    //pickup to ram position - a
    // ram posiion to score - b
    // score to start position - x;

    public void start() {
        switch (specimenPickup) {
            case START_CLAW:
                outtake.clawOpen();
                specimenPickup = SpecimenPickupState.START_SHOULDER;
                break;
            case START_SHOULDER:
                outtake.shoulderStart();
                specimenPickup = SpecimenPickupState.START_ELBOW;
                break;
            case START_ELBOW:
                outtake.elbowStart();
                specimenPickup = SpecimenPickupState.START_WRIST;
                break;
            case START_WRIST:
                outtake.wristStart();
                specimenPickup = SpecimenPickupState.START_CLAW;
                break;

        }
    }

    public void pulldown() {
        switch (specimenPulldown) {
            case PULLDOWN_ELBOW:
                outtake.elbowPulldown();
                specimenPulldown = SpecimenPulldownState.PULLDOWN_WRIST;
                break;
            case PULLDOWN_SHOULDER:
                outtake.shoulderPullDown();
                specimenPulldown = SpecimenPulldownState.PULLDOWN_ELBOW;
                break;

            case PULLDOWN_WRIST:
                outtake.wristPulldown();
                specimenPulldown = SpecimenPulldownState.PULLDOWN_CLAW;
                break;
            case PULLDOWN_CLAW:
                outtake.clawClose();
                specimenPulldown = SpecimenPulldownState.PULLDOWN_SHOULDER;
                break;
        }
    }

    public void score() {
        switch (specimenScore) {
            case SCORE_SHOULDER:
                outtake.shoulderScore();
                specimenScore = SpecimenScoreState.SCORE_ELBOW;
                break;
            case SCORE_ELBOW:
                outtake.elbowScore();
                specimenScore = SpecimenScoreState.SCORE_WRIST;
                break;
            case SCORE_WRIST:
                outtake.wristScore();
                specimenScore = SpecimenScoreState.SCORE_CLAW;
                break;
            case SCORE_CLAW:
                outtake.clawClose();
                specimenScore = SpecimenScoreState.SCORE_SHOULDER;
                break;
        }
    }

    public void openClaw() {
        outtake.clawOpen();
    }
    public void closeClaw() {
        outtake.clawClose();
    }
    public void inOpenClaw() {
        intake.inClawOpen();
    }
    public void inCloseClaw() { intake.inClawClosed(); }
    public void inOut() {
        intake.inArmOut();
    }
    public void inUp() { intake.inArmUp(); }


}
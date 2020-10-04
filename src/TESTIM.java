package org.firstinspires.ftc.teamcode;

import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import java.util.Timer;
import static java.lang.Math.abs;
import static java.lang.Math.floor;
import static java.lang.Math.hypot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


/**
 * Created by HP on 02.06.2017.
 */
@TeleOp(name="XUI")

public class TESTIM extends LinearOpMode{
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor left = null; // контрол хаб порт 2, движение
    private DcMotor right = null; // контрол хаб порт 3, движение
    private DcMotor lift = null; // контрол хаб порт 1, лифт
    private DcMotor LiftL = null; // экспаншн порт 2, подъемник
    private DcMotor LiftR = null; // экспаншн порт 1, подъемник
    private DcMotor conveyor = null; // экспаншн порт 3, конвейер
    private DcMotor WindTurbine = null; // контрол порт 0, ветряк


    private CRServo forwardwind = null; // контрол порт 1, выдвижение
    private CRServo beatingcube = null; // контрол порт 2, поворот куба
    private Servo container = null; // экспаншн порт 4, закрывалка контейнера
    private Servo claw = null; // экспаншн порт 5, клешня
    private CRServo unload = null; // контрол порт 1, отвечает за выгрузку
    private TouchSensor KNOPOCHKA;

    int koff1=1;
    double KDvizh;

    public void runOpMode() throws InterruptedException
    { conveyor = hardwareMap.get(DcMotor.class, "conveyor");
        LiftL = hardwareMap.get(DcMotor.class, "leftgetup");
        LiftR = hardwareMap.get(DcMotor.class, "rightgetup");
        WindTurbine = hardwareMap.get(DcMotor.class, "WindTurbine");
        lift = hardwareMap.get(DcMotor.class, "lift");

        right = hardwareMap.get(DcMotor.class, "right");
        left = hardwareMap.get(DcMotor.class, "left");


        container = hardwareMap.get(Servo.class, "container");
        forwardwind = hardwareMap.get(CRServo.class, "forwardwind");
        claw = hardwareMap.get(Servo.class, "claw");
        unload = hardwareMap.get(CRServo.class, "unload");
        beatingcube = hardwareMap.get(CRServo.class, "beatingcube");
        KNOPOCHKA=hardwareMap.get(TouchSensor.class,"KNOPOCHKA");
        int i=0;
        int z=-10;
        int sum=0;
        int sum1=0;
        boolean bCurrState5;
        boolean bPrevState5=true;
        int STATEMENT5=0;
        boolean bCurrState3;
        boolean bPrevState3=true;
        int STATEMENT3=0;
        boolean bCurrState;
        boolean bPrevState=true;
        boolean bCurrState4;
        boolean bPrevState4=true;
        int STATEMENT=0;
        boolean bCurrState1;
        boolean bPrevState1=true;
        int STATEMENT1=0;
        int STATEMENT4=0;
        int KNOPKA_Y=0;
        int turbinebackward = 0;
        int turbineforward = 0;
        int claw1 = 0;
        int containeropen = 0;
        int VIGR = 2;
        int claw2 = 0;
        double RB = 0.5;
        long time1 = 0;
        long time2 = 0;
        long time3 = 0;
        int LEFTBUMPER=1;
        int loading = 0;
        int REVERSE=1;
        double koff=0.5;
        double start=0;
        double KDvizh=0;
        double KMOVE=1;
        int startaction = 1;
        double CurrMotor=0;
        double Motor=0;
        while(i < 100){
            i++;
            sum=sum+ LiftR.getCurrentPosition();
            sum1=sum1+LiftL.getCurrentPosition();
        }
        sum = sum/100;
        sum1 = sum1/100;
        int Target1 = sum + (650);
        int Target = sum + (int)(850);
        int y = sum1-850;
        int y1 = sum1-650;
        ElapsedTime opmodeRunTime = new ElapsedTime();//время в роботе
        List targets= new ArrayList();
        List ys=new ArrayList();
        while (z<11) {
            z++;
            targets.add(9 + z, Target1+ z );
            ys.add(9 + z, y1 - z );
        }
        waitForStart();
        runtime.reset();
        start=opmodeRunTime.milliseconds();
        while (opModeIsActive()) {
            if(opmodeRunTime.milliseconds()-start<3000) {
                forwardwind.setPower(1);
            }
            else{
                forwardwind.setPower(0);
                turbineforward=1;
            }
            time2 = System.currentTimeMillis();
            Motor=gamepad1.left_stick_y;
            if(gamepad1.y){
                REVERSE=-REVERSE;
                while(gamepad1.y);
            }
            if(gamepad1.dpad_down){
                KMOVE=0.45;
            }
            if(gamepad1.dpad_up){
                KMOVE=1;
            }
            if(REVERSE>0){
                if(gamepad1.a  || gamepad1.left_stick_y==0){
                    left.setPower(gamepad1.left_stick_y*KMOVE);
                    right.setPower(-gamepad1.left_stick_y*KMOVE);
                    KDvizh=0;
                }else{
                    if(gamepad1.b){
                        KDvizh=KDvizh+0.35;
                        while(gamepad1.b);
                    }else{
                        if(gamepad1.x){
                            KDvizh=KDvizh-0.35;
                            while(gamepad1.x);
                        }
                    }
                    if(KDvizh>2)KDvizh=2;
                    if(KDvizh<-2)KDvizh=-2;
                    if(KDvizh>0){
                        left.setPower(gamepad1.left_stick_y*KMOVE);
                        right.setPower(-gamepad1.left_stick_y*(1-KDvizh)*KMOVE);
                    }else{
                        if(KDvizh<0){
                            left.setPower(gamepad1.left_stick_y*(1+KDvizh)*KMOVE);
                            right.setPower(-gamepad1.left_stick_y*KMOVE);
                        }else{left.setPower(gamepad1.left_stick_y*KMOVE);
                            right.setPower(-gamepad1.left_stick_y*KMOVE);}
                    }
                    Motor=gamepad1.left_stick_y;}
                telemetry.addLine("FORWARD");

            }else{if(KMOVE==1){
                if(gamepad1.a ||  gamepad1.left_stick_y==0){

                    left.setPower(gamepad1.left_stick_y*KMOVE);
                    right.setPower(-gamepad1.left_stick_y*KMOVE);
                    KDvizh=0;
                }else{
                    if(gamepad1.b){
                        KDvizh=KDvizh+0.2;
                        while(gamepad1.b);
                    }else{
                        if(gamepad1.x){
                            KDvizh=KDvizh-0.2;
                            while(gamepad1.x);
                        }
                    }
                    if(KDvizh>2)KDvizh=2;
                    if(KDvizh<-2)KDvizh=-2;
                    if(KDvizh>0){
                        right.setPower(gamepad1.left_stick_y*KMOVE);
                        left.setPower(-gamepad1.left_stick_y*(1-KDvizh)*KMOVE);
                    }else{
                        if(KDvizh<0){
                            right.setPower(gamepad1.left_stick_y*(1+KDvizh)*KMOVE);
                            left.setPower(-gamepad1.left_stick_y*KMOVE);
                        }else{right.setPower(gamepad1.left_stick_y*KMOVE);
                            left.setPower(-gamepad1.left_stick_y*KMOVE);}
                    }}}else{

                if(gamepad1.a ||  gamepad1.left_stick_y==0){

                    left.setPower(gamepad1.left_stick_y*KMOVE);
                    right.setPower(-gamepad1.left_stick_y*KMOVE);
                    KDvizh=0;
                }else{
                    if(gamepad1.b){
                        KDvizh=KDvizh+0.35;
                        while(gamepad1.b);
                    }else{
                        if(gamepad1.x){
                            KDvizh=KDvizh-0.35;
                            while(gamepad1.x);
                        }
                    }
                    if(KDvizh>2)KDvizh=2;
                    if(KDvizh<-2)KDvizh=-2;
                    if(KDvizh>0){
                        right.setPower(gamepad1.left_stick_y*KMOVE);
                        left.setPower(-gamepad1.left_stick_y*(1-KDvizh)*KMOVE);
                    }else{
                        if(KDvizh<0){
                            right.setPower(gamepad1.left_stick_y*(1+KDvizh)*KMOVE);
                            left.setPower(-gamepad1.left_stick_y*KMOVE);
                        }else{right.setPower(gamepad1.left_stick_y*KMOVE);
                            left.setPower(-gamepad1.left_stick_y*KMOVE);}
                    }}
            }
                telemetry.addLine("REVERSE");

            }

            bCurrState=gamepad2.x;
            if (bCurrState &&  !bPrevState) {
                STATEMENT += 1;
                bPrevState = true;
                while(gamepad2.x){}
            } else {
                bPrevState = false;
            }
            if (STATEMENT > 0) {
                if ((STATEMENT % 2 == 0)) {
                    KNOPKA_Y=1;}
                else{
                    KNOPKA_Y=0;
                }
            }

            if(KNOPKA_Y==0){
                telemetry.addLine("Автоматическое управление лифтом");
                if (LiftR.getCurrentPosition() != Target && LiftL.getCurrentPosition() != y) {
                    if (gamepad2.y) {
                        telemetry.addLine("RB");
                        LiftR.setTargetPosition(Target);
                        LiftL.setTargetPosition(y);
                        LiftR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        LiftL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        LiftR.setPower(1);
                        LiftL.setPower(-1);
                    }
                }
                if (gamepad2.b) {
                    boolean proverka = targets.contains(LiftR.getCurrentPosition());
                    boolean proverkaL = ys.contains(LiftL.getCurrentPosition());
                    if(LiftR.getCurrentPosition() != Target1 && LiftL.getCurrentPosition() != y1){
                        telemetry.addLine("LB");
                        LiftR.setTargetPosition(Target1);
                        LiftL.setTargetPosition(y1);
                        LiftR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        LiftL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        LiftR.setPower(1);
                        LiftL.setPower(-1);

                    }}
                if (gamepad2.a){
                    telemetry.addLine("A");
                    LiftR.setTargetPosition(Target1-600);
                    LiftL.setTargetPosition(y1+600);
                    LiftR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    LiftL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    LiftR.setPower(-0.5);
                    LiftL.setPower(0.5);
                }
            }else{
                telemetry.addLine("Ручное управление лифтом");
                LiftR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                LiftL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                LiftL.setPower(gamepad2.right_stick_y);
                LiftR.setPower(-gamepad2.right_stick_y);

            }



            if (gamepad2.dpad_up) {
                switch (turbineforward) {
                    case 0:
                        time1 = System.currentTimeMillis();
                        while ((System.currentTimeMillis() - time1) < 1150) {

                            forwardwind.setPower(1);
                        }
                        turbineforward = 1;
                        forwardwind.setPower(0);
                        break;
                    case 1:
                        forwardwind.setPower(0);
                        break;
                }
                while (gamepad2.dpad_up) ;
            }

            if (gamepad2.dpad_down) {
                switch (turbineforward) {
                    case 1:
                        time2 = System.currentTimeMillis();
                        while ((System.currentTimeMillis() - time2) < 1000) {

                            forwardwind.setPower(-1);
                        }
                        turbineforward = 0;
                        forwardwind.setPower(0);
                        break;
                    case 0:
                        turbineforward = 0;
                        forwardwind.setPower(0);
                        break;
                }
                while (gamepad2.dpad_down) ;
            }

            if (gamepad2.dpad_left) {
                WindTurbine.setPower(-1);
            } else if (gamepad2.dpad_right) {
                WindTurbine.setPower(1);
            } else {
                WindTurbine.setPower(0);
            }

            if (gamepad1.right_bumper){
                switch(loading){
                    case 0:
                        loading = 1;
                        lift.setPower(1);
                        unload.setPower(1);
                        beatingcube.setPower(1);
                        break;
                    case 1:
                        loading = 0;
                        lift.setPower(0);
                        unload.setPower(0);
                        beatingcube.setPower(0);
                        break;
                }
                while (gamepad1.right_bumper);
            }
            bCurrState3=gamepad2.left_bumper;
            if (bCurrState3 && !bPrevState3) {
                STATEMENT3 += 1;
                bPrevState3 = true;
                while(gamepad2.left_bumper){}
            } else {
                bPrevState3 = false;
            }
            if (STATEMENT3 > 0) {
                if ((STATEMENT3 % 2 == 0)) {
                    LEFTBUMPER=1;}
                else{
                    LEFTBUMPER=-1;
                }
            }
            conveyor.setPower(LEFTBUMPER*1);
            if(gamepad2.right_bumper){
                VIGR+=1;
                while(gamepad2.right_bumper);
            }
            if(gamepad2.right_trigger>=0.5){
                VIGR-=1;
                while(gamepad2.right_trigger>=0.5);
            }

            if(VIGR>2){
                VIGR=2;
            }if(VIGR<0){
                VIGR=0;
            }
            if(VIGR==0){
                container.setPosition(1);
            }else{if(VIGR==1){
                if(gamepad1.left_bumper){
                    claw.setPosition(0);
                }if(gamepad1.left_trigger>=0.5){
                    claw.setPosition(1);
                }
                container.setPosition(0.6);
            }else{container.setPosition(0.2);
                claw.setPosition(0);}
            }

            telemetry.addData("Позиция закрывалки=",VIGR);
            telemetry.addLine();
            telemetry.addData("Коффициент движения",KDvizh);
            telemetry.addLine();
            telemetry.addData("Позиция закрывалки=",time3/1000);
            telemetry.update();
        }
    }}
 
abstract class Thing {
    int substate = 0;
    int substateGoal = 0;
    int ticksThisState = 0;
    int state = 0;

    // ADMove variables
    final double A = 0.2;
    final double N = 1.7;
    double Ef = 0;
    double Es = 0;
    int D = 3;
    double Cf = 0;
    double Cs = 0;
    boolean deceleratingf = false;
    boolean deceleratings = false;
    int fnegative = 1;
    int snegative = 1;

    //Fake robot variables
    double Ps = 0;
    double Pf = 0;

    public void list (int i) {}
    public final void complete () {
        substate ++;
    }
    public final boolean synchronize () {
        if (ticksThisState == 0) {
            substateGoal ++;
            return true;
        }
        return false;
    }

    public final void update () {
        if (substate == substateGoal) {
            state ++;
            ticksThisState = 0;
        }
        list(state);
        ticksThisState ++;
    }

    public final void ADMove (int forward, int strafe, double targetSpeed) {
        double Sf = 0;
        double Ss = 0;
        if (forward < 0) {
            fnegative = -1;
        }
        if (strafe < 0) {
            snegative = -1;
        }
        if (strafe == 0) {
            Ss = 0;
        } else {
            Ss = 0.1*snegative;
        }
        if (forward == 0) {
            Sf = 0;
        } else {
            Sf = 0.1*fnegative;
        }
        if (synchronize()) {
            Ef = Pf + forward;
            Es = Ps + strafe;
            deceleratingf = false;
            deceleratings = false;
        } else {
            //forward math
            if (!deceleratingf) {
                Sf = Math.min(Math.pow(Math.abs(Cf), Math.abs(A)), Math.abs(targetSpeed))*fnegative;
                if (Math.abs(Ef - Pf) <= D) {
                    deceleratingf = true;
                }
            } else {
                Sf = Math.max(Math.pow(Math.abs(Cf), Math.abs(N)), 0)*fnegative;
            }
            //strafe math
            if (!deceleratings) {
                Ss = Math.min(Math.pow(Math.abs(Cs), Math.abs(A)), Math.abs(targetSpeed))*snegative;
                if (Math.abs(Es - Ps) <= D) {
                    deceleratings = true;
                }
            } else {
                Ss = Math.max(Math.pow(Math.abs(Cs), Math.abs(N)), 0)*snegative;
            }
        }
        if (Ss >= 1) {
            Ss = 0.99;
        }
        if (Ss <= -1) {
            Ss = -0.99;
        }
        if (Sf >= 1) {
            Sf = 0.99;
        }
        if (Sf <= -1) {
            Sf = -0.99;
        }
        //insert movement here. Sf is forward speed, Ss is strafe speed
        //everything after this point is useless and won't work in ftc
        Ps += Ss;
        Pf += Sf;
        Cs = Ss;
        Cf = Sf;
        if (Ss == 0 && Sf == 0) {
            complete();
        }
        System.out.println(
                "Forward position: " + Pf +
                "\nStrafe Position: " + Ps +
                "\nForward speed: " + Cf +
                "\nStrafe speed: " + Cs +
                "\nForward decel: " + deceleratingf +
                "\nStrafe decel: " + deceleratings +
                "\nForward end: " + Ef +
                "\nStrafe end: " + Es +
                "\n" + Math.pow(Math.abs(Cf), Math.abs(A))
        );
    }
}

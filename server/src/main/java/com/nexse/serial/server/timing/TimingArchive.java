package com.nexse.serial.server.timing;

import java.util.ArrayList;
import java.util.List;

public class TimingArchive {
    private static TimingArchive ourInstance = new TimingArchive();


    public static int progr = 0;

    public static TimingArchive getInstance() {
        return ourInstance;
    }

    private TimingArchive() {
    }


    private static TimingBean current;

    public static void renewCurrent() {
        current = new TimingBean();
        current.setMarksenseProgr(progr++);
    }

    public static TimingBean getCurrent() {
        return current;

    }

    public static void storeResult(String bscode) {
        current.setMSCCode(bscode);
        timingBeanList.add(current);
        renewCurrent();
    }

    private static ArrayList<TimingBean> timingBeanList = new ArrayList<>();

    public static List<TimingBean> getAll() {
        return timingBeanList;
    }

    public static void refreshALL() {
        ourInstance = new TimingArchive();
        renewCurrent();
    }


}

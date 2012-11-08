package com.nexse.serial.server.timing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConvertToPrintable {

    public static List<TimeBeanForPrint> convert() {


        List<TimingBean> tblist = TimingArchive.getAll();
        Collections.sort(tblist);


        ArrayList<TimeBeanForPrint> retList = new ArrayList<>();
        for (TimingBean timingBean : tblist) {
            TimeBeanForPrint tb4p = new TimeBeanForPrint();
            tb4p.mscCode =timingBean.getMsccode();
            tb4p.listaTimeDetails = timingBean.getATimeDetailsList();
             retList.add(tb4p);
        }
       return retList;

    }
}

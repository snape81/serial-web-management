package com.nexse.serial.server.bean;

import java.util.ArrayList;

public class ArrayValueContainer {

       private boolean[] readeadValue;
       private int offset;
       private int settledLength;




       public ArrayValueContainer(int arrayLength, int offset) {
           this.offset = offset;
           readeadValue = new boolean[arrayLength];
       }

       public void setAReadedValue(int rowIndex) {
           this.readeadValue[offset-rowIndex] = true;
           settledLength++;
       }

       public boolean isSetted(int index) {
           return this.readeadValue[index];
       }

       public Integer[] getAllSettledIdx() {
           ArrayList<Integer> retTemp = new ArrayList<>();
           for (int i = 0; i < readeadValue.length; i++) {
                if (readeadValue[i]) {
                    retTemp.add(i);
                }
           }
           return retTemp.toArray(new Integer[retTemp.size()]);

       }

    public int getSettledLength() {
        return settledLength;
    }
}
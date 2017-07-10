package com.rukiasoft.rukiapics.utilities;


import com.rukiasoft.rukiapics.model.PicturePojo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListDateTakenComparator implements java.util.Comparator<PicturePojo>, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Override
    public int compare(PicturePojo p1, PicturePojo p2) {
        if(p1.getDatetaken() == null)
            return 1;
        if(p2.getDatetaken() == null)
            return -1;
        Date d1, d2;
        //the date is in format YYYY-MM-dd hh:mm:ss
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
        try {
            d1 = format.parse(p1.getDatetaken());
        }catch (ParseException e){
            return 1;
        }
        try {
            d2 = format.parse(p2.getDatetaken());
        }catch (ParseException e){
            return -1;
        }

        return d2.after(d1)? 1 : -1;
    }
}

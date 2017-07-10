package com.rukiasoft.rukiapics.utilities;


import com.rukiasoft.rukiapics.model.PicturePojo;

import java.io.Serializable;
import java.util.Date;

public class ListDatePublishedComparator implements java.util.Comparator<PicturePojo>, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Override
    public int compare(PicturePojo p1, PicturePojo p2) {
        if(p1.getDateupload() == null)
            return 1;
        if(p2.getDateupload() == null)
            return -1;
        Date d1, d2;
        //The date is in milliseconds
        try {
            d1 = new Date(Long.valueOf(p1.getDateupload()));
        }catch (NumberFormatException e){
            return 1;
        }
        try {
            d2 = new Date(Long.valueOf(p2.getDateupload()));
        }catch (NumberFormatException e){
            return -1;
        }

        return d2.after(d1)? 1 : -1;
    }
}

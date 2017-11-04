package com.nationfis.controlacademicononfc;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Sam on 11/06/2017.
 */

public class Helper {
    public static void getListViewSize(ListView estudiantes) {
        ListAdapter my = estudiantes.getAdapter();
        if(my == null){
            return;
        }
        int total=0;
        for (int size=0;size<my.getCount();size++){
            View ListItem = my.getView(size,null,estudiantes);
            ListItem.measure(0,0);
            total +=ListItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params  = estudiantes.getLayoutParams();
        params.height = total + (estudiantes.getDividerHeight()* (my.getCount() -1));
        estudiantes.setLayoutParams(params);
        Log.i("Heigth of list",String.valueOf(total));

    }
}

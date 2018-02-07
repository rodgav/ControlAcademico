package com.nationfis.controlacademicononfc.Views.ComprobarAsistencia;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

/*
 * Created by GlobalTIC's on 3/02/2018.
 */

public class ItemTouchHelperCallbackAsistencia extends ItemTouchHelperExtension.Callback {
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(ItemTouchHelper.START, ItemTouchHelper.START);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (dY != 0 && dX == 0) super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        AdaptadorAsistencia.CuerpoAsistencia holder = (AdaptadorAsistencia.CuerpoAsistencia) viewHolder;
            if (dX < -holder.accion.getWidth()) {
                dX = -holder.accion.getWidth();
            }
            holder.view.setTranslationX(dX);
    }
}

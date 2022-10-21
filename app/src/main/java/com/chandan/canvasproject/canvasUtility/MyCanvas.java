package com.chandan.canvasproject.canvasUtility;

import android.content.Context;
import android.graphics.Canvas;
import android.view.DragEvent;
import android.view.View;

public class MyCanvas extends View {
    public MyCanvas(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        return super.onDragEvent(event);
    }


}

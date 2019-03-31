package com.pgg.yixiannonapp.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.pgg.yixiannonapp.utils.PixelUtil;


/**
 * Created by pgg on 2019/3/31.
 */

public class MyAlertDialog {
    private Context context;
    private String[] strings;
    AlertDialog dialog;
    DialogInterface.OnClickListener onClick;
    private int GRAVITY_CENTER = Gravity.CENTER;
    private int GRAVITY_BOTTOM = Gravity.BOTTOM;
    private int GRAVITY_TOP = Gravity.TOP;
    private int GRAVITY_RIGHT = Gravity.RIGHT;
    private int GRAVITY_LEFT = Gravity.LEFT;
    private Window window;
    private WindowManager.LayoutParams params;
    public MyAlertDialog(Context context, String[] strings, DialogInterface.OnClickListener onClick) {
        this.context = context;
        this.strings = strings;
        this.onClick = onClick;
    }

    /**
     *
     * @param gravity 窗体位置
     */
    public void initDialog(int gravity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(strings, onClick);
        dialog = builder.create();
        dialog.show();

        initGravity(gravity);
    }

    public void initDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(strings, onClick);
        dialog = builder.create();
        dialog.show();
    }

    /**
     *
     * @param width 宽度
     * @param hight 高度
     * @param x x坐标
     * @param y y坐标
     */
    public void dialogSize(int width,int hight,int x,int y){
        if (width!=0){
            params.width = PixelUtil.dp2px(width, context);
        }
        if (hight!=0){
            params.height=PixelUtil.dp2px(hight,context);
        }
        if (x!=0){
            params.x = PixelUtil.dp2px(x,context);
        }
        if (y!=0){
            params.y = PixelUtil.dp2px(y,context);
        }
        dialog.getWindow().setAttributes(params);
    }


    public void initGravity(int gravity){
        window = dialog.getWindow();
        params = window.getAttributes();
        if (gravity!=0) {
            window.setGravity(gravity);
        }
    }


}

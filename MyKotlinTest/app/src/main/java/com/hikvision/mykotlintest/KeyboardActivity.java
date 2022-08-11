package com.hikvision.mykotlintest;
import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.hikvision.mykotlintest.util.KeyBoardEditTextJava;

public class KeyboardActivity extends AppCompatActivity {

    private KeyBoardEditTextJava text;
    private KeyboardView keyboardView;
    private LinearLayout layout;
    private LinearLayout root;
    private int height = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keyboard_main);

        //输入框，一点击这个text应该是要弹出键盘
        text =  findViewById(R.id.ed_main);
        //难道说是设置整个键盘的样式吗？
        keyboardView =  findViewById(R.id.view_keyboard);
        layout =  findViewById(R.id.layout_main);
        root =  findViewById(R.id.layout_root);

        //设置弹出键盘的样式
        text.setKeyboardType(layout, keyboardView, true);

        text.setOnKeyBoardStateChangeListener(new KeyBoardEditTextJava.OnKeyboardStateChangeListener() {
            @Override
            public void show() {
                root.post(new Runnable() {
                    @Override
                    public void run() {

                        int[] pos = new int[2];
                        //获取编辑框在整个屏幕中的坐标
                        text.getLocationOnScreen(pos);
                        //编辑框的Bottom坐标和键盘Top坐标的差
                        height = (pos[1] + text.getHeight()) -
                                (getScreenHeight(KeyboardActivity.this) - keyboardView.getHeight());
                        if (height > 0) {
                            root.scrollBy(0, height + dp2px(KeyboardActivity.this, 16));
                        }
                    }
                });
            }

            @Override
            public void hide() {

                if (height > 0) {
                    root.scrollBy(0, -(height + dp2px(KeyboardActivity.this, 16)));
                }
            }
        });

        //Log.i("zhangdi", getLngAndLat(this));
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return dip
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }
}


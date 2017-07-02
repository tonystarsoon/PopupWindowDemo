package it.cn.demo;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;


public class LostPopupWindow extends PopupWindow implements View.OnClickListener {
    public Lost lost;
    private View conentView;
    private int mLocationX;
    private int mLocationY;

    public LostPopupWindow(Activity context, View view) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.layout_popup_menu, null);
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 点back键和其他地方使其消失,设置了这个背景,才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_bg));
//        this.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);

        int[] location = new int[2];
        view.getLocationOnScreen(location);
        mLocationX = location[0] - 530;
        mLocationY = location[1] + view.getMeasuredHeight() / 2 - 10;

        LinearLayout send = (LinearLayout) conentView.findViewById(R.id.send);
        LinearLayout mySend = (LinearLayout) conentView.findViewById(R.id.my_send);
        LinearLayout all = (LinearLayout) conentView.findViewById(R.id.all);
        send.setOnClickListener(this);
        mySend.setOnClickListener(this);
        all.setOnClickListener(this);
    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
//            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);// 以下拉方式显示popupwindow
            showAtLocation(parent, Gravity.LEFT | Gravity.TOP, mLocationX, mLocationY);
        } else {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        LostPopupWindow.this.dismiss();
        switch (v.getId()) {
            case R.id.send:
                lost.onLost(2);
                break;
            case R.id.my_send:
                lost.onLost(1);
                break;
            case R.id.all:
                lost.onLost(0);
                break;
        }
    }

    public void onLost(Lost lost) {
        this.lost = lost;
    }

    public View getConentView() {
        return conentView;
    }
}
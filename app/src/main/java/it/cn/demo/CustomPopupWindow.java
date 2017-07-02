package it.cn.demo;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * Created by tony on 2017/7/2.
 */
public class CustomPopupWindow implements View.OnClickListener {
    private Lost lost;
    private View conentView;
    private final PopupWindow mPopupWindow;
    private final int[] mLocation;

    public CustomPopupWindow(Activity context, View view) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.layout_popup_menu, null);
        mPopupWindow = new PopupWindow(conentView, -2, -2);

        // 设置SelectPicPopupWindow弹出窗体可点击
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        // 刷新状态
        mPopupWindow.update();
        // 点back键和其他地方使其消失,设置了这个背景,才能触发OnDismisslistener ，设置其他控件变化等操作
        mPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_bg));
//        this.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        mPopupWindow.setAnimationStyle(R.style.AnimationPreview);

        mLocation = calculatePopWindowPos(view, conentView);

        LinearLayout send = (LinearLayout) conentView.findViewById(R.id.send);
        LinearLayout mySend = (LinearLayout) conentView.findViewById(R.id.my_send);
        LinearLayout all = (LinearLayout) conentView.findViewById(R.id.all);
        send.setOnClickListener(this);
        mySend.setOnClickListener(this);
        all.setOnClickListener(this);
    }

    public void show(View parent) {
        if (!mPopupWindow.isShowing()) {
//            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);// 以下拉方式显示popupwindow
            mPopupWindow.showAtLocation(parent, Gravity.LEFT | Gravity.TOP, mLocation[0], mLocation[1]);
        } else {
            mPopupWindow.dismiss();
        }
    }


    @Override
    public void onClick(View v) {
        mPopupWindow.dismiss();
        switch (v.getId()) {
            case R.id.send:
                if (lost != null) {
                    lost.onLost(2);
                }
                break;
            case R.id.my_send:
                if (lost != null) {
                    lost.onLost(1);
                }
                break;
            case R.id.all:
                if (lost != null) {
                    lost.onLost(0);
                }
                break;
        }
    }

    private static int[] calculatePopWindowPos(View anchorView, View contentView) {
        int windowPos[] = new int[2];
        int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationInWindow(anchorLoc);
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        windowPos[0] = anchorLoc[0] - contentView.getMeasuredWidth() + anchorView.getMeasuredWidth() / 3;
        windowPos[1] = anchorLoc[1] + anchorView.getMeasuredHeight() * 2 / 3;
        return windowPos;
    }

    /**
     * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
     * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
     *
     * @param anchorView  呼出window的view
     * @param contentView window的内容布局
     * @return window显示的左上角的xOff, yOff坐标
     */
    private static int[] calculatePopWindowPos1(View anchorView, View contentView) {
        int windowPos[] = new int[2];
        int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        int screenHeight = ScreenUtils.getScreenHeight(anchorView.getContext());
        int screenWidth = ScreenUtils.getScreenWidth(anchorView.getContext());

        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        int windowHeight = contentView.getMeasuredHeight();
        int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            windowPos[0] = screenWidth - windowWidth - anchorView.getMeasuredWidth();
            windowPos[1] = anchorLoc[1] - windowHeight;
        } else {
            windowPos[0] = screenWidth - windowWidth - anchorView.getMeasuredWidth();
            windowPos[1] = anchorLoc[1] + anchorHeight;
        }
        return windowPos;
    }

    public void onLost(Lost lost) {
        this.lost = lost;
    }

    public View getConentView() {
        return conentView;
    }
}

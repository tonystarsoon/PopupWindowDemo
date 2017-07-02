package it.cn.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.menu).setOnClickListener(this);
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        CustomPopupWindow popWindow = new CustomPopupWindow(this, v);
        ((TextView) (popWindow.getConentView().findViewById(R.id.item_content))).setText("查看评论");
        ((TextView) (popWindow.getConentView().findViewById(R.id.item_content1))).setText("发表评论");
        ((TextView) (popWindow.getConentView().findViewById(R.id.item_content2))).setText("转发文章");
        popWindow.onLost(new Lost() {
            @Override
            public void onLost(int index) {
                switch (index) {
                    case 0: //转发文章
                        Toast.makeText(MainActivity.this, "转发", Toast.LENGTH_SHORT).show();
                        break;
                    case 1: //发表评论
                        Toast.makeText(MainActivity.this, "评论", Toast.LENGTH_SHORT).show();
                        break;
                    case 2://查看评论
                        Toast.makeText(MainActivity.this, "查看", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        popWindow.show(v);
    }
}

package com.example.admin.popupwindow;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.admin.popupwindow.R.id.et_input;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView mListView;
    private ArrayList<String> mDatas;
    private EditText mEt_input;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.ib_dropdown).setOnClickListener(this);
        mEt_input = (EditText) findViewById(et_input);
    }

    @Override
    public void onClick(View v) {
        showPopupWindow();
    }

    private void showPopupWindow() {
        initListView();

        // 显示下拉选择框
        mPopupWindow = new PopupWindow(mListView, mEt_input.getWidth(), 600);

        // 设置点击外部区域, 自动隐藏
        mPopupWindow.setOutsideTouchable(true); // 外部可触摸
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable()); // 设置空的背景, 响应点击事件

        mPopupWindow.setFocusable(true); //设置可获取焦点

        // 显示在指定控件下
        mPopupWindow.showAsDropDown(mEt_input, 0, -5);
    }

    private void initListView() {
        mListView = new ListView(this);
        mListView.setDividerHeight(0);
        mListView.setBackgroundResource(R.drawable.listview_background);
        mListView.setOnItemClickListener(this);
        mDatas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            mDatas.add((10000 + i) + "");
        }
        mListView.setAdapter(new MyAdapter());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String string = mDatas.get(position);
        mEt_input.setText(string); // 设置文本

        mPopupWindow.dismiss(); // 消失了
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(parent.getContext(), R.layout.item_number, null);
            } else {
                view = convertView;
            }
            TextView tv_number = (TextView) view.findViewById(R.id.tv_number);
            tv_number.setText(mDatas.get(position));

            view.findViewById(R.id.ib_delete).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mDatas.remove(position);
                    notifyDataSetChanged();

                    if (mDatas.size() == 0) {
                        // 如果删除的是最后一条, 隐藏popupwindow
                        mPopupWindow.dismiss();
                    }
                }
            });
            return view;
        }
    }


}

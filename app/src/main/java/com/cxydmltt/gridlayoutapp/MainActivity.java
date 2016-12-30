package com.cxydmltt.gridlayoutapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvFormula;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }


    private void initViews() {
        /**获取屏幕宽度和高度**/
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        /**设置按钮的宽度和高度，获取按钮控件**/
        GridLayout gridLayout = (GridLayout) findViewById(R.id.grid_test);
        int count = gridLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            Button button = (Button) gridLayout.getChildAt(i);
            button.setOnClickListener(this);
            button.setHeight(screenHeight / 7);
            button.setWidth(screenWidth / 4);
        }
        tvFormula = (TextView) findViewById(R.id.formula);
        tvResult = (TextView) findViewById(R.id.result);
        tvFormula.setTextSize(20.0f);
        tvResult.setTextSize(35.0f);
        tvResult.setText("0");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn0:
                Calculator.getInstance().transferSymbol('0');
                tvFormula.setText(Calculator.getInstance().getFormula());
                break;
            case R.id.btn1:
                Calculator.getInstance().transferSymbol('1');
                tvFormula.setText(Calculator.getInstance().getFormula());
                break;
            case R.id.btn2:
                Calculator.getInstance().transferSymbol('2');
                tvFormula.setText(Calculator.getInstance().getFormula());
                break;
            case R.id.btn3:
                Calculator.getInstance().transferSymbol('3');
                tvFormula.setText(Calculator.getInstance().getFormula());
                break;
            case R.id.btn4:
                Calculator.getInstance().transferSymbol('4');
                tvFormula.setText(Calculator.getInstance().getFormula());
                break;
            case R.id.btn5:
                Calculator.getInstance().transferSymbol('5');
                tvFormula.setText(Calculator.getInstance().getFormula());
                break;
            case R.id.btn6:
                Calculator.getInstance().transferSymbol('6');
                tvFormula.setText(Calculator.getInstance().getFormula());
                break;
            case R.id.btn7:
                Calculator.getInstance().transferSymbol('7');
                tvFormula.setText(Calculator.getInstance().getFormula());
                break;
            case R.id.btn8:
                Calculator.getInstance().transferSymbol('8');
                tvFormula.setText(Calculator.getInstance().getFormula());
                break;
            case R.id.btn9:
                Calculator.getInstance().transferSymbol('9');
                tvFormula.setText(Calculator.getInstance().getFormula());
                break;
            case R.id.btndot:
                Calculator.getInstance().transferSymbol('.');
                tvFormula.setText(Calculator.getInstance().getFormula());
                break;
            case R.id.btnminus:
                Calculator.getInstance().transferSymbol('-');
                tvFormula.setText(Calculator.getInstance().getFormula());
                break;
            case R.id.btnmultiply:
                Calculator.getInstance().transferSymbol('*');
                tvFormula.setText(Calculator.getInstance().getFormula());
                break;
            case R.id.btnplus:
                Calculator.getInstance().transferSymbol('+');
                tvFormula.setText(Calculator.getInstance().getFormula());
                break;
            case R.id.btndivide:
                Calculator.getInstance().transferSymbol('/');
                tvFormula.setText(Calculator.getInstance().getFormula());
                break;
            case R.id.btnlb:
                Calculator.getInstance().transferSymbol('(');
                tvFormula.setText(Calculator.getInstance().getFormula());
                break;
            case R.id.btnrb:
                Calculator.getInstance().transferSymbol(')');
                tvFormula.setText(Calculator.getInstance().getFormula());
                break;
            case R.id.btnc:
                Calculator.getInstance().setFormula("");
                Calculator.getInstance().setResult("");
                tvFormula.setText("");
                tvResult.setText("0");
                break;
            case R.id.btndelete:
                Calculator.getInstance().deleteChar();
                tvFormula.setText(Calculator.getInstance().getFormula());
                break;
            case R.id.btnequal:
                if (Calculator.getInstance().checkEqual('=')) {
                    tvResult.setText(Calculator.getInstance().getResult());
                    Calculator.getInstance().setFormula("");
                    tvFormula.setText("");
                }
                break;
        }
    }
}

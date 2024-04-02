package com.example.coinflip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ImageView coinFront;
    ImageView coinBack;
    ImageView coinDef;
    AppCompatButton flipButton;
    int flag = 1;

    private long pressedTime;

    TextView headCount, tailCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coinFront = findViewById(R.id.coin_front);
        coinBack = findViewById(R.id.coin_back);
        coinDef = findViewById(R.id.coin_def);

        coinFront.setRotationY(0);
        coinBack.setRotationY(360);

        headCount = findViewById(R.id.head_count);
        tailCount = findViewById(R.id.tail_count);

        flipButton = findViewById(R.id.btn_flip);
        flipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip();
            }
        });
    }

//    void flip(){
//        final Runnable enableButton = new Runnable() {
//            @Override
//            public void run() {
//                flipButton.setClickable(true);
//            }
//        };
//        switch (flag){
//            case 0:
//                flipButton.setClickable(false);
//                coinFront.animate().rotationY(-360).setDuration(10000);
//                coinBack.animate().rotationY(360).setDuration(10000);
//                toss();
//                flag=1;
//                new Handler().postDelayed(enableButton, 500);
//                break;
//            case 1:
//                flipButton.setClickable(false);
//                coinBack.animate().rotationY(-360).setDuration(10000);
//                coinFront.animate().rotationY(360).setDuration(10000);
//                toss();
//                flag=0;
//                new Handler().postDelayed(enableButton, 500);
//                break;
//        }
//    }
    void flip(){
        final Runnable enableButton = new Runnable() {
            @Override
            public void run() {
                flipButton.setClickable(true);
            }
        };
        flipButton.setClickable(false);
        coinBack.setRotationY(0);
        coinFront.setRotationY(0);
        coinDef.animate().rotationY(360).setDuration(10000);
        coinFront.animate().rotationY(360).setDuration(10000);
        coinBack.animate().rotationY(360).setDuration(10000);
        toss();
        new Handler().postDelayed(enableButton, 500);
    }
    void toss(){
        Random random = new Random();
        int defColor = headCount.getCurrentTextColor();
        int temp = random.nextInt(2);
        final Runnable txtColChange = new Runnable() {
            @Override
            public void run() {
                headCount.setTextColor(defColor);
                tailCount.setTextColor(defColor);
            }
        };
        coinDef.animate().alpha(0).setDuration(500);
        if(temp==0) {
            coinBack.animate().alpha(0).setDuration(500);
            coinFront.animate().alpha(1).setDuration(500);
            int hCount = Integer.parseInt(headCount.getText().toString());
            hCount++;
            headCount.setText(String.format(Integer.toString(hCount)));
            headCount.setTextColor(headCount.getContext().getColor(R.color.green));
            new Handler().postDelayed(txtColChange, 200);
        }
        else{
            coinFront.animate().alpha(0).setDuration(500);
            coinBack.animate().alpha(1).setDuration(500);
            int tCount = Integer.parseInt(tailCount.getText().toString());
            tCount++;
            tailCount.setText(String.format(Integer.toString(tCount)));
            tailCount.setTextColor(tailCount.getContext().getColor(R.color.green));
            new Handler().postDelayed(txtColChange, 200);
        }
    }

//    public void onBackPressed() {
//        if (pressedTime + 2000 > System.currentTimeMillis()) {
//            super.onBackPressed();
//            finish();
//        } else {
//            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
//        }
//        pressedTime = System.currentTimeMillis();
//    }
@Override
public boolean dispatchKeyEvent(KeyEvent event) {
    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            finish();
        } else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
        return true;
    }
    return super.dispatchKeyEvent(event);
}
}
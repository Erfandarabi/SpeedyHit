package com.example.speedyhit;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView scoreText;
    TextView timeText;
    int score;
    Button restartButton;

    ImageView[] imageArray = new ImageView[9]; // برای نگهداری ۹ حفره ستاره
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // مقداردهی اولیه
        scoreText = findViewById(R.id.scoreText);
        timeText = findViewById(R.id.timeText);
        restartButton=findViewById(R.id.restartButton);
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        score = 0;

        // ساخت ۹ عدد ImageView برای بازی و اضافه کردن به جدول
        for (int i = 0; i < 9; i++) {
            imageArray[i] = new ImageView(this);
            imageArray[i].setImageResource(android.R.drawable.btn_star_big_on);
            imageArray[i].setLayoutParams(new android.view.ViewGroup.LayoutParams(200, 200));
            imageArray[i].setVisibility(View.INVISIBLE); // در ابتدا مخفی باشند
            gridLayout.addView(imageArray[i]);

            // وقتی روی ستاره کلیک شد
            imageArray[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    score++;
                    scoreText.setText("امتیاز: " + score);
                }
            });
        }

        startTimer();
        hideImages();
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 0;
                scoreText.setText("امتیاز: 0");
                restartButton.setVisibility(View.GONE); // دکمه دوباره مخفی بشه
                startTimer(); // زمان از ۳۰ شروع بشه
                hideImages(); // ستاره‌ها شروع به حرکت کنن
            }
        });
    }

    // مدیریت زمان کل بازی (۳۰ ثانیه)
    private void startTimer() {
        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText("زمان: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                timeText.setText("تمام!");
                restartButton.setVisibility(View.VISIBLE);
                handler.removeCallbacks(runnable); // متوقف کردن حرکت ستاره ها
                for (ImageView image : imageArray) {
                    image.setVisibility(View.INVISIBLE);
                }
                Toast.makeText(MainActivity.this, "بازی تموم شد! امتیاز شما: " + score, Toast.LENGTH_LONG).show();
            }
        }.start();
    }

    // منطق غیب و ظاهر شدن تصادفی ستاره ها
    private void hideImages() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for (ImageView image : imageArray) {
                    image.setVisibility(View.INVISIBLE);
                }

                Random random = new Random();
                int i = random.nextInt(9);
                imageArray[i].setVisibility(View.VISIBLE);

                handler.postDelayed(runnable, 600); // سرعت ظاهر شدن ستاره ها (۶۰۰ میلی‌ثانیه)
            }
        };
        handler.post(runnable);
    }
}
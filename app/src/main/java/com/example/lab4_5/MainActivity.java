package com.example.lab4_5;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText etQuery;
    private Button btnGo, btnLike, btnDislike, btnOpenUrl;
    private ImageView ivImage;
    private TextView tvUrl;

    // Для простоты: показываем локальные картинки,
    // а "URL" будет меняться как будто это адрес источника изображения.
    private int[] images = new int[] {
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3
    };

    private String[] urls = new String[] {
            "https://example.com/img1",
            "https://example.com/img2",
            "https://example.com/img3"
    };

    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1) Находим элементы
        etQuery = (EditText) findViewById(R.id.etQuery);
        btnGo = (Button) findViewById(R.id.btnGo);
        btnLike = (Button) findViewById(R.id.btnLike);
        btnDislike = (Button) findViewById(R.id.btnDislike);
        btnOpenUrl = (Button) findViewById(R.id.btnOpenUrl);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        tvUrl = (TextView) findViewById(R.id.tvUrl);

        // 2) Показать стартовое изображение
        showCurrent();

        // 3) Кнопка Go: "поиск" (в учебном прототипе просто сбрасываем индекс)
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String q = etQuery.getText().toString().trim();

                if (q.length() == 0) {
                    Toast.makeText(MainActivity.this, "Enter query text!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // В реальном приложении тут бы шёл поиск изображений по запросу.
                // В лабе делаем прототип: стартуем показ с 1-го изображения.
                currentIndex = 0;
                showCurrent();
                Toast.makeText(MainActivity.this, "Query: " + q, Toast.LENGTH_SHORT).show();
            }
        });

        // 4) Like: сохранить оценку (демо) и показать следующее
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Liked ✔", Toast.LENGTH_SHORT).show();
                nextImage();
            }
        });

        // 5) Dislike: сохранить оценку (демо) и показать следующее
        btnDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Disliked ✖", Toast.LENGTH_SHORT).show();
                nextImage();
            }
        });

        // 6) Открыть URL в браузере
        btnOpenUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCurrentUrl();
            }
        });
    }

    private void showCurrent() {
        ivImage.setImageResource(images[currentIndex]);
        tvUrl.setText(urls[currentIndex]);
    }

    private void nextImage() {
        currentIndex++;

        if (currentIndex >= images.length) {
            currentIndex = 0;
            Toast.makeText(this, "Images ended, start again.", Toast.LENGTH_SHORT).show();
        }

        showCurrent();
    }

    private void openCurrentUrl() {
        String url = urls[currentIndex];

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No browser found!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Cannot open URL!", Toast.LENGTH_SHORT).show();
        }
    }
}
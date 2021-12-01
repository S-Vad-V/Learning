package com.example.async;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvInfo = findViewById(R.id.tvInfo);
        findViewById(R.id.btn1).setOnClickListener(v -> {
            MyTask task = new MyTask();
            task.execute();
        });
        findViewById(R.id.button2).setOnClickListener(v -> {
            MyTask2 task = new MyTask2();
            task.execute("file_path", "file_path2", "file_path3", "file_path4");
        });
    }


    class MyTask extends AsyncTask<Void, Void, Void> {

        /**
         * Работа в основном потоке, до открытия параллельного
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvInfo.setText("Begin");
        }

        /**
         * Открытие и работа параллельного потока
         */
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * После выполеннения потока
         */
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            tvInfo.setText("End");
        }
    }

    //////////////////////////////Входной, Промежуточный, Результирующий
    class MyTask2 extends AsyncTask<String, Integer, Void> {


        /**
         * Работа в основном потоке, до открытия параллельного
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvInfo.setText("Begin");
        }

        @Override
        protected Void doInBackground(String... urls) {
            try {
                int cnt = 0;
                for (String url : urls) {
                    downloadFile(url);
                    publishProgress(++cnt);
                }
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * Работает в основном во время выполнения параллельного (интегрирует действие)
         *
         * @param values
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tvInfo.setText("Downloaded " + values[0] + " files");
        }

        /**
         * После выполеннения потока
         */
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            tvInfo.setText("End");
        }

        private void downloadFile(String url) throws InterruptedException {
            TimeUnit.SECONDS.sleep(2);
        }
    }
}
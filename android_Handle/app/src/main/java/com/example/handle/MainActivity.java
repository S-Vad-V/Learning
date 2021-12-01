package com.example.handle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.sql.Time;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    TextView tvInfo;
    Button btnStart;
    final String LOG_TAG = "myLog";
    final int STATUS_NONE = 0;
    final int STATUS_CONNECTING = 1;
    final int STATUS_CONNECTED = 2;
    Handler h, h1;

    TextView tvStatus;
    ProgressBar pbConnect;
    Button btnConnect;

    final int STATUS_DOWNLOAD_START = 3; // заргрузка началась
    final int STATUS_DOWNLOAD_FILE = 4;// файл загружен
    final int STATUS_DOWNLOAD_END = 5; // загрузка окончена
    final int STATUS_DOWNLOAD_NONE = 6; // нет файлов для загрузки
    Handler h2;
    ProgressBar pbDownload;
    Button btnConnectOther;

    Handler hh1;
    Handler.Callback hc = new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            Log.d(LOG_TAG, "what = " + message.what);
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hh1 = new Handler(hc);

        tvInfo = (TextView) findViewById(R.id.tvInfo);
        btnStart = (Button) findViewById(R.id.btnStart);
        tvStatus = findViewById(R.id.tvStatus);
        pbConnect = findViewById(R.id.pbConnect);
        btnConnect = findViewById(R.id.btnConnect);

        h = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message msg) {
                tvInfo.setText("Dowload files:" + msg.what);
            }
        };

        h1 = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case STATUS_NONE:
                        btnConnect.setEnabled(true);
                        tvStatus.setText("Not connected");
                        break;
                    case STATUS_CONNECTING:
                        btnConnect.setEnabled(false);
                        pbConnect.setVisibility(View.VISIBLE);
                        tvStatus.setText("Connectiong");
                        break;
                    case STATUS_CONNECTED:
                        pbConnect.setVisibility(View.GONE);
                        tvStatus.setText("Connected");
                        break;
                }
                tvInfo.setText("Dowload files:" + msg.what);
            }
        };
        h1.sendEmptyMessage(STATUS_NONE);

        btnConnectOther = findViewById(R.id.btnConnectOther);
        pbDownload = findViewById(R.id.pbDowload);
        h2 = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case STATUS_NONE:
                        btnConnectOther.setEnabled(true);
                        tvStatus.setText("Not connected");
                        pbDownload.setVisibility(View.GONE);
                        break;
                    case STATUS_CONNECTING:
                        btnConnectOther.setEnabled(false);
                        tvStatus.setText("Connectiong");
                        break;
                    case STATUS_CONNECTED:
                        tvStatus.setText("Connected");
                        break;
                    case STATUS_DOWNLOAD_START:
                        tvStatus.setText("Start downloadin " + msg.arg1 + "files");
                        pbDownload.setMax(msg.arg1);
                        pbDownload.setProgress(0);
                        pbDownload.setVisibility(View.VISIBLE);
                        break;
                    case STATUS_DOWNLOAD_FILE:
                        tvStatus.setText("Download. Left  " + msg.arg2 + "files");
                        pbDownload.setProgress(msg.arg1);
                        saveFile((byte[]) msg.obj);
                        break;
                    case STATUS_DOWNLOAD_END:
                        tvStatus.setText("Download complete");
                        break;
                    case STATUS_DOWNLOAD_NONE:
                        tvStatus.setText("None file for download");
                        break;
                }
                tvInfo.setText("Dowload files:" + msg.what);
            }
        };
        h2.sendEmptyMessage(STATUS_NONE);
    }

    private byte[] dowloadOtherFile() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        return new byte[1024];
    }

    private void saveFile(byte[] file) {

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnConnectOther:
                Thread t2 = new Thread(new Runnable() {
                    Message msg;
                    byte[] file;
                    Random rand = new Random();

                    @Override
                    public void run() {
                        try {
                            h2.sendEmptyMessage(STATUS_CONNECTING);

                            TimeUnit.SECONDS.sleep(2);
                            int filesCount = rand.nextInt(15);
                            if (filesCount == 0) {
                                h2.sendEmptyMessage(STATUS_DOWNLOAD_NONE);
                                TimeUnit.MILLISECONDS.sleep(1500);
                                h2.sendEmptyMessage(STATUS_NONE);
                                return;
                            }
                            msg = h2.obtainMessage(STATUS_DOWNLOAD_START, filesCount, 0);
                            h2.sendMessage(msg);
                            for (int i = 1; i <= filesCount; i++) {
                                file = dowloadOtherFile();
                                msg = h2.obtainMessage(STATUS_DOWNLOAD_FILE, i, filesCount - i, file);
                                h2.sendMessage(msg);
                            }
                            h2.sendEmptyMessage(STATUS_DOWNLOAD_END);
                            TimeUnit.MILLISECONDS.sleep(1500);
                            h2.sendEmptyMessage(STATUS_NONE);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                t2.start();
                break;
            case R.id.btnStart:
//                for (int i = 0; i < 10; i++) {
//                    dowloadFile();
//                    tvInfo.setText("Закачено файлов " + i);
//                    Log.d(LOG_TAG, "Закачено файлов " + i);
//                }
                btnStart.setEnabled(false);
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            dowloadFile();
                            tvInfo.setText("Закачено файлов " + i);
                            Log.d(LOG_TAG, "Закачено файлов " + i);
                        }
                    }
                });
                t.start();
                break;
            case R.id.btnTest:
                Log.d(LOG_TAG, "test");
                break;
            case R.id.btnConnect:
                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            h1.sendEmptyMessage(STATUS_CONNECTING);
                            TimeUnit.SECONDS.sleep(1);
                            h1.sendEmptyMessage(STATUS_CONNECTED);
                            TimeUnit.SECONDS.sleep(1);
                            h1.sendEmptyMessage(STATUS_NONE);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                t1.start();
                break;
            default:
                break;
        }
    }

    private void dowloadFile() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onClick1(View view) {
        hh1.sendEmptyMessageAtTime(999, 29000);
        hh1.sendEmptyMessageDelayed(1, 1000);
        hh1.sendEmptyMessageDelayed(2, 2000);
        hh1.sendEmptyMessageDelayed(3, 3000);
        hh1.sendEmptyMessageDelayed(2, 4000);
        hh1.sendEmptyMessageDelayed(5, 5000);
        hh1.sendEmptyMessageDelayed(2, 6000);
        hh1.sendEmptyMessageDelayed(7, 7000);
        hh1.sendEmptyMessageDelayed(2, 8000);
        hh1.sendEmptyMessageDelayed(9, 9000);
    }
}
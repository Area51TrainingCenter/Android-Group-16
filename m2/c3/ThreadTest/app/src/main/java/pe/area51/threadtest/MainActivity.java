package pe.area51.threadtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_main_thread).setOnClickListener(this);
        findViewById(R.id.button_worker_thread).setOnClickListener(this);
        findViewById(R.id.button_asynctask).setOnClickListener(this);
    }

    private void mainThread() {
        final ProgressDialog progressDialog = createProgressDialog();
        longTask();
        progressDialog.dismiss();
    }

    private void workerThread() {
        final ProgressDialog progressDialog = createProgressDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                longTask();
                /*
                Si bien las vistas no se pueden modificar desde un hilo diferente al hilo principal (o UI Thread),
                se puede llamar a este método "dismiss()" de forma segura desde cualquier hilo ya que internamente ejecuta
                esta operación en el hilo principal.
                */
                progressDialog.dismiss();
            }
        }).start();
    }

    private void asyncTask() {
        new AsyncTask<Void, Void, Void>() {

            private ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                progressDialog = createProgressDialog();
            }

            @Override
            protected Void doInBackground(Void... params) {
                longTask();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                progressDialog.dismiss();
            }
        }.execute();
    }

    private ProgressDialog createProgressDialog() {
        return ProgressDialog.show(this, getString(R.string.loading_title), getString(R.string.loading_message), true, true);
    }

    private void longTask() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_main_thread:
                mainThread();
                break;
            case R.id.button_worker_thread:
                workerThread();
                break;
            case R.id.button_asynctask:
                asyncTask();
                break;
        }
    }
}

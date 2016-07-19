package leapfrog.com.lifecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("The Message is", "onCreate was called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("The Message is", "onResume was called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("The Message is", "onRestart was called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("The Message is", "onPause was called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("The Message is", "onStart was called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("The Message is", "onStop was called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("The Message is", "onDestroy was called");
    }
}

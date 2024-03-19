package androidsamples.java.journalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
  public static final String TAG = "MainActivity";
  public static final String KEY_ENTRY_ID = "KEY_ENTRY_ID";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG,"onCreate");
    setContentView(R.layout.activity_main);
  }
}
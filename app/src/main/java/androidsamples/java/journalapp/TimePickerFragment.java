package androidsamples.java.journalapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.text.format.Time;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

public class TimePickerFragment extends DialogFragment {

  private static TimePickerDialog.OnTimeSetListener timeSetListener;
  private static final String TAG = "TimePicker";

  @NonNull
  public static TimePickerFragment newInstance(Date time,TimePickerDialog.OnTimeSetListener onTimeSetListener) {
    // TODO implement the method
    Log.d(TAG,"newInstance");
    TimePickerFragment fragment = new TimePickerFragment();
    timeSetListener = onTimeSetListener;
    Bundle args = new Bundle();
    args.putSerializable("TIME_KEY", time);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO implement the method
    Log.d(TAG,"onCreate");
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    // TODO implement the method
    // Retrieve the date from arguments and set it as the initial date in the DatePickerDialog
    Calendar initialTime = Calendar.getInstance();
    Log.d(TAG,"onCreateDialog");
    int hour = initialTime.get(Calendar.HOUR_OF_DAY);
    int minute = initialTime.get(Calendar.MINUTE);


    // Create a DatePickerDialog with the initial date and a callback to handle the selected date
    return new TimePickerDialog(getActivity(), timeSetListener, hour, minute, true);
  }

}

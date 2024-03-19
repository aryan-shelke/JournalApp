package androidsamples.java.journalapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import java.util.Date;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DatePickerFragment extends DialogFragment {
  private static final String TAG = "DatePicker";
  private static DatePickerDialog.OnDateSetListener dateSetListener;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG,"onCreate");
  }

  @NonNull
  public static DatePickerFragment newInstance(Date date, DatePickerDialog.OnDateSetListener onDateSetListener) {
    DatePickerFragment fragment = new DatePickerFragment();
    dateSetListener = onDateSetListener;

    // Pass the initial date to the fragment using arguments
    Bundle args = new Bundle();
    args.putSerializable("DATE_KEY", date);
    fragment.setArguments(args);
    Log.d(TAG,"newInstance");
    return fragment;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Retrieve the date from arguments and set it as the initial date in the DatePickerDialog
    Log.d(TAG,"onCreateDialog");
    Date initialDate = (Date) getArguments().getSerializable("DATE_KEY");
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(initialDate);
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    // Create a DatePickerDialog with the initial date and a callback to handle the selected date
    return new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
  }


}

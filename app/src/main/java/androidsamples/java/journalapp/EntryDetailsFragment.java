package androidsamples.java.journalapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntryDetailsFragment # newInstance} factory method to
 * create an instance of this fragment.
 */

public class EntryDetailsFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, OnDialogCloseListener{
  Button addDateButton, startTimeButton, endTimeButton, saveButton;
  EditText titleEdit;
  EntryDetailsViewModel vm;
  TimePickerFragment timePickerFragment;
  DatePickerFragment datePickerFragment;

  View view;

  public static final String TAG = "EntryDetailsFragment";
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    vm = new ViewModelProvider(requireActivity()).get(EntryDetailsViewModel.class);


        String entryIdStr = EntryDetailsFragmentArgs.fromBundle(getArguments()).getEntryId();
//    Log.d(TAG,entryIdStr);
        UUID entryId;
        if(entryIdStr != null) {
          if(savedInstanceState==null){
            entryId = UUID.fromString(entryIdStr);
            vm.setmUUid(entryId);
            vm.loadEntry(entryId);
            Log.d(TAG, "old uuid "+ entryId);
            vm.setOldEntry(true); // for updating old entry on save
          }

        }
        else {
          if(savedInstanceState==null){
            vm.mEntry = new JournalEntry("","DATE","START TIME","END TIME");
            vm.setmUUid(vm.mEntry.getMUUid());
            Log.d(TAG, "New uuid "+vm.mEntry.getMUUid() + ", mentry : "+vm.mEntry);
            vm.setOldEntry(false); // for creating new entry on save
          }

        }


    Log.d(TAG,"onCreate");
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.fragment_entry_details, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.delete) {
      if (vm.isOldEntry())
        new DeleteDialog(this).show(getParentFragmentManager(), "DELETE");
      else
        Toast.makeText(getContext(), "Save Entry Before Delete!", Toast.LENGTH_SHORT).show();

      return true;
    }

    if (item.getItemId() == R.id.share) {
      if (vm.isOldEntry()) {
        String send = "Look what I have been up to: " + vm.mEntry.getMTitle() + " on " + vm.mEntry.getMDate() + ", " + vm.mEntry.getMStartTime() + " to " + vm.mEntry.getMEndTime();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, send);
        intent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(intent, "Share with");
        if (shareIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
          startActivity(shareIntent);
        } else {
          Log.d(TAG, "Not apps available to share");
        }

      } else {
        Toast.makeText(getContext(), "Save Entry Before Sharing!", Toast.LENGTH_SHORT).show();
      }
      return true;

    }
    return super.onOptionsItemSelected(item);
  }
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    Log.d(TAG,"onCreateView");
    return inflater.inflate(R.layout.fragment_entry_details, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    Log.d(TAG,"onViewCreated");
    // Assuming you have a button in your fragment_entry_details.xml with the id "addDateButton"
    this.view = view;
    addDateButton = view.findViewById(R.id.btn_entry_date);
    startTimeButton = view.findViewById(R.id.btn_start_time);
    endTimeButton = view.findViewById(R.id.btn_end_time);
    titleEdit = view.findViewById(R.id.edit_title);
    saveButton = view.findViewById(R.id.btn_save);

    timePickerFragment = TimePickerFragment.newInstance(new Date(),this);
    datePickerFragment = DatePickerFragment.newInstance(new Date(), this);

    if(vm.isOldEntry())
    {
      vm.getEntryLiveData().observe(getActivity(),
              entry -> {
                if(entry != null) {
                  vm.init(entry);
                  updateUI();
                  Log.d(TAG, "in observe in entrydetailsfragment and mentry is not null, " +vm.mEntry.getMTitle());
                }
              });
    }else{
      updateUI();
    }


    addDateButton.setOnClickListener(v -> {
      // Create an instance of DatePickerFragment and show it
       datePickerFragment.show(requireActivity().getSupportFragmentManager(), "datePicker");
    });

    startTimeButton.setOnClickListener(v -> {
      vm.setEditElement(1);
      timePickerFragment.show(requireActivity().getSupportFragmentManager(),"timePicker");
    });

    endTimeButton.setOnClickListener(v -> {
      if(vm.mEntry.getMStartTime()=="START TIME"){
        Toast.makeText(requireContext(),"Select START TIME first." ,Toast.LENGTH_SHORT).show();
        return;
      }
      vm.setEditElement(2);
      timePickerFragment.show(requireActivity().getSupportFragmentManager(),"timePicker");
    });

    saveButton.setOnClickListener(v -> {
      titleEdit.onEditorAction(EditorInfo.IME_ACTION_DONE);
      String title = titleEdit.getText().toString();
      vm.setTitle(title);
      vm.mEntry.setMTitle(title);

      //do some checks
      if(vm.getTitle().isEmpty() || vm.getDate() == "DATE" || vm.getStartTime() == "START TIME" || vm.getEndTime() == "END TIME") {
        Toast.makeText(requireContext(), "All fields are required.", Toast.LENGTH_SHORT).show();
        return;
      }

      //process the entry
      if(!vm.isOldEntry()){
        vm.insert();
      }else{
        Log.d(TAG,vm.mEntry.getMTitle());
        vm.update(vm.mEntry);
      }
      getActivity().onBackPressed();
//      vm.update(new JournalEntry(vm.getTitle(),vm.getDate(),vm.getStartTime(),vm.getEndTime()));

    });

  }

  void updateUI() {
//    addDateButton.setText(vm.getDate());
//    startTimeButton.setText(vm.getStartTime());
//    endTimeButton.setText(vm.getEndTime());
//    titleEdit.setText(vm.getTitle());
    addDateButton.setText(vm.mEntry.getMDate());
    startTimeButton.setText(vm.mEntry.getMStartTime());
    endTimeButton.setText(vm.mEntry.getMEndTime());
    titleEdit.setText(vm.mEntry.getMTitle());
  }

  private void saveEntry(){
    vm.mEntry.setMDate(vm.getDate());
    vm.mEntry.setMTitle(vm.getTitle());
    vm.mEntry.setMEndTime(vm.getEndTime());
    vm.mEntry.setMStartTime(vm.getStartTime());
  }

  @Override
  public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    // Handle the selected date here
    Log.d(TAG,"onDateSet");
    String selectedDate = dateToString(year, month, dayOfMonth);
    addDateButton.setText(selectedDate);
    vm.setDate(selectedDate);
    vm.mEntry.setMDate(selectedDate);
  }

  private String dateToString(int year, int month, int dayOfMonth) {
    // Format the date as needed, for example:
    SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy");
    Date date = new Date(year - 1900, month, dayOfMonth);
    Log.d("EntryDetailsFragment", date.toString()+" "+dateFormat.format(date));
    return dateFormat.format(date);
  }

  @Override
  public void onTimeSet(TimePicker timePicker, int hour, int minute) {
    Log.d(TAG, "onTimeSet");
    String time = (((hour<10)?"0":"")+String.valueOf(hour))+":"+(((minute<10)?"0":"")+String.valueOf(minute));
    Log.d(TAG,"Time: "+time);
    if(vm.getEditElement()==1) {
      if(vm.getEndTime()!=null && !vm.getEndTime().equals("END TIME") && time.compareTo(vm.getEndTime())>0){
        Toast.makeText(requireContext(), "START TIME cannot be after END TIME.", Toast.LENGTH_SHORT).show();
        return;
      }
      Log.d(TAG,"setting time");
      startTimeButton.setText(time);
      vm.setStartTime(time);
      vm.mEntry.setMStartTime(time);
    }else if(vm.getEditElement()==2){
      if(time.compareTo(vm.getStartTime())<0){
        Toast.makeText(requireContext(), "END TIME cannot be before START TIME.", Toast.LENGTH_SHORT).show();
        return;
      }
      endTimeButton.setText(time);
      vm.setEndTime(time);
      vm.mEntry.setMEndTime(time);
    }
    vm.setEditElement(0);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.d(TAG,"onDestroy");
  }

  @Override
  public void onDateDialogClose() {

  }

  @Override
  public void onStartTimeDialogClose() {

  }

  @Override
  public void onEndTimeDialogClose() {

  }

  @Override
  public void onDeleteEntryDialogClose() {
    vm.deleteEntry(vm.mEntry);
    NavDirections action = EntryDetailsFragmentDirections.actionEntryDetailsFragmentToEntryListFragment();
    Navigation.findNavController(requireView()).navigate(action);
  }
}
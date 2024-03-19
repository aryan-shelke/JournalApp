package androidsamples.java.journalapp;

import android.app.TimePickerDialog;
import android.util.Log;
import android.widget.TimePicker;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.Date;
import java.util.UUID;

public class EntryDetailsViewModel extends ViewModel {
    public static final String TAG = "EntryDetailsViewModel";
    private UUID mUUid;
    private String date, startTime, endTime;
    private String title;
    private int editElement;
    private boolean oldEntry;
    JournalEntry mEntry;

    private final JournalRepository mRepository;

    private final MutableLiveData<UUID> entryIdLiveData = new MutableLiveData<>();

    public EntryDetailsViewModel() {
        mRepository = JournalRepository.getInstance();
//        clear();
    }

    public void init(JournalEntry entry){
        mEntry = entry;
        date = entry.getMDate();
        startTime = entry.getMStartTime();
        endTime = entry.getMEndTime();
        title = entry.getMTitle();
        editElement = 0;
    }

    public UUID getmUUid() {
        return mUUid;
    }

    public void setmUUid(UUID mUUid) {
        this.mUUid = mUUid;
    }

    public void clear(){
        date = "DATE";
        startTime = "START TIME";
        endTime = "END TIME";
        title = "";
        editElement = 0;
    }

    public void insert(){
        mRepository.insert(new JournalEntry(title,date,startTime,endTime));
    }

    public void update(JournalEntry entry){
        mRepository.update(entry);
    }

    LiveData<JournalEntry> getEntryLiveData() {
        return Transformations.switchMap(entryIdLiveData, mRepository::getEntry);
    }

    void deleteEntry(JournalEntry entry){ mRepository.delete(entry);}

    void loadEntry(UUID entryId) {
        entryIdLiveData.setValue(entryId);
    }

    public boolean isOldEntry() {
        return oldEntry;
    }

    public void setOldEntry(boolean oldEntry) {
        this.oldEntry = oldEntry;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEditElement() {
        return editElement;
    }

    public void setEditElement(int editElement) {
        this.editElement = editElement;
    }
}

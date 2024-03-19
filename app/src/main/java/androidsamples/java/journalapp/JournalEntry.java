package androidsamples.java.journalapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "journal_table")
public class JournalEntry {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private UUID mUUid;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "date")
    private String mDate;

    @ColumnInfo(name = "start_time")
    private String mStartTime;

    @ColumnInfo(name = "end_time")
    private String mEndTime;

    public JournalEntry(String mTitle, String mDate, String mStartTime, String mEndTime) {
        this.mUUid = UUID.randomUUID();
        this.mTitle = mTitle;
        this.mDate = mDate;
        this.mStartTime = mStartTime;
        this.mEndTime = mEndTime;
    }

    public JournalEntry() {
    }

    // getters and setters


    @NonNull
    public UUID getMUUid() {
        return mUUid;
    }

    public void setMUUid(@NonNull UUID mUUid) {
        this.mUUid = mUUid;
    }

    public String getMTitle() {
        return mTitle;
    }

    public void setMTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getMDate() {
        return mDate;
    }

    public void setMDate(String mDate) {
        this.mDate = mDate;
    }

    public String getMStartTime() {
        return mStartTime;
    }

    public void setMStartTime(String mStartTime) {
        this.mStartTime = mStartTime;
    }

    public String getMEndTime() {
        return mEndTime;
    }

    public void setMEndTime(String mEndTime) {
        this.mEndTime = mEndTime;
    }
}
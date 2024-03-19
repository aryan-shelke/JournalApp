package androidsamples.java.journalapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class EntryListFragment extends Fragment {
  View view;
  EntryListViewModel mEntryListViewModel;
  public static final String TAG = "EntryListFragment";

  @NonNull
  public static EntryListFragment newInstance() {
    EntryListFragment fragment = new EntryListFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    mEntryListViewModel = new ViewModelProvider(this).get(EntryListViewModel.class);
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.fragment_entry_list, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if(item.getItemId()==R.id.menu_info){
      Navigation.findNavController(view).navigate(R.id.action_entryListFragment_to_infoFragment);
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_entry_list, container, false);

    FloatingActionButton addButton = view.findViewById(R.id.btn_add_entry);
    addButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Navigate to entryDetailsFragment when the button is clicked
        Navigation.findNavController(v).navigate(R.id.addEntryAction);
      }
    });

    RecyclerView entriesList = view.findViewById(R.id.recyclerView);
    entriesList.setLayoutManager(new LinearLayoutManager(getActivity()));
    EntryListAdapter adapter = new EntryListAdapter(getActivity());
    entriesList.setAdapter(adapter);

    mEntryListViewModel.getAllEntries()
            .observe(requireActivity(), adapter::setEntries);

    return view;
}

  class EntryListAdapter
          extends RecyclerView.Adapter<EntryListAdapter.EntryViewHolder> {


    private final LayoutInflater mInflater;
    private List<JournalEntry> mEntries;

    public EntryListAdapter(Context context) {
      mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                              int viewType) {
      View itemView = mInflater.inflate(R.layout.fragment_entry,
              parent,
              false);
      return new EntryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder,
                                 int position) {
      if (mEntries != null) {
        JournalEntry current = mEntries.get(position);
        holder.mTxtTitle.setText(current.getMTitle());
        holder.mTxtDate.setText(current.getMDate());
        holder.mTxtStartTime.setText(current.getMStartTime());
        holder.mTxtEndTime.setText(current.getMEndTime());
        holder.itemView.setOnClickListener(view -> {

          androidsamples.java.journalapp.EntryListFragmentDirections.AddEntryAction action = EntryListFragmentDirections.addEntryAction();
          action.setEntryId((current.getMUUid().toString()));
          Log.d(TAG,current.getMUUid().toString());
          Navigation.findNavController(view).navigate(action);
        });
      }
    }

    @Override
    public int getItemCount() {
      return (mEntries==null)?0:mEntries.size();
    }

    public void setEntries(List<JournalEntry> entries){
      mEntries = entries;
      notifyDataSetChanged();
    }

    class EntryViewHolder extends RecyclerView.ViewHolder {
      private final TextView mTxtTitle;
      private final TextView mTxtDate;
      private final TextView mTxtStartTime;
      private final TextView mTxtEndTime;

      public EntryViewHolder(@NonNull View itemView) {
        super(itemView);
        mTxtTitle = itemView.findViewById(R.id.txt_item_title);
        mTxtDate = itemView.findViewById(R.id.txt_item_date);
        mTxtStartTime = itemView.findViewById(R.id.txt_item_start_time);
        mTxtEndTime = itemView.findViewById(R.id.txt_item_end_time);
      }
    }

  }

}
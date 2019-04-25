package com.example.ourx;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CabinetFragment extends ListFragment {

    private ArrayList<MedicineEntity> medications = new ArrayList<>();

    public CabinetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cabinet, container, false);
        return view;
    }

    // Called at the start of the visible lifetime.
    @Override
    public void onStart(){
        super.onStart();
        Log.d ("Cabinet Fragment", "onStart");
        // Apply any required UI change now that the Fragment is visible.

        medications.clear();

        /* The viewModel to hold all data (separates data from activity instances */
        final MedicineViewModel medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);

        /* Listen for changes in the medications database and display them */
        medicineViewModel.getAllMeds().observe(this, new Observer<List<MedicineEntity>>() {
            @Override
            public void onChanged(@Nullable final List<MedicineEntity> meds) {
                /* No longer used now that CabinetCardAdapter uses MedicineEntity directly TODO: delete line
                medications = entityToCabCard(meds); */
                final CabinetCardAdapter adapter = new CabinetCardAdapter(getActivity(), meds);
                setListAdapter(adapter);
            }
        });

        // Get the ListView the Cabinet_fragment is using and register it to have a context menu
        ListView listView = getListView();
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.cabinet_menu, menu);
        menu.setHeaderTitle("Select Action");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            // Get the medication we want to delete
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int index = info.position;
            MedicineEntity card = (MedicineEntity) getListAdapter().getItem(index);
            Toast.makeText(getActivity().getApplicationContext(),"Deleting " + card.MED_NAME,Toast.LENGTH_LONG).show();

            // Delete the medication
            ViewModelProviders.of(this).get(MedicineViewModel.class).delete(card);
        } else {
            return false;
        }

        return true;
    }

    /* No longer used now that CabinetCardAdapter uses MedicineEntity directly TODO: delete method
    Turns medicine entities into cabinet cards to display in schedule
    private ArrayList<CabinetCard> entityToCabCard(List<MedicineEntity> meds) {
        ArrayList<CabinetCard> cabinetCards = new ArrayList<>();
        for (MedicineEntity med : meds) {
            cabinetCards.add(new CabinetCard(
                    med.MED_NAME,
                    med.MED_DOSAGE,
                    med.MED_UNIT,
                    med.MED_INSTRUCT));
        }
        return cabinetCards;
    }*/

    // Called at the start of the active lifetime.
    @Override
    public void onResume(){
        super.onResume();
        Log.d ("Cabinet Fragment", "onResume");
        // Resume any paused UI updates, threads, or processes required
        // by the Fragment but suspended when it became inactive.
        ((MainActivity) getActivity()).setActionBarTitle("Medicine Cabinet");
    }

    // Called at the end of the active lifetime.
    @Override
    public void onPause(){
        Log.d ("Cabinet Fragment", "onPause");
        // Suspend UI updates, threads, or CPU intensive processes
        // that don't need to be updated when the Activity isn't
        // the active foreground activity.
        // Persist all edits or state changes
        // as after this call the process is likely to be killed.
        super.onPause();
    }

    // Called to save UI state changes at the
    // end of the active lifecycle.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d ("Cabinet Fragment", "onSaveInstanceState");
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate, onCreateView, and
        // onCreateView if the parent Activity is killed and restarted.
        super.onSaveInstanceState(savedInstanceState);
    }

    // Called at the end of the visible lifetime.
    @Override
    public void onStop(){
        Log.d ("Cabinet Fragment", "onStop");
        // Suspend remaining UI updates, threads, or processing
        // that aren't required when the Fragment isn't visible.
        super.onStop();
    }

    // Called when the Fragment's View has been detached.
    @Override
    public void onDestroyView() {
        Log.d ("Cabinet Fragment", "onDestroyView");
        // Clean up resources related to the View.
        super.onDestroyView();
    }

    // Called at the end of the full lifetime.
    @Override
    public void onDestroy(){
        Log.d ("Cabinet Fragment", "onDestroy");
        // Clean up any resources including ending threads,
        // closing database connections etc.
        super.onDestroy();
    }

    // Called when the Fragment has been detached from its parent Activity.
    @Override
    public void onDetach() {
        Log.d ("Cabinet Fragment", "onDetach");
        super.onDetach();
    }

}

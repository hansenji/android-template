package org.company.project.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Bus;

import org.company.project.App;
import org.company.project.R;
import org.company.project.domain.main.individual.Individual;
import org.company.project.domain.main.individual.IndividualManager;
import org.company.project.event.EditIndividualEvent;
import org.company.project.event.IndividualDeletedEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pocketknife.InjectArgument;
import pocketknife.PocketKnife;

public class IndividualFragment extends Fragment {
    public static final String TAG = App.createTag(IndividualFragment.class);

    private static final String ARG_ID = "ID";

    @InjectView(R.id.individual_name)
    TextView nameTextView;

    @InjectView(R.id.individual_phone)
    TextView phoneTextView;

    @InjectView(R.id.individual_email)
    TextView emailTextView;

    @Inject
    IndividualManager individualManager;

    @Inject
    Bus bus;

    @InjectArgument(ARG_ID)
    long individualId;

    public static IndividualFragment newInstance(long individualId) {
        IndividualFragment fragment = new IndividualFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ID, individualId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PocketKnife.injectArguments(this);
    }

    @Override
    public View onCreateView(@Nonnull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.individual_fragment, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.inject(this);
        setHasOptionsMenu(true);
        showIndividual();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.individual_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_edit:
                bus.post(new EditIndividualEvent(individualId));
                return true;
            case R.id.menu_item_delete:
                deleteIndividual();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        showIndividual();
    }

    private void showIndividual() {
        if (individualId <= 0) {
            return;
        }

        Individual individual = individualManager.findByRowId(individualId);
        if (individual != null) {
            nameTextView.setText(individual.getFullName());
            phoneTextView.setText(individual.getPhone());
            emailTextView.setText(individual.getEmail());
        }
    }

    private void deleteIndividual() {
        new AlertDialog.Builder(getActivity())
                .setMessage(R.string.delete_individual_confirm)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        individualManager.delete(individualId);
                        bus.post(new IndividualDeletedEvent(individualId));
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}
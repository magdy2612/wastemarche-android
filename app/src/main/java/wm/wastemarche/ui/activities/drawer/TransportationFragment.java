package wm.wastemarche.ui.activities.drawer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import wm.wastemarche.R;
import wm.wastemarche.model.Transportation;
import wm.wastemarche.services.datacenter.DataCenter;
import wm.wastemarche.services.datacenter.DataCenterProtocol;
import wm.wastemarche.ui.InfinitScroll;
import wm.wastemarche.ui.activities.Helper;
import wm.wastemarche.ui.activities.main.MainActivity;
import wm.wastemarche.ui.adapters.TransportAdapter;

public class TransportationFragment extends Fragment implements OnClickListener, InfinitScroll {
    protected TransportAdapter adapter;
    protected int pageNumber = 0;
    private int type = 4;
    private ListView transportationListView;
    private final DataCenter dataCenter = new DataCenter(new DataCenterProtocol(){
        @Override
        public void trasnportWasteLoaded(final String page, final List<Transportation> items) {
            adapter.items.addAll(items);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void disposeWasteLoaded(final String page, final List<Transportation> items) {
            adapter.items.addAll(items);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void allTransportLoaded(final String page, final List<Transportation> items) {
            adapter.items.addAll(items);
            adapter.notifyDataSetChanged();
        }
    });

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_transportation, container, false);
        pageNumber = 0;

        adapter = new TransportAdapter(getActivity());
        transportationListView = view.findViewById(R.id.transportListView);
        transportationListView.setAdapter(adapter);

        Helper.ListViewInfiitScroll(view, R.id.transportListView, this);

        Helper.ImageButtonCickListener(view, R.id.drawerButton, this);
        Helper.ImageButtonCickListener(view, R.id.notificationsButton, this);

        Helper.ButtonClickListener(view, R.id.allButton, this);
        Helper.ButtonClickListener(view, R.id.trasnportButton, this);
        Helper.ButtonClickListener(view, R.id.disposeButton, this);
        Helper.ButtonClickListener(view, R.id.issueButton, this);

        loadNextPage();
        return view;
    }

    @Override
    public void loadNextPage() {
        pageNumber ++;
        switch (type) {
            case 4: dataCenter.getTransportWaste(String.valueOf(pageNumber));break;
            case 5: dataCenter.getDisposeWaste(String.valueOf(pageNumber));break;
            case 0: dataCenter.getAllTransport(String.valueOf(pageNumber));break;
            default:
        }
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.allButton: onAllButtonClicks(); break;
            case R.id.trasnportButton: onTransportButtonClicks(); break;
            case R.id.disposeButton: onDisposeButtonClicks(); break;
            case R.id.issueButton: onIssueButtonClicks(); break;
            case R.id.drawerButton: onDrawerButtonClicks(); break;
            case R.id.notificationsButton: onNotificationsButtonClicks(); break;
            default:
        }
    }

    private void onAllButtonClicks() {
        type = 0;
        pageNumber = 0;
        adapter.items.clear();
        transportationListView.setSelectionAfterHeaderView();
        loadNextPage();
    }

    private void onTransportButtonClicks() {
        type = 4;
        pageNumber = 0;
        adapter.items.clear();
        transportationListView.setSelectionAfterHeaderView();
        loadNextPage();
    }

    private void onDisposeButtonClicks() {
        type = 5;
        pageNumber = 0;
        adapter.items.clear();
        transportationListView.setSelectionAfterHeaderView();
        loadNextPage();
    }

    private static void onIssueButtonClicks() {
        MainActivity.shared.changeCurrentFragment(new TransportationRequestFragment());
    }

    public static void onDrawerButtonClicks() {
        MainActivity.shared.openDrawer();
    }

    public void onNotificationsButtonClicks() {
    }
}

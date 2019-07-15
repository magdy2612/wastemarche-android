package wm.wastemarche.ui.activities.main;

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
import wm.wastemarche.model.Item;
import wm.wastemarche.model.Request;
import wm.wastemarche.services.http.requests.RequestApi;
import wm.wastemarche.services.http.requests.RequestApiProtocol;
import wm.wastemarche.ui.InfinitScroll;
import wm.wastemarche.ui.activities.Helper;
import wm.wastemarche.ui.adapters.OffersAdapter;

public class OffersFragment extends Fragment implements InfinitScroll, OnClickListener, RequestApiProtocol {

    private int pageNumber = 0;
    private final RequestApi requestApi;
    private OffersAdapter adapter;
    public Item item;

    public OffersFragment() {

        requestApi = new RequestApi(this);
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_offers, container, false);
        Helper.adjustViewLayout(view);
        pageNumber = 0;

        final ListView offersListView = view.findViewById(R.id.offerListview);
        adapter = new OffersAdapter(getContext());
        adapter.requests = item.requests;
        offersListView.setAdapter(adapter);

        Helper.ListViewInfiitScroll(view, R.id.offerListview, this);

        //loadNextPage();

        return view;
    }

    @Override
    public void loadNextPage() {
        pageNumber ++;
        requestApi.getRequests(String.valueOf(pageNumber));
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.drawerButton:         onDrawerButtonClicks();break;
            case R.id.notificationsButton:  onNotificationsButtonClicks();break;
            default:
        }
    }

    public static void onDrawerButtonClicks() {
        MainActivity.shared.openDrawer();
    }

    public void onNotificationsButtonClicks() {
    }

    @Override
    public void requestsLoaded(final String page, final List<Request> requests) {
        adapter.requests = requests;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void requestLoaded(final Request request) {
    }

    @Override
    public void requestExtraInfoCompleted() {

    }

    @Override
    public void sendOfferCompleted() {
    }

    @Override
    public void apiError(final int errorCode) {
    }
}

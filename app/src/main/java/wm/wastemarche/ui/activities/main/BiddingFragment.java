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
import wm.wastemarche.model.Bid;
import wm.wastemarche.services.datacenter.DataCenter;
import wm.wastemarche.services.datacenter.DataCenterProtocol;
import wm.wastemarche.ui.InfinitScroll;
import wm.wastemarche.ui.activities.Helper;
import wm.wastemarche.ui.adapters.BiddingAdapter;

public class BiddingFragment extends Fragment implements OnClickListener, InfinitScroll {

    private BiddingAdapter adapter;
    private int pageNumber = 0;
    private final DataCenter dataCenter = new DataCenter(new DataCenterProtocol(){
        @Override
        public void bidsLoaded(final String page, final List<Bid> items) {
            adapter.items.addAll(items);
            adapter.notifyDataSetChanged();
        }
    });

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_bidding, container, false);
        Helper.adjustViewLayout(view);

        final ListView biddingListView = view.findViewById(R.id.biddingListView);
        adapter = new BiddingAdapter(getContext());
        biddingListView.setAdapter(adapter);

        Helper.ListViewInfiitScroll(view, R.id.biddingListView, this);

        Helper.ImageButtonCickListener(view, R.id.drawerButton, this);
        Helper.ImageButtonCickListener(view, R.id.notificationsButton, this);

        loadNextPage();
        return view;
    }

    @Override
    public void onClick(final View view) {
    }

    @Override
    public void loadNextPage() {
        pageNumber ++;
        dataCenter.getBids(String.valueOf(pageNumber));
    }
}

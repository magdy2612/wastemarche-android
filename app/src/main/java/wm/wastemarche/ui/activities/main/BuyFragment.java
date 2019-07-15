package wm.wastemarche.ui.activities.main;

import android.os.Bundle;
import android.os.Handler;
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
import wm.wastemarche.services.datacenter.DataCenter;
import wm.wastemarche.services.datacenter.DataCenterProtocol;
import wm.wastemarche.ui.InfinitScroll;
import wm.wastemarche.ui.activities.Helper;
import wm.wastemarche.ui.adapters.BuyAdapter;

public class BuyFragment extends Fragment implements OnClickListener, InfinitScroll {

    private BuyAdapter adapter;
    private int pageNumber = 0;
    private boolean isSellItems = true;
    private ListView buyListView;

    private final DataCenter dataCenter = new DataCenter(new DataCenterProtocol(){
        @Override
        public void sellItemsLoaded(final String page, final List<Item> items) {
            if( pageNumber == 1 ) {
                adapter.items.clear();
            }
            adapter.items.addAll(items);
            updateListView();
        }

        @Override
        public void buyItemsLoaded(final String page, final List<Item> items) {
            if( pageNumber == 1 ) {
                adapter.items.clear();
            }
            adapter.items.addAll(items);
            updateListView();
        }
    });

    void updateListView() {
        final Handler handler = new Handler(getActivity().getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_buy, container, false);
        Helper.adjustViewLayout(view);
        pageNumber = 0;

        adapter = new BuyAdapter(getContext());
        buyListView = view.findViewById(R.id.buyListView);
        buyListView.setAdapter(adapter);

        Helper.ListViewInfiitScroll(view, R.id.buyListView, this);

        Helper.ImageButtonCickListener(view, R.id.drawerButton, this);
        Helper.ImageButtonCickListener(view, R.id.notificationsButton, this);

        Helper.ButtonClickListener(view, R.id.sellButton, this);
        Helper.ButtonClickListener(view, R.id.buyButton, this);

        loadNextPage();
        return view;
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.sellButton:           onSellButtonClicks();break;
            case R.id.buyButton:            onBuyButtonClicks();break;
            case R.id.drawerButton:         onDrawerButtonClicks();break;
            case R.id.notificationsButton:  onNotificationsButtonClicks();break;
            default:
        }
    }

    @Override
    public void loadNextPage() {
        pageNumber ++;
        if( isSellItems ) {
            dataCenter.getSellItems(String.valueOf(pageNumber));
        }
        else {
            dataCenter.getBuyItems(String.valueOf(pageNumber));
        }
    }

    public void onSellButtonClicks() {
        isSellItems = true;
        pageNumber = 0;
        adapter.items.clear();
        loadNextPage();
        buyListView.setSelectionAfterHeaderView();
    }

    public void onBuyButtonClicks() {
        isSellItems = false;
        pageNumber = 0;
        adapter.items.clear();
        loadNextPage();
        buyListView.setSelectionAfterHeaderView();
    }

    public static void onDrawerButtonClicks() {
        MainActivity.shared.openDrawer();
    }

    public void onNotificationsButtonClicks() {
    }
}

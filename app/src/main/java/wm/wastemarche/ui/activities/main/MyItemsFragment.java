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
import wm.wastemarche.services.datacenter.DataCenter;
import wm.wastemarche.services.datacenter.DataCenterProtocol;
import wm.wastemarche.ui.InfinitScroll;
import wm.wastemarche.ui.activities.Helper;
import wm.wastemarche.ui.adapters.MyItemsAdapter;

public class MyItemsFragment extends Fragment implements InfinitScroll, OnClickListener {

    private MyItemsAdapter adapter;
    private int pageNumber = 0;

    private final DataCenter dataCenter = new DataCenter(new DataCenterProtocol(){
        @Override
        public void myItemsLoaded(final String page, final List<Item> items) {
            adapter.items.addAll(items);
            adapter.notifyDataSetChanged();
        }

    });

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_my_items, container, false);
        Helper.adjustViewLayout(view);
        pageNumber = 0;

        final ListView myItemsListView = view.findViewById(R.id.myItemsListView);
        adapter = new MyItemsAdapter(getContext());
        myItemsListView.setAdapter(adapter);

        Helper.ListViewInfiitScroll(view, R.id.myItemsListView, this);
        Helper.ImageButtonCickListener(view, R.id.drawerButton, this);
        Helper.ImageButtonCickListener(view, R.id.notificationsButton, this);

        loadNextPage();

        return view;
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.drawerButton:         onDrawerButtonClicks();break;
            case R.id.notificationsButton:  onNotificationsButtonClicks();break;
            default:
        }
    }

    @Override
    public void loadNextPage() {
        pageNumber ++;
        dataCenter.getMyItems(String.valueOf(pageNumber));
    }

    public static void onDrawerButtonClicks() {
        MainActivity.shared.openDrawer();
    }

    public void onNotificationsButtonClicks() {
    }
}

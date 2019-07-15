package wm.wastemarche.ui.activities.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import wm.wastemarche.R;
import wm.wastemarche.ui.activities.Helper;
import wm.wastemarche.ui.components.SellView;

public class HomeFragment extends Fragment implements OnClickListener {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        Helper.adjustViewLayout(rootView);

        Helper.ButtonClickListener(rootView, R.id.sellButton, this);
        Helper.ButtonClickListener(rootView, R.id.buyButton, this);
        Helper.ButtonClickListener(rootView, R.id.myItemsButton, this);
        Helper.ButtonClickListener(rootView, R.id.myProrposalsButton, this);

        Helper.ImageButtonCickListener(rootView, R.id.drawerButton, this);
        Helper.ImageButtonCickListener(rootView, R.id.notificationsButton, this);

        return rootView;
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.sellButton:
                onSellButtonClicks();
                break;
            case R.id.buyButton:
                onBuyButtonClicks();
                break;
            case R.id.myItemsButton:
                onMyItesButtonClicks();
                break;
            case R.id.myProrposalsButton:
                onMyProposalsButtonClicks();
                break;
            case R.id.drawerButton:
                onDrawerButtonClicks();
                break;
            case R.id.notificationsButton:
                onNotificationsButtonClicks();
                break;
            default:
        }
    }

    private void onSellButtonClicks() {
        //MainActivity.shared.setSelectedTab(R.id.sell_tab);
        MainActivity.shared.popupView(new SellView(getContext()).createView());
    }

    private static void onBuyButtonClicks() {
        MainActivity.shared.setSelectedTab(R.id.buy_tab);
    }

    private static void onMyItesButtonClicks() {
        MainActivity.shared.setSelectedTab(R.id.my_items_tab);
    }

    private static void onMyProposalsButtonClicks() {
        MainActivity.shared.setSelectedTab(R.id.my_proposals_tab);
    }

    private static void onDrawerButtonClicks() {
        MainActivity.shared.openDrawer();
    }

    private void onNotificationsButtonClicks() {
    }
}

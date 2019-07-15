package wm.wastemarche.ui.activities.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import wm.wastemarche.R;
import wm.wastemarche.model.Item;
import wm.wastemarche.ui.activities.Helper;
import wm.wastemarche.ui.adapters.ImagePagerAdapter;
import wm.wastemarche.ui.components.RequestExtraInfo;
import wm.wastemarche.ui.components.SendOfferView;

public class BuyDetailsFragment extends Fragment implements OnClickListener {

    public Item item;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_buy_details, container, false);
        Log.d("onCreateView", "onCreateView() called with");
        Helper.adjustViewLayout(view);

        Helper.TextViewText(view, R.id.title, item.title);
        Helper.TextViewText(view, R.id.summary, item.summary);
        Helper.TextViewText(view, R.id.price, item.price);
        Helper.TextViewText(view, R.id.date, item.published_at);
        Helper.TextViewText(view, R.id.category, item.category.title);
        Helper.TextViewText(view, R.id.method, item.getMethod());
        Helper.TextViewText(view, R.id.quantity, String.valueOf(item.quantity) + item.unit);
        Helper.TextViewText(view, R.id.packaging, Helper.BooleanString(item.packaging));
        Helper.TextViewText(view, R.id.transportation, Helper.BooleanString(item.transportation));
        Helper.TextViewText(view, R.id.content, item.content);

        Helper.ButtonClickListener(view, R.id.sendOfferButton, this);
        Helper.ButtonClickListener(view, R.id.requestExtraInfoButton, this);

        final ViewPager imagesPager = view.findViewById(R.id.imagesPager);
        imagesPager.setAdapter(new ImagePagerAdapter(item.images, getContext()));

        return view;
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.sendOfferButton:
                onSendOfferButtonClicks();
                break;
            case R.id.requestExtraInfoButton:
                onRequestExtraInfoButtonClicks();
                break;
            default:
        }
    }

    private void onSendOfferButtonClicks() {
        MainActivity.shared.popupView(SendOfferView.forItem(getContext(), String.valueOf(item.id)).createView());
    }

    private void onRequestExtraInfoButtonClicks() {
        MainActivity.shared.popupView(RequestExtraInfo.forItem(getContext(), String.valueOf(item.id)).createView());
    }
}
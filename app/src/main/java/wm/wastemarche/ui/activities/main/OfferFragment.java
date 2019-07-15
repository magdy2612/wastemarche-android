package wm.wastemarche.ui.activities.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import wm.wastemarche.R;
import wm.wastemarche.model.Request;
import wm.wastemarche.ui.activities.Helper;

public class OfferFragment extends Fragment implements OnClickListener {
    public Request request;


    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_offer, container, false);
        Helper.adjustViewLayout(view);

        Helper.TextViewText(view, R.id.comments, request.comment);
        Helper.TextViewText(view, R.id.date, request.created_at);
        Helper.TextViewText(view, R.id.price, "" + request.price + ' ' + request.currency);
        Helper.TextViewText(view, R.id.quantity, "" + request.quantity + ' ' + request.unit);
        Helper.TextViewText(view, R.id.paymentTerms, request.payment_term);
        Helper.TextViewText(view, R.id.transportation, request.transportation);
        Helper.TextViewText(view, R.id.status, "" + request.status);

        Helper.ButtonClickListener(view, R.id.accept, this);
        Helper.ButtonClickListener(view, R.id.deny, this);

        return view;
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId() ) {
            default:
        }
    }
}

package wm.wastemarche.ui.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;

import wm.wastemarche.R;
import wm.wastemarche.model.Request;
import wm.wastemarche.services.http.requests.RequestApi;
import wm.wastemarche.services.http.requests.RequestApiProtocol;
import wm.wastemarche.ui.activities.Helper;
import wm.wastemarche.ui.activities.main.MainActivity;

public class SendOfferView implements OnClickListener, RequestApiProtocol {

    private final Context context;
    private final String itemId;
    private final String bidId;
    private View view;

    public static SendOfferView forItem(final Context context, final String itemId) {
        return new SendOfferView(context, itemId, null);
    }

    public static SendOfferView forBid(final Context context, final String bidId) {
        return new SendOfferView(context, null, bidId);
    }

    public SendOfferView(final Context context, final String itemId, final String bidId) {
        this.context = context;
        this.itemId = itemId;
        this.bidId = bidId;
    }


    public View createView() {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.view_send_offer, null);

        Helper.ButtonClickListener(view, R.id.cancelButton, this);
        Helper.ButtonClickListener(view, R.id.sendOfferButton, this);

        return view;
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId() ) {
            case R.id.cancelButton: onCancelButtonClicks(); break;
            case R.id.sendOfferButton: onSendButtonClicks(); break;
            default:
        }
    }

    private void onCancelButtonClicks() {
        MainActivity.shared.closePopup();
    }

    private void onSendButtonClicks() {
        final Request request = new Request(new JSONObject());
        request.price = Integer.valueOf(Helper.EditTextGetText(view, R.id.price)).intValue();
        request.currency = Helper.EditTextGetText(view, R.id.currency);
        request.payment_term = Helper.EditTextGetText(view, R.id.paymentTerms);
        request.offer_validity = Helper.EditTextGetText(view, R.id.offerValidity);
        request.quantity = Integer.valueOf(Helper.EditTextGetText(view, R.id.quantity)).intValue();
        request.unit = Helper.EditTextGetText(view, R.id.unit);

        final RequestApi requestApi = new RequestApi(this);
        requestApi.createNewRequestForOffer(itemId, bidId, request);
    }

    @Override
    public void requestsLoaded(final String page, final List<Request> requests) {
    }

    @Override
    public void requestLoaded(final Request request) {
    }

    @Override
    public void requestExtraInfoCompleted() {
    }

    @Override
    public void sendOfferCompleted() {
        Toast.makeText(context, R.string.alert_done, Toast.LENGTH_LONG).show();
        MainActivity.shared.closePopup();
    }

    @Override
    public void apiError(final int errorCode) {
        Toast.makeText(context, Helper.LocalizedString(R.string.alert_failed) + ", code: " + errorCode, Toast.LENGTH_LONG).show();
    }
}

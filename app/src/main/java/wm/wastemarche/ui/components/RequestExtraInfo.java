package wm.wastemarche.ui.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.util.List;

import wm.wastemarche.R;
import wm.wastemarche.model.Request;
import wm.wastemarche.services.http.requests.RequestApi;
import wm.wastemarche.services.http.requests.RequestApiProtocol;
import wm.wastemarche.ui.activities.Helper;
import wm.wastemarche.ui.activities.main.MainActivity;

public class RequestExtraInfo implements OnClickListener, RequestApiProtocol {

    private final Context context;
    private final String itemId;
    private final String bidId;
    private View view;

    public static RequestExtraInfo forItem(final Context context, final String itemId) {
        return new RequestExtraInfo(context, itemId, null);
    }

    public static RequestExtraInfo forBid(final Context context, final String bidId) {
        return new RequestExtraInfo(context, null, bidId);
    }

    public RequestExtraInfo(final Context context, final String itemId, final String bidId) {
        this.context = context;
        this.itemId = itemId;
        this.bidId = bidId;
    }

    public View createView() {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.view_request_extra_info, null);

        Helper.ButtonClickListener(view, R.id.cancelButton, this);
        Helper.ButtonClickListener(view, R.id.requestExtraInfoButton, this);

        return view;
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId() ) {
            case R.id.cancelButton: onCancelButtonClicks();break;
            case R.id.requestExtraInfoButton: onSendButtonClicks(); break;
            default:
        }
    }

    private static void onCancelButtonClicks() {
        MainActivity.shared.closePopup();
    }

    private void onSendButtonClicks() {
        final RequestApi requestApi = new RequestApi(this);
        requestApi.createNewRequestForExtraInfo(itemId, bidId, Helper.EditTextGetText(view, R.id.subject), Helper.EditTextGetText(view, R.id.message));
    }

    @Override
    public void requestsLoaded(final String page, final List<Request> requests) {
    }

    @Override
    public void requestLoaded(final Request request) {
    }

    @Override
    public void requestExtraInfoCompleted() {
        Toast.makeText(context, R.string.alert_done, Toast.LENGTH_LONG).show();
        MainActivity.shared.closePopup();
    }

    @Override
    public void sendOfferCompleted() {
    }

    @Override
    public void apiError(final int errorCode) {
        Toast.makeText(context, Helper.LocalizedString(R.string.alert_failed) + ", code: " + errorCode, Toast.LENGTH_LONG).show();
    }
}

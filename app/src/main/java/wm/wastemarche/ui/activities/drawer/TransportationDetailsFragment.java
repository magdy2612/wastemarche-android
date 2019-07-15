package wm.wastemarche.ui.activities.drawer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import wm.wastemarche.R;
import wm.wastemarche.model.Transportation;
import wm.wastemarche.services.http.HttpConstants;
import wm.wastemarche.services.imageloader.ImageLoader;
import wm.wastemarche.services.imageloader.ImageLoaderProtocol;
import wm.wastemarche.ui.activities.Helper;
import wm.wastemarche.ui.activities.main.MainActivity;
import wm.wastemarche.ui.components.RequestExtraInfo;
import wm.wastemarche.ui.components.SendOfferView;

public class TransportationDetailsFragment extends Fragment implements OnClickListener, ImageLoaderProtocol {

    public Transportation item;
    private View view;

    @Override
    public void imageLoaded(final String imageName) {
        final ImageLoader imageLoader = new ImageLoader(this);
        imageLoader.loadImage((ImageView) view.findViewById(R.id.image), HttpConstants.IMAGES_HOSTNAME + "/150x150/" +item.main_image, getContext());
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_transportation_details, container, false);

        Helper.TextViewText(view, R.id.title, item.title);
        Helper.TextViewText(view, R.id.description, item.summary);
        Helper.TextViewText(view, R.id.price, item.price);
        Helper.TextViewText(view, R.id.method, item.getMethod());
        Helper.TextViewText(view, R.id.category, item.category.title);
        Helper.TextViewText(view, R.id.quantity, String.valueOf(item.quantity));
        Helper.TextViewText(view, R.id.packaging, item.packaging);
        Helper.TextViewText(view, R.id.date, item.created_at);

        Helper.ButtonClickListener(view, R.id.sendOfferButton, this);
        Helper.ButtonClickListener(view, R.id.requestExtraInfoButton, this);

        final ImageLoader imageLoader = new ImageLoader(this);
        imageLoader.loadImage((ImageView) view.findViewById(R.id.image), HttpConstants.IMAGES_HOSTNAME + "/150x150/" +item.main_image, getContext());

        return view;
    }


    @Override
    public void onClick(final View view) {
        switch (view.getId() ) {
            case R.id.sendOfferButton: onSendOfferButtonClicks(); break;
            case R.id.requestExtraInfoButton: onRequestExtraInfoButtonClicks(); break;
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

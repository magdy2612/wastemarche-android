package wm.wastemarche.ui.activities.drawer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wm.wastemarche.R;
import wm.wastemarche.model.Category;
import wm.wastemarche.model.Transportation;
import wm.wastemarche.services.datacenter.DataCenter;
import wm.wastemarche.services.datacenter.DataCenterProtocol;
import wm.wastemarche.services.http.transportations.TransportationsApi;
import wm.wastemarche.services.http.transportations.TransportationsApiProtocol;
import wm.wastemarche.ui.activities.Helper;

public class TransportationRequestFragment extends Fragment implements OnClickListener, TransportationsApiProtocol {

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_transportation_request, container, false);

        Helper.ButtonClickListener(view, R.id.cancelButton, this);
        Helper.ButtonClickListener(view, R.id.saveButton, this);
        Helper.ImageButtonCickListener(view, R.id.addImageButton, this);

        final DataCenter dataCenter = new DataCenter(new DataCenterProtocol() {
            @Override
            public void categoreisLoaded(final List<Category> categories) {
                final Handler handler = new Handler(getActivity().getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        final List<String> itemsList = new ArrayList<>(0);
                        for(int i=0,len=categories.size();i<len;i++) {
                            itemsList.add(categories.get(i).title);
                            for(int j=0,lenn=categories.get(i).child_category.size();j<lenn;j++) {
                                itemsList.add("-- " + categories.get(i).child_category.get(j).title);
                            }
                        }
                        final Spinner dropdown = view.findViewById(R.id.category);
                        final SpinnerAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, itemsList);
                        dropdown.setAdapter(adapter);
                    }
                });
            }
        });

        dataCenter.getCategories();

        Helper.EditTextText(view, R.id.name, DataCenter.user.name);
        Helper.EditTextText(view,R.id.email, DataCenter.user.email);
        Helper.EditTextText(view, R.id.mobile, DataCenter.user.contact_phone);

        return view;
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId() ) {
            case R.id.cancelButton: onCancelButtonClicks(); break;
            case R.id.saveButton: onSaveButtonClicks(); break;
            case R.id.addImageButton: onAddImageButtonClicks(); break;
            default:
        }
    }

    private void onCancelButtonClicks() {
    }

    private void onSaveButtonClicks() {
        final Transportation transportation = new Transportation(new JSONObject());
        transportation.method_id = 4;
        transportation.category_id = Integer.valueOf(DataCenter.getCategoryIdAtIndex(Helper.SpinnerSelectedIndex(getView(), R.id.category))).intValue();
        transportation.title = Helper.EditTextGetText(getView(), R.id.description);
        transportation.quantity = Integer.valueOf(Helper.EditTextGetText(getView(), R.id.quantity)).intValue();
        transportation.packaging = Helper.EditTextGetText(getView(), R.id.packaging);
        transportation.unit = Helper.EditTextGetText(getView(), R.id.unit);
        transportation.content = Helper.EditTextGetText(getView(), R.id.comments);
        transportation.pickupAddress = Helper.EditTextGetText(getView(), R.id.pickupAddress);
        transportation.deliveryAddress = Helper.EditTextGetText(getView(), R.id.deliveryAddress);
        transportation.user.name = Helper.EditTextGetText(getView(), R.id.name);
        transportation.user.contact_phone = Helper.EditTextGetText(getView(), R.id.mobile);
        transportation.user.email = Helper.EditTextGetText(getView(), R.id.email);

        final TransportationsApi transportationsApi = new TransportationsApi(this);
        final Drawable drawable = ((ImageButton)getView().findViewById(R.id.addImageButton)).getDrawable();
        transportationsApi.createNewTransportation(transportation, ((BitmapDrawable)drawable).getBitmap());
    }

    private void onAddImageButtonClicks() {
        openGallery();
    }

    protected void openGallery() {
        if( Helper.checkStoragePermission() ) {
            final Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, Helper.SELECT_IMAGE);
        }
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if( requestCode == Helper.SELECT_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            final Bitmap bitmap = Helper.readImage(data, getContext());
            final ImageButton imageButton = getView().findViewById(R.id.addImageButton);
            imageButton.setImageBitmap(bitmap);
        }
    }

    @Override
    public void transportationsLoaded(final String page, final String method_id, final List<Transportation> transportations) {
    }

    @Override
    public void transportationLoaded(final Transportation transportation) {
    }

    @Override
    public void transportationCreated() {
        Toast.makeText(getContext(), Helper.LocalizedString(R.string.alert_done), Toast.LENGTH_LONG).show();
    }

    @Override
    public void apiError(final int errorCode) {
        Toast.makeText(getContext(), Helper.LocalizedString(R.string.alert_failed) + ", code: " + errorCode, Toast.LENGTH_LONG).show();
    }
}

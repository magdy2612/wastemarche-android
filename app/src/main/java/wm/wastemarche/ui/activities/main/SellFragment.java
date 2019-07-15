package wm.wastemarche.ui.activities.main;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import wm.wastemarche.R;
import wm.wastemarche.model.Category;
import wm.wastemarche.model.Item;
import wm.wastemarche.services.datacenter.DataCenter;
import wm.wastemarche.services.datacenter.DataCenterProtocol;
import wm.wastemarche.services.http.HttpConstants;
import wm.wastemarche.services.http.items.ItemsApi;
import wm.wastemarche.services.http.items.ItemsApiProtocol;
import wm.wastemarche.services.imageloader.ImageLoader;
import wm.wastemarche.services.imageloader.ImageLoaderProtocol;
import wm.wastemarche.ui.activities.Helper;
import wm.wastemarche.ui.adapters.ImagesAdapter;

public class SellFragment extends Fragment implements OnClickListener, ImageLoaderProtocol {
    public static enum DoNext {
        NOTHING,
        OPENGALLERY,
        OPENCAMERA
    }

    private ImagesAdapter adapter;
    public DoNext doNext = DoNext.NOTHING;
    public Item item;

    private final ItemsApi itemsApi = new ItemsApi(new ItemsApiProtocol() {
        @Override
        public void itemsLoaded(final String method, final String self, final String page, final List<Item> items) {
        }

        @Override
        public void itemLoaded(final Item item) {
        }

        @Override
        public void itemCreated(Item item) {
            getView().findViewById(R.id.saveButton).setEnabled(true);
            Toast.makeText(getContext(), Helper.LocalizedString(R.string.alert_done), Toast.LENGTH_LONG).show();
            Log.d("SellFragment", "itemCreated() called with: item = [" + item + "]");
            final BuyDetailsFragment fragment = new BuyDetailsFragment();
            fragment.item = item;
            MainActivity.shared.changeCurrentFragment(fragment);
        }

        @Override
        public void apiError(final int errorCode) {
            Toast.makeText(getContext(), Helper.LocalizedString(R.string.alert_failed) + ", code: " + errorCode, Toast.LENGTH_LONG).show();
            getView().findViewById(R.id.saveButton).setEnabled(true);
        }
    });

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_sell, container, false);
        Helper.adjustViewLayout(view);

        Helper.ImageButtonCickListener(view, R.id.addImageButton, this);
        Helper.ButtonClickListener(view, R.id.cancelButton, this);
        Helper.ButtonClickListener(view, R.id.saveButton, this);

        adapter = new ImagesAdapter(getContext());
        final RecyclerView imagesRecyclerView = view.findViewById(R.id.imagesRecyclerView);
        final LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        imagesRecyclerView.setLayoutManager(linearLayout);
        imagesRecyclerView.setAdapter(adapter);

        ((Spinner) view.findViewById(R.id.method)).setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, new String[]{"SELL", "BUY"}));

        final DataCenter dataCenter = new DataCenter(new DataCenterProtocol() {
            @Override
            public void categoreisLoaded(final List<Category> categories) {
                final Handler handler = new Handler(getActivity().getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        final List<String> itemsList = new ArrayList<>(0);
                        for (int i = 0, len = categories.size(); i < len; i++) {
                            itemsList.add(categories.get(i).title);
                            for (int j = 0, lenn = categories.get(i).child_category.size(); j < lenn; j++) {
                                itemsList.add("-- " + categories.get(i).child_category.get(j).title);
                            }
                        }
                        final Spinner dropdown = view.findViewById(R.id.category);
                        final SpinnerAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, itemsList);
                        dropdown.setAdapter(adapter);

                        fillData(view);
                    }
                });
            }
        });

        dataCenter.getCategories();

        Helper.EditTextText(view, R.id.name, DataCenter.user.name);
        Helper.EditTextText(view, R.id.email, DataCenter.user.email);
        Helper.EditTextText(view, R.id.mobile, DataCenter.user.contact_phone);

        return view;
    }

    private void fillData(final View view) {
        if (item == null) {
            return;
        }

        ((Spinner) view.findViewById(R.id.method)).setSelection(item.method_id - 1);
        ((Spinner) view.findViewById(R.id.category)).setSelection(DataCenter.getIndexOfCategory(item.category_id));
        Helper.EditTextText(view, R.id.title, item.title);
        Helper.EditTextText(view, R.id.summary, item.summary);
        Helper.EditTextText(view, R.id.content, item.content);
        Helper.EditTextText(view, R.id.mobile, item.user.contact_phone);
        Helper.EditTextText(view, R.id.email, item.user.email);
        Helper.EditTextText(view, R.id.name, item.user.name);
        Helper.EditTextText(view, R.id.quantity, String.valueOf(item.quantity));
        Helper.EditTextText(view, R.id.unit, item.unit);
        Helper.EditTextText(view, R.id.packaging, item.packaging);
        Helper.EditTextText(view, R.id.price, item.price);
        ((Checkable) view.findViewById(R.id.transportation)).setChecked(item.transportation == 1);

        final ImageLoader imageLoader = new ImageLoader(this);
        for (int i = 0, len = item.images.size(); i < len; i++) {
            imageLoader.loadImage(null, HttpConstants.IMAGES_HOSTNAME + "/150x150/" + item.images.get(i).image_name, getContext());
        }
    }

    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                switch (doNext) {
                    case NOTHING:
                        break;
                    case OPENCAMERA:
                        openCamera();
                        break;
                    case OPENGALLERY:
                        openGallery();
                        break;
                }
            }
        }, 500);
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.addImageButton:
                onAddImageButtonClicks();
                break;
            case R.id.cancelButton:
                onCancelButtonClicks();
                break;
            case R.id.saveButton:
                onSaveButtonClicks();
                break;
            default:
        }
    }

    private void onAddImageButtonClicks() {
        openGallery();
    }

    private void onCancelButtonClicks() {
    }

    private void onSaveButtonClicks() {
        if (getView() == null) {
            return;
        }

        if (!validate()) {
            return;
        }
        final Item item = this.item == null ? new Item(new JSONObject()) : this.item;
        item.category_id = DataCenter.getCategoryIdAtIndex(Helper.SpinnerSelectedIndex(getView(), R.id.category));
        item.method_id = Helper.SpinnerSelectedIndex(getView(), R.id.method) + 1;

        item.title = Helper.EditTextGetText(getView(), R.id.title);
        item.summary = Helper.EditTextGetText(getView(), R.id.summary);
        item.content = Helper.EditTextGetText(getView(), R.id.content);

        item.quantity = Integer.valueOf(Helper.EditTextGetText(getView(), R.id.quantity)).intValue();
        item.unit = Helper.EditTextGetText(getView(), R.id.unit);
        item.transportation = ((Checkable) getView().findViewById(R.id.transportation)).isChecked() ? 1 : 0;
        item.packaging = Helper.EditTextGetText(getView(), R.id.packaging);
        item.price = Helper.EditTextGetText(getView(), R.id.price);

        item.user.contact_phone = Helper.EditTextGetText(getView(), R.id.mobile);
        item.user.email = Helper.EditTextGetText(getView(), R.id.email);
        item.user.name = Helper.EditTextGetText(getView(), R.id.name);

        if (this.item == null) {
            itemsApi.createNewItem(item, adapter.bitmaps);
        } else {
            itemsApi.updateItem(item, adapter.bitmaps);
        }

        //getView().findViewById(R.id.saveButton).setClickable(false);
    }

    private boolean validate() {
        if (Helper.EditTextGetText(getView(), R.id.quantity).isEmpty()) {
            Toast.makeText(getContext(), "Quantity must be a Number", Toast.LENGTH_LONG).show();
            Helper.focus(getView(), R.id.quantity);
            return false;
        }
        return true;
    }

    @Override
    public void imageLoaded(final String imageName) {
        final Bitmap bitmap = ImageLoader.getBitmap(imageName);
        if (bitmap != null) {
            adapter.addImage(bitmap);
        }
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (requestCode == Helper.SELECT_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            final Bitmap bitmap = Helper.readImage(data, getContext());
            adapter.addImage(bitmap);
        } else if (requestCode == Helper.TAKE_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            final Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            adapter.addImage(bitmap);
        }
    }

    // not called
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        if (requestCode == Helper.STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        } else if (requestCode == Helper.CAMERA_PERMISSION) {
            openCamera();
        }
    }


    protected void openGallery() {
        if (Helper.checkStoragePermission()) {
            final Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, Helper.SELECT_IMAGE);
        }
    }

    protected void openCamera() {
        if (Helper.checkCameraPermission()) {
            final Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(i, Helper.TAKE_IMAGE);

        }
    }
}

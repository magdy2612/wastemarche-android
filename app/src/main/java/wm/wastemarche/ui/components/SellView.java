package wm.wastemarche.ui.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import wm.wastemarche.R;
import wm.wastemarche.ui.activities.Helper;
import wm.wastemarche.ui.activities.main.MainActivity;
import wm.wastemarche.ui.activities.main.SellFragment;
import wm.wastemarche.ui.activities.main.SellFragment.DoNext;

public class SellView implements OnClickListener {
    private final Context context;

    public SellView(final Context context) {
        this.context = context;
    }

    public View createView() {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.view_sell, null);

        Helper.ButtonClickListener(view, R.id.cancelButton, this);
        Helper.ButtonClickListener(view, R.id.takePictureButton, this);
        Helper.ButtonClickListener(view, R.id.choosePictureButton, this);

        return view;
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.cancelButton: onCancelButtonClicks(); break;
            case R.id.takePictureButton: onTakePictureButtonClicks(); break;
            case R.id.choosePictureButton: onChoosePictureButtonClicks(); break;
            default:
        }
    }

    private static void onTakePictureButtonClicks() {
        MainActivity.shared.closePopup();
        final SellFragment sellFragment = new SellFragment();
        sellFragment.doNext = DoNext.OPENCAMERA;
        MainActivity.shared.changeCurrentFragment(sellFragment);
    }

    private static void onChoosePictureButtonClicks() {
        MainActivity.shared.closePopup();
        final SellFragment sellFragment = new SellFragment();
        sellFragment.doNext = DoNext.OPENGALLERY;
        MainActivity.shared.changeCurrentFragment(sellFragment);
    }

    private static void onCancelButtonClicks() {
        MainActivity.shared.closePopup();
    }
}

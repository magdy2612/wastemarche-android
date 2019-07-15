package wm.wastemarche.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

import wm.wastemarche.R;
import wm.wastemarche.services.datacenter.DataCenter;
import wm.wastemarche.ui.InfinitScroll;
import wm.wastemarche.ui.activities.main.MainActivity;

public final class Helper {

    public static final int STORAGE_PERMISSION = 1501;
    public static final int CAMERA_PERMISSION = 9215;
    public static final int SELECT_IMAGE = 1592;
    public static final int TAKE_IMAGE = 151;

    private Helper() {
    }

    public static void focus(final View view, final int resId) {
        view.findViewById(resId).requestFocus();
    }

    public static void adjustViewLayout(final View view) {
        if (DataCenter.lang.equalsIgnoreCase("ar")) {
            ViewCompat.setLayoutDirection(view, ViewCompat.LAYOUT_DIRECTION_RTL);
        } else {
            ViewCompat.setLayoutDirection(view, ViewCompat.LAYOUT_DIRECTION_LTR);
        }
    }

    public static String getString(final Activity context, final int id) {
        final Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(new Locale(DataCenter.lang));
        return context.createConfigurationContext(configuration).getResources().getString(id);
    }

    public static void TextViewText(final View view, final int resId, final CharSequence text) {
        final TextView textView = view.findViewById(resId);
        textView.setText(text);
    }

    public static void EditTextText(final View view, final int resId, final CharSequence text) {
        final EditText editText = view.findViewById(resId);
        editText.setText(text);
    }

    public static String EditTextGetText(final View view, final int resId) {
        final EditText editText = view.findViewById(resId);
        return editText.getText().toString();
    }

    public static void ButtonClickListener(final View view, final int resId, final OnClickListener clickListener) {
        final Button button = view.findViewById(resId);
        button.setOnClickListener(clickListener);
    }

    public static void ImageButtonCickListener(final View view, final int resId, final OnClickListener clickListener) {
        final ImageButton imageButton = view.findViewById(resId);
        imageButton.setOnClickListener(clickListener);
    }

    public static void ListViewInfiitScroll(final View view, final int resId, final InfinitScroll infinitScroll) {
        final ListView listView = view.findViewById(resId);
        listView.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(final AbsListView absListView, final int i) {
                if (listView.getLastVisiblePosition() == listView.getAdapter().getCount() - 1 &&
                        listView.getChildAt(listView.getChildCount() - 1).getBottom() <= listView.getHeight()) {
                    infinitScroll.loadNextPage();
                }
            }

            @Override
            public void onScroll(final AbsListView absListView, final int i, final int i1, final int i2) {
            }
        });
    }

    public static int SpinnerSelectedIndex(final View view, final int resId) {
        final Spinner spinner = view.findViewById(resId);
        return spinner.getSelectedItemPosition();
    }

    public static String LocalizedString(final int stringId) {
        return MainActivity.shared.getResources().getString(stringId);
    }

    public static String BooleanString(final int b) {
        return b == 1 ? LocalizedString(R.string.text_yes) : LocalizedString(R.string.text_no);
    }

    public static String BooleanString(final String b) {
        return b.contentEquals("1") ? LocalizedString(R.string.text_yes) : LocalizedString(R.string.text_no);
    }

    public static boolean checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.shared, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;

        } else {
            ActivityCompat.requestPermissions(MainActivity.shared, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
            return false;
        }
    }

    public static boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.shared, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true;

        } else {
            ActivityCompat.requestPermissions(MainActivity.shared, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
            return false;
        }
    }

    public static Bitmap readImage(final Intent data, final Context context) {
        final Uri selectedImage = data.getData();
        final String[] filePathColumn = {MediaColumns.DATA};

        final Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        final int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        final String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return BitmapFactory.decodeFile(picturePath);
    }
}

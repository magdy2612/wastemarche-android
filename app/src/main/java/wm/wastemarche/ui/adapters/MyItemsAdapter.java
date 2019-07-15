package wm.wastemarche.ui.adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import wm.wastemarche.R;
import wm.wastemarche.model.Item;
import wm.wastemarche.services.http.HttpConstants;
import wm.wastemarche.services.imageloader.ImageLoader;
import wm.wastemarche.services.imageloader.ImageLoaderProtocol;
import wm.wastemarche.ui.activities.Helper;
import wm.wastemarche.ui.activities.main.MainActivity;
import wm.wastemarche.ui.activities.main.OffersFragment;
import wm.wastemarche.ui.activities.main.SellFragment;

public class MyItemsAdapter extends BaseAdapter implements ImageLoaderProtocol {
    private final Context context;
    public List<Item> items;
    private final ImageLoader imageLoader;

    public MyItemsAdapter(final Context context) {
        this.context = context;
        items = new ArrayList<>(0);
        imageLoader = new ImageLoader(this);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(final int position) {
        return position;
    }

    @Override
    public long getItemId(final int position) {
        return (long) position;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View vi = convertView;
        final LayoutInflater inflater = LayoutInflater.from(context);
        if (vi == null) {
            vi = inflater.inflate(R.layout.cell_item, parent, false);
        }

        if( position%2 == 0 ) {
            vi.setBackground(new ColorDrawable(context.getResources().getColor(R.color.wmWhite)));
        } else {
            vi.setBackground(new ColorDrawable(context.getResources().getColor(R.color.wmLightGrey)));
        }

        final ImageView image = vi.findViewById(R.id.image);
        imageLoader.loadImage(image, HttpConstants.IMAGES_HOSTNAME + "/150x150/" + items.get(position).main_image, context);

        Helper.TextViewText(vi, R.id.title, items.get(position).title);
        Helper.TextViewText(vi, R.id.method, items.get(position).getMethod());
        Helper.TextViewText(vi, R.id.description, items.get(position).summary);
        Helper.TextViewText(vi, R.id.category, items.get(position).category.title);
        Helper.ImageButtonCickListener(vi, R.id.edit, new OnClickListener() {
            @Override
            public void onClick(final View view) {
                final SellFragment sellFragment = new SellFragment();
                sellFragment.item = items.get(position);
                MainActivity.shared.changeCurrentFragment(sellFragment);
            }
        });

        vi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                final OffersFragment offersFragment = new OffersFragment();
                offersFragment.item = items.get(position);
                MainActivity.shared.changeCurrentFragment(offersFragment);
            }
        });

        return vi;
    }

    @Override
    public void imageLoaded(final String imageName) {
        notifyDataSetChanged();
    }
}

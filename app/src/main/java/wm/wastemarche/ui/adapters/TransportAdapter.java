package wm.wastemarche.ui.adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wm.wastemarche.R;
import wm.wastemarche.model.Transportation;
import wm.wastemarche.services.http.HttpConstants;
import wm.wastemarche.services.imageloader.ImageLoader;
import wm.wastemarche.services.imageloader.ImageLoaderProtocol;
import wm.wastemarche.ui.activities.drawer.TransportationDetailsFragment;
import wm.wastemarche.ui.activities.main.MainActivity;

public class TransportAdapter extends BaseAdapter implements ImageLoaderProtocol {
    private final Context context;
    public List<Transportation> items;
    private final ImageLoader imageLoader;

    public TransportAdapter(final Context context) {
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
            vi = inflater.inflate(R.layout.cell_buy, parent, false);
        }

        if( position%2 == 0 ) {
            vi.setBackground(new ColorDrawable(context.getResources().getColor(R.color.wmWhite)));
        } else {
            vi.setBackground(new ColorDrawable(context.getResources().getColor(R.color.wmLightGrey)));
        }

        final ImageView image = vi.findViewById(R.id.image);
        imageLoader.loadImage(image, HttpConstants.IMAGES_HOSTNAME + "/150x150/" + items.get(position).main_image, context);

        final TextView title = vi.findViewById(R.id.title);
        title.setText(items.get(position).title);

        final TextView description = vi.findViewById(R.id.description);
        description.setText(items.get(position).summary);

        final TextView price = vi.findViewById(R.id.price);
        price.setText(items.get(position).price);

        vi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                final TransportationDetailsFragment fragment = new TransportationDetailsFragment();
                fragment.item = items.get(position);
                MainActivity.shared.changeCurrentFragment(fragment);
            }
        });

        return vi;
    }

    @Override
    public void imageLoaded(final String imageUrl) {
        notifyDataSetChanged();
    }
}

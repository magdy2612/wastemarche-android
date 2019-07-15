package wm.wastemarche.ui.adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import wm.wastemarche.R;
import wm.wastemarche.model.Bid;
import wm.wastemarche.services.imageloader.ImageLoader;
import wm.wastemarche.services.imageloader.ImageLoaderProtocol;
import wm.wastemarche.ui.activities.Helper;

public class BiddingAdapter extends BaseAdapter implements ImageLoaderProtocol {
    private final Context context;
    public List<Bid> items;
    private final ImageLoader imageLoader;

    public BiddingAdapter(final Context context) {
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
        return Integer.valueOf(position);
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
            vi = inflater.inflate(R.layout.cell_bidding, parent, false);
        }

        if( position%2 == 0 ) {
            vi.setBackground(new ColorDrawable(context.getResources().getColor(R.color.wmWhite)));
        } else {
            vi.setBackground(new ColorDrawable(context.getResources().getColor(R.color.wmLightGrey)));
        }

        imageLoader.loadImage((ImageView) vi.findViewById(R.id.image), items.get(position).main_image, context);
        Helper.TextViewText(vi, R.id.title, items.get(position).title);
        Helper.TextViewText(vi, R.id.description, items.get(position).description);
        Helper.TextViewText(vi, R.id.created_at, items.get(position).created_at);
        Helper.TextViewText(vi, R.id.updated_at, items.get(position).updated_at);

        return vi;
    }

    @Override
    public void imageLoaded(final String imageUrl){
        notifyDataSetChanged();
    }
}

package wm.wastemarche.ui.adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import wm.wastemarche.R;
import wm.wastemarche.model.Request;
import wm.wastemarche.ui.activities.Helper;
import wm.wastemarche.ui.activities.main.OfferFragment;
import wm.wastemarche.ui.activities.main.MainActivity;

public class OffersAdapter extends BaseAdapter {
    private final Context context;
    public List<Request> requests;

    public OffersAdapter(final Context context) {
        this.context = context;
        this.requests = new ArrayList<>(0);
    }

    @Override
    public int getCount() {
        return requests.size();
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
            vi = inflater.inflate(R.layout.cell_offer, parent, false);
        }

        if( position%2 == 0 ) {
            vi.setBackground(new ColorDrawable(context.getResources().getColor(R.color.wmWhite)));
        } else {
            vi.setBackground(new ColorDrawable(context.getResources().getColor(R.color.wmLightGrey)));
        }

        Helper.TextViewText(vi, R.id.price, "" + requests.get(position).price);
        Helper.TextViewText(vi, R.id.currency, requests.get(position).currency);

        vi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                final OfferFragment offerFragment = new OfferFragment();
                offerFragment.request = requests.get(position);
                MainActivity.shared.changeCurrentFragment(offerFragment);
            }
        });

        return vi;
    }
}

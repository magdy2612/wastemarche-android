package wm.wastemarche.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import wm.wastemarche.R;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder> {
    public final List<Bitmap> bitmaps = new ArrayList<>(0);
    private final Context context;

    public ImagesAdapter(final Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.cell_image, parent, false);
        return new ImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImagesViewHolder holder, final int position) {
        holder.imageView.setImageBitmap(bitmaps.get(position));
    }

    @Override
    public long getItemId(final int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return bitmaps.size();
    }

    public void addImage(final Bitmap bitmap) {
        bitmaps.add(bitmap);
        notifyDataSetChanged();
    }

    static class ImagesViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        ImagesViewHolder(final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}

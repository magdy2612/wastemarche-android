package wm.wastemarche.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import wm.wastemarche.R;
import wm.wastemarche.model.Image;
import wm.wastemarche.services.http.HttpConstants;
import wm.wastemarche.services.imageloader.ImageLoader;
import wm.wastemarche.services.imageloader.ImageLoaderProtocol;

public class ImagePagerAdapter extends PagerAdapter implements ImageLoaderProtocol {
    private final List<Image> images;
    private final LayoutInflater inflater;
    private final Context context;
    private final ImageLoader imageLoader;

    public ImagePagerAdapter(final List<Image> images, final Context context) {
        this.images = images;
        this.context = context;
        inflater = LayoutInflater.from(context);
        imageLoader = new ImageLoader(this);
    }

    @Override
    public void destroyItem(@NonNull final ViewGroup container, final int position, @NonNull final Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public void imageLoaded(final String imageName) {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup view, final int position) {
        final View imageLayout = inflater.inflate(R.layout.view_image, view, false);
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
        imageLoader.loadImage(imageView, HttpConstants.IMAGES_HOSTNAME + "/150x150/" + images.get(position).image_name, context);
        view.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }
    //
    //@Override
    //public void restoreState(Parcelable state, ClassLoader loader) {
    //}
    //
    //@Override
    //public Parcelable saveState() {
    //    return null;
    //}
}

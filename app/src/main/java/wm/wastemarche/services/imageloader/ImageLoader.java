package wm.wastemarche.services.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ImageLoader {

    private static final Map<String, byte[]> images = new HashMap<>(0);
    private final ImageLoaderProtocol delegate;

    public ImageLoader(final ImageLoaderProtocol delegate) {
        this.delegate = delegate;
    }

    public static void clearCache() {
        images.clear();
    }

    public void loadImage(final ImageView imageView, final String imageURL, final Context context) {
        if (images.get(imageURL) != null) {
            if (imageView == null) {
                delegate.imageLoaded(imageURL);
            } else {
                final Handler handler = new Handler(context.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        final byte[] bytes = images.get(imageURL);
                        final Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        imageView.setImageBitmap(bmp);
                    }
                });
            }
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final URL url = new URL(imageURL);
                        final InputStream inputStream = url.openConnection().getInputStream();
                        final byte[] bytes = readBytes(inputStream);
                        final Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);


                        images.put(imageURL, bytes);
                        final Handler handler = new Handler(context.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //imageView.setImageBitmap(bmp);
                                delegate.imageLoaded(imageURL);
                            }
                        });
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public static Bitmap getBitmap(final String imageUrl) {
        final byte[] bytes = images.get(imageUrl);
        if (bytes == null) {
            return null;
        }

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private static byte[] readBytes(final InputStream inputStream) {
        final ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        final int bufferSize = 1024;
        final byte[] buffer = new byte[bufferSize];
        int len = 0;
        try {
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }

        // and then we can return your byte array.
        return byteBuffer.toByteArray();
    }

    private static Bitmap getBitmap(final VectorDrawable vectorDrawable) {
        final Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap getBitmap(final Context context, final int drawableId) {
        final Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (drawable instanceof BitmapDrawable) {
            return BitmapFactory.decodeResource(context.getResources(), drawableId);
        } else if (drawable instanceof VectorDrawable) {
            return getBitmap((VectorDrawable) drawable);
        } else {
            throw new IllegalArgumentException("unsupported drawable type");
        }
    }

}

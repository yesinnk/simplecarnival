package ca.carnivalgames.simplecarnival;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DiffUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Utils {

  public static class Threads {
    // For future networking
    private static final ExecutorService threadNT = Executors.newSingleThreadExecutor();
    private static final ExecutorService threadBG = Executors.newSingleThreadExecutor();
    private static final ExecutorService threadDB = Executors.newSingleThreadExecutor();

    public static void runOnBackgroundThread(Runnable runnable) {
      threadBG.execute(runnable);
    }

    public static void runOnDBThread(Runnable runnable) {
      threadDB.execute(runnable);
    }

    public static void runOnNetworkThread(Runnable runnable) {
      threadNT.execute(runnable);
    }
  }

  public static class Fragments {
    public static void loadFragment(
        int containerID, Fragment destination, FragmentManager fragmentManager) {
      if (destination != null) {
        deleteLastFragment(fragmentManager);

        fragmentManager
            .beginTransaction()
            .add(containerID, destination, "lastFragment")
            .setPrimaryNavigationFragment(destination)
            .commit();
      }
    }

    static void deleteLastFragment(FragmentManager fragmentManager) {
      Fragment currentFragment = fragmentManager.findFragmentByTag("lastFragment");

      if (currentFragment != null) {
        fragmentManager.beginTransaction().remove(currentFragment).commit();
      }
    }
  }

  public static class Measurement {
    public static float pixelToDP(int pixels) {
      float density = Resources.getSystem().getDisplayMetrics().density;
      return pixels * density;
    }
  }

  public static class UnusedDiffer<T> extends DiffUtil.ItemCallback<T> {

    @Override
    public boolean areItemsTheSame(@NonNull T oldItem, @NonNull T newItem) {
      return false;
    }

    @Override
    public boolean areContentsTheSame(@NonNull T oldItem, @NonNull T newItem) {
      return false;
    }
  }

  public static class ImageTools {
    public static Bitmap createImageWithPorterDuff(
        Bitmap image, Bitmap filterImage, PorterDuff.Mode type) {
      Bitmap result =
          Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas();
      canvas.setBitmap(result);

      Paint paint = new Paint();
      paint.setFilterBitmap(false);

      canvas.drawBitmap(image, 0, 0, paint);
      paint.setXfermode(new PorterDuffXfermode(type));
      canvas.drawBitmap(filterImage, 0, 0, paint);
      paint.setXfermode(null);

      return result;
    }

    public static Bitmap cropBitmap(Bitmap bitmap, int width, int height) {
      return Bitmap.createBitmap(bitmap, 0, 0, width, height);
    }

    public static Bitmap createClippedImage(Bitmap image, Bitmap clipImage) {
      return createImageWithPorterDuff(image, clipImage, PorterDuff.Mode.DST_IN);
    }

    public static Bitmap createInversedClippedImage(Bitmap image, Bitmap clipImage) {
      return createImageWithPorterDuff(image, clipImage, PorterDuff.Mode.DST_OUT);
    }
  }
}

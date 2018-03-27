package ir.apend.searchbox.helper;

import android.os.Build;

/**
 * Created by Fatemeh on 3/27/2018.
 */

public class GeneralHelper {

    public static boolean isLollipopOrNewer() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

}

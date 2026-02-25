// Enums test with code taken from the Android Wordpress app.
// Is the SmartToastType constructor reachable?

public class Main {

    public interface PrefKey {
        String name();
        String toString();
    }

    public enum UndeletablePrefKey implements PrefKey {
        // Theme image size retrieval
        THEME_IMAGE_SIZE_WIDTH,

        // index of the last app-version
        LAST_APP_VERSION_INDEX,

        // visual editor available
        VISUAL_EDITOR_AVAILABLE,

        // When we need to show the new editor beta snackbar
        AZTEC_EDITOR_BETA_REQUIRED,

        // When we need to show the new editor promo dialog
        AZTEC_EDITOR_PROMO_REQUIRED,

        // counter which determines whether it's time to show the above promo
        AZTEC_EDITOR_PROMO_COUNTER,

        // When we need to show the async promo dialog
        ASYNC_PROMO_REQUIRED,

        // When we need to show the new image optimize promo dialog
        IMAGE_OPTIMIZE_PROMO_REQUIRED,

        // Global plans features
        GLOBAL_PLANS_PLANS_FEATURES,

        // When we need to sync IAP data with the wpcom backend
        IAP_SYNC_REQUIRED,

        // When we need to show the Gravatar Change Promo Tooltip
        GRAVATAR_CHANGE_PROMO_REQUIRED,

        // When we need to show the snackbar indicating how notifications can be navigated through
        SWIPE_TO_NAVIGATE_NOTIFICATIONS,

        // Same as above but for the reader
        SWIPE_TO_NAVIGATE_READER,

        // smart toast counters
        SMART_TOAST_MEDIA_LONG_PRESS_USAGE_COUNTER,
        SMART_TOAST_MEDIA_LONG_PRESS_TOAST_COUNTER,
        SMART_TOAST_COMMENTS_LONG_PRESS_USAGE_COUNTER,
        SMART_TOAST_COMMENTS_LONG_PRESS_TOAST_COUNTER,

        // permission keys - set once a specific permission has been asked, regardless of response
        ASKED_PERMISSION_STORAGE_WRITE,
        ASKED_PERMISSION_STORAGE_READ,
        ASKED_PERMISSION_CAMERA,
        ASKED_PERMISSION_LOCATION_COURSE,
        ASKED_PERMISSION_LOCATION_FINE,

        // wizard style login flow active
        LOGIN_WIZARD_STYLE_ACTIVE
    }

    public enum SmartToastType {
        MEDIA_LONG_PRESS
        (UndeletablePrefKey.SMART_TOAST_MEDIA_LONG_PRESS_USAGE_COUNTER,
         UndeletablePrefKey.SMART_TOAST_MEDIA_LONG_PRESS_TOAST_COUNTER),
        COMMENTS_LONG_PRESS
        (UndeletablePrefKey.SMART_TOAST_COMMENTS_LONG_PRESS_USAGE_COUNTER,
         UndeletablePrefKey.SMART_TOAST_COMMENTS_LONG_PRESS_TOAST_COUNTER);
        
        private final UndeletablePrefKey usageKey;
        private final UndeletablePrefKey shownKey;

        SmartToastType(UndeletablePrefKey usageKey, UndeletablePrefKey shownKey) {
            this.usageKey = usageKey;
            this.shownKey = shownKey;
            System.out.println("Generated SmartToastType object: <" + this.usageKey + ", " + this.shownKey + ">");
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Enum test.");

        SmartToastType stt1 = SmartToastType.MEDIA_LONG_PRESS;
        SmartToastType stt2 = SmartToastType.COMMENTS_LONG_PRESS;

        Class<?> c = UndeletablePrefKey.class;
        Object[] enumConsts = c.getEnumConstants();
        System.out.println("Read " + enumConsts.length + " enum constants.");
        for (Object obj : enumConsts) {
            UndeletablePrefKey entry = (UndeletablePrefKey)obj;
            System.out.println("Entry: " + entry.toString());
        }
    }
}


package org.company.project;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.IOException;
import java.util.Properties;

import javax.inject.Inject;

import dagger.ObjectGraph;

/**
 * @author jcampbell
 */
public class App extends Application {
    public static final String TAG = App.createTag(App.class);

    // TODO change this for your app (pick a name similar to package name... get both raw log AND tag logs)
    public static final String DEFAULT_TAG_PREFIX = "company.";
    public static final int MAX_TAG_LENGTH = 23; // if over: IllegalArgumentException: Log tag "xxx" exceeds limit of 23 characters

    private ObjectGraph injectionObjectGraph;

    @Inject
    public App() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        injectObject(this);
        enableStrictMode();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (getFilesDir() != null) {
            MultiDex.install(this);
        } else {
            // During app install it might have experienced "INSTALL_FAILED_DEXOPT" (reinstall is the only known work-around)
            // https://code.google.com/p/android/issues/detail?id=8886
            String message = getString(R.string.app_name) + " is in a bad state, please uninstall/reinstall";
            Log.e(TAG, message);
        }
    }

    protected Object[] getModules() {
        return new Object[]{
                new AppModule(this)
        };
    }

    private void injectObject(Object object) {
        if (injectionObjectGraph == null) {
            injectionObjectGraph = ObjectGraph.create(getModules());
        }

        injectionObjectGraph.inject(object);
    }

    public static void inject(Activity target) {
        ((App) target.getApplication()).injectObject(target);
    }

    public static void inject(Fragment target) {
        getApplication(target).injectObject(target);
    }

    // support for leanback fragments
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void inject(android.app.Fragment target) {
        App app = (App) (target.getActivity().getApplication());
        app.injectObject(target);
    }


    public static void inject(Context context, Object target) {
        getApplication(context).injectObject(target);
    }

    public static App getApplication(Fragment fragment) {
        return (App) fragment.getActivity().getApplication();
    }

    public static App getApplication(Activity activity) {
        return (App) activity.getApplication();
    }

    public static App getApplication(Context context) {
        return (App) context.getApplicationContext();
    }

    public static String createTag(String name) {
        String fullName = DEFAULT_TAG_PREFIX + name;
        return fullName.length() > MAX_TAG_LENGTH ? fullName.substring(0, MAX_TAG_LENGTH) : fullName;
    }

    public static String createTag(Class clazz) {
        return createTag(clazz.getSimpleName());
    }

    public String readBuildNumber() {
        String versionText = null;
        Properties properties = new Properties();
        try {
            properties.load(App.class.getResourceAsStream("/build.properties"));
            versionText = properties.getProperty("build.number");
        } catch (IOException e) {
            Log.e(TAG, "Failed to read build.properties", e);
        }

        if (versionText != null) {
            return "${build.number}".equals(versionText) ? "Developer Build" : versionText;
        } else {
            return "Not available";
        }
    }

    public String getVersionText(Context context) throws PackageManager.NameNotFoundException {
        PackageInfo pInfo = context.getPackageManager().getPackageInfo("org.company.project", PackageManager.GET_META_DATA);
        return pInfo.versionName + " (" + readBuildNumber() + ")";
    }

    /**
     * Enable strict mode
     * This only works if compiling against android 2.3 or greater
     * also, this will crash on devices running on less than 2.3
     */
    private void enableStrictMode() {
        android.os.StrictMode.setThreadPolicy(new android.os.StrictMode.ThreadPolicy.Builder()
                //.detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());

        android.os.StrictMode.setVmPolicy(new android.os.StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }

}
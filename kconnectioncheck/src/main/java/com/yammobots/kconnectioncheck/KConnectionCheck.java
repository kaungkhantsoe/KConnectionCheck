package com.yammobots.kconnectioncheck;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

/**
 * Created by kaungkhantsoe on 25/09/2020.
 **/
public class KConnectionCheck {

    private static boolean connectionStatus = true;

    public static void addConnectionCheck(final Context context, LifecycleOwner lifecycleOwner, final ConnectionStatusChangeListener connectionStatusChangeListener) {
        new ConnectionLiveData(context).observe(lifecycleOwner, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {
                if (connectionStatus != isConnected) {
                    connectionStatus = isConnected;
                    connectionStatusChangeListener.onConnectionStatusChange(isConnected);
                    ConnectionSnack.show(context, ((Activity) context).findViewById(android.R.id.content), isConnected, null);
                }
            }
        });
    }

    public static void addConnectionCheck(final Context context, LifecycleOwner lifecycleOwner, final ConnectionStatusChangeListener connectionStatusChangeListener, final CustomConnectionBuilder customConnectionBuilder) {

        new ConnectionLiveData(context).observe(lifecycleOwner, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {
                if (connectionStatus != isConnected) {
                    connectionStatus = isConnected;
                    connectionStatusChangeListener.onConnectionStatusChange(isConnected);

                    if (customConnectionBuilder != null && customConnectionBuilder.isShowSnackOnStatusChange())
                        if (customConnectionBuilder.getBottomNavigationView() != null)
                            ConnectionSnack.show(context, customConnectionBuilder.getBottomNavigationView(), isConnected, customConnectionBuilder);
                        else
                            ConnectionSnack.show(context, ((Activity) context).findViewById(android.R.id.content), isConnected, customConnectionBuilder);
                }
            }
        });
    }

    public interface ConnectionStatusChangeListener {
        void onConnectionStatusChange(boolean status);
    }

    public static class CustomConnectionBuilder {
        private String noConnectionText;
        private String connectionRestoredText;
        private int noConnectionDrawable = 0;
        private int connectionRestoredDrawable = 0;
        private boolean hideWhenConnectionRestored = true;
        private int noConnectionTextColor = 0;
        private int connectionRestoredTextColor = 0;
        private int dismissTextColor = 0;
        private String dismissText;
        private boolean showSnackOnStatusChange = true;
        private View bottomNavigationView;

        public CustomConnectionBuilder() {
        }

        public CustomConnectionBuilder(String noConnectionText) {
            this.noConnectionText = noConnectionText;
        }

        public CustomConnectionBuilder(String noConnectionText, String connectionRestoredText) {
            this.noConnectionText = noConnectionText;
            this.connectionRestoredText = connectionRestoredText;
        }

        public CustomConnectionBuilder(String noConnectionText, String connectionRestoredText, int noConnectionDrawable) {
            this.noConnectionText = noConnectionText;
            this.connectionRestoredText = connectionRestoredText;
            this.noConnectionDrawable = noConnectionDrawable;
        }

        public CustomConnectionBuilder(String noConnectionText, String connectionRestoredText, @DrawableRes int noConnectionDrawable, @DrawableRes int connectionRestoredDrawable) {
            this.noConnectionText = noConnectionText;
            this.connectionRestoredText = connectionRestoredText;
            this.noConnectionDrawable = noConnectionDrawable;
            this.connectionRestoredDrawable = connectionRestoredDrawable;
        }

        public CustomConnectionBuilder(String noConnectionText, String connectionRestoredText, @DrawableRes int noConnectionDrawable, @DrawableRes int connectionRestoredDrawable, boolean hideWhenConnectionRestored) {
            this.noConnectionText = noConnectionText;
            this.connectionRestoredText = connectionRestoredText;
            this.noConnectionDrawable = noConnectionDrawable;
            this.connectionRestoredDrawable = connectionRestoredDrawable;
            this.hideWhenConnectionRestored = hideWhenConnectionRestored;
        }

        public CustomConnectionBuilder(String noConnectionText, String connectionRestoredText, @DrawableRes int noConnectionDrawable, @DrawableRes int connectionRestoredDrawable, boolean hideWhenConnectionRestored, @ColorInt int noConnectionTextColor) {
            this.noConnectionText = noConnectionText;
            this.connectionRestoredText = connectionRestoredText;
            this.noConnectionDrawable = noConnectionDrawable;
            this.connectionRestoredDrawable = connectionRestoredDrawable;
            this.hideWhenConnectionRestored = hideWhenConnectionRestored;
            this.noConnectionTextColor = noConnectionTextColor;
        }

        public CustomConnectionBuilder(String noConnectionText, String connectionRestoredText, @DrawableRes int noConnectionDrawable, @DrawableRes int connectionRestoredDrawable, boolean hideWhenConnectionRestored, @ColorInt int noConnectionTextColor, @ColorInt int connectionRestoredTextColor) {
            this.noConnectionText = noConnectionText;
            this.connectionRestoredText = connectionRestoredText;
            this.noConnectionDrawable = noConnectionDrawable;
            this.connectionRestoredDrawable = connectionRestoredDrawable;
            this.hideWhenConnectionRestored = hideWhenConnectionRestored;
            this.noConnectionTextColor = noConnectionTextColor;
            this.connectionRestoredTextColor = connectionRestoredTextColor;
        }

        public CustomConnectionBuilder(String noConnectionText, String connectionRestoredText, @DrawableRes int noConnectionDrawable, @DrawableRes int connectionRestoredDrawable, boolean hideWhenConnectionRestored, @ColorInt int noConnectionTextColor, @ColorInt int connectionRestoredTextColor, @ColorInt int dismissTextColor) {
            this.noConnectionText = noConnectionText;
            this.connectionRestoredText = connectionRestoredText;
            this.noConnectionDrawable = noConnectionDrawable;
            this.connectionRestoredDrawable = connectionRestoredDrawable;
            this.hideWhenConnectionRestored = hideWhenConnectionRestored;
            this.noConnectionTextColor = noConnectionTextColor;
            this.connectionRestoredTextColor = connectionRestoredTextColor;
            this.dismissTextColor = dismissTextColor;
        }

        public CustomConnectionBuilder(String noConnectionText, String connectionRestoredText, @DrawableRes int noConnectionDrawable, @DrawableRes int connectionRestoredDrawable, boolean hideWhenConnectionRestored, @ColorInt int noConnectionTextColor, @ColorInt int connectionRestoredTextColor, @ColorInt int dismissTextColor, String dismissText) {
            this.noConnectionText = noConnectionText;
            this.connectionRestoredText = connectionRestoredText;
            this.noConnectionDrawable = noConnectionDrawable;
            this.connectionRestoredDrawable = connectionRestoredDrawable;
            this.hideWhenConnectionRestored = hideWhenConnectionRestored;
            this.noConnectionTextColor = noConnectionTextColor;
            this.connectionRestoredTextColor = connectionRestoredTextColor;
            this.dismissTextColor = dismissTextColor;
            this.dismissText = dismissText;
        }

        public CustomConnectionBuilder(String noConnectionText, String connectionRestoredText, int noConnectionDrawable, int connectionRestoredDrawable, boolean hideWhenConnectionRestored, int noConnectionTextColor, int connectionRestoredTextColor, int dismissTextColor, String dismissText, boolean showSnackOnStatusChange) {
            this.noConnectionText = noConnectionText;
            this.connectionRestoredText = connectionRestoredText;
            this.noConnectionDrawable = noConnectionDrawable;
            this.connectionRestoredDrawable = connectionRestoredDrawable;
            this.hideWhenConnectionRestored = hideWhenConnectionRestored;
            this.noConnectionTextColor = noConnectionTextColor;
            this.connectionRestoredTextColor = connectionRestoredTextColor;
            this.dismissTextColor = dismissTextColor;
            this.dismissText = dismissText;
            this.showSnackOnStatusChange = showSnackOnStatusChange;
        }

        public CustomConnectionBuilder(String noConnectionText, String connectionRestoredText, int noConnectionDrawable, int connectionRestoredDrawable, boolean hideWhenConnectionRestored, int noConnectionTextColor, int connectionRestoredTextColor, int dismissTextColor, String dismissText, boolean showSnackOnStatusChange, @NonNull View bottomNavigationView) {
            this.noConnectionText = noConnectionText;
            this.connectionRestoredText = connectionRestoredText;
            this.noConnectionDrawable = noConnectionDrawable;
            this.connectionRestoredDrawable = connectionRestoredDrawable;
            this.hideWhenConnectionRestored = hideWhenConnectionRestored;
            this.noConnectionTextColor = noConnectionTextColor;
            this.connectionRestoredTextColor = connectionRestoredTextColor;
            this.dismissTextColor = dismissTextColor;
            this.dismissText = dismissText;
            this.showSnackOnStatusChange = showSnackOnStatusChange;
            this.bottomNavigationView = bottomNavigationView;
        }

        public View getBottomNavigationView() {
            return bottomNavigationView;
        }

        public void setBottomNavigationView(@NonNull View bottomNavigationView) {
            this.bottomNavigationView = bottomNavigationView;
        }

        public boolean isShowSnackOnStatusChange() {
            return showSnackOnStatusChange;
        }

        public void showSnackOnStatusChange(boolean showSnackOnStatusChange) {
            this.showSnackOnStatusChange = showSnackOnStatusChange;
        }

        public String getNoConnectionText() {
            return noConnectionText;
        }

        public void setNoConnectionText(String noConnectionText) {
            this.noConnectionText = noConnectionText;
        }

        public String getConnectionRestoredText() {
            return connectionRestoredText;
        }

        public void setConnectionRestoredText(String connectionRestoredText) {
            this.connectionRestoredText = connectionRestoredText;
        }

        public int getNoConnectionDrawable() {
            return noConnectionDrawable;
        }

        public void setNoConnectionDrawable(@DrawableRes int noConnectionDrawable) {
            this.noConnectionDrawable = noConnectionDrawable;
        }

        public int getConnectionRestoredDrawable() {
            return connectionRestoredDrawable;
        }

        public void setConnectionRestoredDrawable(@DrawableRes int connectionRestoredDrawable) {
            this.connectionRestoredDrawable = connectionRestoredDrawable;
        }

        public boolean isHideWhenConnectionRestored() {
            return hideWhenConnectionRestored;
        }

        public void hideSnackWhenConnectionRestored(boolean hideWhenConnectionRestored) {
            this.hideWhenConnectionRestored = hideWhenConnectionRestored;
        }

        public int getNoConnectionTextColor() {
            return noConnectionTextColor;
        }

        public void setNoConnectionTextColor(@ColorInt int noConnectionTextColor) {
            this.noConnectionTextColor = noConnectionTextColor;
        }

        public int getConnectionRestoredTextColor() {
            return connectionRestoredTextColor;
        }

        public void setConnectionRestoredTextColor(@ColorInt int connectionRestoredTextColor) {
            this.connectionRestoredTextColor = connectionRestoredTextColor;
        }

        public int getDismissTextColor() {
            return dismissTextColor;
        }

        public void setDismissTextColor(@ColorInt int dismissTextColor) {
            this.dismissTextColor = dismissTextColor;
        }

        public String getDismissText() {
            return dismissText;
        }

        public void setDismissText(String dismissText) {
            this.dismissText = dismissText;
        }
    }
}

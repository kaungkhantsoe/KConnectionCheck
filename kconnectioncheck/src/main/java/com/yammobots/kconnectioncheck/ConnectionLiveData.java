package com.yammobots.kconnectioncheck;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * Created by kaungkhantsoe on 24/09/2020.
 **/
public class ConnectionLiveData extends LiveData<Boolean> {

    private Context context;

    private ConnectivityManager connectivityManager;

    public ConnectionLiveData(Context context) {
        this.context = context;

        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private ConnectivityManager.NetworkCallback connectivityManagerCallback;

    private NetworkRequest.Builder networkRequestBuilder;

    @Override
    protected void onActive() {
        super.onActive();
        updateConnection();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            networkRequestBuilder = new NetworkRequest.Builder()
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    connectivityManager.registerDefaultNetworkCallback(getConnectivityMarshmallowManagerCallback());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    marshmallowNetworkAvailableRequest();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                try {
                    lollipopNetworkAvailableRequest();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }else {

            context.registerReceiver(networkReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));// android.net.ConnectivityManager.CONNECTIVITY_ACTION
        }

    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager.unregisterNetworkCallback(connectivityManagerCallback);
        } else {
            context.unregisterReceiver(networkReceiver);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void lollipopNetworkAvailableRequest() throws IllegalAccessException {
        connectivityManager.registerNetworkCallback(networkRequestBuilder.build(), getConnectivityLollipopManagerCallback());
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void marshmallowNetworkAvailableRequest() throws IllegalAccessException {
        connectivityManager.registerNetworkCallback(networkRequestBuilder.build(), getConnectivityMarshmallowManagerCallback());
    }

    private ConnectivityManager.NetworkCallback getConnectivityLollipopManagerCallback() throws IllegalAccessException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManagerCallback = new ConnectivityManager.NetworkCallback() {

                @Override
                public void onAvailable(@NonNull Network network) {
                    postValue(true);

                }

                @Override
                public void onLost(@NonNull Network network) {
                    postValue(false);
                }
            };

            return connectivityManagerCallback;

        } else
            throw new IllegalAccessException("Accessing wrong API version");
    }

    private ConnectivityManager.NetworkCallback getConnectivityMarshmallowManagerCallback() throws IllegalAccessException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManagerCallback = new ConnectivityManager.NetworkCallback() {
                @Override
                public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
                    if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))
                        postValue(true);
                }

                @Override
                public void onLost(@NonNull Network network) {
                    postValue(false);
                }
            };

            return connectivityManagerCallback;
        } else
            throw new IllegalAccessException("Accessing wrong API version");
    }

    private BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateConnection();
        }
    };

    private void updateConnection() {
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null)
            postValue(activeNetwork.isConnected());
        else
            postValue(false);
    }
}
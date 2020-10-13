package com.yammobots.kconnectioncheck;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;


/**
 * Created by kaungkhantsoe on 24/09/2020.
 **/
public class ConnectionSnack {

    public static void show(Context context, View parent, boolean isConnectionAvailable, KConnectionCheck.CustomConnectionBuilder connectionBuilder) {

        LayoutInflater inflater = LayoutInflater.from(context);

        // Create the Snackbar
        final Snackbar snackbar;

        if (isConnectionAvailable) {
            if (connectionBuilder != null && !connectionBuilder.isHideWhenConnectionRestored())
                snackbar = Snackbar.make(parent, "", Snackbar.LENGTH_INDEFINITE);
            else
                snackbar = Snackbar.make(parent, "", Snackbar.LENGTH_SHORT);
        } else
            snackbar = Snackbar.make(parent, "", Snackbar.LENGTH_INDEFINITE);

        // Get the Snackbar's layout view
        Snackbar.SnackbarLayout snackLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackLayout.setBackgroundColor(Color.TRANSPARENT);

        // Hide the text
        TextView textView = (TextView) snackLayout.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);

        // Inflate and prepare custom Layout
        View customLayout = inflater.inflate(R.layout.abutil_connection_layout, null);

        TextView connectionTextView = customLayout.findViewById(R.id.tv_connection);
        ImageView connectionImageView = customLayout.findViewById(R.id.iv_connection);

        TextView dismissTextView = customLayout.findViewById(R.id.tv_connection_dismiss);
        dismissTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });

        if (isConnectionAvailable) {
            if (connectionBuilder != null && connectionBuilder.getConnectionRestoredDrawable() != 0)
                connectionImageView.setImageResource(connectionBuilder.getConnectionRestoredDrawable());
            else
                connectionImageView.setImageResource(R.drawable.ic_connection_restored);

            if (connectionBuilder != null && connectionBuilder.getConnectionRestoredText() != null)
                connectionTextView.setText(connectionBuilder.getConnectionRestoredText());
            else
                connectionTextView.setText(R.string.connection_restored);

            if (connectionBuilder != null && connectionBuilder.getConnectionRestoredTextColor() != 0)
                connectionTextView.setTextColor(connectionBuilder.getConnectionRestoredTextColor());

        } else {
            if (connectionBuilder != null && connectionBuilder.getNoConnectionDrawable() != 0)
                connectionImageView.setImageResource(connectionBuilder.getNoConnectionDrawable());
            else
                connectionImageView.setImageResource(R.drawable.ic_no_connection);

            if (connectionBuilder != null && connectionBuilder.getNoConnectionText() != null)
                connectionTextView.setText(connectionBuilder.getNoConnectionText());
            else
                connectionTextView.setText(R.string.no_connection);

            if (connectionBuilder != null && connectionBuilder.getNoConnectionTextColor() != 0)
                connectionTextView.setTextColor(connectionBuilder.getNoConnectionTextColor());

        }

        if (connectionBuilder != null && connectionBuilder.getDismissTextColor() != 0)
            dismissTextView.setTextColor(connectionBuilder.getDismissTextColor());

        if (connectionBuilder != null && connectionBuilder.getDismissText() != null)
            dismissTextView.setText(connectionBuilder.getDismissText());

        //If the view is not covering the whole snackbar layout, add this line
        snackLayout.setPadding(0, 0, 0, 0);

        // Add the view to the Snackbar's layout
        snackLayout.addView(customLayout, 0);

        if (connectionBuilder != null
                && connectionBuilder.getBottomNavigationView() != null
                && snackbar.getView().getLayoutParams() instanceof CoordinatorLayout.LayoutParams
                && parent instanceof BottomNavigationView) {
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snackbar.getView().getLayoutParams();
            params.setAnchorId(parent.getId());
            params.anchorGravity = Gravity.TOP;
            params.gravity = Gravity.TOP;
            snackbar.getView().setLayoutParams(params);
        } else if (connectionBuilder != null
                && connectionBuilder.getBottomNavigationView() != null
                && snackbar.getView().getLayoutParams() instanceof CoordinatorLayout.LayoutParams) {
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snackbar.getView().getLayoutParams();
            params.anchorGravity = Gravity.BOTTOM;
            params.gravity = Gravity.BOTTOM;
            snackbar.getView().setLayoutParams(params);
        }

        // Show the Snackbar
        snackbar.show();

    }

}

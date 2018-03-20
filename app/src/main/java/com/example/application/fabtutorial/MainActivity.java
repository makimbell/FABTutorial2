package com.example.application.fabtutorial;

import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabAdd, fabPhone, fabMessage;
    private String phone = "111-111-1111";
    private Animation showX, showPlus, showButton, hideButton;
    private ConstraintLayout mainLayout;
    private TextView diaYes, diaNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabPhone = (FloatingActionButton) findViewById(R.id.fabPhone);
        fabMessage = (FloatingActionButton) findViewById(R.id.fabMessage);
        mainLayout = (ConstraintLayout) findViewById(R.id.mainLayout);

        // Define animations
        showX = AnimationUtils.loadAnimation(MainActivity.this, R.anim.show_x);
        showPlus = AnimationUtils.loadAnimation(MainActivity.this, R.anim.show_plus);
        showButton = AnimationUtils.loadAnimation(MainActivity.this, R.anim.show_button);
        hideButton = AnimationUtils.loadAnimation(MainActivity.this, R.anim.hide_button);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fabPhone.getVisibility() == View.VISIBLE && fabMessage.getVisibility() == View.VISIBLE) {
                    hideButtons();
                } else {
                    showButtons();
                }
            }
        });

        fabPhone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Create custom dialog from layout
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_layout, null);
                diaYes = (TextView) dialogView.findViewById(R.id.diaBtnYes);
                diaNo = (TextView) dialogView.findViewById(R.id.diaBtnNo);

                // Show view
                builder.setView(dialogView);
                final AlertDialog dialog = builder.create();
                dialog.show();

                diaYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + phone));
                        if(intent.resolveActivity(getPackageManager()) != null){
                            startActivity(intent);
                            hideButtons();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(MainActivity.this, "There is no app that supports this action.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                diaNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        fabMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:" + phone));
                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                    hideButtons();
                } else {
                    Toast.makeText(MainActivity.this, "There is no app that supports this action.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fabPhone.getVisibility() == View.VISIBLE && fabMessage.getVisibility() == View.VISIBLE) {
                    hideButtons();
                }
            }
        });
    }

    private void showButtons() {

        fabPhone.setVisibility(View.VISIBLE);
        fabMessage.setVisibility(View.VISIBLE);

        // Show button animations
        fabPhone.startAnimation(showButton);
        fabMessage.startAnimation(showButton);

        // Transform the + to an x
        fabAdd.startAnimation(showX);
    }

    private void hideButtons() {

        fabPhone.setVisibility(View.GONE);
        fabMessage.setVisibility(View.GONE);

        // Show button animations
        fabPhone.startAnimation(hideButton);
        fabMessage.startAnimation(hideButton);

        // Transform the x to a +
        fabAdd.startAnimation(showPlus);
    }
}

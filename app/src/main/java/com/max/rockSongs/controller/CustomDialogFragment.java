package com.max.rockSongs.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.annotation.NonNull;

public class CustomDialogFragment extends DialogFragment
{
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        @NonNull
        String song = getArguments().getString("song");
        String description = getArguments().getString("description");
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder.setTitle(song)
                      .setMessage(description)
                      .setPositiveButton("ОК", null)
                      .create();
    }
}

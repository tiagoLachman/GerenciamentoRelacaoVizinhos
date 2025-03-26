package com.grv;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class DialogUtils {
    private DialogUtils() {

    }
    public static void confirmarAcao(@NonNull Context context, int idMensagem,
                                     DialogInterface.OnClickListener listenerSim,
                                     DialogInterface.OnClickListener listenerNao){

        confirmarAcao(context, context.getString(idMensagem), listenerSim, listenerNao);
    }
    public static void confirmarAcao(@NonNull Context context, @NonNull String mensagem,
                                     DialogInterface.OnClickListener listenerSim,
                                     DialogInterface.OnClickListener listenerNao){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.confirmacao);
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setMessage(mensagem);

        builder.setPositiveButton(R.string.sim, listenerSim);
        builder.setNegativeButton(R.string.nao, listenerNao);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

package hcmute.edu.vn.fastpen.Model;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import hcmute.edu.vn.fastpen.R;


public class LoadingDialog {
    private Activity activity;
    private AlertDialog dialog;

    public LoadingDialog() {
    }

    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    // hiển thị dialog
    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    // tắt dialog
    public void dismissLoadingDialog(){
        dialog.dismiss();
    }
}

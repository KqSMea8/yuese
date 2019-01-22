package com.net.yuesejiaoyou.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;


/**
 * Created by Administrator on 2016/11/3.
 */

public class YDDialog extends Dialog {

    private static TextView tvContent;
    private static TextView btNagetive;
    private static TextView btPositive;

    public YDDialog(Context context) {
        super(context);
    }

    public YDDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected YDDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder {
        private Context context;
        private CharSequence message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(CharSequence message) {
            this.message = message;
            return this;
        }


        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }


        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public Builder setPositiveButton(int positiveButtonText, DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText, DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public YDDialog show(){
            YDDialog dialog = create();
            dialog.show();
            return dialog;
        }

        public YDDialog create() {
            // instantiate the dialog with the custom Theme
            final YDDialog dialog = new YDDialog(context, R.style.Dialog);
            contentView = View.inflate(context, R.layout.tuichudenglu_1152, null);
            dialog.addContentView(contentView, new ViewGroup.LayoutParams(-1, -2));
            tvContent = (TextView) contentView.findViewById(R.id.title);
            btNagetive = contentView.findViewById(R.id.cancel);
            btPositive = contentView.findViewById(R.id.confirm);


            if (!TextUtils.isEmpty(positiveButtonText)) {
                btPositive.setText(positiveButtonText);
                btPositive.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (positiveButtonClickListener != null) {
                            positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        }
                        dialog.dismiss();
                    }
                });
            } else {
                btPositive.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(negativeButtonText)) {
                btNagetive.setText(negativeButtonText);
                btNagetive.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (negativeButtonClickListener != null) {
                            negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                        dialog.dismiss();
                    }
                });
            } else {
                btNagetive.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(message)) {
                tvContent.setText(message);
            }
            dialog.setContentView(contentView);
            return dialog;
        }
    }
}

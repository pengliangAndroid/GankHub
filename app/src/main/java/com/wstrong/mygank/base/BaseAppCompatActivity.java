package com.wstrong.mygank.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * Created by pengl on 2016/9/6.
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity  {

    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(getLayoutId());

        ButterKnife.bind(this);

        this.initToolbar(savedInstanceState);
        this.initViews(savedInstanceState);
        this.initData();
        this.initListener();
    }


    protected abstract int getLayoutId();

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract void initToolbar(Bundle savedInstanceState);

    protected <V extends View> V findView(int id) {
        return (V) this.findViewById(id);
    }

    public void showProgressDialog() {
        showProgressDialog("");
    }

    public void showProgressDialog(String message) {
        showProgressDialog(message,true);
    }

    public void showProgressDialog(String message, boolean cancelable) {
        progressDialog = ProgressDialog.show(getContext(),"",message);
        progressDialog.setCancelable(cancelable);
    }

    public void stopProgressDialog() {
        if(progressDialog != null)
            progressDialog.dismiss();
    }

    public void showToast(String message) {
        Toast toast = Toast.makeText(getContext(),message,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public void showToast(int messageId) {
        showToast(getString(messageId));
    }

    public Context getContext() {
        return this;
    }
}

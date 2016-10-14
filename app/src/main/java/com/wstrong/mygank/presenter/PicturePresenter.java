package com.wstrong.mygank.presenter;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.wstrong.mygank.Constants;
import com.wstrong.mygank.R;
import com.wstrong.mygank.base.mvp.BasePresenter;
import com.wstrong.mygank.presenter.iview.PictureView;
import com.wstrong.mygank.utils.DeviceUtils;
import com.wstrong.mygank.utils.RxUtils;

import java.io.File;
import java.io.FileOutputStream;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by pengl on 2016/9/19.
 */
public class PicturePresenter extends BasePresenter<PictureView> {

    public void downloadImage(Context context,GlideBitmapDrawable drawable,String imageName){
        mCompositeSubscription.add(saveImageToFile(context,drawable,imageName)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        if(mCompositeSubscription != null)
                            mCompositeSubscription.remove(this);
                    }

                    @Override
                    public void onError(Throwable e) {
                            getMvpView().onDownloadImageFail(Constants.UNKNOWN_ERROR);
                    }

                    @Override
                    public void onNext(String filePath) {
                        getMvpView().onDownloadImageSuccess(filePath);
                    }
                })
        );

    }

    public Observable<String> saveImageToFile(final Context context, final GlideBitmapDrawable drawable, final String imageName){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                String dirPath = DeviceUtils.createAPPFolder(context.getResources().getString(R.string.app_name),
                        (Application) context.getApplicationContext(),"download");
                File dirFile = new File(dirPath);
                if(!dirFile.exists()){
                    dirFile.mkdirs();
                }

                String name = null;
                if(TextUtils.isEmpty(imageName)){
                    String curTimeStr = System.currentTimeMillis()+"";
                    name = Base64.encodeToString(curTimeStr.getBytes(),Base64.DEFAULT);
                }else{
                    name = imageName;
                }

                File imageFile = new File(dirFile,name + ".jpg");
                try {
                    FileOutputStream fos = new FileOutputStream(imageFile);
                    drawable.getBitmap().compress(Bitmap.CompressFormat.JPEG,100,fos);

                    fos.close();

                    // 更新相册
                    Uri uri = Uri.fromFile(imageFile);
                    Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                    context.sendBroadcast(scannerIntent);
                    subscriber.onNext(imageFile.getPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).compose(RxUtils.<String>applyIOToMainThreadSchedulers());
    }


    public void shareImage(Context context,GlideBitmapDrawable drawable,String imageName){
        mCompositeSubscription.add(saveImageToFile(context,drawable,imageName)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        if(mCompositeSubscription != null)
                            mCompositeSubscription.remove(this);
                    }

                    @Override
                    public void onError(Throwable e) {
                            getMvpView().onShareImageFail(Constants.UNKNOWN_ERROR);
                    }

                    @Override
                    public void onNext(String filePath) {
                        getMvpView().onShareImageSuccess(Uri.fromFile(new File(filePath)));
                    }
                })
        );


    }

}

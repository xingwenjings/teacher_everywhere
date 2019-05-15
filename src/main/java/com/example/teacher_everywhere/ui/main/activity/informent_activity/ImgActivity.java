package com.example.teacher_everywhere.ui.main.activity.informent_activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.teacher_everywhere.R;
import com.example.teacher_everywhere.bean.UpLoadBean;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ImgActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgs;
    private ImageView img_img;
    private ImageView zhanwei;
    private PopupWindow popupWindow;
    private Button create;
    private Button picture;
    private static final int CAMERA_CODE = 200;
    private static final int ALBUM_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);
        initView();
    }

    private void initView() {
        imgs = (ImageView) findViewById(R.id.imgs);
        img_img = (ImageView) findViewById(R.id.img_img);
        zhanwei = (ImageView) findViewById(R.id.zhanwei);
        imgs.setOnClickListener(this);
        img_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgs:
                Intent intent = new Intent(ImgActivity.this, InformationActivity.class);
                startActivity(intent);
                break;
            case R.id.img_img:
                pop();
                break;
        }
    }

    private void pop() {
        final View inflate = LayoutInflater.from(this).inflate(R.layout.img_popwindow, null);
        create = inflate.findViewById(R.id.create);
        picture = inflate.findViewById(R.id.picture);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
                popupWindow.dismiss();
            }
        });
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takesD();
                popupWindow.dismiss();
            }
        });
        popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(1.0f);
            }
        });

        setBackgroundAlpha(0.5f);//设置屏幕透明度

        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setFocusable(true);// 点击空白处时，隐藏掉pop窗口
        popupWindow.setAnimationStyle(R.style.pwanim);
        popupWindow.showAsDropDown(inflate);

    }



    private void setBackgroundAlpha(float bg) {
        WindowManager.LayoutParams lp = this.getWindow()
                .getAttributes();
        lp.alpha = bg;
        this.getWindow().setAttributes(lp);


    }
    //处理权限
    private void takePhoto() {
        if (ActivityCompat.checkSelfPermission(ImgActivity.this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            ActivityCompat.requestPermissions(ImgActivity.this,
                    new String[]{Manifest.permission.CAMERA}, 200);
        }
    }
    private File mFile;
    private Uri mImageUri;

    private void openCamera() {
        //创建文件用于保存图片
        mFile = new File(ImgActivity.this.getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
        if (!mFile.exists()) {
            try {
                mFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //适配7.0
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mImageUri = Uri.fromFile(mFile);
        } else {
            //第二个参数要和清单文件中的配置保持一致
            mImageUri = FileProvider.getUriForFile(ImgActivity.this, "com.baidu.upload.provider", mFile);
        }

        //启动相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);//将拍照图片存入mImageUri
        startActivityForResult(intent, CAMERA_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    //Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                    // .openInputStream(mImageUri));
                    //img.setImageBitmap(bitmap);

                    //处理照相之后的结果并上传
                    uploadOk(mFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == ALBUM_CODE) {
            //相册
            if (resultCode == RESULT_OK) {
                Uri imageUri = data.getData();
                //处理uri,7.0以后的fileProvider 把URI 以content provider 方式 对外提供的解析方法
                File file = getFileFromUri(imageUri, ImgActivity.this);

                if (file.exists()) {
                    uploadOk(file);
                }
            }
        }
    }
    public File getFileFromUri(Uri uri, Context context) {
        if (uri == null) {
            return null;
        }
        switch (uri.getScheme()) {
            case "content":
                return getFileFromContentUri(uri, context);
            case "file":
                return new File(uri.getPath());
            default:
                return null;
        }
    }
    /**
     * 文件上传
     */
    private void uploadOk(File file) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        //设置上传文件以及文件对应的MediaType类型
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);

        //MultipartBody文件上传
        /**区别：
         * addFormDataPart:   上传key:value形式
         * addPart:  只包含value数据
         */
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//设置文件上传类型
                .addFormDataPart("key", "h1807a")//文件在服务器中保存的文件夹路径
                .addFormDataPart("file", file.getName(), requestBody)//包含文件名字和内容
                .build();

        Request request = new Request.Builder()
                .url("http://yun918.cn/study/public/file_upload.php")
                .post(body)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();

                Gson gson = new Gson();
                final UpLoadBean upLoadBean = gson.fromJson(str, UpLoadBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (upLoadBean != null && upLoadBean.getCode() == 200) {
                            Log.d("lzj", upLoadBean.getData().getUrl());
                            success(upLoadBean);

                        } else {
                            Toast.makeText(ImgActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (permissions[0] == Manifest.permission.CAMERA) {
                openCamera();
            } else if (permissions[0] == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                openAlbum();
            }
        }
    }
    private void takesD() {
        //权限判断
        if (ContextCompat.checkSelfPermission(ImgActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            openAlbum();
        } else {
            ActivityCompat.requestPermissions(ImgActivity.this, new String[]{Manifest.permission
                    .WRITE_EXTERNAL_STORAGE}, 100);
        }
    }
    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, ALBUM_CODE);
    }

    /**
     * 通过内容解析中查询uri中的文件路径
     */
    private File getFileFromContentUri(Uri contentUri, Context context) {
        if (contentUri == null) {
            return null;
        }
        File file = null;
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(contentUri, filePathColumn, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
            cursor.close();

            if (!TextUtils.isEmpty(filePath)) {
                file = new File(filePath);
            }
        }
        return file;
    }
    private void success(UpLoadBean upLoadBean) {
        Toast.makeText(ImgActivity.this, upLoadBean.getRes(), Toast.LENGTH_SHORT).show();
        Glide.with(this).load(upLoadBean.getData().getUrl()).into(zhanwei);

    }
}

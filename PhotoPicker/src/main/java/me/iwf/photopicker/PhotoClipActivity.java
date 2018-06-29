package me.iwf.photopicker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import me.iwf.photopicker.utils.AsyncTaskUtil;
import me.iwf.photopicker.utils.FileUtils;
import me.iwf.photopicker.widget.ClipViewLayout;

import static me.iwf.photopicker.PhotoPicker.EXTRA_TAILOR_CIRCLE;
import static me.iwf.photopicker.PhotoPicker.EXTRA_TAILOR_DEFALUT;
import static me.iwf.photopicker.PhotoPicker.EXTRA_TAILOR_MODE;
import static me.iwf.photopicker.PhotoPicker.EXTRA_TAILOR_RECT;
import static me.iwf.photopicker.PhotoPicker.KEY_SELECTED_PHOTOS;

/**
 * 图片裁剪
 */
public class PhotoClipActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_PATH = "EXTRA_PATH";
    private ClipViewLayout clipViewLayout1;
    private ClipViewLayout clipViewLayout2;
    private TextView btnCancel;
    private TextView btnOk;
    private int mode;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.__picker_photo_clip);
        initView();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
        setTitle(R.string.__picker_title);
        progressBar = findViewById(R.id.progress_bar);
        clipViewLayout1 = findViewById(R.id.clipViewLayout1);
        clipViewLayout2 = findViewById(R.id.clipViewLayout2);
        btnCancel = findViewById(R.id.btn_cancel);
        btnOk = findViewById(R.id.bt_ok);

        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);

        String path = getIntent().getStringExtra(EXTRA_PATH);

        mode = getIntent().getIntExtra(EXTRA_TAILOR_MODE, EXTRA_TAILOR_DEFALUT);
        switch (mode) {
            case EXTRA_TAILOR_CIRCLE:
                clipViewLayout1.setVisibility(View.VISIBLE);
                clipViewLayout2.setVisibility(View.GONE);
                //设置图片资源
                clipViewLayout1.setImageSrc(path);
                break;
            case EXTRA_TAILOR_RECT:
                clipViewLayout1.setVisibility(View.GONE);
                clipViewLayout2.setVisibility(View.VISIBLE);
                //设置图片资源
                clipViewLayout2.setImageSrc(path);
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v == btnCancel) {
            finish();
        } else if (v == btnOk) {
            progressBar.setVisibility(View.VISIBLE);
            generateUriAndReturn();
        }
    }

    /**
     * 生成Uri并且通过setResult返回给打开的activity
     */
    private void generateUriAndReturn() {
        AsyncTaskUtil.execute(new Runnable() {
            @Override
            public void run() {
                //调用返回剪切图
                Bitmap zoomedCropBitmap;
                if (mode == EXTRA_TAILOR_CIRCLE) {
                    zoomedCropBitmap = clipViewLayout1.clip();
                } else {
                    zoomedCropBitmap = clipViewLayout2.clip();
                }
                if (zoomedCropBitmap == null) {
                    Log.e("android", "zoomedCropBitmap == null");
                    return;
                }
                final Uri mSaveUri = Uri.fromFile(new File(getCacheDir(), "cropped_" + System.currentTimeMillis() + ".png"));
                if (mSaveUri != null) {
                    OutputStream outputStream = null;
                    try {
                        outputStream = getContentResolver().openOutputStream(mSaveUri);
                        if (outputStream != null) {
                            zoomedCropBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                        }
                    } catch (IOException ex) {
                        Log.e("android", "Cannot open file: " + mSaveUri, ex);
                    } finally {
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);
                            Intent intent = new Intent();
                            ArrayList<String> selectedPhotos = new ArrayList<>();
                            selectedPhotos.add(FileUtils.getRealFilePathFromUri(PhotoClipActivity.this, mSaveUri));
                            intent.putStringArrayListExtra(KEY_SELECTED_PHOTOS, selectedPhotos);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
                }
            }
        });

    }
}

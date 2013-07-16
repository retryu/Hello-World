package com.example.toshiba;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.method.BaseMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.toshiba.adapter.HairStyleAdapter;
import com.example.toshiba.interfaze.onLineTouchListenner;
import com.example.toshiba.ui.HairStyleItem;
import com.render.AndroidGLDemoRenderer;
import com.render.SimplePlane;
import com.render.TriangleRender;
import com.util.DisplayUtil;
import com.widget.BitMapRenderer;
import com.widget.FaceImage;
import com.widget.HorizontalListView;

public class MainActivity extends Activity implements OnClickListener {

	String localTempImgDir = "img";

	String localTempImgFileName = "temp.jpeg";
	private final String IMAGE_TYPE = "image/*";

	Uri photoUri;
	private Button btnCammer;
	private Button btnGallery;

	private FaceImage imageView;
	final static int CAMERA_RESULT = 0;
	private final int IMAGE_CODE = 0;
	private static int MEDIA_TYPE = 0;
	private GLSurfaceView view;
	BitMapRenderer renderer;
	private SimplePlane plane;
	private HorizontalListView hairStyleLv;

	private Button btnHair;
	private Button btnFace;
	private Button btnEraser;
	private Activity activity;
	onLineTouchListenner onTouch;
	private static final int camer = 2;
	private static final int gallery = 1;
	private String sdCardPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initWidget();
		isSupport2();
		checkSDCard();
	}

	public void checkSDCard() {
		sdCardPath = "/data/data/com.example.toshiba/temp";

		File dir = new File(sdCardPath);
		if (!dir.exists()) {
			dir.mkdir();
		}
	}

	public void isSupport2() {
		final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		final ConfigurationInfo configurationInfo = activityManager
				.getDeviceConfigurationInfo();
		final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

		if (supportsEs2 == true) {
			Log.e("debug", "support");
		} else {
			Log.e("debug", "not  support");
		}

	}

	public void initWidget() {
		activity = this;
		FrameLayout fm = (FrameLayout) findViewById(R.id.FrameLayout_GLview);
		// GLSurfaceView glView = new GLSurfaceView(this);
		// AndroidGLDemoRenderer renderer = new AndroidGLDemoRenderer();
		// glView.setRenderer(renderer);

		DisplayUtil dp = new DisplayUtil(this);
		dp.getWidth();
		dp.getHeigth();

		TriangleRender tr = new TriangleRender();
		AndroidGLDemoRenderer square = new AndroidGLDemoRenderer();
		view = new GLSurfaceView(this);
		view.setRenderer(square);

		fm.addView(view);

		imageView = (FaceImage) findViewById(R.id.Img);
		onTouch = new onLineTouchListenner();
		imageView.setOnLineTouchListenner(onTouch);
		onTouch.setFaceImage(imageView);
		btnCammer = (Button) findViewById(R.id.Btn_Cammer);
		btnCammer.setOnClickListener(this);
		btnGallery = (Button) findViewById(R.id.Btn_Gallery);
		btnGallery.setOnClickListener(this);
		hairStyleLv = (HorizontalListView) findViewById(R.id.Lv_HairStyle);
		HairStyleAdapter hairStyleAdapter = new HairStyleAdapter(this);
		hairStyleAdapter.setHariStyles(getData());
		hairStyleLv.setAdapter(hairStyleAdapter);
		hairStyleLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(activity, "选中了发型" + position, Toast.LENGTH_SHORT)
						.show();
			}
		});

		btnHair = (Button) findViewById(R.id.Btn_Hair);
		btnFace = (Button) findViewById(R.id.Btn_Face);
		btnEraser = (Button) findViewById(R.id.Btn_Eraser);
		btnEraser.setOnClickListener(this);
		btnHair.setOnClickListener(this);
		btnFace.setOnClickListener(this);

	}

	public GLSurfaceView getSurfaceView() {
		view = new GLSurfaceView(this);
		renderer = new BitMapRenderer();
		view.setRenderer(renderer);
		plane = new SimplePlane(1, 1);
		plane.z = 1.7f;
		plane.rx = 0;// Load the texture.
		plane.loadBitmap(BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher));

		// Add the plane to the renderer.
		renderer.addMesh(plane);
		return view;
	}

	@Override
	public void onClick(View v) {

		int id = v.getId();
		switch (id) {
		case R.id.Btn_Cammer:
			openCamemr();
			break;

		case R.id.Btn_Gallery:
			openGallyer();
			break;
		case R.id.Btn_Face:
			imageView.setType(2);
			break;
		case R.id.Btn_Hair:
			imageView.setType(1);
			break;
		case R.id.Btn_Eraser:
			onTouch.clear();
			break;
		}
	}

	public void openGallyer() {
		MEDIA_TYPE = gallery;
		Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
		getAlbum.setType(IMAGE_TYPE);
		startActivityForResult(getAlbum, IMAGE_CODE);
	}

	public void openCamemr() {
		MEDIA_TYPE = camer;
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			try {
				File dir = new File(Environment.getExternalStorageDirectory()
						+ "/" + localTempImgDir);
				if (!dir.exists())
					dir.mkdirs();

				Intent intent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				File f = new File(dir, localTempImgFileName);// localTempImgDir��localTempImageFileName���Լ����������
				Uri u = Uri.fromFile(f);
				intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
				startActivityForResult(intent, 0);
			} catch (ActivityNotFoundException e) {
				// TODO Auto-generated catch block
			}
		} else {
			Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		ContentResolver resolver = getContentResolver();
		Log.e("debug", "media_type" + MEDIA_TYPE);
		if (MEDIA_TYPE == camer) {
			if (resultCode == RESULT_OK) {
				switch (requestCode) {
				case 0:
					File f = new File(Environment.getExternalStorageDirectory()
							+ "/" + localTempImgDir + "/"
							+ localTempImgFileName);
					try {
						Uri u = Uri
								.parse(android.provider.MediaStore.Images.Media
						  				.insertImage(getContentResolver(),
												f.getAbsolutePath(), null, null));
						try {
							Bitmap bm = MediaStore.Images.Media.getBitmap(
									this.getContentResolver(), u);
							imageView.setImageBitmap(bm);

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// imageView.setImageURI(u);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
			}
		}
		if (MEDIA_TYPE == gallery) {
			Bitmap bm = null;

			if (requestCode == IMAGE_CODE) {
				try {
					Uri originalUri = data.getData();
					bm = MediaStore.Images.Media.getBitmap(resolver,
							originalUri);
					imageView.setImageBitmap(bm);

					File myCaptureFile = new File(sdCardPath + "/galery"
							+ ".jpg");
					BufferedOutputStream bos = new BufferedOutputStream(
							new FileOutputStream(myCaptureFile));
					bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
					bos.flush();
					bos.close();
					// plane.loadBitmap(bm);
					// renderer.addMesh(plane);

					//
					// String[] proj = {MediaStore.Images.Media.DATA};
					//
					// //������android��ý����ݿ�ķ�װ�ӿڣ�����Ŀ�Android�ĵ�
					// Cursor cursor = managedQuery(originalUri, proj, null,
					// null, null);
					// //���Ҹ������ ����ǻ���û�ѡ���ͼƬ������ֵ
					// int column_index =
					// cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					// //�����������ͷ ���������Ҫ����С�ĺ���������Խ��
					// cursor.moveToFirst();
					// //���������ֵ��ȡͼƬ·��
					// String path = cursor.getString(column_index);
				} catch (Exception e) {
					Log.e("debug", e.toString());
				}
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	public List<HairStyleItem> getData() {

		List<HairStyleItem> hairStyleItems = new ArrayList<HairStyleItem>();
		final int style_1 = R.drawable.style_1;
		final int style_2 = R.drawable.style_2;
		final int style_3 = R.drawable.style_3;
		final int style_4 = R.drawable.style_4;
		final int style_5 = R.drawable.style_5;
		final int style_6 = R.drawable.style_6;
		final int style_7 = R.drawable.style_7;
		final int style_8 = R.drawable.style_8;
		HairStyleItem hairStyleItem = new HairStyleItem();
		hairStyleItem.setImgRes(style_1);
		hairStyleItems.add(hairStyleItem);

		hairStyleItem = new HairStyleItem();
		hairStyleItem.setImgRes(style_2);
		hairStyleItems.add(hairStyleItem);

		hairStyleItem = new HairStyleItem();
		hairStyleItem.setImgRes(style_3);
		hairStyleItems.add(hairStyleItem);

		hairStyleItem = new HairStyleItem();
		hairStyleItem.setImgRes(style_4);
		hairStyleItems.add(hairStyleItem);

		hairStyleItem = new HairStyleItem();
		hairStyleItem.setImgRes(style_5);
		hairStyleItems.add(hairStyleItem);

		hairStyleItem = new HairStyleItem();
		hairStyleItem.setImgRes(style_6);
		hairStyleItems.add(hairStyleItem);

		hairStyleItem = new HairStyleItem();
		hairStyleItem.setImgRes(style_7);
		hairStyleItems.add(hairStyleItem);

		hairStyleItem = new HairStyleItem();
		hairStyleItem.setImgRes(style_8);
		hairStyleItems.add(hairStyleItem);

		return hairStyleItems;

	}

}

package com.appoena.mobilenote.screens;

import com.appoena.mobilenote.R;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class ActivityEscolherImagem extends Activity{
	
	private ImageView imageView;
	private Button buttonNewPic;
	private Button buttonImage;
	private Button buttonCancelar;
	private Button buttonOk;
	
	private String filePath;
	private Bitmap image;
	
	private static final int IMAGE_PICK 	= 1;
	private static final int IMAGE_CAPTURE 	= 2;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_imagem);
        
        this.imageView 		= (ImageView) this.findViewById(R.id.imageView_escolher_galeria);
        this.buttonNewPic 	= (Button) this.findViewById(R.id.btn_escolher_camera);
        this.buttonImage 	= (Button) this.findViewById(R.id.btn_escolher_galeria);
        this.buttonCancelar 	= (Button) this.findViewById(R.id.btn_escolher_cancelar);
        this.buttonOk 	= (Button) this.findViewById(R.id.btn_escolher_ok);
        
        this.buttonImage.setOnClickListener(new ImagePickListener());
        this.buttonNewPic.setOnClickListener(new TakePictureListener());
        
        
        this.buttonCancelar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				finish();
			}
		});
        
        this.buttonOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
						    				
				Bundle params = new Bundle();
				params.putString("filePathImage", filePath);
				
				getIntent().replaceExtras(params);
				setResult(RESULT_OK , getIntent());
				finish();
				
			}
		});		
    }
    
    /**
     * Receive the result from the startActivity
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    	if (resultCode == Activity.RESULT_OK) { 
	    	switch (requestCode) {
			case IMAGE_PICK:	
				this.imageFromGallery(resultCode, data);
				break;
			case IMAGE_CAPTURE:
				this.imageFromCamera(resultCode, data);
				break;
			default:
				break;
			}
    	}
    }
    
    /**
     * Update the imageView with new bitmap
     * @param newImage
     */
    private void updateImageView(Bitmap newImage) {
    	
    	this.imageView.setImageBitmap(newImage);
    }
    
    /**
     * Image result from camera
     * @param resultCode
     * @param data
     */
    private void imageFromCamera(int resultCode, Intent data) {
    	this.getFilePath(data); //recupera o caminho do arquivo
    	this.updateImageView((Bitmap) data.getExtras().get("data"));
    }
    
    /**
     * Image result from gallery
     * @param resultCode
     * @param data
     */
    private void imageFromGallery(int resultCode, Intent data) {
    	
    	String filePath = this.getFilePath(data);
    	this.updateImageView(BitmapFactory.decodeFile(filePath));
    }
    
    private String getFilePath(Intent data){
    	Uri selectedImage = data.getData();
    	String [] filePathColumn = {MediaStore.Images.Media.DATA};
    	
    	Log.i("WebView" , "ESCOLHER IMAGEM ---> " + filePathColumn.length);
    	Log.i("WebView" , "ESCOLHER IMAGEM ---> " + filePathColumn.toString());
    	
    	Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
    	cursor.moveToFirst();
    	
    	int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
    	filePath = cursor.getString(columnIndex);
    	cursor.close();
    	    	    	
    	return filePath;
    }
    
    /**
     * Click Listener for selecting images from phone gallery
     * @author tscolari
     *
     */
    class ImagePickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			intent.setType("image/*");
			startActivityForResult(Intent.createChooser(intent, "Escolha uma Foto"), IMAGE_PICK);
			
		}
    }
    
    /**
     * Click listener for taking new picture
     * @author tscolari
     *
     */
    class TakePictureListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, IMAGE_CAPTURE);
			
		}
    }
}
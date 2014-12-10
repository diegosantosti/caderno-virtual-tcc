package com.appoena.mobilenote.screens;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

//import com.appoena.mobilenote.util.BitmapProcessor;

import com.appoena.mobilenote.R;
import com.appoena.mobilenote.util.StorageUtils;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
	
	private Bitmap image;
	
	private String filePath;
	
	private String caminho;
	private Bundle params;
	
	private static final int IMAGE_PICK 	= 1;
	private static final int IMAGE_CAPTURE 	= 2;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_imagem);
        
        Intent it = getIntent();
		params = it.getExtras();
		caminho = params.getString("caminhoCadernoMateria");
        
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
				
				//Verifica se o caminho foi passado, se sim então salva a imagem original dentro da pasta do app.
				if(!filePath.isEmpty()){
					filePath = moveImageToMobileNote(filePath);
				}
				
				Bundle params = new Bundle();
				params.putString("filePathImage", filePath);
				
				getIntent().replaceExtras(params);
				setResult(RESULT_OK , getIntent());
				finish();
				
			}
			
			//método responsável por copiar todas as imagens selecionadas para dentro do app
			private String moveImageToMobileNote(String filePath) {
				// TODO Auto-generated method stub
				try {
//					File file=new File(Environment.getExternalStorageDirectory()+caminho);
				    File sd = Environment.getExternalStorageDirectory();
				    
				    if (sd.canWrite()) {
				    	
				        String sourceImagePath= filePath;
				        String destinationImagePath= caminho+"/imagens/"+StorageUtils.getNomeImagem(caminho+"/imagens");
				        File source= new File(sourceImagePath);
				        File destination= new File(Environment.getExternalStorageDirectory()+destinationImagePath);				        
				        if (source.exists()) {
				            @SuppressWarnings("resource")
							FileChannel src = new FileInputStream(source).getChannel();
				            @SuppressWarnings("resource")
							FileChannel dst = new FileOutputStream(destination).getChannel();
				            dst.transferFrom(src, 0, src.size());
				            src.close();
				            dst.close();
				            
				            //Sobrescreve o caminho original com o caminho gerado 
				            filePath = destination.getAbsolutePath();
				            
				        }
				    }
				} catch (Exception e) {
					
				}
				return filePath;
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
    	
//    	BitmapProcessor bitmapProcessor = new BitmapProcessor(newImage, 1000, 1000, 0);
//    	this.image = bitmapProcessor.getBitmap();
//    	this.imageView.setImageBitmap(this.image);
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
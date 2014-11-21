package com.appoena.mobilenote.screens;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.appoena.mobilenote.R;
import com.appoena.mobilenote.util.NotificationUtils;
import com.appoena.mobilenote.util.StorageUtils;
import com.skd.androidrecording.audio.AudioRecordingHandler;
import com.skd.androidrecording.audio.AudioRecordingThread;
import com.skd.androidrecording.visualizer.VisualizerView;
import com.skd.androidrecording.visualizer.renderer.BarGraphRenderer;

public class ActivityGravarAudio extends Activity{
	
	private static String fileName = null;
    
	private Button recordBtn;
	private VisualizerView visualizerView;
	private Bundle params;
	private String caminho;
	
	private AudioRecordingThread recordingThread;
	private boolean startRecording = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio_record);
		
		Intent it = getIntent();
		params = it.getExtras();
		caminho = params.getString("caminhoCadernoMateria");
		
		if (!StorageUtils.checkExternalStorageAvailable()) {
			NotificationUtils.showInfoDialog(this, getString(R.string.noExtStorageAvailable));
			return;
		}
//		fileName = StorageUtils.getFileName(true);
		fileName = StorageUtils.getFileName(true,caminho);
		
		recordBtn = (Button) findViewById(R.id.btnGravarAudio);
		recordBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				record();
			}
		});
				
		visualizerView = (VisualizerView) findViewById(R.id.visualizerView);
		setupVisualizer();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		recordStop();
	}
	
	@Override
	protected void onDestroy() {
		recordStop();
		releaseVisualizer();
		
		super.onDestroy();
	}
	
	private void setupVisualizer() {
		Paint paint = new Paint();
        paint.setStrokeWidth(5f);
        paint.setAntiAlias(true);
        paint.setColor(Color.argb(200, 227, 69, 53));
        BarGraphRenderer barGraphRendererBottom = new BarGraphRenderer(2, paint, false);
        visualizerView.addRenderer(barGraphRendererBottom);
	}
	
	private void releaseVisualizer() {
		visualizerView.release();
		visualizerView = null;
	}
	
	private void record() {
        if (startRecording) {
        	recordStart();
        }
        else {
        	recordStop();
        }
	}
	
	private void recordStart() {
		startRecording();
    	startRecording = false;
    	recordBtn.setText(R.string.stopRecordBtn);
    	
	}
	
	private void recordStop() {
		stopRecording();
		startRecording = true;
    	recordBtn.setText(R.string.btnGravarAudio);
	}
	
	private void startRecording() {
		Log.i("WebView","START RECORDING  --> " + fileName);
	    recordingThread = new AudioRecordingThread(fileName, new AudioRecordingHandler() {
			@Override
			public void onFftDataCapture(final byte[] bytes) {
				runOnUiThread(new Runnable() {
					public void run() {
						if (visualizerView != null) {
							visualizerView.updateVisualizerFFT(bytes);
						}
					}
				});
			}

			@Override
			public void onRecordSuccess() {
			}

			@Override
			public void onRecordingError() {
				runOnUiThread(new Runnable() {
					public void run() {
						recordStop();
						NotificationUtils.showInfoDialog(ActivityGravarAudio.this, getString(R.string.recordingError));
					}
				});
			}

			@Override
			public void onRecordSaveError() {
				runOnUiThread(new Runnable() {
					public void run() {
						recordStop();
						NotificationUtils.showInfoDialog(ActivityGravarAudio.this, getString(R.string.saveRecordError));
					}
				});
			}
		});
	    recordingThread.start();
    }
    
    private void stopRecording() {
    	if (recordingThread != null) {
    		recordingThread.stopRecording();
    		recordingThread = null;
    		
    		//FINALIZA A ACTIVITY E VOLTA PARA A ACTIVITY QUE CHAMOU PASSANDO O CAMINHO E NOME DO ARQUIVO
    		
    		Bundle params = new Bundle();
			params.putString("caminhoAudio", fileName);
			getIntent().replaceExtras(params);
			setResult(RESULT_OK , getIntent());
			finish();
    		
	    }
    }

//    private void play() {
//		Intent i = new Intent(AudioRecordingActivity.this, AudioPlaybackActivity.class);
//		i.putExtra(VideoPlaybackActivity.FileNameArg, fileName);
//		startActivityForResult(i, 0);
//	}

}

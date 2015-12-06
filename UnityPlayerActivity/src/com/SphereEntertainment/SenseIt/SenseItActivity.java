package com.SphereEntertainment.SenseIt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SenseItActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sense_it);
		
		Button verVideo = (Button) findViewById(R.id.verVideo);
		verVideo.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		EditText nombreDeVideo = (EditText) findViewById(R.id.nombreVideo);
		EditText cantidadDeCuadros = (EditText) findViewById(R.id.cantidadDeCuadros);
		
		UnityPlayerActivity.sensationName = nombreDeVideo.getText().toString();
		UnityPlayerActivity.sensationFramesSize = cantidadDeCuadros.getText().toString();
		Intent lanzable =  new Intent(this, UnityPlayerActivity.class);
		startActivity(lanzable);
	}
}
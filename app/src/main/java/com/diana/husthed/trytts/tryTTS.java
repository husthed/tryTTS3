package com.diana.husthed.trytts;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class tryTTS extends AppCompatActivity  implements TextToSpeech.OnInitListener {

	private int MY_DATA_CHECK_CODE = 0;
	private TextToSpeech tts;
	private EditText inputText;
	private Button speakBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_try_tts);

		inputText = (EditText)findViewById(R.id.inputText);
		speakBtn = (Button)findViewById(R.id.speakBtn);
		speakBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String text = inputText.getText().toString();
				if (text != null && text.length() > 0) {
					Toast.makeText(tryTTS.this, "Saying: " + text, Toast.LENGTH_LONG).show();
					tts.speak(text, TextToSpeech.QUEUE_ADD, null);
				}
			}
		});

		Intent checkIntent = new Intent();
		checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == MY_DATA_CHECK_CODE) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				// success, create tts instance
				tts = new TextToSpeech(this, this);
			} else {
				// miss data, install it
				Intent installIntent = new Intent();
				installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivityForResult(installIntent, resultCode);
			}
		}
	}

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			Toast.makeText(tryTTS.this, "Text-to-Speech engine is initialized", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(tryTTS.this, "Error occured while initializing Text-to-Speech engine", Toast.LENGTH_LONG).show();

		}
	}
}

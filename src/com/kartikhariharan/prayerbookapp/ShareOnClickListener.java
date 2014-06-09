package com.kartikhariharan.prayerbookapp;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class ShareOnClickListener implements OnClickListener {
	
	Context context;
	String textToShare;
	
	public ShareOnClickListener(Context context, String textToShare) {
		
		this.context = context;
		this.textToShare = textToShare;
		
	}

	@Override
	public void onClick(View v) {	
		
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, textToShare);
		sendIntent.setType("text/plain");
		//startActivity(Intent.createChooser(sendIntent, context.getResources().getText(textToShare)));
		context.startActivity(Intent.createChooser(sendIntent, "Share prayer..."));

	}

}

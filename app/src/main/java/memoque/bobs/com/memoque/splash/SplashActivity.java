package memoque.bobs.com.memoque.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import memoque.bobs.com.memoque.R;
import memoque.bobs.com.memoque.title.TitleActivity;

public class SplashActivity extends Activity
{
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		requestWindowFeature( Window.FEATURE_NO_TITLE );
		setContentView( R.layout.activity_splash );

		Handler handler = new Handler(  );
		handler.postDelayed( new Runnable()
		{
			@Override
			public void run()
			{
				startActivity( new Intent( SplashActivity.this, TitleActivity.class ) );
				overridePendingTransition( R.anim.slide_in, R.anim.slide_out );

				finish();
				overridePendingTransition( R.anim.slide_enter, R.anim.slide_exit );
			}
		}, 1000 );
	}
}
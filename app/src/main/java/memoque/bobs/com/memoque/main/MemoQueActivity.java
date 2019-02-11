package memoque.bobs.com.memoque.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import memoque.bobs.com.memoque.R;
import memoque.bobs.com.memoque.main.memo.MemoFragment;
import memoque.bobs.com.memoque.main.search.SearchFragment;
import memoque.bobs.com.memoque.main.setting.SettingFragment;

public class MemoQueActivity extends AppCompatActivity
{
	private final long FINISH_INTERVAL_TIME = 2000;
	private       long backPressedTime      = 0;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );

		// 뷰 페이져 생성
		ViewPager viewPager = findViewById( R.id.viewPager );
		viewPager.setAdapter( new FragmentPagerAdapter( getSupportFragmentManager() )
		{
			@Override
			public int getCount()
			{
				String[] tabs = getResources().getStringArray( R.array.tabnames );
				return tabs.length;
			}

			@Override
			public Fragment getItem( int i )
			{
				switch( i ) {
					case 0:
						return new MemoFragment();

					case 1:
						return new SearchFragment();

					case 2:
						return new SettingFragment();
				}
				return null;
			}

			@Nullable
			@Override
			public CharSequence getPageTitle( int position )
			{
				return getResources().getStringArray( R.array.tabnames )[position];
			}
		} );

		// 탭에 뷰 페이져 연결
		TabLayout tabLayout = findViewById( R.id.tabLayout );
		tabLayout.setupWithViewPager( viewPager );
	}

	@Override
	public void onBackPressed()
	{
		// 백키 두번 터치할경우 꺼지도록 한다
		long tempTime = System.currentTimeMillis();
		long intervalTime = tempTime - backPressedTime;

		// 첫 백키를 터치한지 2초 내에 백키를 터치하면 앱을 종료하고 아니면 토스트를 띄운다
		if( intervalTime >= 0 && intervalTime <= FINISH_INTERVAL_TIME )
			finish();
		else {
			backPressedTime = tempTime;
			Toast.makeText( this, getResources().getString( R.string.main_backpressed ), Toast.LENGTH_SHORT ).show();
		}
	}
}
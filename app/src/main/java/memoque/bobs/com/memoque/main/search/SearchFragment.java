package memoque.bobs.com.memoque.main.search;


import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import memoque.bobs.com.memoque.R;
import memoque.bobs.com.memoque.main.MemoQueManager;
import memoque.bobs.com.memoque.main.MemoQueManager.Adapterkey;
import memoque.bobs.com.memoque.main.adapter.SearchAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment
{
	@Override
	public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
	{
		// Inflate the layout for this fragment
		View view = inflater.inflate( R.layout.fragment_search, container, false );
		if( null == view )
			return null;

		final EditText searchEdit = view.findViewById( R.id.search_editText );

		Button searchbutton = view.findViewById( R.id.btn_search );
		searchbutton.setOnClickListener( new OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				String searchText = searchEdit.getText().toString();

				if( TextUtils.isEmpty( searchText ) )
					Toast.makeText( getContext(), R.string.search_empty_text, Toast.LENGTH_SHORT ).show();
				else {
					if( !MemoQueManager.Companion.getInstance().memosSearch( Adapterkey.SEARCH, searchText ) )
						Toast.makeText( getContext(), R.string.search_disable_memo, Toast.LENGTH_SHORT ).show();
				}
			}
		} );

		ImageButton calendarButton = view.findViewById( R.id.calendar_button );
		calendarButton.setOnClickListener( new OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				DateTime now = new DateTime();

				DatePickerDialog datePickerDialog = new DatePickerDialog( Objects.requireNonNull( getContext() ), new OnDateSetListener()
				{
					@Override
					public void onDateSet( DatePicker view, int year, int month, int dayOfMonth )
					{
						DateTime searchDate = new DateTime( year, month + 1, dayOfMonth, 0, 0, 0 );

						String searchDateText = searchDate.toString( "yyyy/MM/dd" );
						searchEdit.setText( searchDateText );

						if( !MemoQueManager.Companion.getInstance().memosSearch( Adapterkey.SEARCH, searchDateText ) )
							Toast.makeText( getContext(), R.string.search_disable_memo, Toast.LENGTH_SHORT ).show();
					}
				}, now.getYear(), now.getMonthOfYear() - 1, now.getDayOfMonth() );

				datePickerDialog.show();
			}
		} );

		RecyclerView recyclerView = view.findViewById( R.id.search_recyclerView );
		SearchAdapter searchAdapter = new SearchAdapter();
		searchAdapter.initData();
		recyclerView.setAdapter( searchAdapter );
		recyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );

		return view;
	}
}

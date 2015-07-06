package vocab.sat;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import vocab.sat.data.MyDB;
import  vocab.sat.data.Constants;


public class SelectAWordList extends ListActivity implements OnClickListener {
	MyDB dba;
	WordListAdapter myAdapter;
	Button createNewList;
	private class MyWordList{
		public MyWordList(String t)
		{
			title =t;
			
		}
		public String title;
		public int content;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState){
		dba = new MyDB(this);
		dba.open();
		setContentView(R.layout.wordlist);
		createNewList = (Button) findViewById(R.id.create_list);
		super.onCreate(savedInstanceState);
		myAdapter = new WordListAdapter(this);
		this.setListAdapter(myAdapter);
		createNewList.setOnClickListener(this);
		ListView lv = getListView();
		  lv.setTextFilterEnabled(true);
		  final Intent i = new Intent(this, WordList.class);
		  lv.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view,
		        int position, long id) {
		      Bundle b = new Bundle();
		      b.putString("name", myAdapter.getItem(position).title);
		      i.putExtras(b);
	            startActivity(i);
		    }
		  });
	}
	public class WordListAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private ArrayList<MyWordList> MyWordLists;
		public WordListAdapter(Context context){
			mInflater = LayoutInflater.from(context);
			MyWordLists = new ArrayList<MyWordList>();
			MyWordLists.add(new MyWordList("All Words"));
			getData();
		}
		public void getData(){
			Cursor c = dba.getUserCreatedList();
			startManagingCursor(c);
			String previous ="x.x";
			if(c.moveToFirst()){
				do{
					String title = c.getString(c.getColumnIndex(Constants.TITLE_NAME));
					String content = c.getString(c.getColumnIndex(Constants.CONTENT_NAME));
					if(title.equals(previous)==false)
					{
						MyWordLists.add(new MyWordList(title));
					}
					previous = title;
					
				}while(c.moveToNext());
			}
			dba.close();
		}

		@Override
		public int getCount(){return MyWordLists.size();}
		public MyWordList getItem(int i){return MyWordLists.get(i);}
		public long getItemId(int i){return i;}
		public View getView(int arg0, View arg1, ViewGroup arg2){
			final ViewHolder holder;
			View v = arg1;
			if((v==null)||(v.getTag()==null)){
				v=mInflater.inflate(R.layout.list_row_plain, null);
				holder = new ViewHolder();
				holder.mTitle =(TextView)v.findViewById(R.id.list_title);
				v.setTag(holder);
			}else{
				holder = (ViewHolder) v.getTag();
			}
			holder.mList = getItem(arg0);
			holder.mTitle.setText(holder.mList.title);

			
			v.setTag(holder);
			return v;
			
		}
		public class ViewHolder{
			MyWordList mList;
			TextView mTitle;
			
		}

	}
	@Override
	public void onClick(View v) {
		 switch (v.getId()) {
	      case R.id.create_list:
	    	  Intent a = new Intent(this, CreateWordList.class);
	            startActivity(a);
	    	 break;
	    
	    
	      
	      }
		
	}

}

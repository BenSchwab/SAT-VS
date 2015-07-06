package vocab.sat;

import java.util.ArrayList;
import java.util.Arrays;


import vocab.sat.data.MyDB;
import vocab.sat.data.Constants;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/** This class is used to select a word list.
 *   It will call a list view with all words in selected list that will then call a study card. **/

public class SelectWordList extends ListActivity {
	MyDB dba;
	private ArrayList<String> lists = new ArrayList<String>(Arrays.asList("All Words"));
	
	
	public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  
		  dba = new MyDB(this);
		  dba.open();
		  getData();
		  setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lists));
		  
		  ListView lv = getListView();
		  lv.setTextFilterEnabled(true);
		  final Intent intent = new Intent(this, WordList.class);
		  lv.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view,
		        int position, long id) {
		    	 Bundle b = new Bundle();
		    	 String selectedList = lists.get(position);
				 b.putString("name",selectedList);
				 intent.putExtras(b);
		    	
	            startActivity(intent);
		    }
		  });
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
					lists.add(title);
				}
				previous = title;
				
			}while(c.moveToNext());
		}
	}
	
}

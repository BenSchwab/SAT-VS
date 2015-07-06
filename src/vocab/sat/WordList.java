package vocab.sat;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import vocab.sat.data.Constants;
import vocab.sat.data.MyDB;

public class WordList extends ListActivity
{
	MyDB dba;
	private ArrayList<Integer> wordKeys = new ArrayList<Integer>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  dba = new MyDB(this);
		  dba.open();
		  Bundle b = getIntent().getExtras();   
		  
		  
		  final String listName = b.getString("name");
		  ArrayList<Word> words = new ArrayList<Word>();
		  ArrayList<String> wordsToUse = new ArrayList<String>();
		  words = getListWords(listName);
		  
		  for(Word w: words)
		  {
			  wordsToUse.add(w.getName());
		  }
		  
		  setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, wordsToUse));

		  ListView lv = getListView();
		  final Intent i = new Intent(this, StudyCard.class);
		  lv.setTextFilterEnabled(true);
		 
		  lv.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener (){ 
                @Override 
                public boolean onItemLongClick(AdapterView<?> av, View v, int 
                		position, long id) { 
    		    	Bundle sendToStudyCard = new Bundle();
    		    	sendToStudyCard.putString("listName", listName);
    		    	sendToStudyCard.putInt("start", position);
    		    	sendToStudyCard.putIntegerArrayList("wordKey", wordKeys);
    		    	i.putExtras(sendToStudyCard);
    		    	startActivity(i);
                        
                        return false; 
                } 
		  		}); 
		  
		  //implement to display popup quick definition
		  lv.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view,
		        int position, long id) {
		    	
		    }
		  });
		}
	 public ArrayList<Word> getListWords(String listName)
	 {
		 if(listName.equals("All Words"))
		 {
			 return MasterList.allWords;
		 }
		 	ArrayList<Word> wordsInList = new ArrayList<Word>();
		 	Cursor c = dba.getUserCreatedList();
			startManagingCursor(c);
			
			if(c.moveToFirst()){
				do{
					String title = c.getString(c.getColumnIndex(Constants.TITLE_NAME));
					int content = c.getInt(c.getColumnIndex(Constants.CONTENT_NAME));
					if(title.equals(listName))
					{
						wordsInList.add(MasterList.allWords.get(content));
						wordKeys.add(content);
					}
					
					
				}while(c.moveToNext());
			}
			return wordsInList;
			
	 }
		    
}



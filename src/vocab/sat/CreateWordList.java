package vocab.sat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;




import vocab.sat.data.MyDB;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class CreateWordList extends ListActivity {
	MyDB dba;
	WordListAdapter myAdapter;
	private EditText newTitleBox;
	private Button createListButton;
	private ArrayList<Word> Words;
	
	protected void onCreate(Bundle savedInstanceState){
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		
		
		dba = new MyDB(this);
		dba.open();
		setContentView(R.layout.selectable_word_list);
		createListButton = (Button)findViewById(R.id.submit_list);
		newTitleBox = (EditText)findViewById(R.id.new_List_Title);
		createListButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if(newTitleBox.getText().toString().equals(null))
				{
					Toast.makeText(getApplicationContext(), "List not created, please enter title for list.",Toast.LENGTH_LONG).show();
				}
				else{
				try{
					saveItToDB();
				}catch(Exception e){
					e.printStackTrace();
				}
			}

			}
		});
		
		
		super.onCreate(savedInstanceState);
		myAdapter = new WordListAdapter(this);
		this.setListAdapter(myAdapter);
		
	}
	private void saveItToDB() {
		int count =0;
		String display="";
		for(int i=0; i<Words.size(); i++)
		{
			if(Words.get(i).getCheckedState())
			{
				dba.insertUserCreatedList(newTitleBox.getText().toString(), i);
				display+=Words.get(i).getName()+", ";
				count++;
			}
			
			
		}
		if(count ==0)
		{
			Toast.makeText(getApplicationContext(), "List not created, no words selected.",Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(getApplicationContext(), "List  created: "+display,Toast.LENGTH_SHORT).show();
		}
		
		newTitleBox.setText("");
		
		
	}
	private class WordListAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		
		
		public WordListAdapter(Context context){
			mInflater = LayoutInflater.from(context);
			Words = (ArrayList<Word>) MasterList.allWords.clone();
			//getData();
		}
		public int getCount(){return Words.size();}
		public Word getItem(int i){return Words.get(i);}
		public long getItemId(int i){return i;}
		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2){
			final ViewHolder holder;
			final int position =arg0;
			View v = arg1;
			if((v==null)||(v.getTag()==null)){
				v=mInflater.inflate(R.layout.list_row, null);
				holder = new ViewHolder();
				holder.mTitle =(TextView)v.findViewById(R.id.word);
				holder.mCheck =(CheckBox)v.findViewById(R.id.select_word);
				holder.mCheck.setOnCheckedChangeListener(new OnCheckedChangeListener()
				{
				    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
				    {
				    	//Log.e(""+holder.mWord.getName(),"check change listener called");
				    		holder.mWord.setChecked(isChecked);
				        
				        	
				            //Log.e(""+holder.mWord.getName(),""+holder.mWord.getCheckedState());
				            //Log.e(""+holder.mWord.getName(),""+holder.mWord.getCheckedState());
				        
				        

				    }
				});
				
				v.setTag(holder);
				holder.mCheck.setTag(position);
			}else{
				holder = (ViewHolder) v.getTag();
				
				
			}
			holder.mWord = (Word) getItem(arg0);
			holder.mTitle.setText(holder.mWord.getName());
			holder.mCheck.setChecked(holder.mWord.getCheckedState());
			//Log.e("nt "+holder.mWord.getName(),""+Words.get(position).getCheckedState());
			
			
			
			
			
			
			v.setTag(holder);
			return v;
			
		}
		
		public class ViewHolder{
			Word mWord;
			TextView mTitle;
			CheckBox mCheck;
		
			
		}

	}
	public void onResume()//potentially buggy code
	{
		if(MasterList.allWords==null)
		{

	    	String word;
	    	String quickDef;
	    	String fullDef;
	    	
	        Scanner sc;
			try {
				sc = new Scanner(getAssets().open("wordlist.txt"));
				while(sc.hasNext())
		    	{
		    		word = sc.nextLine();
		    		quickDef = sc.nextLine();
		    		fullDef = sc.nextLine();
		    		MasterList.allWords.add(new Word(word, quickDef,fullDef));
		    		//Log.e("t",""+fileWords.size());
		    		
		    	}
			} catch (IOException e) {
				Log.e("NF","File not found");
				e.printStackTrace();
			}
		}
		super.onResume();
	}
	


}

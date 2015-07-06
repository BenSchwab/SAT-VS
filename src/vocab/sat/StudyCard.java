package vocab.sat;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
//creates a study activity which calls different card views
	
public class StudyCard extends Activity implements OnClickListener, OnTouchListener{
	
	private TextView word;
	private TextView progressBar;
	private TextView quickDefinition;
	private TextView longDefinition;
	//private Spinner listSelect;
	private ToggleButton definitionToggle;
	private Button showButton;
	private CheckBox saveWordForLater;
	public static int start;
	private ArrayList<Integer> wordsKey;
	private String listTitle =".xxx.";
	private int position;
	private ArrayList<Word> selectedWordSet = new ArrayList<Word>();//use this to implement multiple sets of words, and word deletions as studying
	
	public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        start = b.getInt("start", 0 );
        wordsKey= b.getIntegerArrayList("wordKey");
        listTitle = b.getString("listName");
        if(listTitle.equals("All Words"))
        {
        	selectedWordSet=MasterList.allWords;
        }
        else
        {
        	for(Integer i: wordsKey)
        	{
        		selectedWordSet.add(MasterList.allWords.get(i));
        	}
        }
        Log.e("SAT",listTitle);
       
        position = start;
        setContentView(R.layout.study_card);
        selectedWordSet.size();
        //Find Views
        
        View next = findViewById(R.id.next_button);
        next.setOnClickListener(this);
        View previous = findViewById(R.id.previous_button);
        previous.setOnClickListener(this);
        
        progressBar = (TextView) findViewById(R.id.progressbar);
        progressBar.setText(position+1+"/"+selectedWordSet.size());
        showButton = (Button) findViewById(R.id.definition_hider);
        showButton.setOnTouchListener(this);
        word = (TextView) findViewById(R.id.vocab_word);
        //listSelect = (Spinner) findViewById(R.id.list_spinner);
        definitionToggle = (ToggleButton) findViewById(R.id.showDefinition);
        quickDefinition = (TextView) findViewById(R.id.quick_def);
        longDefinition = (TextView) findViewById(R.id.full_def);
        saveWordForLater = (CheckBox) findViewById(R.id.saveWordForLater);
        definitionToggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(definitionToggle.isChecked()){
	  	    		  quickDefinition.setVisibility(View.INVISIBLE);
	  	    		  longDefinition.setVisibility(View.INVISIBLE);
	  	    		Toast.makeText(getApplicationContext(), "Definitions hiden",
	  			          Toast.LENGTH_SHORT).show();
	  	    	  }
	  	    	  else{
	  	    		  
	  	    		  quickDefinition.setVisibility(View.VISIBLE);
	  	    		  longDefinition.setVisibility(View.VISIBLE);
	  	    		Toast.makeText(getApplicationContext(), "Definitions shown",
		  			          Toast.LENGTH_SHORT).show();
	  	    	  }
				
			}
			
        });
        
        updateWord(position);

    }

	@Override
	public void onClick(View v) {
		if(saveWordForLater.isChecked()==true)
		{
			saveWordForLater.setChecked(false);
			if(selectedWordSet.size()==1)
			{
				
			}
			selectedWordSet.remove(position);
			
			switch (v.getId()) {
		      case R.id.next_button:
		    	 if(position==selectedWordSet.size())
		    	 {
		    		 position = 0;
		    	 }
		         updateWord(position);
		         
		         break;
		         // ...
		      case R.id.previous_button:
		    	  if(position-1<0)
			    	 {
			    		 position = selectedWordSet.size();
			    	 }
		    	  position--;
		         updateWord(position);
		         
		         break;
		      }
			
		}
		else
		{
			
	      switch (v.getId()) {
	      case R.id.next_button:
	    	 if(position+1==selectedWordSet.size())
	    	 {
	    		 position = -1;
	    	 }
	         updateWord(position+1);
	         position++;
	         break;
	         // ...
	      case R.id.previous_button:
	    	  if(position-1<0)
		    	 {
		    		 position = selectedWordSet.size();
		    	 }
	         updateWord(position-1);
	         position-=1;
	         break;
	      }
	    	  
	      
	      }
	   }
	public void updateWord(int position)
	{
		Word w= selectedWordSet.get(position);
		word.setText(w.getName());
		quickDefinition.setText(w.getQuickDef());
		longDefinition.setText(w.getFullDef());
		progressBar.setText(position+1+"/"+selectedWordSet.size());
		
	
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
	
		int action = event.getAction();
		if(action == MotionEvent.ACTION_DOWN)
		{
			if(definitionToggle.isChecked()){
	    		  quickDefinition.setVisibility(View.VISIBLE);
	    		  longDefinition.setVisibility(View.VISIBLE);
	    		 
	    	  }
		}
		if(action==MotionEvent.ACTION_UP)
		{
			if(definitionToggle.isChecked()){
	    		  quickDefinition.setVisibility(View.INVISIBLE);
	    		  longDefinition.setVisibility(View.INVISIBLE);
	    	  }
		}
		return false;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  { //issue has to do with cloning and deletion
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	    	Log.e("Before Repop",""+selectedWordSet.size());
	    	Log.e("Before Repop ListT",""+listTitle);
	    	Log.e("Before Repop ListT",""+(listTitle.equals("All Words")));
	    	selectedWordSet= new ArrayList<Word>();
	    	Log.e("After reset",""+selectedWordSet.allWords.size());
	    	 
	    	 if(listTitle.equals("All Words"))
	         {
	    		 Log.e("Inside refill",""+MasterList.allWords.size());
	         	selectedWordSet=MasterList.allWords;
	         }
	         else
	         {
	         	for(Integer i: wordsKey)
	         	{
	         		selectedWordSet.add(MasterList.allWords.get(i));
	         	}
	         } 
	    	 Log.e("After Repop",""+selectedWordSet.size());
	    }

	    return super.onKeyDown(keyCode, event);
	} 
	

}

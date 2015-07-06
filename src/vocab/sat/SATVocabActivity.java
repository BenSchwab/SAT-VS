package vocab.sat;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;


public class SATVocabActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	public ArrayList<Word> masterList = new ArrayList<Word>();
	public static ArrayList<Word> fileWords = new ArrayList<Word>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createMasterWordList();
        addWords();
        //Log.d("",MasterList.allWords.get(0).getName());
        setContentView(R.layout.main);
        
        //Set up click listeners for all the buttons
        View wordListButton = findViewById(R.id.word_list_button);
        wordListButton.setOnClickListener(this); 
        View aboutButton = findViewById(R.id.about_button);
        aboutButton.setOnClickListener(this); 
        View editButton = findViewById(R.id.edit_button);
        editButton.setOnClickListener(this); 
        View studyButton = findViewById(R.id.study_button);
        studyButton.setOnClickListener(this); 
        View exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);
        //exitButton.setOnClickListener(this); 
    }
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.word_list_button:
        	Intent i = new Intent(this, SelectAWordList.class);
            startActivity(i);
            break;
           // ...
        case R.id.edit_button:
        	Intent k = new Intent(this, SelectAWordListToEdit.class);
            startActivity(k);
            break;
        case R.id.study_button:
        	Intent m = new Intent(this, SelectWordList.class);
            startActivity(m);
            break;
        case R.id.about_button:
           Intent j = new Intent(this, About.class);
           startActivity(j);
           break;
        // More buttons go here (if any) ...
        
        case R.id.exit_button:
           finish();
           break;
        }
     }
    public void createMasterWordList()
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
	    		fileWords.add(new Word(word, quickDef,fullDef));
	    		//Log.e("t",""+fileWords.size());
	    		
	    	}
		} catch (IOException e) {
			Log.e("NF","File not found");
			e.printStackTrace();
		}
	    	
	    	
               
          
           
        
        
        
    	
    	
    }
    public static void addWords()
    {
    	MasterList.allWords = fileWords;
    }
}
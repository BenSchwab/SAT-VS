package vocab.sat;


import vocab.sat.data.MyDB;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SelectAWordListToEdit extends SelectAWordList {
	
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
		  final Intent i = new Intent(this, StudyCard.class);
		  lv.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view,
		        int position, long id) {
		      
		    	
	            startActivity(i);
		    }
		  });
	}
}

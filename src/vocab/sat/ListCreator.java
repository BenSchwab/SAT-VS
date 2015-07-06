package vocab.sat;

import vocab.sat.data.MyDB;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ListCreator extends Activity {
	EditText title;
	EditText content;
	Button submit;
	MyDB dba;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newlist);
		dba = new MyDB(this);
		dba.open();
		title = (EditText)findViewById(R.id.editTitle);
		content = (EditText)findViewById(R.id.editContent);
		submit = (Button)findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				try{
					saveItToDB();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		
		
		
	}
	public void saveItToDB(){
		dba.insertdiary(title.getText().toString(), content.getText().toString());
		dba.close();
		title.setText("");
		content.setText("");
	}

}

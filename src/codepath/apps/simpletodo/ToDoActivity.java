package codepath.apps.simpletodo;

// Hey, how come the demo's don't include the import statements?
// That's where I spent most of my time...:)

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.EditText;

import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
import org.apache.commons.io.FileUtils;

public class ToDoActivity extends Activity {

	private ArrayList<String> todoItems;
	private ArrayAdapter<String> todoAdapter;
	private ListView lvItems;
	private EditText etNewItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        lvItems = (ListView) findViewById(R.id.lvitems);
        readItems();
        todoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
        lvItems.setAdapter(todoAdapter);
        setupListViewListener();
    }

    private void setupListViewListener() {
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

    		@Override
    		public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
    			todoItems.remove(pos);
    			todoAdapter.notifyDataSetChanged();
    			writeItems();
    			return true;

    		}
    	});
    }

    private void populateArrayItems() {
    	todoItems = new ArrayList<String>();
    	todoItems.add("Item 1");
    	todoItems.add("Item 2");
    	todoItems.add("Item 3");
    }

    public void onAddedItem(View v) {
    	String itemText = etNewItem.getText().toString();
    	todoAdapter.add(itemText);
    	etNewItem.setText("");
    	writeItems();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.to_do, menu);
        return true;
    }

    private void readItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	// This approach feels ghetto.
    	try {
    		todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
    	} catch (IOException e) {
    		todoItems = new ArrayList<String>();
    	}
    }

    private void writeItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		FileUtils.writeLines(todoFile, todoItems);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}

    }

}
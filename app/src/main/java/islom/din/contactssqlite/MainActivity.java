package islom.din.contactssqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import islom.din.contactssqlite.adapters.ContactsListAdapter;
import islom.din.contactssqlite.db.DBHelper;
import islom.din.contactssqlite.models.Contact;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    TextView activityName;
    ListView listViewOfContacts;
    FloatingActionButton fab;
    TextView textView;
    List<Contact> list;

    // Database
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        activityName = findViewById(R.id.activity_name);
        listViewOfContacts = findViewById(R.id.main_activity__listview_of_contacts);
        fab = findViewById(R.id.main_activity__fab);
        textView = findViewById(R.id.main_activity__no_contacts_message);
        list = new ArrayList<>();

        activityName.setText("Список контактов");
        fab.setOnClickListener(fabListiner);

        initListOfContacts();

        listViewOfContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ContactActivity.class);
                intent.putExtra("contact", list.get(position));
                intent.putExtra("context", getApplicationInfo());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        initListOfContacts();
    }

    View.OnClickListener fabListiner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), NewContactActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    };

    private void initListOfContacts() {
        list.clear();
        Log.d(TAG, "initListOfContacts: =======================================>");
        getDataFromDB(list);

        ContactsListAdapter adapter = new ContactsListAdapter(this, R.layout.adapter_view_layout, list);
        textView.setVisibility(View.GONE);
        listViewOfContacts.setAdapter(adapter);

        if(list.isEmpty())
            textView.setVisibility(View.VISIBLE);
    }

    private void getDataFromDB(List<Contact> list) {
        dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
        Contact contact;

        if(cursor.moveToFirst()) {

            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int lastNameIndex = cursor.getColumnIndex(DBHelper.KEY_LASTNAME);
            int phoneIndex = cursor.getColumnIndex(DBHelper.KEY_PHONE);
            int emailIndex = cursor.getColumnIndex(DBHelper.KEY_EMAIL);

            do {
                contact = new Contact(
                        cursor.getString(idIndex),
                        cursor.getString(nameIndex),
                        cursor.getString(lastNameIndex),
                        cursor.getString(phoneIndex),
                        cursor.getString(emailIndex)
                );
                list.add(contact);
            } while (cursor.moveToNext());

        }
    }
}
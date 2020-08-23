package islom.din.contactssqlite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import islom.din.contactssqlite.db.DBHelper;
import islom.din.contactssqlite.models.Contact;

public class ContactActivity extends AppCompatActivity {

    TextView contactNameLastName;
    TextView contactPhone;
    TextView contactEmail;

    DBHelper dbHelper;
    Contact contact;
    Context mainActivityContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        contactNameLastName = findViewById(R.id.contact_activity__contact_name_lastname);
        contactPhone = findViewById(R.id.contact_activity__contact_phone);
        contactEmail = findViewById(R.id.contact_activity__contact_email);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(backArrowListener);
        Toolbar toolbar = findViewById(R.id.contact_activity__toolbar);
        setSupportActionBar(toolbar);

        Intent mainActivityIntent = getIntent();
        contact = (Contact) mainActivityIntent.getSerializableExtra("contact");
        mainActivityContext = (Context) mainActivityIntent.getSerializableExtra("context");

        fillFields();


        dbHelper = new DBHelper(this);
    }

    @SuppressLint("SetTextI18n")
    private void fillFields() {
        contactNameLastName.setText(contact.getFirstName() + " " + contact.getLastName());
        contactPhone.setText(contact.getPhone());
        contactEmail.setText(contact.getEmail());
    }

    View.OnClickListener backArrowListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
            case R.id.menu_delete:
                SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
                sqLiteDatabase.delete(DBHelper.TABLE_NAME, DBHelper.KEY_ID + "=?",new String[] {contact.getId()});
                sqLiteDatabase.close();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

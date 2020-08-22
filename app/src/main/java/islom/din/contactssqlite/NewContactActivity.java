package islom.din.contactssqlite;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import islom.din.contactssqlite.db.DBHelper;

public class NewContactActivity extends AppCompatActivity {
    private static final String TAG = "NewContactActivity";

    private EditText contactName;
    private EditText contactLastName;
    private EditText contactPhone;
    private EditText contactEmail;
    private Button addButton;
    private TextView activityName;

    // DataBase
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcontact);

        contactName = findViewById(R.id.activity_newcontact__name);
        contactLastName = findViewById(R.id.activity_newcontact__last_name);
        contactPhone = findViewById(R.id.activity_newcontact__phone);
        contactEmail = findViewById(R.id.activity_newcontact__email);
        addButton = findViewById(R.id.activity_newcontact__add_button);
        activityName = findViewById(R.id.activity_name);

        dbHelper = new DBHelper(this);

        activityName.setText("Новый контакт");

        addButton.setOnClickListener(addButtonListiner);
    }

    View.OnClickListener addButtonListiner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(widgetsHasBeenFilled()) {
                SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();

                contentValues.put(DBHelper.KEY_NAME, contactName.getText().toString());
                contentValues.put(DBHelper.KEY_LASTNAME, contactLastName.getText().toString());
                contentValues.put(DBHelper.KEY_PHONE, contactPhone.getText().toString());
                contentValues.put(DBHelper.KEY_EMAIL, contactEmail.getText().toString());

                sqLiteDatabase.insert(DBHelper.TABLE_NAME, null, contentValues);
                sqLiteDatabase.close();
                finish();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Заполните все поля!", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    };

    private boolean widgetsHasBeenFilled() {
        String name = contactName.getText().toString();
        String lastName = contactLastName.getText().toString();
        String phone = contactPhone.getText().toString();
        String email = contactEmail.getText().toString();

        if(name.isEmpty() || lastName.isEmpty() || phone.isEmpty() || email.isEmpty())
            return false;
        else return true;
    }

}

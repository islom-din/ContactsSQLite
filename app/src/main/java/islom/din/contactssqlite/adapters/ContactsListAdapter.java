package islom.din.contactssqlite.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import islom.din.contactssqlite.R;
import islom.din.contactssqlite.models.Contact;

public class ContactsListAdapter extends ArrayAdapter<Contact> {

    private Context context;
    private int resource;

    public ContactsListAdapter(@NonNull Context context, int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getFirstName();
        String lastName = getItem(position).getLastName();
        String nameAndLastNameString = name + " " + lastName;

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView nameAndLastName = convertView.findViewById(R.id.name_and_last_name);

        nameAndLastName.setText(nameAndLastNameString);
        return convertView;
    }
}

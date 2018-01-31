package com.example.shad2018_practical4.contacts;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cursor phones = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        StringBuilder stringBuilder = new StringBuilder().append("Contacts: ").append("\n");
        while (phones.moveToNext()) {
            String name =
                    phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber =
                    phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String contactID =
                    phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));

            stringBuilder.append("- contact: name = ").append(name).append("; ")
                    .append("phoneNumber = ").append(phoneNumber).append(";")
                    .append("contactID = ").append(contactID).append("\n").append("\n");
        }
        phones.close();

        TextView textView = findViewById(R.id.tv_main);
        textView.setText(stringBuilder.toString());

        Log.i("Shad", stringBuilder.toString());
    }
}

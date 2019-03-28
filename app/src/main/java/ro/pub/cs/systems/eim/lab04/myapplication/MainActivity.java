package ro.pub.cs.systems.eim.lab04.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button showButton;
    Button saveButton;
    Button cancelButton;

    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText addressEditText;
    private EditText jobTitleEditText;
    private EditText companyEditText;
    private EditText websiteEditText;
    private EditText imEditText;
    static int CONTACTS_MANAGER_REQUEST_CODE = 2905;
    LinearLayout additionalFieldsLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showButton  = (Button) findViewById(R.id.showFieldsButton);
        showButton.setOnClickListener(buttonListener);
        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(buttonListener);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(buttonListener);

        additionalFieldsLayout = (LinearLayout) findViewById(R.id.additionalFieldsLayout);

        nameEditText = (EditText) findViewById(R.id.nameTextField);
        phoneEditText = (EditText) findViewById(R.id.phoneTextField);
        emailEditText = (EditText) findViewById(R.id.emailTextField);
        addressEditText = (EditText) findViewById(R.id.addressTextField);
        jobTitleEditText = (EditText) findViewById(R.id.jobTextField);
        companyEditText = (EditText) findViewById(R.id.companyTextField);
        websiteEditText = (EditText) findViewById(R.id.websiteTextField);
        imEditText = (EditText) findViewById(R.id.imTextField);

        Intent intent = getIntent();
        if (intent != null) {
            String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.myapplication.PHONE_NUMBER_KEY");
            if (phone != null) {
                phoneEditText.setText(phone);
            } else {
                Toast.makeText(this, "EROARE", Toast.LENGTH_LONG).show();
            }
        }
    }

    ButtonClickListener buttonListener = new ButtonClickListener();
    class ButtonClickListener implements View.OnClickListener  {

        @Override
        public void onClick(View v) {
            switch(v.getId()){
            case R.id.showFieldsButton :
                switch (additionalFieldsLayout.getVisibility()) {
                    case View.VISIBLE:
                        additionalFieldsLayout.setVisibility(View.INVISIBLE);
                        showButton.setText("Show additional fields");
                        break;
                    case View.INVISIBLE:
                        additionalFieldsLayout.setVisibility(View.VISIBLE);
                        showButton.setText("Hide additional fields");
                        break;
                }
                break;
                case(R.id.cancelButton):
                    setResult(Activity.RESULT_CANCELED, new Intent());
                    finish();
                    break;
                case (R.id.saveButton) :
                    String name = nameEditText.getText().toString();
                    String phone = phoneEditText.getText().toString();
                    String email = emailEditText.getText().toString();
                    String address = addressEditText.getText().toString();
                    String jobTitle = jobTitleEditText.getText().toString();
                    String company = companyEditText.getText().toString();
                    String website = websiteEditText.getText().toString();
                    String im = imEditText.getText().toString();

                    Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                    if (name != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                    }
                    if (phone != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                    }
                    if (email != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                    }
                    if (address != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                    }
                    if (jobTitle != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
                    }
                    if (company != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                    }
                    ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
                    if (website != null) {
                        ContentValues websiteRow = new ContentValues();
                        websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                        websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                        contactData.add(websiteRow);
                    }
                    if (im != null) {
                        ContentValues imRow = new ContentValues();
                        imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                        imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                        contactData.add(imRow);
                    }
                    intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA,  contactData);
                    //startActivity(intent);
                    startActivityForResult(intent, CONTACTS_MANAGER_REQUEST_CODE);
                    break;
            }
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch(requestCode) {
            case 2905:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }

}

/*
 * Copyright 2023-2024 wintmain
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wintmain.foundation.prejob.extDemo;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.AutoCompleteTextView;
import androidx.annotation.Nullable;
import com.wintmain.foundation.R;

public class ep6_3 extends Activity {
    private String[] columns =
            new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ep6_3);
        ContentResolver resolver = getContentResolver();
        Cursor cursor =
                resolver.query(ContactsContract.Contacts.CONTENT_URI, columns, null, null, null);
        ContactListAdapter adapter = new ContactListAdapter(this, cursor);
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.edit);
        autoCompleteTextView.setAdapter(adapter);
    }
}

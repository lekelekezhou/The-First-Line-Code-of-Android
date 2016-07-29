package ca.mahram.demo.picasso.activity;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import ca.mahram.demo.picasso.activity.base.BaseDemoActivity;
import ca.mahram.demo.picasso.misc.Contact;
import ca.mahram.demo.picasso.misc.ContactsAdapter;

import static android.provider.ContactsContract.Contacts.DISPLAY_NAME;
import static android.provider.ContactsContract.Contacts._ID;

/**
 show contacts and their display pictures
 Created by mahram on 15-03-02.
 */
public class ContactsListActivity
  extends BaseDemoActivity
  implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    private ContactsAdapter contactsAdapter;
    private ListView        list;

    private static final int LOADER_ID_CONTACTS = 100;

    @Override protected void onCreate (final Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        list = new ListView (this);
        list.setId (android.R.id.list);
        setContentView (list);
        list.setOnItemClickListener (this);
        getLoaderManager ().initLoader (LOADER_ID_CONTACTS, null, this);
    }

    @Override public Loader<Cursor> onCreateLoader (final int id, final Bundle args) {
        if (id != LOADER_ID_CONTACTS) {
            throw new IllegalArgumentException ("Unknown loader id: " + id);
        }

        return new CursorLoader (this,
                                 ContactsContract.Contacts.CONTENT_URI,
                                 new String[] {_ID, DISPLAY_NAME},
                                 null,
                                 null,
                                 DISPLAY_NAME);
    }

    @Override public void onLoadFinished (final Loader<Cursor> loader, final Cursor data) {
        if (loader.getId () != LOADER_ID_CONTACTS) {
            return;
        }

        if (null == contactsAdapter) {
            contactsAdapter = new ContactsAdapter (this, data);
            list.setAdapter (contactsAdapter);
        } else {
            contactsAdapter.update (data);
        }
    }

    @Override public void onLoaderReset (final Loader<Cursor> loader) { /* nothing */ }

    @Override public void onItemClick (final AdapterView<?> parent,
                                       final View view,
                                       final int position,
                                       final long id) {
        final Contact contact = contactsAdapter.getItem (position);
        final Intent contactActivity = new Intent (this, ContactActivity.class);
        contactActivity.setData (contact.getUri ());
        startActivity (contactActivity);
    }
}

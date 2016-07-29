package ca.mahram.demo.picasso.misc;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 Created by mahram on 15-03-04.
 */
@EqualsAndHashCode @ToString
public class Contact {
    public final long id;

    @Getter private final String name;
    @Getter private final Uri    uri;
    @Getter private final Uri    photoUri;

    private Contact (final Cursor cursor, final int idxId, final int idxName) {
        id = cursor.getLong (idxId);
        name = cursor.getString (idxName);

        uri = getContactUri (id);
        photoUri = getPhotoUri (uri);
    }

    static Contact[] extractAll (final Cursor cursor) {
        final int idxRow = cursor.getColumnIndexOrThrow (ContactsContract.Contacts._ID);
        final int idxName = cursor.getColumnIndexOrThrow (ContactsContract.Contacts.DISPLAY_NAME);

        if (!cursor.moveToFirst ()) {
            return new Contact[0];
        }

        final Contact[] all = new Contact[cursor.getCount ()];

        do {
            all[cursor.getPosition ()] = new Contact (cursor, idxRow, idxName);
        } while (cursor.moveToNext ());

        return all;
    }

    public static Uri getContactUri (final long rawId) {
        return ContentUris.withAppendedId (ContactsContract.Contacts.CONTENT_URI, rawId);
    }

    public static Uri getPhotoUri (final Uri contactUri) {
        return Uri.withAppendedPath (contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
    }

    public static Uri getPhotoUri (final long rawId) {
        return getPhotoUri (getContactUri (rawId));
    }
}

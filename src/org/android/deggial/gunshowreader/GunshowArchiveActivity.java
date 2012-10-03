package org.android.deggial.gunshowreader;


import net.bytten.comicviewer.ArchiveActivity;
import net.bytten.comicviewer.IComicDefinition;

import org.android.deggial.gunshowreader.R;

public class GunshowArchiveActivity extends ArchiveActivity {

    @Override
    protected IComicDefinition makeComicDef() {
        return new GunshowComicDefinition();
    }

    @Override
    protected String getStringArchive() {
        //return null;
    	return getResources().getString(R.string.app_archive_label);
    }

    @Override
    protected String getStringBookmarks() {
        return getResources().getString(R.string.app_bookmarks_label);
    }

    @Override
    protected String getStringSearchByTitle() {
    	return "Search by Title";
    }

}

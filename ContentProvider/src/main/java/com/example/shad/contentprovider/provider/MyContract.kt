package com.example.shad.contentprovider.provider

import android.content.ContentResolver

object MyContract {

    const val AUTHORITY = "com.example.shad.contentprovider.provider"

    object Books {

        const val CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.example.shad.contentprovider.provider_books"
        const val CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.com.example.shad.contentprovider.provider_books"
    }

    object Students {

        const val CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.example.shad.contentprovider.provider_students"
        const val CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.com.example.shad.contentprovider.provider_students"
    }
}
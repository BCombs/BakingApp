/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class ListViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new RecipeRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

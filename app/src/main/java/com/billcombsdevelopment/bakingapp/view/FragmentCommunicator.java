/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.view;

interface FragmentCommunicator {
    void onRecipeSelected(int position);

    void updateUi(int position);
}

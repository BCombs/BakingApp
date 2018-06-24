/*
 * Copyright (c) 2018 Bill Combs
 */

package com.billcombsdevelopment.bakingapp.view;

/**
 * Callback for communication between fragment and parent
 */
interface FragmentCommunicator {
    void onRecipeSelected(int position);

    void updateUi(int position);
}

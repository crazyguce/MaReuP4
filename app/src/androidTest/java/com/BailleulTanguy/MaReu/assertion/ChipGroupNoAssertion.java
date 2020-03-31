package com.BailleulTanguy.MaReu.assertion;

import android.view.View;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;

import com.google.android.material.chip.ChipGroup;

import static org.junit.Assert.assertEquals;

public class ChipGroupNoAssertion implements ViewAssertion {

    public static ChipGroupNoAssertion matchesChipGroupEmpty() {
        return new ChipGroupNoAssertion();
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        int lCount = ((ChipGroup) view).getChildCount();

        assertEquals(0, lCount);
    }

}

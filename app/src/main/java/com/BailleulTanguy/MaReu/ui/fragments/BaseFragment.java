package com.BailleulTanguy.MaReu.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;

/**
 * Created by Bailleul Tanguy on 21/02/2020
 */
public abstract class BaseFragment extends Fragment {

    protected abstract int getFragmentLayout();
    protected abstract void configureDesign(View pView);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View lView = inflater.inflate(getFragmentLayout(), container, false);
        ButterKnife.bind(this, lView);
        this.configureDesign(lView);
        return(lView);
    }

}

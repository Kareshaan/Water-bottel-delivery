package com.maddlabs.androidcartfirebase.listener;

import com.maddlabs.androidcartfirebase.model.DrinkModel;

import java.util.List;

public interface IDrinkLoadListener {
    void onDrinkLoadSuccess(List<DrinkModel> drinkModelList);
    void onDrinkLoadFailed(String message);
}

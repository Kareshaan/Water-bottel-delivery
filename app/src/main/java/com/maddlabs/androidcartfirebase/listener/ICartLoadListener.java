package com.maddlabs.androidcartfirebase.listener;

import com.maddlabs.androidcartfirebase.model.CartModel;
import com.maddlabs.androidcartfirebase.model.DrinkModel;

import java.util.List;

public interface ICartLoadListener {
    void onCartLoadSuccess(List<CartModel> cartModelList);
    void onCartLoadFailed(String message);
}

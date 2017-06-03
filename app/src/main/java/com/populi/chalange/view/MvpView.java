package com.populi.chalange.view;

import java.util.List;

/**
 * Created by victg on 03.06.2017.
 */
public interface MvpView<T> {

    void showLoadingView();

    void hideRetryView();

    void hideLoading();

    void renderWithData(List<T> list);

    void showRetry();
}

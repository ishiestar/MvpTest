package com.example.mvptest;


import android.support.v4.app.FragmentManager;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

/**
 * Created by ishitasinha on 28/01/17.
 */

public class OauthLoginProvider {
    private FragmentManager fragmentManager;
    private ResponseListener<String> responseListener;
    private OAuthConsumer consumer;
    private OAuthProvider provider;

    public OauthLoginProvider(FragmentManager fm, ResponseListener<String> listener) {
        fragmentManager = fm;
        responseListener = listener;
        consumer = new DefaultOAuthConsumer(BuildConfig.API_KEY, BuildConfig.API_SECRET);
        provider = new DefaultOAuthProvider(Constants.REQUEST_TOKEN_URL, Constants.ACCESS_TOKEN_URL, Constants.AUTHORIZE_URL);
    }

    public void execute(String callback) {
        Log.d(OauthLoginProvider.class.getSimpleName(), "getObservable");
        getObservable(callback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseListener::onResponse, responseListener::onFailure);
    }

    public String emit(String callbackUrl) throws OAuthCommunicationException, OAuthExpectationFailedException, OAuthNotAuthorizedException, OAuthMessageSignerException {
        return provider.retrieveRequestToken(consumer, callbackUrl);
    }

    private Observable<String> getObservable(String callbackUrl) {
        return Observable.defer(() -> Observable.just(emit(callbackUrl)));
    }
}

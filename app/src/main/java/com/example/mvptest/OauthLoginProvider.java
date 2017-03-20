package com.example.mvptest;


import org.json.JSONException;
import org.json.JSONObject;

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
 * Handles the login request from MainActivity (at least for now)
 *
 * Created by ishitasinha on 28/01/17.
 */

public class OauthLoginProvider {
    private ResponseListener<Object> responseListener;
    private OAuthConsumer consumer;
    private OAuthProvider provider;

    //initialize all member variables
    public OauthLoginProvider(ResponseListener<Object> listener) {
        responseListener = listener;
        consumer = new DefaultOAuthConsumer(BuildConfig.API_KEY, BuildConfig.API_SECRET);
        provider = new DefaultOAuthProvider(Constants.REQUEST_TOKEN_URL, Constants.ACCESS_TOKEN_URL, Constants.AUTHORIZE_URL);
    }

    //calling onResponse and onFailure in MainActivity with whatever data subscribe is handed
    public void execute(String callback, boolean shouldReturnRequestToken) {
        getObservable(callback, shouldReturnRequestToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseListener::onResponse, responseListener::onFailure);
    }

    //this hands subscribe the "data" or the "throwable" after calling the API
    private Object emit(String callbackUrl, boolean shouldReturnRequestToken) throws OAuthCommunicationException, OAuthExpectationFailedException, OAuthNotAuthorizedException, OAuthMessageSignerException, JSONException {
        if (shouldReturnRequestToken)
            return provider.retrieveRequestToken(consumer, callbackUrl);
        else {
            provider.retrieveAccessToken(consumer, callbackUrl);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Constants.KEY_TOKEN, consumer.getToken());
            jsonObject.put(Constants.KEY_SECRET, consumer.getTokenSecret());
            return jsonObject;
        }
    }

    //creates an observable
    private Observable<Object> getObservable(String callbackUrl, boolean shouldReturnRequestToken) {
        return Observable.defer(() -> Observable.just(emit(callbackUrl, shouldReturnRequestToken)));
    }
}

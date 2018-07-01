package com.example.nejat.journalapptrail1.GoogleSignIn;

public class GSigninPresenter implements GSigninContract.GSigninPresenter {

    private GSigninActivity gSigninActivity;
    private GSigninModel gSigninModel;

    GSigninPresenter(GSigninActivity gSigninActivity){
        this.gSigninActivity = gSigninActivity;
        this.gSigninModel = new GSigninModel();

    }
    @Override
    public void googleButtonClicked() {
        gSigninActivity.navigateToMain();
    }
}

package com.iww.classifiedolx.api.interfaces;

import com.iww.classifiedolx.Fragments.*;
import com.iww.classifiedolx.Fragments.AddAdvertise.*;
import com.iww.classifiedolx.LoginSignupScreen;
import com.iww.classifiedolx.MainActivity;
import com.iww.classifiedolx.api.ApplicationModule;
import com.iww.classifiedolx.api.HttpModule;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {HttpModule.class, ApplicationModule.class})
public interface AppComponent {
  void inject(MainActivity mainActivity);
  void inject(FavouriteFragment favouriteFragment);
  void inject(HomeFragment homeFragment);

  void inject(MyAccountFragment myAccountFragment);
  void inject(MyAdsFragment myAdsFragment);
  void inject(SubCatFrag subCatFrag);
  void inject(SubCateBaseAdd subCateBaseAdd);
  void inject(LoginSignupScreen loginSignupScreen);
  void inject(AllMainCatForAdd allMainCatForAddKt);
  void inject(SubCatFragForAdd subCatFragForAdd);
  void inject(SellFrag sellFrag);
  void inject(FieldForAddNewAdvertise fieldForAddNewAdvertise);
  void inject(ChooseLocationForAdver chooseLocationForAddver);
  void inject(ImageAndNumberForAdd imageAndNumberForAdd);
  void inject(AdvertisementDetailsFrag advertisementDetailsFrag);


}

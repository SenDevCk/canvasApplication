package com.chandan.canvasproject.utility;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.chandan.canvasproject.entity.UserDetails;


public class CommonPref {

	static Context context;

	CommonPref() {

	}

	CommonPref(Context context) {
		CommonPref.context = context;
	}



	public static void setUserDetails(Context context,String userid, String Code,String ReffCode,String Name,String Location,String Address,String State,
									  String District,String PINCode,String Mobile,String Email,String LandMark) {

		String key = "_USER_DETAILS";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		Editor editor = prefs.edit();

		editor.putString("UserId", userid);
		editor.putString("Code", Code);
		editor.putString("ReffCode", ReffCode);
		editor.putString("Name", Name);
		editor.putString("Location", Location);
		editor.putString("Address", Address);
		editor.putString("State", State);
		editor.putString("District", District);
		editor.putString("PINCode", PINCode);
		editor.putString("Mobile", Mobile);
		editor.putString("Email", Email);
		editor.putString("LandMark", LandMark);

/*		editor.putString("Code", userInfo.getCode());
		editor.putString("ReffCode", userInfo.getReffCode());
		editor.putString("Name", userInfo.getName());
		editor.putString("Location", userInfo.getLocation());
		editor.putString("Address", userInfo.getAddress());
		editor.putString("State", userInfo.getState());
		editor.putString("District", userInfo.getDistrict());
		editor.putString("PINCode", userInfo.getPINCode());
		editor.putString("Mobile", userInfo.getMobile());
		editor.putString("Email", userInfo.getEmail());
		editor.putString("LandMark", userInfo.getLandMark());
		editor.putString("message", userInfo.getMessage());
		editor.putString("status", userInfo.getStatus());*/

		editor.commit();

	}

	public static UserDetails getUserDetails(Context context) {

		String key = "_USER_DETAILS";
		UserDetails userInfo = new UserDetails();
		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);


		userInfo.setUserId(prefs.getString("UserId", ""));
		userInfo.setCode(prefs.getString("Code", ""));
		userInfo.setReffCode(prefs.getString("ReffCode", ""));
		userInfo.setName(prefs.getString("Name", ""));
		userInfo.setLocation(prefs.getString("Location", ""));
		userInfo.setAddress(prefs.getString("Address", ""));
		userInfo.setState(prefs.getString("State", ""));
		userInfo.setDistrict(prefs.getString("District", ""));
		userInfo.setPINCode(prefs.getString("PINCode", ""));
		userInfo.setMobile(prefs.getString("Mobile", ""));
		userInfo.setEmail(prefs.getString("Email", ""));
		userInfo.setLandMark(prefs.getString("LandMark", ""));
		userInfo.setMessage(prefs.getString("message", ""));
		userInfo.setStatus(prefs.getString("status", ""));

		return userInfo;
	}

	

	public static void setCheckUpdate(Context context, long dateTime) {

		String key = "_CheckUpdate";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		Editor editor = prefs.edit();

		
		dateTime=dateTime+1*3600000;
		editor.putLong("LastVisitedDate", dateTime);

		editor.commit();

	}

	public static int getCheckUpdate(Context context) {

		String key = "_CheckUpdate";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		long a = prefs.getLong("LastVisitedDate", 0);

		
		if(System.currentTimeMillis()>a)
			return 1;
		else
			return 0;
	}

	public static void setAwcId(Activity activity, String awcid){
		String key = "_Awcid";
		SharedPreferences prefs = activity.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		Editor editor = prefs.edit();
		editor.putString("code2", awcid);
		editor.commit();
	}
	public static void clearLog(Context context) {

		String key = "_USER_DETAILS";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putString("UserId", "");
		//editor.putString("password", "");
		editor.clear();
		editor.commit();

	}

}

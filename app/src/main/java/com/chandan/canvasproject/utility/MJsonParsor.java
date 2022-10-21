package com.chandan.canvasproject.utility;

import com.chandan.canvasproject.entity.Versioninfo;

import org.json.JSONArray;
import org.json.JSONObject;



public class MJsonParsor {


    public static Versioninfo parseCheckVersion(String res) {
        Versioninfo versioninfo=null;
        try{
            JSONArray jsonArray=new JSONArray(res);
            if (jsonArray.length()>0) {
                versioninfo=new Versioninfo();
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                if (jsonObject.has("appDetailsSno"))
                    versioninfo.setAppDetailsSno(String.valueOf(jsonObject.getInt("appDetailsSno")));
                if (jsonObject.has("appDetailsVer"))
                    versioninfo.setAppDetailsVer(jsonObject.getString("appDetailsVer"));
                if (jsonObject.has("appDetailsPriority"))
                    versioninfo.setAppDetailsPriority(jsonObject.getString("appDetailsPriority"));
                if (jsonObject.has("appDetailsAadminmsg"))
                    versioninfo.setAppDetailsAadminmsg(jsonObject.getString("appDetailsAadminmsg"));
                if (jsonObject.has("appDetailsAdmintitle"))
                    versioninfo.setAppDetailsAdmintitle(jsonObject.getString("appDetailsAdmintitle"));
                if (jsonObject.has("appDetailsUpdatemsg"))
                    versioninfo.setAppDetailsUpdatemsg(jsonObject.getString("appDetailsUpdatemsg"));
                if (jsonObject.has("appDetailsUpdatetitle"))
                    versioninfo.setAppDetailsUpdatetitle(jsonObject.getString("appDetailsUpdatetitle"));
                if (jsonObject.has("appDetailsAppurl"))
                    versioninfo.setAppDetailsAppurl(jsonObject.getString("appDetailsAppurl"));
                if (jsonObject.has("appDetailsRole"))
                    versioninfo.setAppDetailsRole(jsonObject.getString("appDetailsRole"));
                if (jsonObject.has("appDetailsUpdateDate"))
                    versioninfo.setAppDetailsUpdateDate(String.valueOf(jsonObject.getString("appDetailsUpdateDate")));
            }else{
                return versioninfo;
            }

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return versioninfo;
    }
}

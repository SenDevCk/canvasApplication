package com.chandan.canvasproject.entity;

public class UserDetails {
	private boolean _isAuthenticated = false;
	private String UserId = "";
	private String Code = "";
	private String ReffCode = "";
	private String Name = "";
	private String Location = "";
	private String Address = "";
	private String State = "";
	private String District = "";
	private String PINCode = "";
	private String Mobile = "";
	private String Email = "";
	private String LandMark = "";
	private String message = "";
	private String status = "";


	public UserDetails() {
	}


	public boolean is_isAuthenticated() {
		return _isAuthenticated;
	}

	public void set_isAuthenticated(boolean _isAuthenticated) {
		this._isAuthenticated = _isAuthenticated;
	}


	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getReffCode() {
		return ReffCode;
	}

	public void setReffCode(String reffCode) {
		ReffCode = reffCode;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getDistrict() {
		return District;
	}

	public void setDistrict(String district) {
		District = district;
	}

	public String getPINCode() {
		return PINCode;
	}

	public void setPINCode(String PINCode) {
		this.PINCode = PINCode;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getLandMark() {
		return LandMark;
	}

	public void setLandMark(String landMark) {
		LandMark = landMark;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}

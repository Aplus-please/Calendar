package hkust.cse.calendar.unit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

public class User implements Serializable {

	private String mPassword; // User password
	private String mID; // User id
	private String mEmail = "email";
	private String mFullName = "name";
	private boolean isAdmin;
	public int status;

	// Getter of the user id
	public String ID() {
		return mID;
	}

	// Constructor of class 'User' which set up the user id and password
	public User(String id, String pass,int stat) {
		mID = id;
		mPassword = pass;
		isAdmin = false;
		status = stat;
	}

	public User(String id, String pass, boolean b, String email, String name, int stat) {
		mID = id;
		mPassword = pass;
		isAdmin = b;
		if(!email.equals(""))
			mEmail = email;
		if(!name.equals(""))
			mFullName = name;
		status = stat;
	}

	public User(User a) {
		mID = a.ID();
		mPassword = a.Password();
		isAdmin = isAdmin();
	}

	// Another getter of the user id
	public String toString() {
		return ID();
	}

	public void ID(String ID){
		mID = ID;
	}
	
	// Getter of the user password
	public String Password() {
		return mPassword;
	}
	

	// Setter of the user password
	public void Password(String pass) {
		mPassword = pass;
	}
	
	
	public void Admin(boolean a){
		isAdmin = a;
	}
	
	public boolean isAdmin() {
		return isAdmin;
	}
	
	public void Email(String e){
		mEmail = e;
	}
	
	public String Email(){
		return mEmail;
	}
	
	public void FullName(String e){
		mFullName = e;
	}
	
	public String FullName(){
		return mFullName;
	}
	
}

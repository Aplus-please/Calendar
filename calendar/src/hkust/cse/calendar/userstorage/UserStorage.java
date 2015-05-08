package hkust.cse.calendar.userstorage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;
import hkust.cse.calendar.apptstorage.ApptStorageNullImpl;
import hkust.cse.calendar.gui.CalGrid;
import hkust.cse.calendar.unit.User;

public class UserStorage {
	public User[] user;
	private int numOfUser = 0;
	private int max_num_user = 30;

	public UserStorage(String fileName) {		//write the txt file into the user array
		user = new User[max_num_user];
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String tmp = null;
			int i = 0;
			while ((tmp = br.readLine()) != null) {
				String[] splited = tmp.split("\\|");
				user[i] = new User(splited[0], splited[1], splited[2].equals("Admin"), splited[3], splited[4], Integer.parseInt(splited[5]));
//				if(user[i].isAdmin())
//					System.out.println("this user is admin.");
//				else
//					System.out.println("this user is noraml user.");
				i++;
				numOfUser++;
			}
			br.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public boolean IDExist(String ID){
		for(int i = 0; i < numOfUser; i++){
			if(user[i].ID().equals(ID))
				return true;
		}
		return false;
	}
	
	public int UserIndex(User u){
		for(int i = 0; i < numOfUser; i++){
			if(user[i].ID().equals(u.ID()))
				return i;
		}
		return -1;
	}
	
	public void add(User u){
		user[numOfUser] = u;
		numOfUser++;
		update();
	}
	
	public void delete(int deleteindex) {
		// TODO Auto-generated method stub
		numOfUser--;
		while(deleteindex < numOfUser){
			user[deleteindex] = user[deleteindex + 1];
			deleteindex++;
		}
		update();
	}
	
	public void update() {
		try {
			File inputFile = new File("user_list.txt");
			File tempFile = new File("tempFile.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			for(int i = 0; i < numOfUser; i++){
				writer.write(user[i].ID() + "|");
				writer.write(user[i].Password() + "|");
				if(user[i].isAdmin())
					writer.write("Admin" + "|");
				else
					writer.write("Normal" + "|");
				writer.write(user[i].Email() + "|");
				writer.write(user[i].FullName() + "|");
				writer.write(user[i].status + "|" + "\n");
			}
			writer.close();
			inputFile.delete();
			tempFile.renameTo(inputFile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("cannot open");
			e1.printStackTrace();
		}
	}

	public User isValidUsername(String username, String password) {
		for(int i = 0; i < numOfUser; i++){
			if(user[i].ID().equals(username) &&  user[i].Password().equals(password))
				return user[i];
		}
		return null;
	}

	public int getNumOfUsers() {
		// TODO Auto-generated method stub
		return numOfUser;
	}

	public User getUser(int i) {
		// TODO Auto-generated method stub
		return user[i];
	}
	
	public String[] getAllUsername(){
		String[] username = new String [numOfUser];
		for(int i = 0; i < numOfUser; i++){
			username[i] = user[i].ID();
		}
		return username;
	}

	public void update(User u) {
		int userIndex = UserIndex(u);
		user[userIndex] = u;
		update();
	}

	public User getUser(String username) {
		for(int i = 0; i < numOfUser; i++){
			if(user[i].ID().equals(username))
				return user[i];
		}
		return null;
	}

	

}

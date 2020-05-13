package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/*The following class is a model of the password.
 * The password is key to the login system in the program and contains functions which are needed throughout the application. */
public class Password {
	private String password;
	public Password() {
		/*Init method */
		Decrypt();
	}
	public String getPassword() {
		/*returns the password as string */
		return password;
	}

	public void setPassword(String Password) {
		/*Sets the password */
		Encrypt(Password);
	}
	
	
	public Integer Encrypt(String Pass) {
		/*Hashes the password then stores it within a text file.
		 * Returns the hashed value. */
		try {
			FileOutputStream file = new FileOutputStream("../Finished Timer/src/pass/Password.txt");
			ObjectOutputStream saveFile = new ObjectOutputStream(file);
			saveFile.writeObject(Pass.hashCode());
			saveFile.close();
		} catch (Exception e) {
			return 0;
		}
		return Pass.hashCode();
	}

	public void Decrypt() {
		/*Takes the password from the text file*/
		ObjectInputStream file;
		try {
			file = new ObjectInputStream(new FileInputStream("../Finished Timer/src/pass/Password.txt"));
			Object obj = file.readObject();
			password = obj.toString();
			file.close();
			
		} catch (Exception e) {
			return;
		}

	}
	
	@Override
	public String toString() {
		/*Returns the password in a neat format. */
		return "Password:[Password = "+password+" ]";
	}
}



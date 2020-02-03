package in.srijanju.androidapp.model;

import androidx.annotation.NonNull;

import java.util.Map;

public class UserSend {
	public String name, email, college, degree, course, year;
	public int complete;
	public Map<String, String> updatetime;

	public UserSend(
		String name, String email, String college, String degree, String course, String year,
		int complete, Map<String, String> updatetime) {
		this.name = name;
		this.email = email;
		this.college = college;
		this.degree = degree;
		this.course = course;
		this.year = year;
		this.complete = complete;
		this.updatetime = updatetime;
	}

	@NonNull
	@Override
	public String toString() {
		return "{Name: " + name + ", Email: " + email + ", College: " + college + ", Degree: " + degree + ", Course: " + course + ", Year: " + year + ", complete: " + complete + ", updatetime: " + updatetime + "}";
	}
}

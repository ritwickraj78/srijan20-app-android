package in.srijanju.androidapp.model;

import androidx.annotation.NonNull;

public class User {
	public String name, email, college, degree, course, year;
	public int complete;
	public long updatetime;

	public User() {}

	public User(
		String name, String email, String college, String degree, String course, String year,
		int complete, long updatetime) {
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

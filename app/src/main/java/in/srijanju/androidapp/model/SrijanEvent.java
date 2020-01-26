package in.srijanju.androidapp.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class SrijanEvent implements Serializable {
	public String poc;
	public String desc;
	public String name;
	public String type;
	public String rules;
	public String url;
	public String poster;

	@NonNull
	@Override
	public String toString() {
		return name + " - " + type + ": ";
	}
}

package com.example.custom.object;

public class PictureVO {

	public PictureVO(String name, int source) {
		super();
		this.name = name;
		this.source = source;
	}

	private String name;
	private int source;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

}

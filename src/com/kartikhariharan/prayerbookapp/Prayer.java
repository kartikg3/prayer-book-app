package com.kartikhariharan.prayerbookapp;

public class Prayer {
	
	private int id;
	private String title;
	private String content;
	private boolean expandedState;
	private boolean favoriteState;
	
	public Prayer(int id, String title, String content, boolean expandedState, boolean favoriteState) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.expandedState = expandedState;
		this.favoriteState = favoriteState;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isExpandedState() {
		return expandedState;
	}

	public void setExpandedState(boolean expandedState) {
		this.expandedState = expandedState;
	}

	public boolean isFavoriteState() {
		return favoriteState;
	}

	public void setFavoriteState(boolean favoriteState) {
		this.favoriteState = favoriteState;
	}	
	
}

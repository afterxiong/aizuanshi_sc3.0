package com.sc.aizuanshi.util;

public class Game {
	private int id;
	private String name;
	private String packages;
	private int exist;
	private int qq;
	private int qzones;
	private int wechats;
	private int wechatmom;

	public String toString() {
		return "Game [id=" + id + ", name=" + name + ", packages=" + packages + ", exist=" + exist + ", qq=" + qq
				+ ", qzones=" + qzones + ", wechats=" + wechats + ", wechatmom=" + wechatmom + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPackages() {
		return packages;
	}

	public void setPackages(String packages) {
		this.packages = packages;
	}

	public int getExist() {
		return exist;
	}

	public void setExist(int exist) {
		this.exist = exist;
	}

	public int getQq() {
		return qq;
	}

	public void setQq(int qq) {
		this.qq = qq;
	}

	public int getQzones() {
		return qzones;
	}

	public void setQzones(int qzones) {
		this.qzones = qzones;
	}

	public int getWechats() {
		return wechats;
	}

	public void setWechats(int wechats) {
		this.wechats = wechats;
	}

	public int getWechatmom() {
		return wechatmom;
	}

	public void setWechatmom(int wechatmom) {
		this.wechatmom = wechatmom;
	}

}

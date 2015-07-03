package com.msg.phonebook.model;

public class Dept {
	private int deptno;
	private String deptname;
	//上级部门（层级关系）
	private int superiordeptno;
	
	public int getSuperiordeptno() {
		return superiordeptno;
	}
	public void setSuperiordeptno(int superiordeptno) {
		this.superiordeptno = superiordeptno;
	}
	public int getDeptno() {
		return deptno;
	}
	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}	
}

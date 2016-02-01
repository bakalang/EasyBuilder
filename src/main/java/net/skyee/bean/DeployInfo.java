
package net.skyee.bean;

public class DeployInfo
{
	private int projectNo;
	private String source;
	private String host;
	private String service;
	private String userid;
	private String password;
	private String root;

	public DeployInfo(int project_no, String source, String host, String service, String userid, String password, String root) {
		this.projectNo = project_no;
		this.source = source;
		this.host = host;
		this.service = service;
		this.userid = userid;
		this.password = password;
		this.root = root;
	}

	public int getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(int projectNo) {
		this.projectNo = projectNo;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}
}

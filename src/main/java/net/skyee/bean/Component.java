package net.skyee.bean;


import java.util.ArrayList;
import java.util.List;

public class Component
{
	private String name;
	private long version;
	private boolean checkout;
	private String token;
	private List<Config> configList = new ArrayList<Config>();

	public Component(String name, long version, boolean checkout) {
		this.name = name;
		this.version = version;
		this.checkout = checkout;
	}

	public boolean isCheckout() {
		return checkout;
	}

	public void setCheckout(boolean checkout) {
		this.checkout = checkout;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public List<Config> getConfigList() {
		return configList;
	}

	public void setConfigList(List<Config> configList) {
		this.configList = configList;
	}

	public void addConfigList(Config config) {
		if(null != config)
			this.configList.add(config);
	}
}

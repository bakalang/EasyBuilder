
package net.skyee.bean;

import java.util.Map;

public class Project
{
	private int projectNo;
	private String module;
	private String repository;
	private int lastVersion;

	public Project(int projectNo, String module, String repository, int lastVersion) {
		this.projectNo = projectNo;
		this.module = module;
		this.repository = repository;
		this.lastVersion = lastVersion;
	}

	public String getModule()
	{
		return module;
	}

	public void setModule(String module)
	{
		this.module = module;
	}

	public String getRepository()
	{
		return repository;
	}

	public void setRepository(String repository)
	{
		this.repository = repository;
	}

	public int getProjectNo()
	{
		return projectNo;
	}

	public void setProjectNo(int projectNo)
	{
		this.projectNo = projectNo;
	}

	public int getLastVersion()
	{
		return lastVersion;
	}

	public void setLastVersion(int lastVersion)
	{
		this.lastVersion = lastVersion;
	}

}

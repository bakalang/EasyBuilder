package net.skyee.dao.mapper;

import net.skyee.bean.Project;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectMapper implements ResultSetMapper<Project>
{
    public Project map(int index, ResultSet r, StatementContext ctx) throws SQLException
    {
        return new Project(r.getInt("project_no"), r.getString("module"), r.getString("repository"), r.getInt("last_version"));
    }
}
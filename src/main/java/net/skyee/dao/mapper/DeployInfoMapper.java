package net.skyee.dao.mapper;

import net.skyee.bean.DeployInfo;
import net.skyee.bean.Project;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeployInfoMapper implements ResultSetMapper<DeployInfo>
{
    public DeployInfo map(int index, ResultSet r, StatementContext ctx) throws SQLException
    {
        return new DeployInfo(r.getInt("project_no"),
                r.getString("source"),
                r.getString("host"),
                r.getString("service"),
                r.getString("userid"),
                r.getString("password"),
                r.getString("root"));
    }
}
package net.skyee.dao;

import net.skyee.bean.DeployInfo;
import net.skyee.bean.Project;
import net.skyee.dao.mapper.DeployInfoMapper;
import net.skyee.dao.mapper.ProjectMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Define;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

import java.math.BigDecimal;

@UseStringTemplate3StatementLocator
public interface DeployInfoDAO {

    @SqlQuery(" SELECT project_no, source, host, service, userid, password, root FROM deploy_info WHERE project_no = :project_no ")
    @Mapper(DeployInfoMapper.class)
    DeployInfo getDeployInfoByNo(@Bind("project_no") int projectNo);
}

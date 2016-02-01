package net.skyee.dao;

import net.skyee.bean.Project;
import net.skyee.dao.mapper.ProjectMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

import java.util.List;

@UseStringTemplate3StatementLocator
public interface ProjectDependenceDAO {

    @SqlQuery(" SELECT a.depend_on, b.project_no, b.module, b.repository, b.last_version " +
            " FROM project_dependence a " +
            " LEFT OUTER JOIN project b ON a.depend_on = b.project_no " +
            " WHERE a.project_no = :projectNo ")
    @Mapper(ProjectMapper.class)
    List<Project> getDependenceProjectByProjectNo(@Bind("projectNo") int projectNo);
}

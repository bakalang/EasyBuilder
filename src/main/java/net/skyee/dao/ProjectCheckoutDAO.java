
package net.skyee.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

@UseStringTemplate3StatementLocator
public interface ProjectCheckoutDAO
{
	@SqlUpdate("insert into project_checkout values (:checkout_time, :project_no, :version, :remark)")
	void insert(@Bind("checkout_time") String checkoutTime,
				@Bind("project_no") int projectNo,
				@Bind("version") long version,
				@Bind("remark") String remark);

}

package net.skyee;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.xml.internal.ws.api.PropertySet;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
//import net.skyee.bean.Template;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class SampleConf extends Configuration {

    @NotEmpty
    private String svnURL;

    @NotEmpty
    private String svnUser;

    @NotEmpty
    private String svnPwd;

    @NotEmpty
    private String sourcePath;

    @NotEmpty
    private String configPath;

    @JsonProperty
    public String getSvnURL() { return svnURL; }

    @JsonProperty
    public String getSvnUser() { return svnUser; }

    @JsonProperty
    public String getSvnPwd() { return svnPwd; }

    @JsonProperty
    public String getConfigPath() { return configPath; }

    @JsonProperty
    public String getSourcePath() { return sourcePath; }

    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }


}

package mz.org.csaude.mentoring.dao.form;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

import mz.org.csaude.mentoring.base.dao.MentoringBaseDaoImpl;
import mz.org.csaude.mentoring.model.form.FormType;

public class FormTypeDAOImpl extends MentoringBaseDaoImpl<FormType, Integer> {

    public FormTypeDAOImpl(Class<FormType> dataClass) throws SQLException {
        super(dataClass);
    }

    public FormTypeDAOImpl(ConnectionSource connectionSource, Class<FormType> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public FormTypeDAOImpl(ConnectionSource connectionSource, DatabaseTableConfig<FormType> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}

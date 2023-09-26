package mz.org.csaude.mentoring.dao.tutor;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;

import mz.org.csaude.mentoring.model.tutor.Tutor;

public class TutorDAOImpl extends BaseDaoImpl<Tutor, Integer> implements TutorDAO {


    public TutorDAOImpl(Class<Tutor> dataClass) throws SQLException {
        super(dataClass);
    }

    public TutorDAOImpl(ConnectionSource connectionSource, Class<Tutor> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public TutorDAOImpl(ConnectionSource connectionSource, DatabaseTableConfig<Tutor> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }


    @Override
    public boolean checkTutorExistance(String uuid) throws SQLException {
        List<Tutor> tutors = this.queryForEq("uuid", uuid);
        return !tutors.isEmpty();
    }
}

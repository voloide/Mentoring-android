package mz.org.csaude.mentoring.dao.question;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

import mz.org.csaude.mentoring.base.dao.MentoringBaseDaoImpl;
import mz.org.csaude.mentoring.model.question.QuestionType;

public class QuestionTypeDAOImpl extends MentoringBaseDaoImpl<QuestionType, Integer> implements QuestionTypeDAO {

    public QuestionTypeDAOImpl(Class<QuestionType> dataClass) throws SQLException {
        super(dataClass);
    }

    public QuestionTypeDAOImpl(ConnectionSource connectionSource, Class<QuestionType> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public QuestionTypeDAOImpl(ConnectionSource connectionSource, DatabaseTableConfig<QuestionType> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}

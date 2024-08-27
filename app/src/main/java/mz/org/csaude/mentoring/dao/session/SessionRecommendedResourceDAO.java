package mz.org.csaude.mentoring.dao.session;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import mz.org.csaude.mentoring.model.session.SessionRecommendedResource;

@Dao
public interface SessionRecommendedResourceDAO {

    @Transaction
    @Query("SELECT * FROM session_recommended_resource WHERE session_id = :sessionId")
    List<SessionRecommendedResource> findBySessionId(int sessionId);

    @Transaction
    @Query("SELECT * FROM session_recommended_resource WHERE tutor_id = :tutorId")
    List<SessionRecommendedResource> findByTutorId(int tutorId);

    @Transaction
    @Query("SELECT * FROM session_recommended_resource WHERE tutored_id = :tutoredId")
    List<SessionRecommendedResource> findByTutoredId(int tutoredId);

    @Transaction
    @Query("SELECT * FROM session_recommended_resource WHERE sync_status = :syncStatus")
    List<SessionRecommendedResource> queryForAllPending(String syncStatus);

    @Transaction
    @Query("SELECT * FROM session_recommended_resource WHERE uuid = :uuid LIMIT 1")
    SessionRecommendedResource getByUuid(String uuid);

    @Insert
    void insert(SessionRecommendedResource sessionRecommendedResource);

    @Update
    void update(SessionRecommendedResource sessionRecommendedResource);
}

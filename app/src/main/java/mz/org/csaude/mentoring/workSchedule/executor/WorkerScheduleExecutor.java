package mz.org.csaude.mentoring.workSchedule.executor;

import android.app.Application;

import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.Arrays;
import java.util.List;

import mz.org.csaude.mentoring.base.application.MentoringApplication;
import mz.org.csaude.mentoring.model.setting.Setting;
import mz.org.csaude.mentoring.model.user.User;
import mz.org.csaude.mentoring.util.Http;
import mz.org.csaude.mentoring.workSchedule.work.DistrictWorker;
import mz.org.csaude.mentoring.workSchedule.work.DoorWorker;
import mz.org.csaude.mentoring.workSchedule.work.EvaluationTypeWorker;
import mz.org.csaude.mentoring.workSchedule.work.FormQuestionWorker;
import mz.org.csaude.mentoring.workSchedule.work.FormWorker;
import mz.org.csaude.mentoring.workSchedule.work.HealthFacilityWorker;
import mz.org.csaude.mentoring.workSchedule.work.IterationTypeWorker;
import mz.org.csaude.mentoring.workSchedule.work.MentorshipWorker;
import mz.org.csaude.mentoring.workSchedule.work.PartnerWorker;
import mz.org.csaude.mentoring.workSchedule.work.ProfessionalCategoryWorker;
import mz.org.csaude.mentoring.workSchedule.work.ProvinceWorker;
import mz.org.csaude.mentoring.workSchedule.work.ResponseTypeWorker;
import mz.org.csaude.mentoring.workSchedule.work.RondaTypeWorker;
import mz.org.csaude.mentoring.workSchedule.work.RondaWorker;
import mz.org.csaude.mentoring.workSchedule.work.SessionStatusWorker;
import mz.org.csaude.mentoring.workSchedule.work.TimeOfDayWorker;
import mz.org.csaude.mentoring.workSchedule.work.TutorWorker;
import mz.org.csaude.mentoring.workSchedule.work.TutoredWorker;

public class WorkerScheduleExecutor {

    private static final String TAG = "WorkerScheduler";
    public static final int JOB_ID = 1000;
    public static final int ONE_TIME_REQUEST_JOB_ID = 1001;

    private WorkManager workManager;

    private static WorkerScheduleExecutor instance;

    private MentoringApplication application;

    private List<Setting> settings;

    private WorkerScheduleExecutor(Application application) {
        this.application = (MentoringApplication) application;
        this.workManager = WorkManager.getInstance(application);
    }

    public WorkManager getWorkManager() {
        return workManager;
    }

    public static WorkerScheduleExecutor getInstance(Application application) {
        if (instance == null) {
            instance = new WorkerScheduleExecutor(application);
        }
        return instance;
    }
    public OneTimeWorkRequest runInitialSync() {
        OneTimeWorkRequest provinceOneTimeWorkRequest = new OneTimeWorkRequest.Builder(ProvinceWorker.class).addTag("ONE_TIME_CABINET_ID" + ONE_TIME_REQUEST_JOB_ID).build();
        OneTimeWorkRequest districtOneTimeWorkRequest = new OneTimeWorkRequest.Builder(DistrictWorker.class).addTag("ONE_TIME_DISTRICT_ID" + ONE_TIME_REQUEST_JOB_ID).build();
        OneTimeWorkRequest categoriesOneTimeWorkRequest = new OneTimeWorkRequest.Builder(ProfessionalCategoryWorker.class).addTag("ONE_TIME_CATEGORIES_ID" + ONE_TIME_REQUEST_JOB_ID).build();
        OneTimeWorkRequest partnersOneTimeWorkRequest = new OneTimeWorkRequest.Builder(PartnerWorker.class).addTag("ONE_TIME_PARTNERS_ID" + ONE_TIME_REQUEST_JOB_ID).build();
        OneTimeWorkRequest rondaTypesOneTimeWorkRequest = new OneTimeWorkRequest.Builder(RondaTypeWorker.class).addTag("ONE_TIME_RONDA_TYPES_ID" + ONE_TIME_REQUEST_JOB_ID).build();
        OneTimeWorkRequest responseTypesOneTimeWorkRequest = new OneTimeWorkRequest.Builder(ResponseTypeWorker.class).addTag("ONE_TIME_RESPONSE_TYPES_ID" + ONE_TIME_REQUEST_JOB_ID).build();
        OneTimeWorkRequest evaluationTypesOneTimeWorkRequest = new OneTimeWorkRequest.Builder(EvaluationTypeWorker.class).addTag("ONE_TIME_EVALUATION_TYPES_ID" + ONE_TIME_REQUEST_JOB_ID).build();
        OneTimeWorkRequest iterationTypesOneTimeWorkRequest = new OneTimeWorkRequest.Builder(IterationTypeWorker.class).addTag("ONE_TIME_ITERATION_TYPES_ID" + ONE_TIME_REQUEST_JOB_ID).build();
        OneTimeWorkRequest timesOfDayOneTimeWorkRequest = new OneTimeWorkRequest.Builder(TimeOfDayWorker.class).addTag("ONE_TIME_TIME_OF_DAY_ID" + ONE_TIME_REQUEST_JOB_ID).build();
        OneTimeWorkRequest doorsOneTimeWorkRequest = new OneTimeWorkRequest.Builder(DoorWorker.class).addTag("ONE_TIME_DOORS_ID" + ONE_TIME_REQUEST_JOB_ID).build();
        OneTimeWorkRequest sessionStatusOneTimeWorkRequest = new OneTimeWorkRequest.Builder(SessionStatusWorker.class).addTag("ONE_TIME_SESSION_STATUS_ID" + ONE_TIME_REQUEST_JOB_ID).build();

        workManager.beginUniqueWork("INITIAL_APP_SETUP", ExistingWorkPolicy.KEEP, provinceOneTimeWorkRequest)
                .then(Arrays.asList(districtOneTimeWorkRequest, partnersOneTimeWorkRequest))
                .then(Arrays.asList(rondaTypesOneTimeWorkRequest, responseTypesOneTimeWorkRequest,
                        evaluationTypesOneTimeWorkRequest, iterationTypesOneTimeWorkRequest,
                        timesOfDayOneTimeWorkRequest, doorsOneTimeWorkRequest, sessionStatusOneTimeWorkRequest))
                .then(categoriesOneTimeWorkRequest).enqueue();
        return categoriesOneTimeWorkRequest;

    }

    public OneTimeWorkRequest runPostLoginSync(User user) {
        /*Data inputData = new Data.Builder()
                .putString("username", user.getUserName())
                .putString("password", user.getPassword())
                .build();
        OneTimeWorkRequest userOneTimeWorkRequest = new OneTimeWorkRequest.Builder(UserWorker.class).addTag("ONE_TIME_USER_ID" + ONE_TIME_REQUEST_JOB_ID).setInputData(inputData).build();*/
        Data inputData = new Data.Builder()
                .putString("requestType", String.valueOf(Http.POST))
                .build();
        OneTimeWorkRequest tutorOneTimeWorkRequest = new OneTimeWorkRequest.Builder(TutorWorker.class).addTag("ONE_TIME_TUTOR_ID" + ONE_TIME_REQUEST_JOB_ID).build();
        OneTimeWorkRequest hfOneTimeWorkRequest = new OneTimeWorkRequest.Builder(HealthFacilityWorker.class).addTag("ONE_TIME_HF_ID" + ONE_TIME_REQUEST_JOB_ID).build();
        OneTimeWorkRequest mentorFormsOneTimeWorkRequest = new OneTimeWorkRequest.Builder(FormWorker.class).addTag("ONE_TIME_MENTOR_FORMS_ID" + ONE_TIME_REQUEST_JOB_ID)
                .setInputData(inputData).build();
        OneTimeWorkRequest mentorFormsQuestionsOneTimeWorkRequest = new OneTimeWorkRequest.Builder(FormQuestionWorker.class).addTag("ONE_TIME_MENTOR_FORMS_QUESTIONS_ID" + ONE_TIME_REQUEST_JOB_ID)
                .setInputData(inputData).build();
        OneTimeWorkRequest mentorRondasOneTimeWorkRequest = new OneTimeWorkRequest.Builder(RondaWorker.class).addTag("ONE_TIME_MENTOR_RONDAS_ID" + ONE_TIME_REQUEST_JOB_ID)
                .setInputData(inputData).build();
        OneTimeWorkRequest mentorMentorshipsOneTimeWorkRequest = new OneTimeWorkRequest.Builder(MentorshipWorker.class).addTag("ONE_TIME_MENTOR_MENTORSHIPS_ID" + ONE_TIME_REQUEST_JOB_ID)
                .setInputData(inputData).build();

        workManager.beginUniqueWork("INITIAL_MENTOR_APP_SETUP", ExistingWorkPolicy.KEEP, tutorOneTimeWorkRequest)
                .then(hfOneTimeWorkRequest)
                .then(mentorFormsOneTimeWorkRequest)
                .then(mentorFormsQuestionsOneTimeWorkRequest)
                .then(mentorRondasOneTimeWorkRequest)
                .then(mentorMentorshipsOneTimeWorkRequest)
                .enqueue();

        return hfOneTimeWorkRequest;
    }

    public OneTimeWorkRequest menteesDownload() {
        OneTimeWorkRequest menteesOneTimeWorkRequest = new OneTimeWorkRequest.Builder(TutoredWorker.class).addTag("ONE_TIME_MENTEES_ID" + ONE_TIME_REQUEST_JOB_ID).build();
        OneTimeWorkRequest mentorFormsOneTimeWorkRequest = new OneTimeWorkRequest.Builder(FormWorker.class).addTag("ONE_TIME_MENTOR_FORMS_ID" + ONE_TIME_REQUEST_JOB_ID).build();
        workManager.enqueue(Arrays.asList(menteesOneTimeWorkRequest, mentorFormsOneTimeWorkRequest));

        return menteesOneTimeWorkRequest;
    }

    public OneTimeWorkRequest uploadMentees() {
        Data inputData = new Data.Builder()
                .putString("requestType", String.valueOf(Http.POST))
                .build();
        OneTimeWorkRequest menteesOneTimeWorkRequest = new OneTimeWorkRequest.Builder(TutoredWorker.class).addTag("ONE_TIME_MENTEES_ID" + ONE_TIME_REQUEST_JOB_ID).setInputData(inputData).build();
        workManager.enqueue(menteesOneTimeWorkRequest);
        return menteesOneTimeWorkRequest;
    }
}

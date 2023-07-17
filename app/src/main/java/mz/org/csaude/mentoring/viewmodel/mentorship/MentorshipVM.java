package mz.org.csaude.mentoring.viewmodel.mentorship;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import mz.org.csaude.mentoring.BR;
import mz.org.csaude.mentoring.base.viewModel.BaseViewModel;
import mz.org.csaude.mentoring.model.form.Form;
import mz.org.csaude.mentoring.model.location.Cabinet;
import mz.org.csaude.mentoring.model.location.HealthFacility;
import mz.org.csaude.mentoring.model.mentorship.Door;
import mz.org.csaude.mentoring.model.mentorship.IterationType;
import mz.org.csaude.mentoring.model.mentorship.Mentorship;
import mz.org.csaude.mentoring.model.mentorship.TimeOfDay;
import mz.org.csaude.mentoring.model.session.Session;
import mz.org.csaude.mentoring.model.tutor.Tutor;
import mz.org.csaude.mentoring.model.tutored.Tutored;
import mz.org.csaude.mentoring.service.mentorship.MentorshipService;
import mz.org.csaude.mentoring.service.mentorship.MentorshipServiceImpl;

public class MentorshipVM extends BaseViewModel {

    private Mentorship mentorship;
    private MentorshipService mentorshipService;

    public MentorshipVM(@NonNull Application application) {
        super(application);
        this.mentorshipService = new MentorshipServiceImpl(application, getCurrentUser());
    }

    @Override
    public void preInit() {

    }

    @Bindable
    public String getCode() {
        return this.mentorship.getCode();
    }

    public void setName(String code) {
        this.mentorship.setCode(code);
        notifyPropertyChanged(BR.code);
    }

    @Bindable
    public LocalDateTime getStartDate() {
        return this.mentorship.getStartDate();
    }

    public void setName(LocalDateTime startDate) {
        this.mentorship.setStartDate(startDate);
       // notifyPropertyChanged(BR.startDate);
    }

    @Bindable
    public LocalDateTime getEndDate() {
        return this.mentorship.getEndDate();
    }

    public void setEndDate(LocalDateTime endDate) {
        this.mentorship.setEndDate(endDate);
        // notifyPropertyChanged(BR.startDate);
    }

    @Bindable
    public LocalDate getPerformedDate() {
        return this.mentorship.getPerformedDate();
    }

    public void setEndDate(LocalDate performedDate) {
        this.mentorship.setPerformedDate(performedDate);
        // notifyPropertyChanged(BR.startDate);
    }

    @Bindable
    public Tutor getTutor() {
        return this.mentorship.getTutor();
    }

    public void setTutor(Tutor tutor) {
        this.mentorship.setTutor(tutor);
        // notifyPropertyChanged(BR.startDate);
    }

    @Bindable
    public Tutored getTutored() {
        return this.mentorship.getTutored();
    }

    public void setTutored(Tutored tutored) {
        this.mentorship.setTutored(tutored);
        // notifyPropertyChanged(BR.startDate);
    }

    @Bindable
    public Form getForm() {
        return this.mentorship.getForm();
    }

    public void setForm(Form form) {
        this.mentorship.setForm(form);
        // notifyPropertyChanged(BR.startDate);
    }

    @Bindable
    public HealthFacility getHealthFacility() {
        return this.mentorship.getHealthFacility();
    }

    public void setHealthFacility(HealthFacility healthFacility) {
        this.mentorship.setHealthFacility(healthFacility);
        // notifyPropertyChanged(BR.startDate);
    }

    @Bindable
    public Session getSession() {
        return this.mentorship.getSession();
    }

    public void setSession(Session session) {
        this.mentorship.setSession(session);
        // notifyPropertyChanged(BR.startDate);
    }

    @Bindable
    public Cabinet getCabinet() {
        return this.mentorship.getCabinet();
    }

    public void setCabinet(Cabinet cabinet) {
        this.mentorship.setCabinet(cabinet);
        // notifyPropertyChanged(BR.startDate);
    }

    @Bindable
    public IterationType getIterationType() {
        return this.mentorship.getIterationType();
    }

    public void setCabinet(IterationType iterationType) {
        this.mentorship.setIterationType(iterationType);
        // notifyPropertyChanged(BR.startDate);
    }

    @Bindable
    public Integer getIterationNumber() {
        return this.mentorship.getIterationNumber();
    }

    public void setIterationNumber(Integer iterationNumber) {
        this.mentorship.setIterationNumber(iterationNumber);
        // notifyPropertyChanged(BR.startDate);
    }

    @Bindable
    public TimeOfDay getTimeOfDay() {
        return this.mentorship.getTimeOfDay();
    }

    public void setTimeOfDay(TimeOfDay timeOfDay) {
        this.mentorship.setTimeOfDay(timeOfDay);
        // notifyPropertyChanged(BR.startDate);
    }

    @Bindable
    public Door getDoor() {
        return this.mentorship.getDoor();
    }

    public void setDoor(Door door) {
        this.mentorship.setDoor(door);
        // notifyPropertyChanged(BR.startDate);
    }

    public void save() {
        try {
            this.mentorshipService.save(this.mentorship);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

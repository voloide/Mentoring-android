package mz.org.csaude.mentoring.viewmodel.ronda;

import android.app.Application;
import android.app.Dialog;
import android.util.Log;

import androidx.annotation.NonNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.org.csaude.mentoring.R;
import mz.org.csaude.mentoring.base.searchparams.AbstractSearchParams;
import mz.org.csaude.mentoring.base.viewModel.SearchVM;
import mz.org.csaude.mentoring.listner.dialog.IDialogListener;
import mz.org.csaude.mentoring.listner.rest.ServerStatusListener;
import mz.org.csaude.mentoring.model.ronda.Ronda;
import mz.org.csaude.mentoring.model.ronda.RondaMentee;
import mz.org.csaude.mentoring.model.ronda.RondaSummary;
import mz.org.csaude.mentoring.model.rondatype.RondaType;
import mz.org.csaude.mentoring.model.session.Session;
import mz.org.csaude.mentoring.model.session.SessionSummary;
import mz.org.csaude.mentoring.util.PDFGenerator;
import mz.org.csaude.mentoring.util.Utilities;
import mz.org.csaude.mentoring.view.mentorship.MentorshipActivity;
import mz.org.csaude.mentoring.view.mentorship.ZeroMentorshipListActivity;
import mz.org.csaude.mentoring.view.ronda.CreateRondaActivity;
import mz.org.csaude.mentoring.view.ronda.RondaActivity;
import mz.org.csaude.mentoring.view.session.SessionActivity;
import mz.org.csaude.mentoring.view.session.SessionListActivity;

public class RondaSearchVM extends SearchVM<Ronda> implements IDialogListener, ServerStatusListener {
    private RondaType rondaType;

    private String title;

    private Ronda selectedRonda;

    private Dialog progress;

    public RondaSearchVM(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void doOnNoRecordFound() {
        getRelatedActivity().populateRecyclerView();
    }

    @Override
    public void preInit() {
    }

    public void createNewRonda() {
        Map<String, Object> params = new HashMap<>();
        params.put("rondaType", rondaType);
        params.put("title", title);
        getApplication().getApplicationStep().changetocreate();
        getRelatedActivity().nextActivityFinishingCurrent(CreateRondaActivity.class, params);
    }

    public RondaType getRondaType() {
        return rondaType;
    }

    public void setRondaType(RondaType rondaType) {
        this.rondaType = rondaType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public List<Ronda> doSearch(long offset, long limit) throws SQLException {
        return getApplication().getRondaService().getAllByRondaType(this.rondaType);
    }

    @Override
    public void displaySearchResults() {
        getRelatedActivity().populateRecyclerView();
    }

    @Override
    public RondaActivity getRelatedActivity() {
        return (RondaActivity) super.getRelatedActivity();
    }

    @Override
    public AbstractSearchParams<Ronda> initSearchParams() {
        return null;
    }

    public void goToMentoriships(Ronda ronda) {
        getApplication().getApplicationStep().changeToList();
        Map<String, Object> params = new HashMap<>();
        params.put("ronda", ronda);

        if (ronda.isRondaZero()) {
            Log.d("goToMentoriships", "Navigating to ZeroMentorshipListActivity with params: " + params);
            getRelatedActivity().nextActivity(ZeroMentorshipListActivity.class, params);
        } else {
            Log.d("goToMentoriships", "Navigating to SessionListActivity with params: " + params);
            getRelatedActivity().nextActivity(SessionListActivity.class, params);
        }
    }


    public void printRondaSummary(Ronda ronda) {
        this.selectedRonda = ronda;
        getRelatedActivity().checkStoragePermission();
    }

    public void printRondaReport() {
        try {
            List<RondaSummary> rondaSummaryList = new ArrayList<>();

            Ronda ronda = this.selectedRonda;
            ronda = getApplication().getRondaService().getFullyLoadedRonda(ronda);

            for (RondaMentee mentee : ronda.getRondaMentees()){
                RondaSummary rondaSummary = new RondaSummary();
                rondaSummary.setRonda(ronda);
                rondaSummary.setZeroEvaluation(mentee.getTutored().getZeroEvaluationScore());
                rondaSummary.setMentor(ronda.getActiveMentor().getEmployee().getFullName());
                rondaSummary.setMentee(mentee.getTutored().getEmployee().getFullName());
                rondaSummary.setNuit(mentee.getTutored().getEmployee().getNuit());
                List<Session> sessions = new ArrayList<>();
                for (Session session : ronda.getSessions()){
                    if (session.getTutored().equals(mentee.getTutored())){
                        sessions.add(session);
                    }
                }
                sessions.sort(Comparator.comparing(Session::getStartDate));
                rondaSummary.setSummaryDetails(new HashMap<>());
                int i = 1;
                for (Session session : sessions){
                    rondaSummary.getSummaryDetails().put(i, getApplication().getSessionService().generateSessionSummary(session));

                    i++;
                }
                rondaSummary.setSession1(determineSessionScore(rondaSummary.getSummaryDetails().get(1)));
                rondaSummary.setSession2(determineSessionScore(rondaSummary.getSummaryDetails().get(2)));
                rondaSummary.setSession3(determineSessionScore(rondaSummary.getSummaryDetails().get(3)));
                rondaSummary.setSession4(determineSessionScore(rondaSummary.getSummaryDetails().get(4)));
                rondaSummaryList.add(rondaSummary);
            }
            boolean print = PDFGenerator.createRondaSummary(getRelatedActivity(), rondaSummaryList);
            if (print) {
                String successMessage = getRelatedActivity().getString(R.string.ronda_print_success);
                Utilities.displayAlertDialog(getRelatedActivity(), successMessage).show();
            } else {
                String failureMessage = getRelatedActivity().getString(R.string.ronda_print_failure);
                Utilities.displayAlertDialog(getRelatedActivity(), failureMessage).show();
            }

        } catch (SQLException e) {
            Log.e("printRondaSummary", "Exception: " + e.getMessage());
        }


    }

    private double determineSessionScore(List<SessionSummary> sessionSummaries) {
        int yesCount = 0;
        int noCount = 0;
        for (SessionSummary sessionSummary : sessionSummaries){
            yesCount = yesCount + sessionSummary.getSimCount();
            noCount = noCount + sessionSummary.getNaoCount();
        }
        return (double) yesCount / (yesCount + noCount) *100;
    }

    public void edit(Ronda ronda) {
        // Perform the database query in a background thread
        getExecutorService().execute(() -> {
            try {
                // Fetch sessions in the background thread
                ronda.setSessions(getApplication().getSessionService().getAllOfRonda(ronda));

                // Check if the Ronda has sessions and update the UI on the main thread
                runOnMainThread(() -> {
                    if (Utilities.listHasElements(ronda.getSessions())) {
                        Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.ronda_edit_error_msg)).show();
                        return;
                    }

                    // Proceed to edit if there are no sessions
                    navigateToEditRonda(ronda);
                });

            } catch (SQLException e) {
                // Handle SQL exceptions on the main thread
                runOnMainThread(() -> {
                    Log.e("Ronda Search VM", "Exception: " + e.getMessage());
                    String errorMessage = getRelatedActivity().getString(R.string.ronda_session_error);
                    Utilities.displayAlertDialog(getRelatedActivity(), errorMessage).show();
                });
            }
        });
    }

    private void navigateToEditRonda(Ronda ronda) {
        // Prepare the parameters for the next activity
        Map<String, Object> params = new HashMap<>();
        params.put("ronda", ronda);

        // Change the application step to "edit"
        getApplication().getApplicationStep().changeToEdit();

        // Navigate to the CreateRondaActivity
        getRelatedActivity().nextActivity(CreateRondaActivity.class, params);
    }


    public void delete(Ronda ronda) {
        this.selectedRonda = ronda;

        getExecutorService().execute(() -> {
            try {
                List<Session> sessions = getApplication().getSessionService().getAllOfRonda(ronda);
                ronda.setSessions(sessions);

                runOnMainThread(() -> {
                    if (Utilities.listHasElements(ronda.getSessions())) {
                        String errorMessage = getRelatedActivity().getString(R.string.ronda_delete_error_msg);
                        Utilities.displayAlertDialog(getRelatedActivity(), errorMessage).show();
                        return;
                    }

                    String confirmationMessage = getRelatedActivity().getString(R.string.ronda_delete_confirmation);
                    Utilities.displayConfirmationDialog(getRelatedActivity(), confirmationMessage, getRelatedActivity().getString(R.string.yes), getRelatedActivity().getString(R.string.no), this).show();
                });

            } catch (SQLException e) {
                runOnMainThread(() -> {
                    Log.e("Ronda Search VM", "Exception: " + e.getMessage());
                    String errorMessage = getRelatedActivity().getString(R.string.ronda_session_error);
                    Utilities.displayAlertDialog(getRelatedActivity(), errorMessage).show();
                });
            }
        });
    }


    @Override
    public void doOnConfirmed() {
        this.progress = Utilities.showLoadingDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.processando));
        getApplication().getApplicationStep().changeToRemove();
        getApplication().isServerOnline(this);
    }

    @Override
    public void doOnDeny() {

    }

    @Override
    public void onServerStatusChecked(boolean isOnline) {

        if (isOnline) {
            if (getApplication().getApplicationStep().isApplicationStepRemove()) {
                getApplication().getRondaRestService().delete(selectedRonda, this);
            }
        }
    }

    @Override
    public void doOnResponse(String flag, List<Ronda> objects) {
        if (getApplication().getApplicationStep().isApplicationStepRemove()) {
            getApplication().getApplicationStep().changeToList();
            dismissProgress(this.progress);
            initSearch();
        }
    }

    @Override
    public void doOnRestErrorResponse(String errormsg) {
        if (getApplication().getApplicationStep().isApplicationStepRemove()) {
            getApplication().getApplicationStep().changeToList();
            dismissProgress(this.progress);
            Utilities.displayAlertDialog(getRelatedActivity(), errormsg).show();
        }
    }
}

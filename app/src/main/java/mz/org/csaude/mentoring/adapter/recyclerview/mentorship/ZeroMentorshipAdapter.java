package mz.org.csaude.mentoring.adapter.recyclerview.mentorship;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mz.org.csaude.mentoring.R;
import mz.org.csaude.mentoring.adapter.recyclerview.generic.AbstractRecycleViewAdapter;
import mz.org.csaude.mentoring.base.activity.BaseActivity;
import mz.org.csaude.mentoring.databinding.MentorshipListItemBinding;
import mz.org.csaude.mentoring.model.mentorship.Mentorship;
import mz.org.csaude.mentoring.viewmodel.mentorship.AbstractSearchMentorshipVM;

public class ZeroMentorshipAdapter extends AbstractRecycleViewAdapter<Mentorship> {
    public ZeroMentorshipAdapter(RecyclerView recyclerView, List<Mentorship> records, BaseActivity activity) {
        super(recyclerView, records, activity);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MentorshipListItemBinding mentorshipListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.mentorship_list_item, parent, false);
        return new MentorshipViewHolder(mentorshipListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MentorshipViewHolder) holder).mentorshipListItemBinding.setMentorship(records.get(position));
        ((MentorshipViewHolder) holder).mentorshipListItemBinding.setViewModel((AbstractSearchMentorshipVM) activity.getRelatedViewModel());
    }

    public class MentorshipViewHolder extends RecyclerView.ViewHolder {

        private MentorshipListItemBinding mentorshipListItemBinding;

        public MentorshipViewHolder(@NonNull MentorshipListItemBinding mentorshipListItemBinding) {
            super(mentorshipListItemBinding.getRoot());
            this.mentorshipListItemBinding = mentorshipListItemBinding;

        }
    }
}

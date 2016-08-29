package com.ghimire.swagat.merivaleapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class AnnounceFragment extends Fragment {
    public AnnounceFragment(){

    }
    Vector<String> rawText = new Vector<>(1,3);
    class Announcement{
        String headline, organization, details;

        Announcement(String headline, String organization, String details){
            this.headline = headline;
            this.organization = organization;
            this.details = details;
        }
    }
    protected List<Announcement> announcements;

    public class RVAdapter extends RecyclerView.Adapter<RVAdapter.AnnouncementViewHolder>{

        @Override
        public AnnouncementViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_announce_cv, viewGroup, false);
            AnnouncementViewHolder pvh = new AnnouncementViewHolder(v);
            return pvh;
        }

        @Override
        public int getItemCount() {
            return announcements.size();
        }

        List<Announcement> announcements;

        RVAdapter(List<Announcement> announcements){
            this.announcements = announcements;
        }

        @Override
        public void onBindViewHolder(AnnouncementViewHolder announcementViewHolder, int i) {
            announcementViewHolder.headline.setText(announcements.get(i).headline );
            String source = new StringBuilder().append("From: ").append(announcements.get(i).organization).toString();
            announcementViewHolder.organization.setText(source);
            announcementViewHolder.details.setText(announcements.get(i).details);
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        public class AnnouncementViewHolder extends RecyclerView.ViewHolder {
            CardView cv;
            TextView headline;
            TextView organization;
            TextView details;

            AnnouncementViewHolder(View itemView) {
                super(itemView);
                cv = (CardView)itemView.findViewById(R.id.ann_cv);
                headline = (TextView)itemView.findViewById(R.id.ann_headline);
                organization = (TextView)itemView.findViewById(R.id.ann_organization);
                details = (TextView)itemView.findViewById(R.id.ann_details);
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_announce, null);

        FetchAnnouncements getAnnouncements = new FetchAnnouncements();
        ProgressBar p1 = (ProgressBar) rootView.findViewById(R.id.progressBarAnn);
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.ann_rv);
        getAnnouncements.Initialize(p1, rv);
        rawText = getAnnouncements.returnVec();

        announcements = new ArrayList<>();
        if (rawText.size() > 3){
            for (int i = 3; i < rawText.size(); i+=3){
                announcements.add(new Announcement(rawText.elementAt(i), rawText.elementAt(i+2), rawText.elementAt(i+1)));
            }
        } else{
            announcements.add(new Announcement("No Announcements", "Swagat and Stefano :)", "This may be due to: school not being in session, a change in the school's announcement spreadsheet, or a number of other reasons. Sorry"));
        }

        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        RVAdapter adapter = new RVAdapter(announcements);
        rv.setAdapter(adapter);

        return rootView;
    }

}
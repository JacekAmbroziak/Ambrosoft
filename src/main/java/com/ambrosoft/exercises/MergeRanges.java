package com.ambrosoft.exercises;

import java.util.*;

/**
 * Created on 1/1/18
 */
public class MergeRanges {

    static class Meeting {
        private int startTime;
        private int endTime;

        Meeting(int startTime, int endTime) {
            // number of 30 min blocks past 9:00 am
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public int getStartTime() {
            return startTime;
        }

        public void setStartTime(int startTime) {
            this.startTime = startTime;
        }

        int getEndTime() {
            return endTime;
        }

        void setEndTime(int endTime) {
            this.endTime = endTime;
        }

        @Override
        public String toString() {
            return String.format("[%d, %d]", startTime, endTime);
        }
    }

    public static List<Meeting> mergeRanges(List<Meeting> meetings) {

        // make a copy so we don't destroy the input
        final List<Meeting> sortedMeetings = new ArrayList<>();
        for (Meeting meeting : meetings) {
            sortedMeetings.add(new Meeting(meeting.getStartTime(), meeting.getEndTime()));
        }

        // sort by start time
        Collections.sort(sortedMeetings, new Comparator<Meeting>() {
            @Override
            public int compare(Meeting m1, Meeting m2) {
                return m1.getStartTime() - m2.getStartTime();
            }
        });

        // initialize mergedMeetings with the earliest meeting
        final List<Meeting> mergedMeetings = new ArrayList<>();
        mergedMeetings.add(sortedMeetings.get(0));

        for (final Meeting currentMeeting : sortedMeetings) {
            final Meeting lastMergedMeeting = mergedMeetings.get(mergedMeetings.size() - 1);
            // if the current and last meetings overlap, use the latest end time == extend the last range
            if (currentMeeting.getStartTime() <= lastMergedMeeting.getEndTime()) {
                lastMergedMeeting.setEndTime(Math.max(lastMergedMeeting.getEndTime(), currentMeeting.getEndTime()));
            } else {// add the current meeting since it doesn't overlap
                mergedMeetings.add(currentMeeting);
            }
        }

        return mergedMeetings;
    }

    public static void main(String[] args) {
        System.out.println(
                mergeRanges(
                        Arrays.asList(
                                new Meeting(0, 1),
                                new Meeting(3, 5),
                                new Meeting(4, 8),
                                new Meeting(10, 12),
                                new Meeting(9, 10))));
    }

}
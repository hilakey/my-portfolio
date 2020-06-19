// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    //throw new UnsupportedOperationException("TODO: Implement this method.");
    Collection<TimeRange> freeMeetingTimes = new ArrayList<TimeRange>();
    List<TimeRange> unavailableTimes = new ArrayList<TimeRange>();
    int meetingDuration = (int) request.getDuration();
    String person;
    int eventStart;
    int eventDuration;

    //-----------Base Cases-------------------//

   //If the meeting request duration exceeds the whole day, then there are no meeting times available
   if(meetingDuration > TimeRange.WHOLE_DAY.duration()){
       return freeMeetingTimes;
    }

  //If the meeting request has no attendees or there are no events, then by default there are no conflicting events and so the MR has the whole day to chose a meeting time
   if(request.getAttendees().isEmpty() || events.isEmpty()){
      freeMeetingTimes.add(TimeRange.WHOLE_DAY);
      return freeMeetingTimes;
    }




    //A whole day is 1440 minutes
    //end of day is 1439 minutes
    //start of day is 0 minutes

   //If there are requested attendees for the meeting request find if there are unavailable times
   if(!request.getAttendees().isEmpty()){
       for(Event hasEvents: events) {
           for(String hasAttendee: hasEvents.getAttendees()){
               person = hasAttendee;

               //If the person in the events list matches with the requested attendee, continue to retrieve their event time ranges
               if(request.getAttendees().contains(person)){
                  System.out.println(person);
                  eventStart = hasEvents.getWhen().start();
                  eventDuration = hasEvents.getWhen().duration();
                  System.out.println("Event start " + eventStart);
                  System.out.println("Event end " + hasEvents.getWhen().end());
                  System.out.println("Event duration " + eventDuration);
                  System.out.println("=============================================");

                  unavailableTimes.add(TimeRange.fromStartDuration(eventStart, eventDuration));
                }
            }
          
        }

        System.out.println("Before sorting: " + unavailableTimes);
        //Sort the unavailable time ranges
        Collections.sort(unavailableTimes, TimeRange.ORDER_BY_START);
        System.out.println("After sorting: " + unavailableTimes);

        //If there is only one event, then return the available time that is before/after the event
        if(unavailableTimes.size() == 1){
          freeMeetingTimes.add(TimeRange.fromStartEnd(TimeRange.START_OF_DAY, unavailableTimes.get(0).start(), false));
          freeMeetingTimes.add(TimeRange.fromStartEnd(unavailableTimes.get(0).end(), TimeRange.END_OF_DAY, true));

          return freeMeetingTimes;
        }else{
            //loop through the unavailable times and check if they overlap with one another






        }
        
    }

    return freeMeetingTimes;

  }
}

package xyz.itshark.conf.talk.graphqltorescue.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.itshark.conf.talk.graphqltorescue.pojo.Attendee;
import xyz.itshark.conf.talk.graphqltorescue.pojo.Speaker;
import xyz.itshark.conf.talk.graphqltorescue.pojo.Talk;
import xyz.itshark.conf.talk.graphqltorescue.service.AttendeeService;
import xyz.itshark.conf.talk.graphqltorescue.service.SpeakerService;
import xyz.itshark.conf.talk.graphqltorescue.service.TalkService;

import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {

    @Autowired
    TalkService talkService;

    @Autowired
    SpeakerService speakerService;

    @Autowired
    AttendeeService attendeeService;

    public List<Talk> allTalks() {
        return talkService.findAll();
    }


    public List<Speaker> allSpeakers() {
        return speakerService.findAll();
    }

    public List<Attendee> allAttendees() {
        return attendeeService.findAll();
    }

    public List<Talk> allTalksForSpeaker(Long speakerId) {
        return talkService.findAllTalksBySpeakerId(speakerId);
    }

}

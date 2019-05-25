package xyz.itshark.conf.talk.graphqltorescue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.itshark.conf.talk.graphqltorescue.pojo.Attendee;
import xyz.itshark.conf.talk.graphqltorescue.pojo.Speaker;
import xyz.itshark.conf.talk.graphqltorescue.pojo.Talk;
import xyz.itshark.conf.talk.graphqltorescue.service.AttendeeService;
import xyz.itshark.conf.talk.graphqltorescue.service.SpeakerService;
import xyz.itshark.conf.talk.graphqltorescue.service.TalkService;

import java.util.List;

@RestController
public class DemoController {

    @Autowired
    SpeakerService speakerService;

    @Autowired
    TalkService talkService;

    @Autowired
    AttendeeService attendeeService;

    @RequestMapping("/speakers")
    public List<Speaker> allSpeakers() {
        return speakerService.findAll();
    }

    @RequestMapping("/speakers/{id}")
    public Speaker getSpeaker(@PathVariable Long id) {
        return speakerService.findById(id);
    }

    @RequestMapping("/talks")
    public List<Talk> getAllTalks(@RequestParam(name="speaker_id", required = false) Long speakerId) {
        if(speakerId!= null) {
            return talkService.findAllTalksBySpeakerId(speakerId);
        } else {
            return talkService.findAll();
        }
    }

    @RequestMapping("/attendees")
    public List<Attendee> getAllAttendees() {
        return attendeeService.findAll();
    }
}

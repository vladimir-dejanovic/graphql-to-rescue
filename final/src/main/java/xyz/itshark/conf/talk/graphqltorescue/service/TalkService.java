package xyz.itshark.conf.talk.graphqltorescue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.itshark.conf.talk.graphqltorescue.pojo.*;
import xyz.itshark.conf.talk.graphqltorescue.repository.AttendeeTalkRepository;
import xyz.itshark.conf.talk.graphqltorescue.repository.SpeakerTalkRepository;
import xyz.itshark.conf.talk.graphqltorescue.repository.TalkRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TalkService {

    @Autowired
    private TalkRepository talkRepository;

    @Autowired
    private SpeakerTalkRepository speakerTalkRepository;

    @Autowired
    private AttendeeTalkRepository attendeeTalkRepository;

    public List<Talk> findAll() {
        return talkRepository.findAll();
    }

    public List<Talk> findAllTalksBySpeakerId(Long speakerId) {
        List<SpeakerTalk> st = speakerTalkRepository.findAllBySpeakerId(speakerId);

        return st.stream()
                .map(e -> talkRepository.findById(e.getTalkId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

    }

    public List<Talk> findAllTalksBySpeaker(Speaker speaker) {
        return findAllTalksBySpeakerId(speaker.getId());
    }

    public List<Talk> findAllTAlksByAttendee(Attendee attendee) {
        List<AttendeeTalk> st = attendeeTalkRepository.findAllByAttendeeId(attendee.getId());

        return st.stream()
                .map(e -> talkRepository.findById(e.getTalkId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

    }
}

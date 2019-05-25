package xyz.itshark.conf.talk.graphqltorescue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.itshark.conf.talk.graphqltorescue.pojo.Speaker;
import xyz.itshark.conf.talk.graphqltorescue.pojo.SpeakerTalk;
import xyz.itshark.conf.talk.graphqltorescue.pojo.Talk;
import xyz.itshark.conf.talk.graphqltorescue.repository.SpeakerRepository;
import xyz.itshark.conf.talk.graphqltorescue.repository.SpeakerTalkRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpeakerService {

    @Autowired
    private SpeakerRepository speakerRepository;

    @Autowired
    private SpeakerTalkRepository speakerTalkRepository;

    public List<Speaker> findAll() {
        return speakerRepository.findAll();
    }

    public Speaker findById(Long id) {
        return (Speaker) speakerRepository.findById(id).orElseThrow( () -> new RuntimeException("No speaker for provided ID"));
    }

    public List<Speaker> findAllSpeakersForTalk(Talk talk) {
        List<SpeakerTalk> st = speakerTalkRepository.findAllByTalkId(talk.getId());

        return st.stream()
                .map(e -> speakerRepository.findById(e.getSpeakerId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public Speaker save(Speaker speaker) {
        return speakerRepository.save(speaker);
    }
}

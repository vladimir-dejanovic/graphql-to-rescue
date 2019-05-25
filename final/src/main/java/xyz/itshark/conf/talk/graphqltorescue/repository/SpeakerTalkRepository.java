package xyz.itshark.conf.talk.graphqltorescue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.itshark.conf.talk.graphqltorescue.pojo.SpeakerTalk;

import java.util.List;

@Repository
public interface SpeakerTalkRepository extends JpaRepository<SpeakerTalk,Long> {
    List<SpeakerTalk> findAllBySpeakerId(Long speakerId);
    List<SpeakerTalk> findAllByTalkId(Long talkId);
}

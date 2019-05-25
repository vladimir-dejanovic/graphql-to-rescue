package xyz.itshark.conf.talk.graphqltorescue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.itshark.conf.talk.graphqltorescue.pojo.AttendeeTalk;

import java.util.List;

@Repository
public interface AttendeeTalkRepository extends JpaRepository<AttendeeTalk, Long> {
    List<AttendeeTalk> findAllByAttendeeId(Long attendeeId);

    List<AttendeeTalk> findAllByTalkId(Long talkId);
}

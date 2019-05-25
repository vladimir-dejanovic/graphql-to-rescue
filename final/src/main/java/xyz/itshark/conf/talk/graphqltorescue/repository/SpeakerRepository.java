package xyz.itshark.conf.talk.graphqltorescue.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import xyz.itshark.conf.talk.graphqltorescue.pojo.Speaker;

@Repository
public interface SpeakerRepository extends JpaRepository<Speaker,Long> {
}

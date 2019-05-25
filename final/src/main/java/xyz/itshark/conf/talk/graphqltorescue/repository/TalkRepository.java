package xyz.itshark.conf.talk.graphqltorescue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.itshark.conf.talk.graphqltorescue.pojo.Talk;

@Repository
public interface TalkRepository extends JpaRepository<Talk, Long> {
}

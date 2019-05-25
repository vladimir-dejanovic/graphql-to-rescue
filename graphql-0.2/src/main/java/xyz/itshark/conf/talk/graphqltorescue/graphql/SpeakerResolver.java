package xyz.itshark.conf.talk.graphqltorescue.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.itshark.conf.talk.graphqltorescue.pojo.Speaker;
import xyz.itshark.conf.talk.graphqltorescue.pojo.Talk;
import xyz.itshark.conf.talk.graphqltorescue.service.TalkService;

import java.util.List;

@Component
public class SpeakerResolver implements GraphQLResolver<Speaker> {

    @Autowired
    TalkService talkService;

    public List<Talk> talks(Speaker speaker) {
        return talkService.findAllTalksBySpeaker(speaker);
    }
}

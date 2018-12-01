package xyz.itshark.conf.talk.graphqltorescue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xyz.itshark.conf.talk.graphqltorescue.service.AttendeeService;
import xyz.itshark.conf.talk.graphqltorescue.service.SpeakerService;
import xyz.itshark.conf.talk.graphqltorescue.service.TalkService;

@SpringBootApplication
public class GraphQLToRescue {

	public static void main(String[] args) {
		SpringApplication.run(GraphQLToRescue.class, args);
	}


}

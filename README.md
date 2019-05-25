# GraphQL to rescue

this is code example for my talk *What limitations and problems of REST API can be solved by GraphQL*

## Issue 1 - No spec &  documentation not up to date

Let us open code in init directory

We have no spec or documentation, so only thing we have is code.

Let us investigate what we have here.

### Open pom

We have here Spring Boot app with dependencies
- Web
- JPA/H2

### Find controller

- here we see all end points that we expose over REST API and how response look like if we follow code
- also we can see the full structure of code, since code isn't that big.

In case Code base is much bigger it wouldn't be so easy or fun to go this way.

### Solution to Issue 1

- add dependencies for graphql (uncomment them from pom.xml)

```
<!--  GraphQL -->
<dependency>
  <groupId>com.graphql-java</groupId>
  <artifactId>graphql-java</artifactId>
  <version>11.0</version>
</dependency>
<dependency>
  <groupId>com.graphql-java</groupId>
  <artifactId>graphql-java-tools</artifactId>
  <version>5.2.4</version>
</dependency>
<dependency>
  <groupId>com.graphql-java</groupId>
  <artifactId>graphql-spring-boot-starter</artifactId>
  <version>5.0.2</version>
</dependency>

<!-- Dev Only -->
<dependency>
  <groupId>com.graphql-java</groupId>
  <artifactId>graphiql-spring-boot-starter</artifactId>
  <version>5.0.2</version>
</dependency>
<!-- GraphQL end -->
```
Add schema to *schema.graphqls*

```
type Attendee {
    id: ID!
    name: String!
}

type Speaker {
    id: ID!
    name: String!
    twitter: String
}

type Talk {
    id: ID!
    # this is comment for title
    title: String!
    description: String
}

type Query {
    allTalks: [Talk]
    allSpeakers: [Speaker]
    allAttendees: [Attendee]
    allTalksForSpeaker(speakerId: Int) : [Talk]
}

schema {
    query: Query
}
```

Add Query resolver

```
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
```
Let us build the code and check result

In browser hit http://localhost:8080/graphiql

request all talks

```
query {
  allTalks {
    id
    title
    description
  }
}
```

request all speakers
```
query {  
  allSpeakers {
    id
    name
    twitter
  }
}
```

request all attendees
```
query {  
  allAttendees {
    id
    name
  }
}
```

request all talks for speaker
```
query {
  allTalksForSpeaker(speakerId:1) {
    id
    title
    description
  }
}
```

- There is validation happening of all input queries and responses, they need to match schema, so there is no wayt that code and schema are out of sync.
- Schema is mandatory so it has to be present.
- Schema is in the same time documentation, so it is also always present.

End result of code can be found in **graphql-0.1** directory.

## Issue 2 - client & server not in sync

In case of GraphQL this isn't the issue due to specification.
On init connection client will get spec from server and will not event sent incorrect requests according to specification.

## Issue 3 - Over fetching fetching

Solved by default by GraphQL

```
query {
  allTalks {
    id
    title
  }
}
```

## Issue 4 - Under fetching

In case we want to show Speaker details and also details about speaker talks, we need to make multiple calls to backend or we need to add new entry point that would return this specific combination. With GraphQL we can solve this in an easy way.

### Update graphql schema

```
type Speaker {
    id: ID!
    name: String!
    twitter: String
    talks: [Talk]
}
```

### update java code

Let us add new resolver

```
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
```

and now we can have query like

```
query {
  allSpeakers {
    name
    talks {
      title
      description
    }
  }
}
```

End result of code can be found in **graphql-0.2** directory.

## Issue 5 - naming conventions

Automatically solved by GraphQL

```
query {
  speakers:allSpeakers {
    speaker_name:name
    talks {
      title
      desc:description
    }
  }
}
```

## Issue 6 - different type of data in same request

### update GraphQL Schema

```
union Any = Speaker | Talk

type Query {
    allTalks: [Talk]
    allSpeakers: [Speaker]
    allAttendees: [Attendee]
    allTalksForSpeaker(speakerId: Int) : [Talk]
    allAny: [Any]
}
```

### Update java

```
@Component
public class Query implements GraphQLQueryResolver {

....

    public List<Object> allAny() {
        List list = speakerService.findAll();
        List list2 = talkService.findAll();

        list.addAll(list2);

        return list;
    }

}
```

### Test solution

```
query {
  allAny {
    ... on Speaker {
      name
    }
    ... on Talk {
      title
    }
  }
}
```

we can also use fragments in query

```
query {
  allAny {
    ... on Speaker {
      name
    }
    ...testFragment
  }
}


fragment testFragment on Talk {
  talk_title:title
}
```

if we need info about type we can get it in this way

```
query {
  allAny {
    __typename
    ... on Speaker {
      name
    }
    ...testFragment
  }
}


fragment testFragment on Talk {
  talk_title:title
}
```

End result of code can be found in **graphql-0.3** directory.

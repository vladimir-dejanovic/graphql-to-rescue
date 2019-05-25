package xyz.itshark.conf.talk.graphqltorescue.graphql;

import com.coxautodev.graphql.tools.GraphQLSubscriptionResolver;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import xyz.itshark.conf.talk.graphqltorescue.pojo.Score;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class Subscription implements GraphQLSubscriptionResolver {


    public Publisher<Score> scoreForTalk(String talk) {
        Observable<Score> observable = Observable.create( e -> {
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            executorService.scheduleAtFixedRate(() -> {
                Score score = new Score();
                score.setTalk(talk);
                score.setScore((int) Math.floor(Math.random()*10));
                e.onNext(score);
            }, 0, 2, TimeUnit.SECONDS);
        });

        ConnectableObservable connectableObservable = observable.share().publish();
        connectableObservable.connect();
        return connectableObservable.toFlowable(BackpressureStrategy.BUFFER);
    }

}

package xyz.itshark.conf.talk.graphqltorescue.graphql;

import com.coxautodev.graphql.tools.GraphQLSubscriptionResolver;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import xyz.itshark.conf.talk.graphqltorescue.pojo.Hello;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class Subscription implements GraphQLSubscriptionResolver {


    public Publisher<Hello> say() {
        Observable<Hello> observable = Observable.create( e -> {
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            executorService.scheduleAtFixedRate(() -> {
                Hello h = new Hello();
                h.setMessage("hello " + new Date().toString());
                e.onNext(h);
            }, 0, 2, TimeUnit.SECONDS);
        });

        ConnectableObservable connectableObservable = observable.share().publish();
        connectableObservable.connect();
        return connectableObservable.toFlowable(BackpressureStrategy.BUFFER);
    }

}

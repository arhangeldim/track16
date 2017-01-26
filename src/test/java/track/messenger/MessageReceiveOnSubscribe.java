package track.messenger;

import rx.Observable;
import rx.Subscriber;
import track.messenger.messages.server.ResultMessage;
import track.messenger.teacher.client.MessengerClient;
import track.messenger.teacher.client.ReceiveCallback;


public class MessageReceiveOnSubscribe implements Observable.OnSubscribe<ResultMessage>{
    MessengerClient client;

    public MessageReceiveOnSubscribe(MessengerClient client) {
        this.client = client;
    }

    @Override
    public void call(Subscriber<? super ResultMessage> subscriber) {
        client.setReceiveCallback(new ReceiveCallback() {
            @Override
            public void onRecieve(ResultMessage msg) {
                subscriber.onStart();
                subscriber.onNext(msg);
                subscriber.onCompleted();
            }
        });
    }
}

package a1000phone.com.rxandroiddemo01;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    private String tag = "MainActivity";
    private String TAG = "MainActivity";

    //这是create branch and pull request 的测试 内容

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        create();
//        thread();
//        sample01();
//        sample02();
//        just();
//        from();
//        range();
//        repeat();
//        work();
//        map();
//        map02();
//        flatMap();
//        mapVSflatMap();
//        workMap();
//        take();
//        distinct();
//        distinctUntilChanged();
//        interval();


//        concat();
        Observable.just("0","1","2").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG, "call: "+s );
            }
        });
        /**Retrolambda*/
        Observable.just("0","1","2").subscribe(s-> Log.e(TAG, "onCreate: "+s));

        String[] str = {"0","1","2","3","3","5"};
        Observable.from(str)
                .map(s-> Integer.parseInt(s))
                .filter(num->num>1)
                .distinct()
                .take(3)
                .subscribe(num-> Log.e(TAG, "onCreate: "+ num));

    }

    private void concat() {
        Observable.concat(Observable.just(0), Observable.just(1), Observable.just(2))
                .first()
                .subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: " );
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: "+integer );
            }
        });
    }

    private void interval() {
        Observable.just(0, 1, 2, 3).interval(1, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.e(TAG, "call: " + aLong);
            }
        });
    }

    private void distinctUntilChanged() {
        Observable.just(1, 2, 5, 3, 4, 5, 5, 5, 6).distinctUntilChanged().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.e(TAG, "call: " + integer);
            }
        });
    }

    private void distinct() {
        Observable.just(0, 1, 2, 3, 2, 2, 4, 5).distinct().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.e(TAG, "call: " + integer);
            }
        });
    }

    private void take() {
        Observable.just(1, 2, 3, 4, 5, 6).take(3).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.e(TAG, "call: " + integer);
            }
        });
    }

    private void workMap() {
        Integer[] ids = {R.mipmap.ic_launcher, R.mipmap.ic_launcher};
        Observable.from(ids)
                .map(new Func1<Integer, Bitmap>() {
                    @Override
                    public Bitmap call(Integer integer) {
                        Log.e(TAG, "call: " + Thread.currentThread().getName());
                        return BitmapFactory.decodeResource(getResources(), integer);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        Log.e(TAG, "call: " + bitmap.getWidth() + ">>>thread:" + Thread.currentThread().getName());
                    }
                });
    }

    private void mapVSflatMap() {
        Observable.just(2).map(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                return integer.toString();
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG, "call: " + s);
            }
        });
        Observable.just(2).flatMap(new Func1<Integer, Observable<String>>() {
            @Override
            public Observable<String> call(Integer integer) {
                return Observable.just(integer.toString());
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG, "call: " + s);
            }
        });
    }

    private void flatMap() {
        List<List<String>> data = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<String> list = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                list.add(j + ">>>");
            }
            data.add(list);
        }
        Observable.from(data)
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> strings) {
                        return Observable.from(strings);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG, "call: " + s);
                    }
                });
    }

    private void map02() {
        Observable.just(R.mipmap.ic_launcher).map(new Func1<Integer, Bitmap>() {
            @Override
            public Bitmap call(Integer integer) {
                return BitmapFactory.decodeResource(getResources(), integer);
            }
        }).subscribe(new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                Log.e(TAG, "call: " + bitmap.getWidth());
            }
        });
    }

    private void map() {
        Observable<Integer> observable = Observable.just(1, 2, 3, 4);

        Action1<String> action1 = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG, "call: " + s);
            }
        };
        observable.map(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                return integer.toString();
            }
        }).subscribe(action1);
    }

    private void work() {
         /*给出任意一组数字字符串数组
                得到大于1的数字
        进行输出*/
        String[] str = {"2", "3", "4", "5", "5", "6", "7"};
        Observable.from(str)
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return Integer.parseInt(s);
                    }
                })
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer > 3;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.e(TAG, "call: " + integer);
                    }
                });
    }

    /**
     * 重复发射
     */
    private void repeat() {
        Observable.just(1, 2, 3)
                .repeat(3)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.e(TAG, "call: " + integer);
                    }
                });
    }

    /**
     * 从“star”开始往后执行“多少”次命令
     */
    private void range() {
        Observable.range(5, 100)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.e(TAG, "call: " + integer);
                    }
                });
    }

    private void from() {
        String[] arr = {"a", "b", "c", "常来", "djdjd"};
        Observable.from(arr)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG, "call: " + s);
                    }
                });
    }

    private void just() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.e(TAG, "call: " + integer);
                    }
                });
    }

    /**
     * 最简洁的方式
     */
    private void sample02() {
        Observable.just("来一桶矿泉水", "加跟火腿")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(tag, "call: " + s);
                    }
                });
    }

    /**
     * 简洁的事项方式
     */
    private void sample01() {
        Observable<String> observable = Observable.just("来一桶矿泉水", "加跟火腿");
        Action1<String> action1 = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(tag, "call: " + s);
            }
        };
        observable.subscribe(action1);
    }

    /**
     * 线程的调度
     */
    private void thread() {
        tv = (TextView) findViewById(R.id.tv);
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                tv.setText(">>>" + s);
                Log.e(tag, "onNext: " + Thread.currentThread().getName());
            }
        };
        /**被观察者*/
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep(100);
                        subscriber.onNext(i + "");
                        Log.e(tag, "observable: " + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        /**主线程和子线程切换*/
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 基本的创建
     */
    private void create() {
        /**被观察者*/
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                /**给观察者发送指令*/
                subscriber.onNext("来一桶加牛肉的泡面");
                subscriber.onCompleted();
            }
        });
        /**观察者*/
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.e(tag, "撤退了");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e(tag, "onNext: " + s);
            }
        };
        /***将"观察者"绑定到 "被观察者"  上*/
        observable.subscribe(subscriber);
    }
}

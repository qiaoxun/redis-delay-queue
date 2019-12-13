# delay-queue
redis实现延迟消息队列



整个延迟队列由4个部分组成：

1. JobPool用来存放所有Job的元信息。
2. DelayBucket是一组以时间为维度的有序队列，用来存放所有需要延迟的Job（这里只存放Job Id）。
3. Timer负责实时扫描各个Bucket，并将delay时间大于等于当前时间的Job放入到对应的Ready Queue。
4. ReadyQueue存放处于Ready状态的Job（这里只存放JobId），以供消费程序消费。

![结构图](https://tech.youzan.com/content/images/2016/03/all-1.png)

#### 消息结构
每个Job必须包含一下几个属性：

1. topic：Job类型。可以理解成具体的业务名称。
2. id：Job的唯一标识。用来检索和删除指定的Job信息。
3. delayTime：jod延迟执行的时间，13位时间戳
4. ttr（time-to-run)：Job执行超时时间。单位：秒。主要是为了消息可靠性
5. message：Job的内容，供消费者做具体的业务处理，以json格式存储。

#### 举例说明一个Job的生命周期
1. 用户预约后，同时往JobPool里put一个job。job结构为：{‘topic':'book’, ‘id':'123456’, ‘delayTime’:1517069375398 ,’ttrTime':60 , ‘body':’{...}’}
2. timer 每隔一秒轮询delay queue，获取时间小于当前时间的10个job，将job从 delay queue 中删除，并放入 ready queue 中。
3. 消费端轮询对应的topic的ready queue，获取job后做自己的业务逻辑。



#### 参考：

https://github.com/yangwenjie88/delay-queue

参考[有赞延迟队列](https://tech.youzan.com/queuing_delay/)思路设计实现
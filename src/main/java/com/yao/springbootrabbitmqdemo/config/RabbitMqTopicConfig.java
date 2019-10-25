package com.yao.springbootrabbitmqdemo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述: Topic Exchange的使用配置
 *
 * @author pengjie_yao
 * @date 2019/10/911:53
 */
@Configuration
public class RabbitMqTopicConfig {

    /**
     * 只接收一个topic
     */
    final static String message = "topic.message";

    /**
     * 接收多个topic
     */
    final static String messages = "topic.messages";


    @Bean
    public Queue queueMessage() {
        return new Queue(RabbitMqTopicConfig.message);
    }

    @Bean
    public Queue queueMessages() {
        return new Queue(RabbitMqTopicConfig.messages);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("exchange");
    }

    /**
     * 队列与交换机的绑定
     *
     * @param queueMessage
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
    }

    /**
     * 绑定多个队列到交换机
     * 这里的Topic Exchange 转发消息主要是根据通配符，
     * 队列topic.message只能匹配topic.message的路由。
     * 而topic.messages匹配路由规则是topic.#，所以它可以匹配topic.
     * 开头的全部路由。而topic.#发送的消息也只能是topic.#的接受者才能接收。
     *
     * @param queueMessages
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        //这里的#表示零个或多个词。
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }

    /**
     * 測試一下版本回退
     */
    public void test() {
        System.out.println("測試一下版本回退");
    }

}

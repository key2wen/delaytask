package com.key.delay.server.biz.center.message;

import com.key.delay.server.biz.model.DelayTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by shuguang on 17/11/21.
 */

@Component
public class MessageComponent {


    Logger logger = LoggerFactory.getLogger("swallow");

    public void sendMail(DelayTask mailMessage) {



    }

    public void sendSms(DelayTask smsMessage) {

    }
}

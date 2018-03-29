package com.key.delay.server.biz.center.disconf;

//import com.baidu.disconf.client.common.annotations.DisconfFile;
//import com.baidu.disconf.client.common.update.DisconfKVMapUpdateLoader;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by shuguang on 17/11/18.
 */

@Component
@Scope("singleton")
//@DisconfFile(filename = "delayTopicConfig.properties")
//public class DelayTopicConfig extends DisconfKVMapUpdateLoader {
public class DelayTopicConfig  {


    private static volatile String[] delayTopicList;

    Logger logger = LoggerFactory.getLogger(DelayTopicConfig.class);

//    @Override
    public void initConfig(Map<String, String> configInfoMap) {


//        String delayTopicConfig = super.getValue("delayTopicList");
        String delayTopicConfig = null;
        if (StringUtils.isNotBlank(delayTopicConfig)) {
            String[] delayArray = delayTopicConfig.split(",");
            if (delayArray != null && delayArray.length > 0) {
                logger.info("=======>delayTopicList,first:{}, last:{}", delayArray[0], delayArray[delayArray.length - 1]);
            }
            delayTopicList = delayArray;
        }

    }

    public static String[] getDelayTopicList() {
        return delayTopicList;
    }
}


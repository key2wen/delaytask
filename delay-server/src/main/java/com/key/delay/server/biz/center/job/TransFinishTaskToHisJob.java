//package com.key.delay.server.biz.center.job;
//
//import com.key.delay.server.biz.center.TransferTaskToHisComponent;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * create by shuguang on 2017/11/21
// */
//@Component
//public class TransFinishTaskToHisJob extends AbstractJob {
//
//    private static final Logger logger = LoggerFactory.getLogger("swallow");
//
//    @Autowired
//    TransferTaskToHisComponent transferTaskToHisComponent;
//
//    @Override
//    public String execute(String param) {
//
//        if (isTerminated()) {
//            return "job terminated";
//        }
//
//        if (StringUtils.isNotBlank(param)) {
//            return transferTaskToHisComponent.transferMonth(Integer.parseInt(param));
//        } else {
//
//            String ret = "";
//
//            int[] months = {-3, -2, -1};
//            for (int month : months) {
//
//                if (isTerminated()) {
//                    return ret;
//                }
//
//                ret += transferTaskToHisComponent.transferMonth(month) + "|";
//            }
//
//            if (isTerminated()) {
//                return ret;
//            }
//            ret += transferTaskToHisComponent.transferAllTIme();
//            return ret;
//        }
//
//    }
//
//}
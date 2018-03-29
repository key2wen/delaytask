

CREATE TABLE `swallow_flow` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `biz_type` varchar(10) NOT NULL COMMENT '业务类型',
  `seq_id` varchar(100) NOT NULL COMMENT '业务订单号:通过业务类型和工具生成',
  `task_id` bigint(20) DEFAULT NULL COMMENT '消息任务编号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_biz_type_order` (`biz_type`,`seq_id`),
  KEY `idx_task_id` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `swallow_delay_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务编号',
  `topic` varchar(60) NOT NULL COMMENT '任务分组code,对应mq形式的tag',
  `delay` bigint(20) NOT NULL COMMENT '延迟时间',
  `ttr` bigint(20) NOT NULL COMMENT '任务执行超时时长',
  `fail_num` tinyint(4) DEFAULT NULL COMMENT '失败次数',
  `status` tinyint(4) NOT NULL COMMENT '状态：1.正常延迟中；2.消费延迟中；3.可消费状态；4.消费完成；5.超时关闭',
  `body` varchar(256) NOT NULL COMMENT '自定义任务内容json',
  `next_exec_time` bigint(20) NOT NULL COMMENT '下次执行时间，单位秒',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_next_exec_time` (`next_exec_time`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='延迟任务';
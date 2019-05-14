-- 主表字段注释
ALTER TABLE `tqj_cdbiz`
  CHANGE COLUMN `code` `code` VARCHAR(32) NOT NULL COMMENT '编号' ,
  CHANGE COLUMN `type` `type` VARCHAR(4) NULL DEFAULT NULL COMMENT '类型' ,
  CHANGE COLUMN `biz_type` `biz_type` VARCHAR(4) NULL DEFAULT NULL COMMENT '业务类型（0新车，1二手车）' ,
  CHANGE COLUMN `repay_biz_code` `repay_biz_code` VARCHAR(32) NULL DEFAULT NULL COMMENT '还款业务编号' ,
  CHANGE COLUMN `company_code` `company_code` VARCHAR(32) NULL DEFAULT NULL COMMENT '公司编号' ,
  CHANGE COLUMN `team_code` `team_code` VARCHAR(32) NULL DEFAULT NULL COMMENT '团队编号' ,
  CHANGE COLUMN `status` `status` VARCHAR(4) NULL DEFAULT NULL COMMENT '主状态' ,
  CHANGE COLUMN `mq_status` `mq_status` VARCHAR(4) NULL DEFAULT NULL COMMENT '面签状态' ,
  CHANGE COLUMN `fbhgps_status` `fbhgps_status` VARCHAR(4) NULL DEFAULT NULL COMMENT '发保合gps状态' ,
  CHANGE COLUMN `fircundang_status` `fircundang_status` VARCHAR(4) NULL DEFAULT NULL COMMENT '第一次入档状态' ,
  CHANGE COLUMN `seccundang_status` `seccundang_status` VARCHAR(4) NULL DEFAULT NULL COMMENT '第二次入档状态' ,
  CHANGE COLUMN `zf_status` `zf_status` VARCHAR(4) NULL DEFAULT NULL COMMENT '作废状态' ,
  CHANGE COLUMN `cur_node_code` `cur_node_code` VARCHAR(5) NULL DEFAULT NULL COMMENT '主流程节点' ,
  CHANGE COLUMN `captain_code` `captain_code` VARCHAR(32) NULL DEFAULT NULL COMMENT '团队长编号' ,
  CHANGE COLUMN `inside_job` `inside_job` VARCHAR(32) NULL DEFAULT NULL COMMENT '内勤编号';

# 添加修改主表字段
ALTER TABLE `tqj_cdbiz`
  DROP COLUMN `seccundang_status`,
  CHANGE COLUMN `mq_status` `intev_status` VARCHAR(4) NULL DEFAULT NULL COMMENT '面签状态' ,
  CHANGE COLUMN `fircundang_status` `enter_status` VARCHAR(4) NULL DEFAULT NULL COMMENT '第一次入档状态' ,
  CHANGE COLUMN `zf_status` `cancel_status` VARCHAR(4) NULL DEFAULT NULL COMMENT '作废状态' ,
  ADD COLUMN `enter_node_code` VARCHAR(5) NULL COMMENT '入档节点' AFTER `fbhgps_node`;


INSERT INTO `tdq_file_list` (`category`, `kname`, `vname`, `attach_type`, `number`)
VALUES ('car_pledge', 'pledge_user_id_card_front', '代理人身份证正', '图片', '1');
INSERT INTO `tdq_file_list` (`category`, `kname`, `vname`, `attach_type`, `number`)
VALUES ('car_pledge', 'pledge_user_id_card_reverse', '代理人身份证反', '图片', '1');

# 修改车辆抵押字段
ALTER TABLE `tdq_car_pledge`
  CHANGE COLUMN `pledge_user_id_card_copy` `pledge_user_id_card` TINYTEXT NULL DEFAULT NULL COMMENT '代理人身份证号';

ALTER TABLE `tdq_car_pledge`
  CHANGE COLUMN `pledge_user_id_card_copy` `pledge_user_id_card` TINYTEXT NULL DEFAULT NULL COMMENT '代理人身份证复印件';

INSERT INTO `tsys_node` (`code`, `name`, `type`)
VALUES ('f15', '确认存档', 'f');

INSERT INTO `tsys_node` (`code`, `name`, `type`)
VALUES ('f7', '风控审核通过（车辆抵押）', 'f');

INSERT INTO `dev_xn_htwt_new`.`tsys_node` (`code`, `name`, `type`)
VALUES ('c3', '发保合完成', 'c');



DROP TABLE IF EXISTS `tdq_enter_file_list`;
CREATE TABLE `tdq_enter_file_list`
(
  `code`              varchar(32) NOT NULL COMMENT '编号',
  `biz_code`          varchar(32)  DEFAULT NULL COMMENT '业务编号',
  `content`           text         DEFAULT NULL COMMENT '内容',
  `file_count`        int(11)      DEFAULT NULL COMMENT '份数',
  `deposit_date_time` datetime     DEFAULT NULL COMMENT '存放位置',
  `operator`          varchar(32)  DEFAULT NULL COMMENT '存放人',
  `remark`            varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='档案存放清单';
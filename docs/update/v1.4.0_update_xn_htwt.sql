ALTER TABLE `tdq_budget_order` 
ADD COLUMN `intev_cur_node_code` varchar(32) NULL COMMENT '面签节点编号' AFTER `cur_node_code`;

UPDATE `tsys_node_flow` SET `next_node`='002_07' WHERE `id`='63';

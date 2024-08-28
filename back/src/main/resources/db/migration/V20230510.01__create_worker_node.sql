CREATE TABLE worker_node
(
	-- auto increment id
	ID bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'auto increment id',
	-- host name
	HOST_NAME varchar(64) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT 'host name',
	-- port
	PORT varchar(64) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT 'port',
	-- node type: ACTUAL or CONTAINER
	TYPE int NOT NULL COMMENT 'node type: ACTUAL or CONTAINER',
	-- launch date
	LAUNCH_DATE date NOT NULL COMMENT 'launch date',
	-- modified time
	MODIFIED timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'modified time',
	-- created time
	CREATED timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'created time',
	PRIMARY KEY (ID)
)
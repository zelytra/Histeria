CREATE TABLE `Ban` (
  `uuid` char(64) NOT NULL DEFAULT '0',
  `name` char(25) DEFAULT NULL,
  `startTime` mediumtext DEFAULT 0,
  `time` mediumtext NOT NULL DEFAULT 0,
  `reason` varchar(500) DEFAULT NULL,
  `staff` char(25) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `Mute_uuid_uindex` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Bank` (
  `id` int(11) NOT NULL,
  `uuid` varchar(40) NOT NULL,
  `name` varchar(25) NOT NULL,
  `money` int(11) NOT NULL DEFAULT 3000,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Bank_id_uindex` (`id`),
  UNIQUE KEY `Bank_uuid_uindex` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Mute` (
  `uuid` char(64) NOT NULL DEFAULT '0',
  `name` char(25) DEFAULT NULL,
  `startTime` mediumtext DEFAULT 0,
  `time` mediumtext NOT NULL DEFAULT 0,
  `reason` varchar(500) DEFAULT NULL,
  `staff` char(25) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `Mute_uuid_uindex` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Player` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(40) NOT NULL,
  `name` varchar(25) NOT NULL,
  `ip` varchar(20) NOT NULL DEFAULT '0.0.0.0',
  `kill` int(11) NOT NULL DEFAULT 0,
  `death` int(11) NOT NULL DEFAULT 0,
  `playTime` int(11) NOT NULL DEFAULT 0,
  `primconnection` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `lang` varchar(5) NOT NULL DEFAULT 'EN',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Player_id_uindex` (`id`),
  UNIQUE KEY `Player_uuid_uindex` (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Shop` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `itemName` varchar(100) NOT NULL,
  `type` varchar(10) NOT NULL,
  `sellPrice` int(11) NOT NULL DEFAULT 0,
  `buyPrice` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=297 DEFAULT CHARSET=utf8mb4;

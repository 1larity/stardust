delimiter $$

CREATE TABLE `surnames` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(20) NOT NULL,
  `race` varchar(3) NOT NULL,
  `sex` varchar(1) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1 COMMENT='all sur names'$$




insert into surnames (select NULL,surname, "rus" from russiansurname)
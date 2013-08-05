-- DROP SCHEMA ciscowt_db
-- DROP USER ciscowt_user@localhost

CREATE SCHEMA `ciscowt_db` DEFAULT CHARACTER SET utf8;

grant all privileges on ciscowt_db.* to ciscowt_user@localhost identified by 'welcome' with grant option;

USE ciscowt_db;

CREATE TABLE `user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `admin` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `approval` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `signoff_id` bigint(11) DEFAULT NULL,
  `approver_id` bigint(11) DEFAULT NULL,
  `approvedDate` datetime DEFAULT NULL,
  `approverType_id` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `approvertype` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `file` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `filetype_id` bigint(11) DEFAULT NULL,
  `signoff_id` bigint(11) DEFAULT NULL,
  `filesource` varchar(20) DEFAULT NULL,
  `path` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `filetype` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `product` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `signoff` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(11) DEFAULT NULL,
  `version` varchar(45) DEFAULT NULL,
  `releaseDate` varchar(45) DEFAULT NULL,
  `releaseType` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


alter table `approval` add  CONSTRAINT `approval_signoff_id_fkey`
    FOREIGN KEY (`signoff_id` )
    REFERENCES `signoff` (`id` )
    ON DELETE CASCADE;
    
alter table `approval` add  CONSTRAINT `approval_approver_id_fkey`
    FOREIGN KEY (`approver_id` )
    REFERENCES `user` (`id` )
    ON DELETE SET NULL;

alter table `approval` add  CONSTRAINT `approval_approverType_id_fkey`
    FOREIGN KEY (`approverType_id` )
    REFERENCES `approvertype` (`id` )
    ON DELETE SET NULL;
    
    
alter table `file` add  CONSTRAINT `file_filetype_id_fkey`
    FOREIGN KEY (`filetype_id` )
    REFERENCES `filetype` (`id` )
    ON DELETE SET NULL;

alter table `file` add  CONSTRAINT `file_signoff_id_fkey`
    FOREIGN KEY (`signoff_id` )
    REFERENCES `signoff` (`id` )
    ON DELETE CASCADE;
    
alter table `signoff` add  CONSTRAINT `signoff_product_id_fkey`
    FOREIGN KEY (`product_id` )
    REFERENCES `product` (`id` )
    ON DELETE SET NULL;


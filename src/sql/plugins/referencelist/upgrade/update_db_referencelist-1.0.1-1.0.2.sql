
--
-- Structure for table referencelist_itemvalue
--

DROP TABLE IF EXISTS referencelist_itemvalue;
CREATE TABLE referencelist_itemvalue (
	id_itemvalue int AUTO_INCREMENT,
	lang varchar(10) NOT NULL,
	value long varchar NOT NULL,
	id_reference_item int NOT NULL,
	PRIMARY KEY (id_itemvalue)
);

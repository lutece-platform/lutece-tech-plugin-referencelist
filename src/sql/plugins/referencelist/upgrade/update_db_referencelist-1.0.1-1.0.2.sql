
--
-- Structure for table referencelist_translation
--

DROP TABLE IF EXISTS referencelist_translation;
CREATE TABLE referencelist_translation (
	id_translation int AUTO_INCREMENT,
	lang varchar(10) NOT NULL,
	value long varchar NOT NULL,
	id_reference_item int NOT NULL,
	PRIMARY KEY (id_translation)
);

--
-- Sample for referencelist_reference
--
TRUNCATE TABLE referencelist_reference;
TRUNCATE TABLE referencelist_item;
TRUNCATE TABLE referencelist_translation;

INSERT INTO referencelist_reference ( name, description ) VALUES 
( 'categories', 'Liste des catégories de composants \'starters\'' ),
( 'content_management', 'Gestion de contenus');

INSERT INTO referencelist_item ( code, name, idreference ) VALUES 
( 'content_management', 'Gestion de contenus', 1),
( 'communication', 'Communication', 1),
( 'technical_services', 'Services techniques', 1),
( 'extensions', 'Extensions de contenus', 1),
( 'authentication', 'Authentification', 1);






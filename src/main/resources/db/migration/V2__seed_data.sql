
/* ADD USERS */
INSERT INTO app_user (username, password, firstName, lastName, email) VALUES('misrule', 's3cr3t', 'Sy', 'Smythe', 'sy@example.com');
INSERT INTO app_user (username, password, firstName, lastName, email) VALUES('dora', 's3cr3t', 'Dorota', 'Letowska', 'dora@example.com');

/* ADD WIDGETS */

/*
INSERT INTO widget (name, owner, description) VALUES('alpha', 1, 'The first widget ever!');
INSERT INTO widget (name, owner) VALUES('beta', 1, '');
INSERT INTO widget (name, owner) VALUES('charlie', 1);
INSERT INTO widget (name, owner) VALUES('delta', 2);
INSERT INTO widget (name, owner) VALUES('echo', 2);
INSERT INTO widget (name, owner) VALUES('foxtrot', 2);
*/

INSERT INTO widget(name, owner, kind, image, description) VALUES('alpha',1, 'kind1', 'image1','The first widget ever!');
INSERT INTO widget(name, owner, kind, image, description) VALUES('beta',1, 'kind1', 'image1','the beta widget.');
INSERT INTO widget(name, owner, kind, image, description) VALUES('delta',2, 'kind1', 'image1','The delta widget.');
INSERT INTO widget(name, owner, kind, image, description) VALUES('echo',2, 'kind2', 'image2','The echo widget.');
INSERT INTO widget(name, owner, kind, image, description) VALUES('foxtrot',2, 'kind3', 'image3','The foxtrot widget.');




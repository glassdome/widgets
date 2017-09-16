

CREATE TABLE app_user (
  id  	    BIGSERIAL PRIMARY KEY,
  username  VARCHAR NOT NULL,
  password  VARCHAR NOT NULL,
  firstName VARCHAR,
  lastName  VARCHAR,
  email     VARCHAR NOT NULL,

  CONSTRAINT user_username_unique UNIQUE (username),
  CONSTRAINT user_email_unique UNIQUE (email)
);


CREATE TABLE widget(
  id  			BIGSERIAL PRIMARY KEY,
  name 			VARCHAR NOT NULL,
  owner  		BIGINT REFERENCES app_user ON DELETE CASCADE,
  kind          VARCHAR,
  image         VARCHAR,
  description 	VARCHAR,
  created 		TIMESTAMPTZ DEFAULT NOW(),

  CONSTRAINT widget_name_owner_unique UNIQUE (name, owner)
);


CREATE TABLE widget_share (
  owner  	BIGINT REFERENCES app_user ON DELETE CASCADE,
  widget 	BIGINT REFERENCES widget ON DELETE CASCADE,
  app_user	BIGINT REFERENCES app_user ON DELETE CASCADE,
  
  CONSTRAINT pk_widget_share PRIMARY KEY (owner, widget, app_user)
);


/*
inviter
invitee
widget
message [opt]
wtatus 	(open|accepted|rejected)
created
expires 	[opt] default: never
*/
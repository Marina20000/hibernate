CREATE TABLE post (
    Id SERIAL PRIMARY KEY,
    Name CHARACTER VARYING(30)
);

CREATE TABLE post_comment (
    Id SERIAL PRIMARY KEY,
    comment CHARACTER VARYING(30),
	post_id SERIAL,
	FOREIGN KEY (post_id)       ****  REFERENCES post (id) ON DELETE CASCADE; ***
);

CREATE OR REPLACE FUNCTION delete_post_comment()   RETURNS trigger AS
$$
BEGIN
RAISE NOTICE '% %', (OLD.name, OLD.id);
         DELETE FROM post_comment where post_id=OLD.id;
		 return OLD;
END;
$$
LANGUAGE 'plpgsql';

CREATE OR REPLACE TRIGGER delete_post_comment_trigger
  BEFORE DELETE ON post
  FOR EACH ROW
  EXECUTE PROCEDURE delete_post_comment();

delete from post where id='1';



insert into post(name) values ('post1');
insert into post_comment (comment, post_id) values ('comment1', '1');

delete from post where id='1';

DROP TRIGGER delete_post_comment_trigger on post;
drop FUNCTION delete_post_comment
--Create the bank_user table
CREATE TABLE bank_user(bank_user_id serial not null primary key,
					   bank_user_f_name text not null, 
					   bank_user_l_name text not null, 
					   bank_user_user_name text not null, 
					   bank_user_pass_word text not null);
----------------------------------------------------------------------------------------
--Create the bank_checking_account table
CREATE TABLE bank_checking_account (ch_account_id serial not null primary key,
								    ch_account_bal numeric(15,2), 
								    bank_user_id serial references bank_user(bank_user_id));
----------------------------------------------------------------------------------------
--Create the bank_saving_account table
CREATE TABLE bank_saving_account (sv_account_id serial not null primary key,
							      sv_account_bal numeric(15,2),
							      bank_user_id serial references bank_user(bank_user_id));

							     
							     
							     
--Insert three default records to each.
----------------------------------------------------------------------------------------
--John Dobson
INSERT INTO bank_user (bank_user_f_name, 
					   bank_user_l_name, 
					   bank_user_user_name, 
					   bank_user_pass_word )
VALUES ('John', 'Dobson', 'Johndob','password');

INSERT INTO bank_checking_account (ch_account_bal,
								   bank_user_id)
VALUES (10423.34, (select bank_user_id from bank_user where bank_user_f_name = 'John'));

INSERT INTO bank_saving_account (sv_account_bal,
								 bank_user_id)
VALUES (223452.99, (select bank_user_id from bank_user where bank_user_f_name = 'John'));
----------------------------------------------------------------------------------------
--Karen Gillian
INSERT INTO bank_user (bank_user_f_name, 
					   bank_user_l_name, 
					   bank_user_user_name, 
					   bank_user_pass_word )
VALUES ('Karen', 'Gillian', 'PumpkinPie','S3creT\/\/0rd');

INSERT INTO bank_checking_account (ch_account_bal,
								   bank_user_id)
VALUES (233.43, (select bank_user_id from bank_user where bank_user_f_name = 'Karen'));

INSERT INTO bank_saving_account (sv_account_bal,
								 bank_user_id)
VALUES (779.99, (select bank_user_id from bank_user where bank_user_f_name = 'Karen'));
----------------------------------------------------------------------------------------
--Laney Carroway
INSERT INTO bank_user (bank_user_f_name, 
					   bank_user_l_name, 
					   bank_user_user_name, 
					   bank_user_pass_word )
VALUES ('Laney', 'Carroway', 'livelove','passphrase');

INSERT INTO bank_checking_account (ch_account_bal,
								   bank_user_id)
VALUES (1000.00, (select bank_user_id from bank_user where bank_user_f_name = 'Laney'));

INSERT INTO bank_saving_account (sv_account_bal,
								 bank_user_id)
VALUES (6666.66, (select bank_user_id from bank_user where bank_user_f_name = 'Laney'));
----------------------------------------------------------------------------------------

--get_user gets the user's id given a username and password. Also used to check if user exists.
create or replace function get_user (user_name text, pass_word text)
returns integer as $id$
declare
	id integer;
begin
	select bank_user_id 
	into id 
	from bank_user 
	where bank_user_user_name = user_name 
	and bank_user_pass_word  = pass_word;
	return id;
end;
$id$ language plpgsql;
---------------------------------------------------------

--get_user_ch gets the user's checking balance given an id.
create or replace function get_user_ch (user_id integer)
returns numeric as $bal$
declare
	bal numeric;
begin
	select ch_account_bal 
	into bal 
	from bank_checking_account 
	where bank_user_id = user_id;
	return bal;
end;
$bal$ language plpgsql;
---------------------------------------------------------

--get_user_sv gets the user's saving balance given an id.
create or replace function get_user_sv (user_id integer)
returns numeric as $bal$
declare
	bal numeric;
begin
	select sv_account_bal 
	into bal 
	from bank_saving_account 
	where bank_user_id = user_id;
	return bal;
end;
$bal$ language plpgsql;
-------------------------------------------------------------------

--get_user_ch gets the user's first name given an id.
create or replace function get_user_fn (user_id integer)
returns text as $fname$
declare
	fname text;
begin
	select bank_user_f_name
	into fname 
	from bank_user 
	where bank_user_id = user_id;
	return fname;
end;
$fname$ language plpgsql;
-------------------------------------------------------------------

--get_user_ch gets the user's last name given an id.
create or replace function get_user_ln (user_id integer)
returns text as $lname$
declare
	lname text;
begin
	select bank_user_l_name
	into lname 
	from bank_user 
	where bank_user_id = user_id;
	return lname;
end;
$lname$ language plpgsql;
-------------------------------------------------------------------

--insert_new creates a new user in bank_user.
create or replace function insert_new (fname text, lname text, user_name text, pass_word text)
returns void as $BODY$
begin
	
	INSERT INTO bank_user(bank_user_f_name, bank_user_l_name, bank_user_user_name, bank_user_pass_word) 
	VALUES (fname, lname, user_name, pass_word);
end;
$BODY$ language plpgsql;
-------------------------------------------------------------------

--insert_new_ch creates a checking account in bank_checking_account. Default amount is always 0.00
create or replace function insert_new_ch (user_name text, pass_word text)
returns void as $BODY$
begin
	INSERT INTO bank_checking_account(bank_user_id, ch_account_bal) 
	VALUES ((SELECT bank_user_id FROM bank_user 
	WHERE bank_user_user_name = user_name AND bank_user_pass_word = pass_word), 0.00);
end;
$BODY$ language plpgsql;
-------------------------------------------------------------------

--insert_new_sv creates a saving account in bank_checking_account. Default amount is always 0.00
create or replace function insert_new_sv (user_name text, pass_word text)
returns void as $BODY$
begin
	INSERT INTO bank_saving_account(bank_user_id, sv_account_bal) 
	VALUES ((SELECT bank_user_id FROM bank_user 
	WHERE bank_user_user_name = user_name AND bank_user_pass_word = pass_word), 0.00);
end;
$BODY$ language plpgsql;
-------------------------------------------------------------------

--update_ch function updates the amount of a sepcified checking account.
create or replace function update_ch (user_id integer, amount double precision)
returns void as $BODY$
begin
	update bank_checking_account
	set ch_account_bal = amount
	where bank_user_id = user_id;
end;
$BODY$ language plpgsql;

select update_ch(24, 34594.85);
-------------------------------------------------------------------

--update_sv function updates the amount of a sepcified saving account.
create or replace function update_sv (user_id integer, amount double precision)
returns void as $BODY$
begin
	update bank_saving_account
	set sv_account_bal = amount
	where bank_user_id = user_id;
end;
$BODY$ language plpgsql;

--That's it.

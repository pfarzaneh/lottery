insert into lottery(`name`,create_date,award, ballot_price, ballot_unit, state)
select * from (select 'Black Friday', now(), 'BMW X4', 200, 'DOLLAR', 'ACTIVE') x
where not exists(select * from lottery);
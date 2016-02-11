
select * from frenchsurname where uid >=(select  floor((1 + (rand() * (select count(*) from `frenchsurname`)))) as surname_id)

SELECT * FROM `frenchsurname` WHERE uid >= (SELECT FLOOR( MAX(uid) * RAND()) FROM `frenchsurname` ) ORDER BY uid LIMIT 1;

SELECT uid FROM `frenchfemnames` ORDER BY RAND() LIMIT 1

select firstnames.firstname,surnames.surname from surnames, firstnames where 
(surnames.uid=(select * from getrandomfrsurname)
and
firstnames.uid=(select * from getrandomfrfemname))

select firstnames.firstname from firstnames where uid=(select * from getrandomfrfemname)

update npc 
set npc.x=landscape.x
WHERE  (
select landscape.x
from landscape ORDER BY RAND() LIMIT 1 )

insert into player_account (select * from testbackup.player_account)

update npc set race="rus" where uid> 10

values(
select landscape.x,landscape.y,landscape.z from landscape ORDER BY RAND() LIMIT 1
)
select 

insert into npc (`race`,`firstnames_uid`, `surnames_uid`)  values("fr", (select * from getrandomfrmalename),(select * from getrandomfrsurname));

select npc.race, 
npc.x,
npc.y,
npc.z,
npc.static,
firstnames.firstname,
surnames.surname 
from firstnames,surnames,npc
where (npc.firstnames_uid=firstnames.uid 
and npc.surnames_uid=surnames.uid)

select * from landscape where (x=2 and y=2)
select `firstnames`.`firstname` AS `firstname`,`surnames`.`surname` AS `surname` from (`surnames` join `firstnames`) 
where ((`surnames`.`uid` = (select `getrandomfrsurname`.`uid` from `getrandomfrsurname`)) 
and (`firstnames`.`uid` = (select `getrandomfrmalename`.`uid` from `getrandomfrmalename`)))

insert into npc (`race`,`firstnames_uid`, `surnames_uid`)
values("fr", 
(select * from getrandomfrmalename),
(select * from getrandomfrsurname)
)
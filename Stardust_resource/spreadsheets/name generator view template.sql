select `firstnames`.`firstname` AS `firstname`,
`surnames`.`surname` AS `surname`, 
firstnames.race as race
from (`firstnames` join `surnames`) 
where `firstnames`.`uid` = (select *from `getrandomfrfemname`)
and
`surnames`.`uid` = (select *from `getrandomfrsurname`)






select floor((1 + (rand() * (select count(0) from `firstnames`Where (sex="f" and race="fr"))))) AS `firstname_id` 


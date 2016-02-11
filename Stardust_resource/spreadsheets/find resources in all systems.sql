
select distinct `systemnames`.`sysname` AS `sysname`,
`landscape`.`x` AS `x`,
`landscape`.`y` AS `y`,
`landscape`.`z` AS `z`,
`landscape`.`ground` AS `ground`,
`stucttypes`.`name` 
from (`systemnames` join `landscape` join `structures`join`stucttypes`) 
where ((`systemnames`.`x` = `landscape`.`x`) 
and (`systemnames`.`y` = `landscape`.`y`) 
and (`systemnames`.`z` = `landscape`.`z`)
and (`structures`.`x`=`landscape`.`x`)
and (`structures`.`y`=`landscape`.`y`)
and (`structures`.`z`=`landscape`.`z`)
and (`structures`.`stucttypes_uid`=`stucttypes`.`uid`))

 SELECT RAND(1), RAND( ), RAND( );
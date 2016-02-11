select 
    player_char.uid AS uid,
    player_char.firstname AS firstname,
    player_char.surname AS surname,
    player_char.stamina AS stamina,
    player_char.intelligence AS intelligence,
    player_char.social AS social,
    player_char.dexterity AS dexterity,
    player_char.leadership AS leadership,
    player_char.recuperation AS recuperation,
    player_char.exp AS exp,
    player_char.slots AS slots,
    player_char.player_account_UID AS player_account_UID,
    player_char.x AS x,
    player_char.y AS y,
    player_char.z AS z,
    systems.sysname AS sysname,
    player_char.sysx AS sysx,
    player_char.sysy AS sysy,
    player_char.sysz AS sysz,
    player_char.hitpoints AS hitpoints,
    player_char.firstattackeruid AS firstattackeruid,
    player_char.firstattacktime AS firstattacktime,
    player_char.lastattackeruid AS lastattackeruid,
    player_char.lastattacktime AS lastattacktime,
    player_char.expvalue AS expvalue,
    player_char.creditvalue AS creditvalue,
    player_char.status AS status,
    item.item AS shipname,
    player_char.yawangle as yawangle,
    player_char.pitchangle as pitchangle,
    player_char.lastupdate as lastupdate
from
   (((player_char
    join systems)
    join inventory)
    join item)
where
    ((player_char.x = systems.x) and (player_char.y = systems.y) and (player_char.z = systems.z) and (player_char.uid = inventory.characteruid) and (item.uid = inventory.item_ID) and (player_char.currentship = inventory.uid))
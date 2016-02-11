select 
    -(npc.UID) AS uid,
    firstnames.firstname AS firstname,
    surnames.surname AS surname,
    npc.stamina AS stamina,
    npc.intelligence AS intelligence,
    npc.social AS social,
    npc.dexterity AS dexterity,
    npc.leadership AS leadership,
    npc.recuperation AS recuperation,
    npc.exp AS exp,
    40 AS slots,
    1 AS player_account_UID,
    npc.x AS x,
    npc.y AS y,
    npc.z AS z,
    'na' AS sysname,
    npc.sysx AS sysx,
    npc.sysy AS sysy,
    npc.sysz AS sysz,
    npc.hitpoints AS hitpoints,
    npc.faction AS faction,
    npc.firstattackeruid AS firstattackeruid,
    npc.firstattacktime AS firstattacktime,
    npc.lastattackeruid AS lastattackeruid,
    npc.lastattacktime AS lastattacktime,
    npc.expvalue AS expvalue,
    npc.creditvalue AS creditvalue,
    npc.status AS status,
    npc.pitchangle as pitchangle,
    npc.yawangle as yawangle,
    npc.shipname as shipname,
    npc.lastupdate as lastupdate
from
    ((npc
    join firstnames ON ((npc.firstnames_uid = firstnames.uid)))
    join surnames ON ((npc.surnames_uid = surnames.uid))) 
union select 
    playercharsinsystem.uid AS uid,
    playercharsinsystem.firstname AS firstname,
    playercharsinsystem.surname AS surname,
    playercharsinsystem.stamina AS stamina,
    playercharsinsystem.intelligence AS intelligence,
    playercharsinsystem.social AS social,
    playercharsinsystem.dexterity AS dexterity,
    playercharsinsystem.leadership AS leadership,
    playercharsinsystem.recuperation AS recuperation,
    playercharsinsystem.exp AS exp,
    playercharsinsystem.slots AS slots,
    playercharsinsystem.player_account_UID AS player_account_UID,
    playercharsinsystem.x AS x,
    playercharsinsystem.y AS y,
    playercharsinsystem.z AS z,
    playercharsinsystem.sysname AS sysname,
    playercharsinsystem.sysx AS sysx,
    playercharsinsystem.sysy AS sysy,
    playercharsinsystem.sysz AS sysz,
    playercharsinsystem.hitpoints AS hitpoints,
    'player' AS faction,
    playercharsinsystem.firstattackeruid AS firstattackeruid,
    playercharsinsystem.firstattacktime AS firstattacktime,
    playercharsinsystem.lastattackeruid AS lastattackeruid,
    playercharsinsystem.lastattacktime AS lastattacktime,
    playercharsinsystem.expvalue AS expvalue,
    playercharsinsystem.creditvalue AS creditvalue,
    playercharsinsystem.status AS status,
    playercharsinsystem.pitchangle as pitchangle,
    playercharsinsystem.yawangle as yawangle,
    playercharsinsystem.shipname as shipname,
    playercharsinsystem.lastupdate as lastupdate
from
    playercharsinsystem

select 
    missiondefs.uid,
    missiondefs.title,
    missiondefs.text,
    missiondefs.type,
    missiondefs.conditions,
    missiondefs.rewards,
    missiondefs.giver,
    missiondefs.handin,
    missiondefs.prereq,
    missiondefs.completetext,
    charmission.charuid,
    firstname,
    surname,
    faction,
    npc.x,
    npc.y,
    npc.z,
    sysname
from
    charmission,
    missiondefs,
    npc,
    firstnames,
    surnames,
    systemnames
where
    (missiondefs.uid = missionuid) 
    and missiondefs.giver = npc.uid 
    and npc.firstnames_uid = firstnames.uid 
    and npc.surnames_uid = surnames.uid
    and npc.x=systemnames.x
    and npc.y=systemnames.y
    and npc.z=systemnames.z
update medicos set sn_ativo = 1;
alter table medicos modify column SN_ATIVO tinyint not null default 1;

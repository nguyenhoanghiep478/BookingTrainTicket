alter table train add column if not exists train_status varchar(20) default 'ON_STOPPED';

alter table train add column if not exists current_station_id INTEGER REFERENCES station(id)
alter table location
    add primary key (id);

alter table Event
    add location_id uuid,
    add constraint fk_event_location
        foreign key (location_id)
            references location (id);

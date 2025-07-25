create table if not exists agencies
(
    uuid                 varchar(255) not null
        primary key,
    adress_number        varchar(255),
    adresse              varchar(255),
    agency_name          varchar(255),
    city                 varchar(255),
    complementary_adress varchar(255),
    country              varchar(255),
    created_at           timestamp(6),
    deleted_at           timestamp(6),
    email                varchar(255),
    phone_number         varchar(255),
    postal_code          varchar(255),
    updated_at           timestamp(6)
);

alter table agencies
    owner to pguser;

create table if not exists users
(
    uuid                 varchar(255)   not null
        primary key,
    adress_number        varchar(255),
    adresse              varchar(255),
    birth_date           date,
    city                 varchar(255),
    complementary_adress varchar(255),
    country              varchar(255),
    created_at           timestamp(6),
    deleted_at           timestamp(6),
    email                varchar(255)   not null
        constraint uk_6dotkott2kjsp8vw4d0m25fb7
            unique,
    first_name           varchar(255),
    last_connection_at   timestamp(6),
    last_name            varchar(255),
    password             varchar(255)   not null,
    postal_code          varchar(255),
    roles                varchar(255)[] not null,
    striped              varchar(255),
    updated_at           timestamp(6)
);

alter table users
    owner to pguser;

create table if not exists messages
(
    uuid       varchar(255) not null
        primary key,
    content    text         not null,
    created_at timestamp(6),
    deleted_at timestamp(6),
    status     varchar(255)
        constraint messages_status_check
            check ((status)::text = ANY
                   ((ARRAY ['SENT'::character varying, 'READ'::character varying, 'ARCHIVED'::character varying])::text[])),
    updated_at timestamp(6),
    from_uuid  varchar(255)
        constraint fkaqvxbghfs0uwr8wmg28evw2j1
            references users,
    to_uuid    varchar(255)
        constraint fk68xss5pma7r3v1qmav0y0sdws
            references users
);

alter table messages
    owner to pguser;

create table if not exists payments
(
    uuid        varchar(255) not null
        primary key,
    amount      real         not null,
    created_at  timestamp(6),
    currency    varchar(255),
    date_time   timestamp(6),
    deleted_at  timestamp(6),
    status      varchar(255)
        constraint payments_status_check
            check ((status)::text = ANY
                   ((ARRAY ['SUCCEED'::character varying, 'FAILED'::character varying, 'ERROR'::character varying])::text[])),
    stripe_id   varchar(255),
    updated_at  timestamp(6),
    agency_uuid varchar(255)
        constraint fklcwc928fnm32gjyiyb87ev22l
            references agencies,
    user_uuid   varchar(255)
        constraint fkpjnj3lnoxge3ijcoxahsohpi4
            references users
);

alter table payments
    owner to pguser;

create table if not exists vehicle_categories
(
    uuid     varchar(255) not null
        primary key,
    car_code varchar(255) not null,
    category varchar(255)
);

alter table vehicle_categories
    owner to pguser;

create table if not exists vehicles
(
    uuid                   varchar(255) not null
        primary key,
    brand                  varchar(255),
    color                  varchar(255),
    description            varchar(255),
    image_url              varchar(255),
    label                  varchar(255) not null,
    model_ref              varchar(255),
    number_plate           varchar(255),
    seats                  integer      not null,
    status                 varchar(255) not null
        constraint vehicles_status_check
            check ((status)::text = ANY
                   ((ARRAY ['AVAILABLE'::character varying, 'USED'::character varying, 'OFF_SERVICE'::character varying])::text[])),
    agency_owner_uuid      varchar(255)
        constraint fk64a36thveony3nqjry528p9kp
            references agencies,
    vehicule_category_uuid varchar(255)
        constraint fkfbpeyk47dybg9krtx7m2r534q
            references vehicle_categories
);

alter table vehicles
    owner to pguser;

create table if not exists rentals
(
    uuid                   varchar(255) not null
        primary key,
    arrival_time           timestamp(6),
    departure_time         timestamp(6),
    price                  real         not null,
    status                 varchar(255)
        constraint rentals_status_check
            check ((status)::text = ANY
                   ((ARRAY ['ACCEPTED'::character varying, 'REFUSED'::character varying, 'PENDING'::character varying, 'CANCELLED'::character varying])::text[])),
    arrival_agency_uuid    varchar(255) not null
        constraint fk3xtaorjfbp0wf0p1o1tn3ycko
            references agencies,
    chosen_vehicule_uuid   varchar(255)
        constraint fkpk1hddiu07wxc28fs157yohxy
            references vehicles,
    departure_agency_uuid  varchar(255) not null
        constraint fk5nqwwys9lpkhux54jnevog4xx
            references agencies,
    payment_uuid           varchar(255)
        constraint fkcjnl1v87y6q6ljoj4nb7rkttx
            references payments,
    user_uuid              varchar(255) not null
        constraint fk8nery4ducdonxe0uuttm1nota
            references users,
    vehicule_uuid          varchar(255) not null
        constraint fko48wmhg3xtob836ud0j43fci0
            references vehicles,
    vehicule_category_uuid varchar(255)
        constraint fks5wq45ufbi6h8ybhga1uckwqr
            references vehicle_categories
);

alter table rentals
    owner to pguser;


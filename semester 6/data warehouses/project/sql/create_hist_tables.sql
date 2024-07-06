
CREATE TABLE History_Crimes_Weather.HIST_DIM_Weapon_Used (
    weapon_code SMALLINT,
    weapon_description VARCHAR(100),
    start_datetime DATETIME NOT NULL,
    end_datetime DATETIME,
    PRIMARY KEY (weapon_code, start_datetime)
);


CREATE TABLE History_Crimes_Weather.HIST_DIM_Premise (
    premise_id SMALLINT,
    premise_description VARCHAR(100),
    start_datetime DATETIME NOT NULL,
    end_datetime DATETIME,
    PRIMARY KEY (premise_id, start_datetime)
);


CREATE TABLE History_Crimes_Weather.HIST_DIM_Area (
    area_id SMALLINT,
    area_name VARCHAR(100),
    start_datetime DATETIME NOT NULL,
    end_datetime DATETIME,
    PRIMARY KEY (area_id, start_datetime)
);


CREATE TABLE History_Crimes_Weather.HIST_DIM_Weather_Condition (
    weather_condition_id SMALLINT,
    weather_condition VARCHAR(100),
    start_datetime DATETIME NOT NULL,
    end_datetime DATETIME,
    PRIMARY KEY (weather_condition_id, start_datetime)
);

CREATE TABLE History_Crimes_Weather.HIST_DIM_Crime (
    crime_code SMALLINT,
    crime_description VARCHAR(100),
    start_datetime DATETIME NOT NULL,
    end_datetime DATETIME,
    PRIMARY KEY (crime_code, start_datetime)
);

CREATE TABLE History_Crimes_Weather.HIST_DIM_Descent (
    descent CHAR,
    descent_description VARCHAR(100),
    start_datetime DATETIME NOT NULL,
    end_datetime DATETIME,
    PRIMARY KEY (descent, start_datetime)
);

CREATE TABLE History_Crimes_Weather.HIST_DIM_Sex (
    sex CHAR,
    sex_description VARCHAR(100),
    start_datetime DATETIME NOT NULL,
    end_datetime DATETIME,
    PRIMARY KEY (sex, start_datetime)
);

CREATE TABLE History_Crimes_Weather.HIST_DIM_Sunrise_Sunset (
    id CHAR,
    name VARCHAR(100),
    start_datetime DATETIME NOT NULL,
    end_datetime DATETIME,
    PRIMARY KEY (id, start_datetime)
);

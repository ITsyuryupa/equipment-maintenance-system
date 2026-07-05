CREATE SCHEMA IF NOT EXISTS equipment;


CREATE TABLE IF NOT EXISTS equipment.equipment_type
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    manufacturer VARCHAR(255),
    maintenance_interval_days INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT check_maintenance_interval_days
    CHECK (maintenance_interval_days > 0)

);

CREATE UNIQUE INDEX IF NOT EXISTS uk_equipment_type_name_not_deleted
    ON equipment.equipment_type (name)
    WHERE deleted = FALSE;



CREATE TABLE IF NOT EXISTS equipment.equipment
(
    id BIGSERIAL PRIMARY KEY,
    equipment_type_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    inventory_number VARCHAR(255) NOT NULL,
    location VARCHAR(255),
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    decommissioned_at TIMESTAMP,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_equipment_type
    FOREIGN KEY (equipment_type_id)
    REFERENCES equipment.equipment_type(id)
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_equipment_inventory_number_not_deleted
    ON equipment.equipment (inventory_number)
    WHERE deleted = FALSE;
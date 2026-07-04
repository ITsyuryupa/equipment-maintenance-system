CREATE SCHEMA IF NOT EXISTS maintenance;

CREATE TABLE IF NOT EXISTS maintenance.service_request
(
    id BIGSERIAL PRIMARY KEY,
    equipment_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'NEW',
    priority VARCHAR(50) NOT NULL DEFAULT 'MEDIUM',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT check_service_request_status
        CHECK (
            status IN
            (
             'NEW',
             'IN_PROGRESS',
             'DONE',
             'CANCELLED'
            )
        ),

    CONSTRAINT check_service_request_priority
        CHECK (
            priority IN
            (
             'LOW',
             'MEDIUM',
             'HIGH'
                )
            )
);
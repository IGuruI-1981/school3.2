-- liquibase formatted sql

-- changeset vikhtiyarov:1
CREATE INDEX student_name_index
ON student (name);

-- changeset vikhtiyarov:2
CREATE INDEX faculty_cn_index
ON faculty (color,name);



#!/bin/bash

DB_NAME="learningSquare.db"

if ! command -v sqlite3 &> /dev/null; then
    echo "SQLite3 is not installed. Please install SQLite3 and try again."
    exit 1
fi

if [ -f "$DB_NAME" ]; then
    echo "Removing existing database: $DB_NAME"
    rm "$DB_NAME"
fi

echo "Running learningSquare.sql to create schema..."
if sqlite3 "$DB_NAME" < /app/database/learningSquare.sql; then
    echo "Schema created successfully."
else
    echo "Failed to create schema. Exiting."
    exit 1
fi

echo "Running insertData.sql to insert data..."
if sqlite3 "$DB_NAME" < /app/database/insertData.sql; then
    echo "Data inserted successfully."
else
    echo "Failed to insert data. Exiting."
    exit 1
fi

echo "Database setup completed successfully: $DB_NAME"

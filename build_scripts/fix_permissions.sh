#!/usr/bin/env bash

# This script needs to be run from the root directory (atm-cash-dispenser)

# Stop on failure
set -e

# Change permissions post build to ensure the local user can execute / delete files in target folder
# ('target' directory can be cleaned)
if [[ -d "$(pwd)/target" ]]; then
    current_user_id=$(id -u)
    echo "Changing owner on $(pwd)/target to $current_user_id"
    docker run --rm -v "$(pwd)":/usr/src/mymaven -w /usr/src/mymaven maven:3.6.3-amazoncorretto-11 sh -c "find target ! -user $current_user_id -exec chown $current_user_id {} \;"
    if [[ $? -ne 0 ]]; then
        echo "Failed to reset the permissions on the build folder. This may stop cleans of the workspace in future builds".
    fi
fi

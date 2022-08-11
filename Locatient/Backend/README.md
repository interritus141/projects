# Deploying the Backend

## Requirements

- nodejs v16 or above
- npm v8 or above

## Creating a mongodb database

First, you will need create an instance of MongoDB. You can host this locally on a machine, on a server, or on MongoDB Atlas. Depending on your choice, the following links contains guides on how to deploy an instance.

- Locally: https://www.mongodb.com/docs/guides/server/install/
- Atlas: https://www.mongodb.com/docs/atlas/getting-started/
- GCP: https://cloud.google.com/mongodb
- AWS: https://aws.amazon.com/quickstart/architecture/mongodb/
- Azure: https://www.mongodb.com/mongodb-on-azure

## Configurations

Create a file in the root directory with the name ".env". Make sure that .env is the file ending. Inside the .env folder, paste the following contents:

> MONGODB_CONNECTION=""
> MONGODB_UNITS_CONNECTION=""
> PASSPORT_KEY=""

Set the MONGODB_CONNECTION to your production collection you wish to use in the MongoDB instance created in the previous step and set MONGODB_UNITS_CONNECTION the the unit testing collection. They can both be empty to start.

Next, inside "src/config/bcrypt.config.js", "src/config/logging.config.js", and "src/config/server.config.js", you can configure that desired hashing salt rounds, logging data expiry time (s), and login token expiry time respectively.

## Execute and deploy the application

In the project directory, you can run:

### `npm install`

Installs the required node_modules for the app

### `npm run start`

Launches the API on port 8080 (default, can be changed in server.config.js).

### `npm run live`

Launches an live server which watches the codebase

### `jest`

Executes the unittests

# ro-routing-datasource-sample (Read Write Routing Datasource Example)
This sample is implementation in spring to route database  based on read only and write operations runtime.

## Prerequisites
- Docker
- Java 16

## Purpose
Purpose of the sample to achieve the separate databases calls based on the type of the transactions. If the transaction is read only, it must be routed to readonly database replica. If it is write operation, it must be routed to write copy of the database, which will be master database.

In order to achieve that, it is important to have a setup of databases in the replication mode where one is master and another is read replica. As a part of this setup, I have setup docker-compose file that uses PostgreSQL database with master and read replica docker containers. The setup can be found in the `docker` directory. `pg_hba.conf` file is externalised if some access related changes to be made.

The image used here is from [bitnami](https://bitnami.com/stack/postgresql/containers). Find here the [GitHub page](https://github.com/bitnami/bitnami-docker-postgresql#how-to-use-this-image) for more configuration information.

Here are some credentials required for client connections:

Database    |   Host    |   Port    |       User    |   Password    |
------------|-----------|-----------|---------------|---------------|
master      |localhost  |   5432    | postgresql    |   password    |
slave       |localhost  |   5433    | postgresql    |   password    |

The above information can be changed from `docker-compose.yaml` file in `docker` directory.

### Run Docker
To run the docker, use following command:
```shell
$ docker-compose up -d --build
```

use following command for graceful shutdown:
```shell
$ docker-compose down
```

## TODO
- Spring Implementation description
- Running the application
- Postman Collection
- curl command inclusion
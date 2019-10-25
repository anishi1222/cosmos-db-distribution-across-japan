# Cosmos DB distribution demo (across Jaapn)

- Demo code for distribution

## How to use this?

- maven clean package
- java -jar cosmos.globaldistributions.demo-1.0-SNAPSHOT-jar-with-dependencies.jar "configuration file"

## Configuration file

- endpoint: URL for accessing Cosmos DB instance
- key: Cosmos DB write/read key (either primary or secondary key)
- multiWrite: If multi-master configuration is used, this attribute should be true.
- testMode: READ or WRITE
- targetRegion: Array of region name.

```json
{
  "partitionKey": "/myPartitionKey",
  "partitionKeyValue": "1001",
  "database": "demo",
  "container": "customers",

  "testName": "東日本（地理冗長、マルチマスタなし・Strong）",
  "testDescription": "100件のデータを登録し、それを東日本リージョンから読み取る",
  "testMode": "READ",
  "multiWrite": false,

  "endpoint": "Cosmos DB URL",
  "key": "Cosmos DB Connection Key",
  "targetRegion": ["Japan East"]
}
```

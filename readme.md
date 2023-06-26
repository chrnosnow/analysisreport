# Water samples analysis reports generation project

This API was created for learning purposes of Spring Boot and Java. 
The API uses an in-memory database managed by the H2 Database Engine.
Start the API by running the AnalysisReportApplication file.
Among the options to do:
 - search analysis reports by the following filters: issue date, client id, contract id, analyst id;
 - search for analysis results by: sample id, analysis report id, water quality indicator name;
 - search by partial name of contact person or client;
 - search water sample by client or contract id or by receiving date between;
 - partially update an entity's details.

   
**Notes**: 
1. An analysis report can have only a part of the quality indicators requested initially. 
2. Multiple (maybe not more than 2) analysis reports can be issued for the same sample; e.g., due to various errors on the first report that was already given to the client.

## Database schema

```plantuml
@startuml
' hide the spot
' hide circle

' avoid problems with angled crows feet
skinparam linetype ortho

entity "CLIENTS" as client {
  *id : number <<generated>>
  --
  *name : text
  address: text
}

entity "CLIENT_CONTACT_PERSONS" as contact_person {
  *id : number <<generated>>
  --
  *client_id : number <<FK>>
  *first_name: text
  *last_name: text
  position : text
  email: text
  phone: text
}

entity "CONTRACTS" as contract {
  *id : number <<generated>>
  --
  *client_id : number <<FK>>
  number: text
  date: date
  type : enum
}

entity "WATER_QUALITY_INDICATORS" as quality_indicator{
  *id : number <<generated>>
  --
  *name : text
  measurement_unit: text
  accredited : enum
  method_reference : text
}

entity "SAMPLES" as sample{
  *id : number <<generated>>
  --
  *sample_code: text
  client_id: number <<FK>>
  contract_id: number <<FK>>
  water_sample_type: enum
  sampling_source : text
  sampling_date : date
  sampling_time : time
  receiving_date : date
  receiving_time : time
}

entity "ADMINISTRATORS" as admin {
  *id : number <<generated>>
  --
  *first_name: text
  *last_name: text
  email1: text
  email2: text
  phone: text
  description : text
}

entity "ANALYSTS" as analyst {
  *id : number <<generated>>
  --
  *first_name: text
  *last_name: text
  email1: text
  email2: text
  phone: text
  description : text
}

entity "REPORTS" as report {
  *id : number <<generated>>
  --
  *report_number: text
  report_issue_date: date
  *water_sample_id: number <<FK>>
  analyst_id: number <<FK>>
}

entity "RESULTS" as result {
  *id : number <<generated>>
  --
  *sample_id: number <<FK>>
  report_id: number <<FK>>
  *quality_indicator_id: number <<FK>>
  result: text
}

contact_person }|..|| client
contract }|..|| client
sample ||..|| client
sample ||..|| contract
sample ||..|| report
analyst ||..|| report
result }|..|| sample
result }|..|| report
result ||..|| quality_indicator

@enduml
```
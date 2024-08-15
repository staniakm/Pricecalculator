## How to run

Run application with command
`.\gradlew bootRun --args='--spring.profiles.active=local'`

What is important, `local` profile is needed to load data into in memory database

There are 4 products in database with IDs

- `794c5988-4bbb-4f6b-9988-0d40e15dd8f6`
- `60e01266-a6de-484e-b3c0-325ae19575e6`
- `6ba4b7de-fb5c-4101-a02b-9d4b6dc79f19`
- `161fda06-205d-45d2-8234-04cf654b43b2"`

Products details are available in class `DataInitializer`.

Discounts can be configured through `application.yaml` file.

Some assumptions have been made during implementation. Some of them are marked in code and all of them should be
mentioned here. Most of them should be consulted with business to confirm if they are ok.

Tech assumptions:

- this service is not selling service, it is only used to calculate prices of products and amount
- to have data about products there should be some events sourcing or process to update products available in DB. It is
  out of scope of this task
- in memory "database" (hashmap) was used to simplify process. With more time, probably postgressDb and testcontainers
  would be usefully here.

Business

- client will decide what type of discount he wants to use
- in case of missing discount type we will return error
- in case of missing product we will return proper status for that product
- in case of not enough items we will make calculation for max available and proper status will be returned
- for amount discount type we assume that lowest possible price is 0.01
- price cannot be zero and lower

Things that was skipped during implementation:

- error handling and tests
- more detailed tests
- DB and testcontainers configuration
- logs were skipped
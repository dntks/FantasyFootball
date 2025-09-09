Fantasy football app

Simple app to simulate group phase of a football tournament.

Architecture and design decisions:
The app follows the common Data - Domain - UI layer architecture, using Hilt for dependency injection.
The data layer has access to the Database, and exposes the data mostly through Flows through the repository.
The domain layer is a bit thin, and skipped at some parts due to the lack of time, but the statistics creation 
and match generation is in the use cases.
The UI layer consists of compose methods (including nav graph), and viewmodels which are communicating with the 
layers below.

My approach to the task was to focus on the important functionality, which is to be able to generate and show
the data of the matches. During development I've set up some convenient views to check the database queries'
functionality, and most of my time went with setting up a database which is scalable.

Improvements, which I had no time to develop:
- More separating the layers, not having references of entities outside of the data layer, having a clearer boundary between the layers by defining separate model for the domain layer and UI layer.
- add unit tests for classes with logic, add integration tests with setup database, and ui tests to check general usability and edge cases.
- As it doesn't have Android specific modules, I planned to make it in kotlin multiplatform, as I had smooth experience before.
- I thought it'd be cool to add AI functionality to request a game summary based on the teams, players and match outcome. I already tested it a bit, maybe I'll create a pet project from the idea.

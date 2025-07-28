# gotchas.md

- The app includes JPA entities and repositories for demonstration/upgrade only. If a datasource is configured but not available, the app may fail to start. This code is not used in the main logic. 
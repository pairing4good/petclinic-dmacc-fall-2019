# Spring PetClinic Sample Application [![Build Status](https://travis-ci.org/pairing4good/petclinic.png?branch=master)](https://travis-ci.org/pairing4good/petclinic/)

## Running petclinic locally
```
	git clone https://github.com/pairing4good/petclinic.git
	cd petclinic
	./gradlew bootRun
```

You can then access petclinic here: [http://localhost:8080/](http://localhost:8080/)


## Connecting to the Database
1. http://localhost:8080/h2-console
1. Saved Settings = `Genertic H2 (Embedded)`
1. Setting Name = `Genertic H2 (Embedded)`
1. Driver Class = `org.h2.Driver`
1. JDBC URL = `jdbc:h2:mem:testdb`
1. User Name = `sa`
1. Password = [leave blank]
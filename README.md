1.	I have used RESTAssured (BDD based framework) for creating the automation tests for the Movies API provided in the challenge.
2.	I have created a modular framework where all the core logic is written in MoviesInformationHelper.java, ReusableMethods.java, PayLoad.java.
3.	All the tests written will just call the methods in the above classes.
4.	I am using TestNG (unit test framework) to manage, configure and run the tests.
5.	I have used DataProvider to automate the tests which required to tested with many sets of data e.g.:  MovieTypeOtherThanBatmanTest, checkNoOfRecordsByCountTest etc.

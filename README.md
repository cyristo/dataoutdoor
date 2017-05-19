# Simple data externalization engine for testing or other usage. 

## BDD use case : instead of having data inside the test scenario, use Data Outdoor to reference an external dataset. 

Without Data Outdoor :
* Given the country name is France
* And the country population is 64768389
* And the country area is 547030
* And the country GDP is 2.739 Trillion
* When I do something with that country
* Then I should get this result

With Data Outdoor:
* Given the dataset COUNTRY:France
* When I do something with that country
* Then I should get this result
